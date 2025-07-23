package com.algojudge.algojudge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CompilerService {
    public String compilerAndRun(MultipartFile file, String language, String input) throws IOException {
        Path tempDir = Paths.get("tmp");
        Files.createDirectories(tempDir);

        String fileName = file.getOriginalFilename();
        Path filePath = tempDir.resolve(fileName);
        file.transferTo(filePath);

        String[] command = getCompileCommand(language, filePath);
        ProcessBuilder pb = new ProcessBuilder(command)
                .directory(tempDir.toFile());
        Process process = pb.start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(input);
        }

        String output = new String(process.getInputStream().readAllBytes());
        String errors = new String(process.getErrorStream().readAllBytes());

        if (!errors.isBlank()) {
            return "Compilation Error:\n" + errors;
        }
        return output;
    }

    private String[] getCompileCommand(String language, Path filePath){
        String fileName = filePath.getFileName().toString();
        String baseName = fileName.substring(0, fileName.lastIndexOf("."));

        // bach -c is required when channing multiple commands like in c++ and java
        String[] command = switch (language) {
            case "python" -> new String[]{"python3", fileName};
            case "java" -> new String[]{"bash", "-c", "javac " + fileName + " && java -cp . " + baseName};
            case "cpp" -> new String[]{"bash", "-c", "g++ " + fileName + " -o out && ./out"};
            default -> throw new IllegalArgumentException("Unsupported Language");
        };
        return command;
    }

    private String getClassName(String filePath){
        return filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."));
    }

    private String getExtension(String language){
        return switch(language){
            case "python" -> "py";
            case "java" -> "java";
            case "cpp" -> "cpp";
            default -> "txt";
        };
    }
}
