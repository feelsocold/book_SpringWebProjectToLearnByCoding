package org.zerock.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//스프링을 실행하는 역할을 할 것이라는 것을 표시한다.
@RunWith(SpringJUnit4ClassRunner.class)

//지정된 클래스나 문자열을 이용해서 필요한 개체들을 스프링 내에 객체로 등록한다.
//@ContextConfiguration에 사용하는 문자열은 'classpath:'나 'file:'을 이용한다.
//이클립스에서 자동으로 생성된 root-context.xml의 경로를 지정한다.
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleTests {

	@Setter(onMethod_ = { @Autowired })
	private Restraunt restaurant;
	
	@Test								// JUnit에서 테스트 대상을 표시하는 어노테이션	
	public void testExist() {
		
		assertNotNull(restaurant);		// restaurant가 null이 아니어야만 테스트가 성공한다는 것을 의미한다.
		
		log.info(restaurant);
		log.info("------------------");
		log.info(restaurant.getChef());
		
	}
	
}
