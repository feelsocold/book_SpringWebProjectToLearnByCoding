package org.zerock.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.pageDTO;
import org.zerock.service.BoardService;

import lombok.extern.log4j.Log4j;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

@Controller
@Log4j
@RequestMapping("/board/*")
//@AllArgsConstructor
public class BoardController {
	
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
	
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		
		log.info("delete attach files...");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("/Users/MACBOHAN/eclipse/temp/" + attach.getUploadPath() + "/"
						+ attach.getUuid() + "_" + attach.getFileName());
				
				System.err.println(file);
				
				Files.deleteIfExists(file);
				
				if(Files.probeContentType(file).startsWith("image")) {
					System.err.println("!!!!!!!!!!!!!!!!!!!!PLZPZLPLZPLPZLPLZ");
					
					Path thumbNail = Paths.get("/Users/MACBOHAN/eclipse/temp/"
						+ attach.getUploadPath() + "/s_" + attach.getUuid() + "_" + attach.getFileName());
					
					Files.delete(thumbNail);
				}
						
			} catch (Exception e) {
				log.error("delete file error " + e.getMessage());
			}	//end catch
		});		// end foreachd
	}
	
	@Autowired
	private BoardService service;
	
	//리스트 이동
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		
		log.info("►►►►►►►►►►►►►►►►►►►►►►►+ " + cri.toString());
		
		List<BoardVO> list = service.getList(cri);
		int total = service.getTotal(cri);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", new pageDTO(cri, total));
	}
	
	//등록화면 이동
	@GetMapping("/register")
	public void register() {
		log.info("register()");
	}	
	//등록 작업처리
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("----------register() POST");
		log.info("register : " + board);

		if(board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		service.register(board);
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}
	//수정화면 이동
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, Model model, @ModelAttribute("cri") Criteria cri) {
		log.info("/get or /modify");
		model.addAttribute("board", service.get(bno));
	}
	//수정 작업처리
	@PostMapping("/modify")
	public String modify(@ModelAttribute BoardVO board, RedirectAttributes rttr, @ModelAttribute("cri") Criteria cri) {
		log.info("modify : " + board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
	
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr, @ModelAttribute("cri") Criteria cri) {
		log.info("remove.... " + bno);
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		
		System.err.println("첨부파일 리스트 사이즈 : " + attachList.size());
		
		if(service.remove(bno)){
			
			//delete Attach Files
			deleteFiles(attachList);
			
			rttr.addFlashAttribute("result","success");
		}

		/*
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		*/
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	//첨부파일 리스트
	@GetMapping(value = "/getAttachList",
						produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		
		log.info("getAttachList " + bno);
		
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}
	
	
	
	
}
