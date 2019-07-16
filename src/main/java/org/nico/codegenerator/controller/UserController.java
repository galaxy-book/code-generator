package org.nico.codegenerator.controller;

import org.apache.commons.lang3.StringUtils;
import org.nico.codegenerator.consts.AppConsts;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.model.vo.UserRegisterInVo;
import org.nico.codegenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "用户注册")
	@PostMapping
	public RespVo<?> register(
			@RequestBody UserRegisterInVo registerVo
			){
	    String username = registerVo.getUsername();
	    String password = registerVo.getPassword();
	    String code = registerVo.getCode();
	    if(StringUtils.isBlank(username) || ! username.matches(AppConsts.REGEX_USERNAME)) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "username");
	    }
	    if(StringUtils.isBlank(password) || password.length() < 6) {
            return RespVo.failure(RespCode.PARAMS_ERROR, "password");
        }
	    if(StringUtils.isBlank(code)) {
            return RespVo.failure(RespCode.PARAMS_ERROR, "code");
        }
	    return userService.register(registerVo);
	}
	
}
