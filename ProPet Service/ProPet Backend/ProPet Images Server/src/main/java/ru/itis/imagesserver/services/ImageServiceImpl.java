package ru.itis.imagesserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.imagesserver.models.PetImage;
import ru.itis.imagesserver.models.UserImage;
import ru.itis.imagesserver.repositories.PetImageRepository;
import ru.itis.imagesserver.repositories.UserImageRepository;
import ru.itis.imagesserver.utils.ImageLoader;
import ru.itis.imagesserver.utils.ImageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    protected final UserImageRepository userImageRepository;
    protected final PetImageRepository petImageRepository;
    protected final ImageLoader imageLoader;

    @Override
    public Resource getImageForUser(Long accountId) {
        Optional<UserImage> image = userImageRepository.findByAccountId(accountId)
                .filter(entity -> !entity.getIsDeleted());
        return (image.isPresent()) ? imageLoader.getUserImage(image.get().getImageKey())
                : imageLoader.getDefaultUserImage();
    }

    @Override
    public Resource getPetImage(Long petId) {
        Optional<PetImage> image = petImageRepository.findByPetId(petId)
                .filter(entity -> !entity.getIsDeleted());
        return (image.isPresent()) ? imageLoader.getPetImage(image.get().getImageKey())
                : imageLoader.getDefaultPetImage();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void saveUserImage(MultipartFile file, Long accountId) {
        String imageType = ImageType.get(file.getContentType()).value();
        UserImage image = userImageRepository.findByAccountId(accountId)
                .filter(entity -> !entity.getIsDeleted())
                .orElse(UserImage.builder()
                        .accountId(accountId)
                        .build());
        String newImageKey = UUID.randomUUID().toString() + accountId + "." + imageType;
        imageLoader.saveUserImage(file, newImageKey);
        if ((image.getImageKey() != null) && (!image.getImageKey().equals(newImageKey))){
            imageLoader.deleteUserImage(image.getImageKey());
        }
        image.setImageKey(newImageKey);
        userImageRepository.save(image);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void savePetImage(MultipartFile file, Long accountId, Long petId) {
        String imageType = ImageType.get(file.getContentType()).value();
        PetImage image = petImageRepository.findByPetId(petId)
                .filter(entity -> !entity.getIsDeleted())
                .orElse(PetImage.builder()
                        .accountId(accountId)
                        .petId(petId)
                        .build());
        String newImageKey = UUID.randomUUID().toString() + petId + "." + imageType;
        imageLoader.savePetImage(file, newImageKey);
        if ((image.getImageKey() != null) && (!image.getImageKey().equals(newImageKey))){
            imageLoader.deletePetImage(image.getImageKey());
        }
        image.setImageKey(newImageKey);
        petImageRepository.save(image);
    }
}
