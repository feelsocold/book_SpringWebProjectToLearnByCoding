package org.zerock.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;


import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UploadController {
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("UPLOAD FORM");
	}
	
	@PostMapping
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "/Users/MACBOHAN/eclipse/temp";
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("-----------------------");
			log.info("UPLOAD FILE NAME : " + multipartFile.getOriginalFilename());
			log.info("UPLOAD FILE SIZE : " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);	
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			
		} //end for
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload AJAX");
	}
	
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
	String uploadFolder = "/Users/MACBOHAN/eclipse/temp";
		log.info("uploadAjaxPost()");
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("-----------------------");
			log.info("UPLOAD FILE NAME : " + multipartFile.getOriginalFilename());
			log.info("UPLOAD FILE SIZE : " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);	
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			
		} //end for
	}
}
