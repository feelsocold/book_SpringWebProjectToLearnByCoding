package org.zerock.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SampleDtoList {

	private List<SampleDto> list;
	
	public SampleDtoList() {
		list = new ArrayList<>();
	}
	
}
