package com.project.advtRecords.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    
    private final Path root = Paths.get("uploads");

    public FileStorageService() {
        try {
            // Ensure the directory exists
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!", e);
        }
    }

    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Cannot save an empty file.");
        }
        
        try {
            // Generate a unique filename using UUID
            String originalName = file.getOriginalFilename();
            String filename = UUID.randomUUID().toString() + "_" + (originalName != null ? originalName : "file");
            
            // Resolve the path and copy the file
            Files.copy(file.getInputStream(), this.root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            
            return filename; 
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage(), e);
        }
    }
}