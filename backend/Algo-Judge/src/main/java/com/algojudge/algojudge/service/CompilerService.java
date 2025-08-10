package com.algojudge.algojudge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

@Service
public class CompilerService {

    private static final int TIME_LIMIT_SECONDS = 2;     // Max execution time
    private static final int MEMORY_LIMIT_KB = 256000;   // ~256MB
    private static final int FILE_SIZE_LIMIT_KB = 100000; // ~100MB

    public String compilerAndRun(MultipartFile file, String language, String input) throws IOException, InterruptedException {
        // Create isolated temp directory for this run
        Path tempDir = Files.createTempDirectory("algojudge_");
        try {
            // Save uploaded file
            String fileName = file.getOriginalFilename();
            Path filePath = tempDir.resolve(fileName);
            file.transferTo(filePath);

            // Prepare safe command
            String[] command = getSafeCommand(language, fileName);

            ProcessBuilder pb = new ProcessBuilder(command)
                    .directory(tempDir.toFile())
                    .redirectErrorStream(true);

            Process process = pb.start();

            // Feed input to program
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                if (input != null && !input.isBlank()) {
                    writer.write(input);
                    writer.flush();
                }
            }

            // Wait with timeout
            boolean finished = process.waitFor(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return "Error: Time Limit Exceeded";
            }

            // Collect output
            String output = new String(process.getInputStream().readAllBytes());
            return output.isBlank() ? "No Output" : output;

        } finally {
            // Cleanup temp dir
            deleteDirectory(tempDir);
        }
    }

    private String[] getSafeCommand(String language, String fileName) {
        // The pre-command enforces limits and isolation:
        // - run as nobody:     sudo -u nobody
        // - disable network:   unshare -n
        // - CPU/mem/file limit: ulimit
        String prefix = String.format(
                "sudo -u nobody unshare -n bash -c 'ulimit -t %d -v %d -f %d && ",
                TIME_LIMIT_SECONDS, MEMORY_LIMIT_KB, FILE_SIZE_LIMIT_KB
        );

        String suffix = "'";

        return switch (language) {
            case "python" ->
                    new String[]{"bash", "-c", prefix + "python3 " + fileName + suffix};
            case "java" -> {
                String baseName = fileName.substring(0, fileName.lastIndexOf("."));
                yield new String[]{"bash", "-c", prefix + "javac " + fileName + " && java -cp . " + baseName + suffix};
            }
            case "cpp" ->
                    new String[]{"bash", "-c", prefix + "g++ " + fileName + " -o out && ./out" + suffix};
            default -> throw new IllegalArgumentException("Unsupported Language");
        };
    }

    private void deleteDirectory(Path dir) throws IOException {
        if (Files.exists(dir)) {
            Files.walk(dir)
                    .sorted((a, b) -> b.compareTo(a)) // delete children first
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
