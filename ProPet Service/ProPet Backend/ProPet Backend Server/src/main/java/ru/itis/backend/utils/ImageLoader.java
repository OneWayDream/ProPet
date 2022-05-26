package ru.itis.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.backend.exceptions.images.ImageLoadException;
import ru.itis.backend.exceptions.images.ImageStoreException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageLoader {

    protected final Path usersImagesFolder;
    protected final Path petsImagesFolder;
    protected final String defaultImage;

    @Autowired
    public ImageLoader(
            @Value("${images.users-images-folder}") String usersImagesFolder,
            @Value("${images.pets-images-folder}") String petsImagesFolder,
            @Value("${images.default-image}") String defaultImage,
            @Value("${images.root}") String imagesRoot,
            @Value("${images.folder}") String imagesFolder
    ) throws Exception {
        if (imagesRoot.equals("null")){
            this.usersImagesFolder = Paths.get(new ClassPathResource(imagesFolder + usersImagesFolder)
                    .getURL().toExternalForm().substring(6).replace("%20", " "));
            this.petsImagesFolder = Paths.get(new ClassPathResource(imagesFolder + petsImagesFolder)
                    .getURL().toExternalForm().substring(6).replace("%20", " "));
        } else {
            this.usersImagesFolder = Paths.get(imagesRoot + usersImagesFolder);
            this.petsImagesFolder = Paths.get(imagesRoot + petsImagesFolder);
        }
        this.defaultImage = defaultImage;
    }

    public Resource getUserImage(Long userId) {
        return loadImage(usersImagesFolder, "" + userId);
    }

    public Resource getPetImage(Long userId, Long petId) {
        return loadImage(petsImagesFolder, userId + "_" + petId);
    }

    public void saveUserImage(MultipartFile file, Long id){
        saveImage(usersImagesFolder, "" + id, file);
    }

    public void savePetImage(MultipartFile file, Long userId, Long petId){
        saveImage(petsImagesFolder, userId + "_" + petId, file);
    }

    protected Resource loadImage(Path folderPath, String fileName){

        Resource result = checkImageByName(folderPath, fileName);
        if (result != null){
            return result;
        }

        Path filePath = folderPath.resolve(defaultImage).normalize();
        System.out.println(filePath);

        try{
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new ImageLoadException();
            }
        } catch (Exception ex) {
            throw new ImageLoadException(ex);
        }
    }

    protected void saveImage(Path folderPath, String fileName, MultipartFile file){

        Resource result = checkImageByName(folderPath, fileName);
        if (result != null){
            try{
                boolean b = result.getFile().delete();
                if (!b){
                    throw new ImageStoreException();
                }
            } catch (IOException ex){
                throw new ImageStoreException(ex);
            }
        }

        String contentType = file.getContentType();

        try {
            ImageType imageType = ImageType.get(contentType);

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = folderPath.resolve(fileName + "." + imageType.value());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new ImageStoreException(ex);
        }
    }

    protected Resource checkImageByName(Path folderPath, String fileName){

        Path filePath;

        for (ImageType type: ImageType.values()){
            try{
                filePath = folderPath.resolve(fileName + "." + type.value()).normalize();
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists()) {
                    return resource;
                }
            } catch (MalformedURLException ex){
                //ignore
            }

        }
        return null;
    }



}
