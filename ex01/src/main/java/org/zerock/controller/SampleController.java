package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDto;
import org.zerock.domain.SampleDtoList;
import org.zerock.domain.TodoDto;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class,
				new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping(value="/basic", method = {RequestMethod.GET,RequestMethod.POST})
	public void basic() {
		log.info("basic.....................");
	}
	
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		log.info("basic get only get ...................");
	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDto dto){
		log.info("" + dto);
		return "ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, 
						@RequestParam("age") int age) {
		log.info("name : " + name);
		log.info("age : " + age);
		
		return "ex02";
	}
	
	//리스트 처리
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids")ArrayList<String> ids) {
		log.info("ids : " + ids);
		
		return "ex02List";
	}
	//배열 처리
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("array ids : " + Arrays.toString(ids));
		
		return "ex02Array";
	}
	
	//객체 리스트
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDtoList list){
		log.info("list dtos : " + list);
		
		return "ex02Bean";
	}
	
	//initBinder
	@GetMapping("/ex03")
	public String ex03(TodoDto todo) {
		log.info("todo : " + todo);
		return "ex03";
	}
	
	//ModelAttribute
	@GetMapping("/ex04")
	public String ex04(SampleDto dto, @ModelAttribute("page")int page) {
		
		log.info("dto : " + dto);
		log.info("page : " + page);
		
		return "sample/ex04";
	}

	//return:객체타입
	@GetMapping("/ex06")
	public @ResponseBody SampleDto ex06() {
		log.info("/ex06.......");
		
		SampleDto dto = new SampleDto();
		dto.setAge(10);
		dto.setName("조휴일");
		
		return dto;
	}
	
	//return:ResponseEntity타입
	@GetMapping
	public ResponseEntity<String> ex07(){
		log.info("/ex07....");
		
		String msg = "{\"name\" : \"조휴일\"}";
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload.......");
	}
	//FileUpload
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file -> {
			log.info("-------------------");
			log.info("name : " + file.getOriginalFilename());
			log.info("size : " + file.getSize());
			
		});
	}
}
