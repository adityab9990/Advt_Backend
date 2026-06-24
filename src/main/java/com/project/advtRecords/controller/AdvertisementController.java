package com.project.advtRecords.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.advtRecords.entity.Advertisement;
import com.project.advtRecords.service.AdvertisementService;

@RestController
@RequestMapping("/api/advertisements")


public class AdvertisementController {

    // Inject the SERVICE, not the Repository
    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/search")
    public List<Advertisement> search(
            @RequestParam(required = false) String advtNo,
            @RequestParam(required = false) String advtName) {
        // Call the service
        return advertisementService.searchAdvertisements(advtNo, advtName);

    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAdvertisement(
            @RequestParam("file") MultipartFile file,
            @RequestParam("advtNo") String advtNo,
            @RequestParam("advtName") String advtName,
            @RequestParam("department") String department,
            @RequestParam("category") String category,
            @RequestParam("size") String size,
            @RequestParam("billRupees") Double billRupees,
            @RequestParam("publishingDate") String publishingDate) {

        // Create the entity
        Advertisement ad = new Advertisement();
        ad.setAdvtNo(advtNo);
        ad.setAdvtName(advtName);
        ad.setDepartment(department);
        ad.setCategory(Advertisement.Category.valueOf(category));
        ad.setSize(size);
        ad.setBillRupees(billRupees);
        ad.setPublishingDate(LocalDate.parse(publishingDate));

        // Call the service (ensure this matches the method signature we just updated)
        advertisementService.saveAdvertisement(ad, file);
        
        return ResponseEntity.ok("Advertisement added successfully!");
    }

    @PutMapping("/{id}")
    public Advertisement updateAd(@PathVariable Long id, @RequestBody Advertisement updatedAd) {
        // Call the service
        return advertisementService.updateAdvertisement(id, updatedAd);
    }

  
    
}
