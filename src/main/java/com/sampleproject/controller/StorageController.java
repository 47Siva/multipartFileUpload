package com.sampleproject.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sampleproject.service.StorageService;

@RestController
@RequestMapping("/api/uploadAndDownload")
public class StorageController {

	@Autowired
	StorageService storageService;
	
	// file save in db only 
	@PostMapping("/fileUpload")
	public ResponseEntity<?> uploadImage(@RequestParam  ("image")MultipartFile file ) throws IOException{
		
		String uploadImage = storageService.uploadImage(file);
		
		return ResponseEntity.status(HttpStatus.OK)
				             .body(uploadImage);
		
	}
	// file retrive from  db and show in post man
	@GetMapping("/filedowload/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable ("fileName") String fileName){

		byte [] imageData = storageService.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				             .contentType(MediaType.valueOf("image/png"))
				             .body(imageData);
	}
	
	
	// fil upload in db and save in system
	@PostMapping("/fileUploadfromfile")
	public ResponseEntity<?> uploadImageFileSystem(@RequestParam  ("image")MultipartFile file ) throws IOException{
		
		String uploadImage = storageService.uploadImagefileSystem(file);
		
		return ResponseEntity.status(HttpStatus.OK)
				             .body(uploadImage);
		
	}
	
	//file retrive from system
	@GetMapping("/filedowloadfromfile/{fileName}")
	public ResponseEntity<?> downloadImageFileSystem(@PathVariable ("fileName") String fileName) throws IOException{

		byte [] imageData = storageService.downloadImageFromFileSystem(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				             .contentType(MediaType.valueOf("image/png"))
				             .body(imageData);
	}
}
