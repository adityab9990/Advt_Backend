package com.project.advtRecords.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.advtRecords.entity.Advertisement;
import com.project.advtRecords.repository.AdvtRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class AdvertisementService {

    @Autowired
    private AdvtRepository repository;

    @Autowired
    private FileStorageService fileStorageService; // Inject your file storage logic

    // Handles both metadata and file saving
    public void save(Advertisement advt, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String path = fileStorageService.saveFile(file);
            advt.setPdfPath(path);
        }
        repository.save(advt);
    }

    public List<Advertisement> searchAdvertisements(String advtNo, String department, String category) {
        return repository.findAll((Specification<Advertisement>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (advtNo != null && !advtNo.isEmpty()) 
                predicates.add(cb.like(root.get("advtNo"), "%" + advtNo + "%"));
            
            if (department != null && !department.isEmpty()) 
                predicates.add(cb.equal(root.get("department"), department));
            
            if (category != null && !category.isEmpty()) 
                predicates.add(cb.equal(root.get("category"), Advertisement.Category.valueOf(category)));
            
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

	public void save(Advertisement advt) {
		// TODO Auto-generated method stub
		
	}
}