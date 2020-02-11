package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

@Controller
@Log4j
public class UploadController {
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
	
	/*
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			log.info("contentType : " + contentType);
			
			return contentType.startsWith("image");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	} */
	
    private boolean checkImageType(File file){
        Magic magic = new Magic();
        try {
            MagicMatch match = Magic.getMagicMatch(file, false);
            String contentType = match.getMimeType();

            log.error("CONTENT TYPE : " + contentType);
            
            return match.getMimeType().contains("image");
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }    
	
	
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
	
	@PostMapping(value="/uploadAjaxAction",
				produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("uploadAjaxPost()");
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "/Users/MACBOHAN/eclipse/temp";
		
		String uploadFolderPath = getFolder();
		// make folder -----
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("UPLOAD PATH : " + uploadPath);
		
		if(uploadPath.exists() == false) {
			log.info("IT DOESNT EXIST");
			boolean a = uploadPath.mkdirs();
			log.info("mkdirs SUCCESS? -- " + a);
		}
		// make yyyy/MM/dd folder
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("-----------------------");
			log.info("UPLOAD FILE NAME : " + multipartFile.getOriginalFilename());
			log.info("UPLOAD FILE SIZE : " + multipartFile.getSize());
			
			AttachFileDTO attachDTO = new AttachFileDTO();
		
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +  1);
			log.info("only file name : " + uploadFileName);
			attachDTO.setFileName(uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = (uuid.toString() + "_" + uploadFileName).trim();
			log.info("UUID uploadFILENAME : " + uploadFileName);
			//File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				log.info("! SAVE FILE" +  saveFile);
				
				// check image type file
				if(checkImageType(saveFile)) {
					
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail 
						= new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 200, 200);
					
					thumbnail.close();
				}
				
				//add to List
				list.add(attachDTO);
				
				for (AttachFileDTO a : list) {
					log.error(a);
				}
				
			} catch (Exception e) {
				log.error(e.getMessage());
			} 
			
		} //end for
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
//	섬네일 데이터 전송
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("getFile()");
		log.info("fileName : " + fileName.trim());
		
		File file = new File("/Users/MACBOHAN/eclipse/temp/" + fileName.trim());
		
		log.info("!!displayed file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			MagicMatch match = Magic.getMagicMatch(file, false);
            String contentType = match.getMimeType();
            
			header.add("Content-Type", contentType);
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)	// 다운로드를 할 수 있는 MIME 타입
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName
												, @RequestHeader("User-Agent") String userAgent){
		log.info("DOWNLOAD File : " + fileName);
		
		Resource resource = new FileSystemResource("/Users/MACBOHAN/eclipse/temp//" + fileName);
		log.info("resource : " + resource);

		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		
		//remove UUID
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			String downloadName = null;
			// HTTP 헤더 메시지 중에서 디바이스의 정보를 알 수 있는 헤더는 'User_Agent'값을 이용한다.
			// 이를 이용해서 브라우저의 종류나 모바일인지 데스크톱인지 혹은 브라우저 프로그램의 종류를 구분할 수 있다.
			if(userAgent.contains("Treident")) {
				log.info("IE brower");
				//downloadName = URLEncoder.encode(resourceName, "UTF-8").replace("//+", " ");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replace("//+", " ");
			}else if(userAgent.contains("Edge")) {
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
			}else {
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
				log.info("DOWNLOAD FILENAME : " + downloadName);
			}
		
			//headers.add("Content-Disposition", "attachment; filename=" + new String(resourceName.getBytes("UTF-8"),"ISO-8859-1"));
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	} //end function
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile : " + fileName);
		
		File file;
		
		try {
			file = new File("/Users/MACBOHAN/eclipse/temp/" + URLDecoder.decode( fileName, "UTF-8"));
			
			file.delete();
			
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				
				log.info("largeFileName : " + largeFileName);

				file = new File(largeFileName);
				
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
