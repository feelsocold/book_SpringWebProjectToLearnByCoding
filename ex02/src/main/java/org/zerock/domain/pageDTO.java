package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class pageDTO {

	private int startPage;			
	private int endPage;			
	private boolean prev, next;		
	
	private int total;
	private Criteria cri;
	
	public pageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
	// 페이징의 끝번호 계산	
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
	// 페이징의 시작번호 계산
		this.startPage = this.endPage - 9;		
		
	// total을 통한 endPage의 재계산	
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		
		if(realEnd < this.endPage) {	// 진짜 끝 페이지(realEnd)가 구해둔 끝 번호(endPage)보다 작다면 끝 번호는 작은 값이 되어야한다.
			this.endPage = realEnd;
		}
	// 이전과 다음 계산	
		this.prev = this.startPage > 1; 		// 시작 번호가 1보다 큰 경우라면 존재
		this.next = this.endPage < realEnd;		// realEnd가 endPage보다 큰 경우에만 존재
	}
}
