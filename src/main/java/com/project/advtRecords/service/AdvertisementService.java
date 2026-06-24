package com.project.advtRecords.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.advtRecords.entity.Advertisement;
import com.project.advtRecords.repository.AdvtRepository;

// @Service tells Spring Boot "This is where the business logic lives"
@Service
public class AdvertisementService {

    @Autowired
    private AdvtRepository advertisementRepository;

    @Autowired
    private FileStorageService fileService;

    // 1. Search Method
    public List<Advertisement> searchAdvertisements(String advtNo, String advtName) {
        return advertisementRepository.searchAdvertisements(advtNo, advtName);
    }

   
    // 2. Save / Add Method
    public void saveAdvertisement(Advertisement ad, MultipartFile file) {
        // Only attempt to save a file if it's provided
        if (file != null && !file.isEmpty()) {
            String filename = fileService.saveFile(file);
            ad.setPdfPath(filename); // Store filename in DB
        }
        advertisementRepository.save(ad);
    }

    // 3. Update Method (Used for your Editing and Paid/Pending button)
    public Advertisement updateAdvertisement(Long id, Advertisement updatedData) {
        Optional<Advertisement> existingAd = advertisementRepository.findById(id);
        
        if (existingAd.isPresent()) {
            Advertisement ad = existingAd.get();
            // Update the fields
            ad.setAdvtNo(updatedData.getAdvtNo());
            ad.setAdvtName(updatedData.getAdvtName());
            ad.setDepartment(updatedData.getDepartment());
            ad.setCategory(updatedData.getCategory());
            ad.setSize(updatedData.getSize());
            ad.setBillRupees(updatedData.getBillRupees());
            ad.setPublishingDate(updatedData.getPublishingDate());
            
            // Update the payment status!
            ad.setIsPaid(updatedData.getIsPaid()); 
            
            return advertisementRepository.save(ad);
        } else {
            throw new RuntimeException("Advertisement not found with id: " + id);
        }
    }
    
 // Inside AdvertisementService.java
    public Advertisement updateAdvertisement1(Long id, Advertisement updatedDetails) {
        Advertisement ad = advertisementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        
        // Update the field
        ad.setIsPaid(updatedDetails.getIsPaid());
        
        return advertisementRepository.save(ad);
    }
}