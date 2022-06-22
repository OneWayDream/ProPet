package ru.itis.imagesserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.imagesserver.exceptions.image.ImageLoadException;
import ru.itis.imagesserver.exceptions.image.ImageStoreException;

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

    public Resource getUserImage(String imageKey) {
        return loadImage(usersImagesFolder, imageKey);
    }

    public Resource getDefaultUserImage(){
        return loadImage(usersImagesFolder, defaultImage);
    }

    public Resource getPetImage(String imageKey) {
        return loadImage(petsImagesFolder, imageKey);
    }

    public Resource getDefaultPetImage(){
        return loadImage(usersImagesFolder, defaultImage);
    }

    public void saveUserImage(MultipartFile file, String fileName){
        saveImage(usersImagesFolder, "" + fileName, file);
    }

    public void savePetImage(MultipartFile file, String fileName){
        saveImage(petsImagesFolder, fileName, file);
    }

    public void deleteUserImage(String fileName){
        deleteImage(usersImagesFolder, fileName);
    }

    public void deletePetImage(String fileName){
        deleteImage(petsImagesFolder, fileName);
    }

    protected Resource loadImage(Path folderPath, String fileName){

        Path filePath = folderPath.resolve(fileName).normalize();

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
        try {
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = folderPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new ImageStoreException(ex);
        }
    }

    protected void deleteImage(Path folderPath, String fileName){
        try{
            Path filePath = folderPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            boolean b = resource.getFile().delete();
            if (!b){
                throw new ImageStoreException();
            }
        } catch (Exception ex){
            throw new ImageStoreException(ex);
        }

    }

}
