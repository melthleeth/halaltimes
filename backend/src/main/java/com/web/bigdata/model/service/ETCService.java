package com.web.bigdata.model.service;

public interface ETCService {

	void sendSimpleMessage(String to) throws Exception;
	
	void findPwd(String Email) throws Exception;
}
