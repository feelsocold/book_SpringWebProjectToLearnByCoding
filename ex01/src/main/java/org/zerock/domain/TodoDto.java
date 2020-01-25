package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class TodoDto {
	
	private String title;
	private Date dueDate;
}
