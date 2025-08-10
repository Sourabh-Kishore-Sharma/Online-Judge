package com.algojudge.algojudge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CompilerService {

    // Max execution time in seconds
    private static final int TIMEOUT_SECONDS = 5;

    public String compilerAndRun(MultipartFile file, String language, String input) throws IOException {
        Path tempDir = Paths.get("tmp");
        Files.createDirectories(tempDir);

        String fileName = file.getOriginalFilename();
        Path filePath = tempDir.resolve(fileName);
        file.transferTo(filePath);

        String[] command = getSafeCommand(language, filePath);
        ProcessBuilder pb = new ProcessBuilder(command)
                .directory(tempDir.toFile());

        // Drop privileges to "nobody" user
        pb.environment().put("USER", "nobody");

        Process process = pb.start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(input);
            writer.flush();
        }

        String output = new String(process.getInputStream().readAllBytes());
        String errors = new String(process.getErrorStream().readAllBytes());

        if (!errors.isBlank()) {
            return "Compilation/Runtime Error:\n" + errors;
        }
        return output;
    }

    private String[] getSafeCommand(String language, Path filePath) {
        String fileName = filePath.getFileName().toString();
        String baseName = fileName.substring(0, fileName.lastIndexOf("."));

        return switch (language) {
            case "python" -> new String[]{
                    "bash", "-c",
                    "timeout " + TIMEOUT_SECONDS + "s python3 " + fileName
            };
            case "java" -> new String[]{
                    "bash", "-c",
                    "javac " + fileName + " && timeout " + TIMEOUT_SECONDS + "s java -cp . " + baseName
            };
            case "cpp" -> new String[]{
                    "bash", "-c",
                    "g++ " + fileName + " -o out && timeout " + TIMEOUT_SECONDS + "s ./out"
            };
            default -> throw new IllegalArgumentException("Unsupported Language");
        };
    }

}
