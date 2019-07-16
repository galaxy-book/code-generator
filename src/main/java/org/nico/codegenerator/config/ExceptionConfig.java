package org.nico.codegenerator.config;

import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.exception.ByteAnimationException;
import org.nico.codegenerator.model.vo.RespVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionConfig {

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public RespVo<?> errorHandler(Exception ex) {
		ex.printStackTrace();
		return RespVo.failure(RespCode.FAILURE);
	}
	
	@ResponseBody
	@ExceptionHandler(value = ByteAnimationException.class)
	public RespVo<?> errorHandler(ByteAnimationException ex) {
		ex.printStackTrace();
		return RespVo.failure(ex.getRespCode());
	}
	
}
