package org.nico.codegenerator.controller;

import org.apache.commons.lang3.StringUtils;
import org.nico.codegenerator.consts.AppConsts;
import org.nico.codegenerator.consts.MailType;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.model.vo.MailSendInVo;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private MailService mailService;
	
	@ApiOperation(value = "发送业务邮件")
	@PostMapping("/")
	public RespVo<?> send(
			@RequestBody MailSendInVo mailSendInVo
			){
	    String email = mailSendInVo.getEmail();
	    int type = mailSendInVo.getType();
	    
	    if(StringUtils.isBlank(email) || ! email.matches(AppConsts.REGEX_USERNAME)) {
	        return RespVo.failure(RespCode.REGISTER_USERNAME_ERROR);
	    }
	    if(! MailType.contains(type)) {
	        return RespVo.failure(RespCode.MAIL_TYPE_ERROR);
	    }
	    return mailService.send(email, type);
	    
	}
	
	
	
	
	
}
