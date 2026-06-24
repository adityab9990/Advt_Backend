package com.project.advtRecords.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.project.advtRecords.entity.Advertisement;
import com.project.advtRecords.repository.AdvtRepository;

@Service
public class AdvertisementService {

    @Autowired
    private AdvtRepository advertisementRepository;

    @Autowired
    private FileStorageService fileService; // Added this missing dependency

    // 1. Search
    public List<Advertisement> searchAdvertisements(String advtNo, String advtName) {
        return advertisementRepository.searchAdvertisements(advtNo, advtName);
    }

    // 2. Save / Add
    public void saveAdvertisement(Advertisement ad, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String filename = fileService.saveFile(file);
            ad.setPdfPath(filename);
        }
        advertisementRepository.save(ad);
    }

    // 3. Consolidated Update Method
    public Advertisement updateAdvertisement(Long id, Advertisement updatedData) {
        Advertisement ad = advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found with id: " + id));

        // Update basic fields
        ad.setAdvtNo(updatedData.getAdvtNo());
        ad.setAdvtName(updatedData.getAdvtName());
        ad.setDepartment(updatedData.getDepartment());
        ad.setCategory(updatedData.getCategory());
        ad.setSize(updatedData.getSize());
        ad.setBillRupees(updatedData.getBillRupees());
        ad.setPublishingDate(updatedData.getPublishingDate());
        
        // Update payment status (handles both full updates and status toggles)
        if (updatedData.getIsPaid() != null) {
            ad.setIsPaid(updatedData.getIsPaid());
        }
        
        return advertisementRepository.save(ad);
    }
}
