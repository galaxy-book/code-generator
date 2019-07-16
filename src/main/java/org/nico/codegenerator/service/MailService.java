package org.nico.codegenerator.service;

import java.util.concurrent.TimeUnit;

import org.nico.codegenerator.component.CacheComponent;
import org.nico.codegenerator.component.MailComponent;
import org.nico.codegenerator.consts.CacheKeys;
import org.nico.codegenerator.consts.MailType;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.utils.GenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private MailComponent mailComponent;
    
    @Autowired
    private CacheComponent cacheComponent;
    
    public RespVo<?> send(String email, int type) {
        String mailAuthCountKey = CacheKeys.MAIL_AUTH_COUNT + type + email;
        String mailSendLimitKey = CacheKeys.MAIL_SEND_LIMIT + type + email;
        String mailCodeKey = CacheKeys.MAIL_CODE + type + email;
        
        if(cacheComponent.hasKey(mailSendLimitKey)) {
            return RespVo.failure(RespCode.MAIL_SEND_BUSY);
        }
        
        String authCode = GenerateUtils.createCode(5);
        MailType mailType = MailType.parse(type);
        boolean success = mailComponent.sendSimpleMail(email, mailType.getMsg(), "Auth Code: " + authCode);
        if(success) {
            cacheComponent.setEx(mailSendLimitKey, "NICO", 1L, TimeUnit.MINUTES);
            cacheComponent.setEx(mailCodeKey, authCode, 2L, TimeUnit.MINUTES);
            cacheComponent.incrBy(mailAuthCountKey, 0);
            cacheComponent.expire(mailAuthCountKey, 2L, TimeUnit.MINUTES);
            return RespVo.success();
        }else {
            return RespVo.failure(RespCode.MAIL_SEND_ERROR);
        }
    }
}
