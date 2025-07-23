package com.algojudge.algojudge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

@Service
public class CompilerService {
    public String compilerAndRun(MultipartFile file, String language, String input) throws IOException {
        File tempFile = File.createTempFile("submission","."+getExtension(language));
        file.transferTo(tempFile);

        ProcessBuilder pb = new ProcessBuilder(getCompileCommand(language, tempFile.getAbsolutePath()));
        Process process = pb.start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(input);
        }

        String output = new String(process.getInputStream().readAllBytes());
        String errors = new String(process.getErrorStream().readAllBytes());

        // Cleanup
        tempFile.delete();

        if (!errors.isBlank()) {
            return "Compilation Error:\n" + errors;
        }
        return output;
    }

    private String[] getCompileCommand(String language, String path){
        // bach -c is required when channing multiple commands like in c++ and java
        return switch (language) {
            case "python" -> new String[]{"python3", path};
            case "java" -> new String[]{"bash", "-c", "javac " + path + " && java " + getClassName(path)};
            case "cpp" -> new String[]{"bash", "-c", "g++ " + path + " -o out && ./out"};
            default -> throw new IllegalArgumentException("Unsupported Language");
        };
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
