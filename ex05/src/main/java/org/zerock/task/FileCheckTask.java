package org.zerock.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zerock.domain.BoardAttachVO;
import org.zerock.mapper.BoardAttachMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {
	
	//@Setter(onMethod_ = { @Autowired })
	@Autowired
	private BoardAttachMapper attachMapper;
	
	private String getFolderYesterday() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}
	
	@Scheduled(cron="0 * * * * * ")
	public void checkFiles() throws Exception{
		
		log.warn("FIlE CHECK TASK RUN ~ ~ ~ ~ ~");
		
		log.warn(new Date());

		//file list in database
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();
		
		//ready for check file in directory with database file list
		List<Path> fileListPaths = fileList.stream()
			.map(vo -> Paths.get("/Users/MACBOHAN/eclipse/temp", vo.getUploadPath(), "s_" 
					+ vo.getUuid() + "_" + vo.getFileName()))
			
			.collect(Collectors.toList());
		
		//image file has thumnail file
		fileList.stream().filter(vo -> vo.isFileType() == true)
			.map(vo -> Paths.get("/Users/MACBOHAN/eclipse/temp", "s_" +
				vo.getUuid() + "_" + vo.getFileName()))
			.forEach(p -> fileListPaths.add(p));
		
		log.warn("=========================================================");
		
		//files in yesterday directory
		File targetDir = Paths.get("/Users/MACBOHAN/eclipse/temp", getFolderYesterday()).toFile();
		
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		log.warn("----------------------------------------------------------");
		
		for (File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			
			file.delete();
		}
		
		
	}
	
}