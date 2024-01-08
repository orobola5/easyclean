package com.example.easyclean.service.serviceImpl;
import com.example.easyclean.dto.input.CleaningServiceDto;
import com.example.easyclean.dto.response.ApiResponse;
import com.example.easyclean.dto.response.DefaultResponses;
import com.example.easyclean.model.CleaningService;
import com.example.easyclean.repository.CleaningServiceRepository;
import com.example.easyclean.service.service.CleaningServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CleaningServiceImpl implements CleaningServiceService {

    private final CleaningServiceRepository cleaningServiceRepository;


    @Autowired
    public CleaningServiceImpl(CleaningServiceRepository cleaningServiceRepository) {
        this.cleaningServiceRepository = cleaningServiceRepository;
    }

    @Override
    public ApiResponse getAllCleaningServices() {
        List<CleaningService> cleaningService= cleaningServiceRepository.findAll();
        return DefaultResponses.response200("Successfully Fetched All CleaningService",cleaningService);
    }

    @Override
    public ApiResponse getCleaningServiceById(Long id) {
        Optional<CleaningService> optionalCleaningService = cleaningServiceRepository.findById(id);
        return DefaultResponses.response200("Successfully Fetched CleaningService",optionalCleaningService.orElseThrow(null));
    }

//    @Override
//    public ApiResponse getCleaningServiceByName(String serviceName) {
//        Optional<CleaningService> optionalCleaningService = cleaningServiceRepository.findCleaningServiceByName(serviceName);
//        return DefaultResponses.response200("Successfully Fetched CleaningService",optionalCleaningService.orElseThrow(null));
//    }


    @Override
    public ApiResponse createCleaningService(CleaningServiceDto cleaningServiceDto) {
        CleaningService cleaningService =new CleaningService();
        cleaningService.setServiceName(cleaningServiceDto.getServiceName());
        cleaningService.setAvailability(cleaningServiceDto.getAvailability());
        cleaningService.setDescription(cleaningServiceDto.getDescription());
        cleaningService.setDuration(cleaningServiceDto.getDuration());
        cleaningService.setPrice(cleaningServiceDto.getPrice());
        cleaningServiceRepository.save(cleaningService);
        return DefaultResponses.response200("Congratulations "+cleaningService.getServiceName()+" CleaningService is created",cleaningService);
    }

    @Override
    public ApiResponse updateCleaningService(Long id, CleaningServiceDto updatedCleaningService) {
        Optional<CleaningService> optionalCleaningService = cleaningServiceRepository.findById(id);
        if (optionalCleaningService.isPresent()) {
            CleaningService existingCleaningService = optionalCleaningService.get();
            // Update only the fields that need to be updated
            existingCleaningService.setServiceName(updatedCleaningService.getServiceName());
            existingCleaningService.setDescription(updatedCleaningService.getDescription());
            existingCleaningService.setPrice(updatedCleaningService.getPrice());
            existingCleaningService.setDuration(updatedCleaningService.getDuration());
            existingCleaningService.setAvailability(updatedCleaningService.getAvailability());
            cleaningServiceRepository.save(existingCleaningService);
            return DefaultResponses.response200(updatedCleaningService.getServiceName()+"Successfully Updated",existingCleaningService);
        }
        return DefaultResponses.genericResponse(false, HttpStatus.NOT_FOUND.value(),
                "could not find product with id " + id);
    }


    @Override
    public ApiResponse deleteCleaningService(Long id) {
        if (cleaningServiceRepository.findById(id).isPresent()) {
            cleaningServiceRepository.deleteById(id);
            return DefaultResponses.response200("Successfully deleted product with id " + id);
        }
        return DefaultResponses.response200("Unable to delete CleaningService with id " + id + "because it doesn't exists");

    }
}


