package com.example.easyclean.service.service;

import com.example.easyclean.dto.input.CleaningServiceDto;
import com.example.easyclean.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

@Service
public interface CleaningServiceService {
    ApiResponse getAllCleaningServices();
    ApiResponse getCleaningServiceById(Long id);
   // ApiResponse getCleaningServiceByName(String serviceName);
    ApiResponse createCleaningService(CleaningServiceDto cleaningService);
    ApiResponse updateCleaningService(Long id, CleaningServiceDto updatedCleaningService);
    ApiResponse deleteCleaningService(Long id);
}
