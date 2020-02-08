package org.zerock.service;

import java.io.Serializable;

public interface SampleService extends Serializable {
	
	public Integer doAdd(String str1, String str2) throws Exception;
}
