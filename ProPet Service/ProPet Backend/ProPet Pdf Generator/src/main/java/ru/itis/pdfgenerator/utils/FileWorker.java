package ru.itis.pdfgenerator.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import ru.itis.pdfgenerator.exceptions.store.PdfLoadException;
import ru.itis.pdfgenerator.exceptions.store.PdfStoreException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileWorker {

    protected final Path petTransferFolder;

    @Autowired
    public FileWorker(
            @Value("${pdf.pet-transfer-folder}") String petTransferFolder,
            @Value("${pdf.root}") String pdfRoot,
            @Value("${pdf.folder}") String pdfFolder
    ) throws Exception {
        if (pdfRoot.equals("null")) {
            this.petTransferFolder = Paths.get(new ClassPathResource(pdfFolder + petTransferFolder)
                    .getURL().toExternalForm().substring(6).replace("%20", " "));
        } else {
            this.petTransferFolder = Paths.get(pdfRoot + petTransferFolder);
        }
    }


    public void savePetTransferPdf(byte[] data, String fileName){
        saveFile(petTransferFolder, fileName, data);
    }

    public byte[] loadPetTransferPdf(String fileName){
        return loadFile(petTransferFolder, fileName);
    }

    protected void saveFile(Path folderPath, String fileName, byte[] data){
        try {
            Path targetLocation = folderPath.resolve(fileName);
            File newFile = new File(String.valueOf(targetLocation));
            try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
                outputStream.write(data);
            }
        } catch (IOException ex) {
            throw new PdfStoreException(ex);
        }
    }

    protected byte[] loadFile(Path folderPath, String fileName){
        try {
            Path targetLocation = folderPath.resolve(fileName);
            File file = new File(String.valueOf(targetLocation));
            return Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            throw new PdfLoadException(ex);
        }
    }

}
