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
        // Create a unique temp directory per execution
        Path tempDir = Files.createTempDirectory("sandbox_");

        try {
            String fileName = file.getOriginalFilename();
            Path filePath = tempDir.resolve(fileName);
            file.transferTo(filePath);

            String[] command = getSafeCommand(language, filePath);
            ProcessBuilder pb = new ProcessBuilder(command)
                    .directory(tempDir.toFile());

            // Drop privileges (works only if container has 'nobody' user)
            pb.environment().put("USER", "nobody");

            Process process = pb.start();

            // Send input to the program
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                if (input != null && !input.isBlank()) {
                    writer.write(input);
                    writer.flush();
                }
            }

            // Hard kill if it runs too long
            boolean finished;
            try {
                finished = process.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                process.destroyForcibly();
                Thread.currentThread().interrupt();
                return "Execution interrupted.";
            }

            if (!finished) {
                process.destroyForcibly();
                return "Execution timed out after " + TIMEOUT_SECONDS + " seconds.";
            }

            String output = new String(process.getInputStream().readAllBytes());
            String errors = new String(process.getErrorStream().readAllBytes());

            if (!errors.isBlank()) {
                return "Compilation/Runtime Error:\n" + errors;
            }
            return output;

        } finally {
            // Clean up sandbox directory
            try {
                Files.walk(tempDir)
                        .sorted((a, b) -> b.compareTo(a)) // delete files before dirs
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (IOException ignored) {}
                        });
            } catch (IOException ignored) {}
        }
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
