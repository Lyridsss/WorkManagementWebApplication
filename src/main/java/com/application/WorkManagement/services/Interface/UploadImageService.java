package com.application.WorkManagement.services.Interface;

import com.application.WorkManagement.exceptions.custom.EmptyImageException;
import com.application.WorkManagement.exceptions.custom.InvalidFileExtensionException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

public interface UploadImageService {
    URI uploadImageToS3(MultipartFile file) throws EmptyImageException, InvalidFileExtensionException, URISyntaxException, IOException;

    void removeFileFromS3(URI uri) throws URISyntaxException;

}
