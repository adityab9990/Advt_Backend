package com.project.advtRecords.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.advtRecords.entity.Advertisement;
import com.project.advtRecords.repository.AdvtRepository;
import com.project.advtRecords.service.AdvertisementService;

@RestController
@RequestMapping("/api/advertisements")
@CrossOrigin(origins = "*") 
public class AdvertisementController {

    @Autowired
    private AdvertisementService service;
   
    @Autowired
    private AdvtRepository advtRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file, 
            @ModelAttribute Advertisement advt) {
        
        try {
            service.save(advt, file);
            return ResponseEntity.ok("Advertisement added successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Advertisement>> searchAds(
        @RequestParam(required = false) String advtNo,
        @RequestParam(required = false) String advtName 
    ) {
        List<Advertisement> results = advtRepository.searchAdvertisements(advtNo, advtName);
        return ResponseEntity.ok(results);
    }

    // NEW: The Update Endpoint!
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdvertisement(
            @PathVariable Long id, 
            @RequestBody Advertisement updatedAdvt) {
        
        try {
            // 1. Find the existing advertisement in the database
            Advertisement existingAdvt = advtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found with id: " + id));

            // 2. Overwrite the old data with the newly typed data from React
            existingAdvt.setAdvtNo(updatedAdvt.getAdvtNo());
            existingAdvt.setAdvtName(updatedAdvt.getAdvtName());
            existingAdvt.setDepartment(updatedAdvt.getDepartment());
            existingAdvt.setCategory(updatedAdvt.getCategory());
            existingAdvt.setSize(updatedAdvt.getSize());
            existingAdvt.setBillRupees(updatedAdvt.getBillRupees());
            existingAdvt.setPublishingDate(updatedAdvt.getPublishingDate());
            
            // Note: We leave the PDF path alone so they don't accidentally delete the file!

            // 3. Save the changes back to MySQL
            advtRepository.save(existingAdvt);
            
            return ResponseEntity.ok("Advertisement updated successfully");
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating: " + e.getMessage());
        }
    }
}