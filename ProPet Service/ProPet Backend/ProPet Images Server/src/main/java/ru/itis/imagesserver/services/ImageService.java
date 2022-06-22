package ru.itis.imagesserver.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    Resource getImageForUser(Long accountId);
    Resource getPetImage(Long petId);
    void saveUserImage(MultipartFile file, Long accountId);
    void savePetImage(MultipartFile file, Long accountId, Long petId);

}
