package com.sampleproject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sampleproject.entity.FileData;
import com.sampleproject.entity.ImageData;
import com.sampleproject.repository.FileDataRepository;
import com.sampleproject.repository.StorageRepository;
import com.sampleproject.util.ImageUtil;

@Service
public class StorageService {

	@Autowired
	StorageRepository storageRepository;

	@Autowired
	FileDataRepository fileDataRepsitory;
	
	private final static String FOLDER_PATH ="C:\\Users\\VC\\Desktop\\ReportsAndSave\\fileUpload";

	// file save in db only 
	public String uploadImage(MultipartFile file) throws IOException {

		ImageData imagedata = storageRepository.save(ImageData.builder().name(file.getName())
				.type(file.getContentType()).imageData(ImageUtil.compressImage(file.getBytes())).build());

		if (imagedata != null) {
			return "file uploaded successfully : " + file.getOriginalFilename();
		}

		return "file not uploaded ";
	}

	// file retrive from  db and show in post man
	public byte[] downloadImage(String filleName) {

		Optional<ImageData> dbImageData = storageRepository.findByName(filleName);
		byte[] images = ImageUtil.decompressImage(dbImageData.get().getImageData());

		return images;
	}

	
	// fil upload in db and save in system
	public String uploadImagefileSystem(MultipartFile file) throws IOException {

		String filePath = FOLDER_PATH+file.getOriginalFilename();
		
		FileData fileDate = fileDataRepsitory.save(FileData.builder()
				                             .name(file.getOriginalFilename())
				                             .type(file.getContentType())
				                             .filePath(filePath).build());
		file.transferTo(new File(filePath));

		if (fileDate != null) {
			return "file uploaded successfully : " + filePath;
		}

		return "file not uploaded ";
	}
	
	//file retrive from system
	public byte[] downloadImageFromFileSystem(String filleName) throws IOException {

		Optional<FileData> fileData = fileDataRepsitory.findByName(filleName);
		String filePath =fileData.get().getFilePath();
		byte[] file =Files.readAllBytes(new File(filePath).toPath()) ;

		return file;
	}
	
}
