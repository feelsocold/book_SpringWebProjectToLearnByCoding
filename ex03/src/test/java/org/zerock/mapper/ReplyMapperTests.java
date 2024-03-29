package org.zerock.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	
	private Long[] bnoArr = { 37L, 39L , 40L, 41L, 43L};
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;

	/*
	@Test
	public void testCreate() {
		IntStream.range(11, 20).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			
			//게시물의 번호
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트 " + i);
			vo.setReplyer("댓글작성자" + i);
			
			mapper.insert(vo);
		});
	}
	
	
	@Test
	public void testDelete() {
		mapper.delete(28);
	}
	
	@Test
	public void testMapper() {
		log.info(mapper);
	}
	
	@Test
	public void testUpdate() {
		Long targetRno = 30L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		vo.setReply("update reply " );
		
		int count = mapper.update(vo);
		
		log.info("UPDATE COUNT : " + count);
		
	}
	
	
	
	@Test
	public void testList() {
		
		Criteria cri = new Criteria();
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		
		replies.forEach(reply -> log.info(reply));
	}
	
	*/
	
	@Test
	public void testList2() {
		Criteria cri = new Criteria(1, 10);
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 86L);
		
		replies.forEach(reply -> log.info(reply));
	}
	
	
	
	
}
