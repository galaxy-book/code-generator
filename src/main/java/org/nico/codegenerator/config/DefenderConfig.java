package org.nico.codegenerator.config;

import java.util.concurrent.TimeUnit;

import org.nico.codegenerator.component.CacheComponent;
import org.nico.codegenerator.consts.CacheKeys;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.utils.HttpContextUtils;
import org.nico.defender.Defender;
import org.nico.defender.annotation.EnableDefender;
import org.nico.defender.guarder.AbstractPreventer;
import org.nico.defender.guarder.Caller;
import org.nico.defender.guarder.Guarder;
import org.nico.defender.guarder.GuarderType;
import org.nico.defender.guarder.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDefender("* org.nico.codegenerator.controller..*.*(..)")
public class DefenderConfig {

    @Bean
    public Defender defender() {
        return Defender.getInstance()
                .registry(Guarder.builder(GuarderType.URI)
                        .pattern("POST /project/**")
                        .preventer(new AbstractPreventer() {
                            
                            @Autowired
                            private CacheComponent cacheComponent;
                            
                            @Override
                            public Result detection(Caller caller) {
                                String token = caller.getRequest().getHeader("token");
                                String userStr = null;
                                if((userStr = cacheComponent.get(CacheKeys.USER_TOKEN + token)) != null) {
                                    cacheComponent.expire(CacheKeys.USER_TOKEN + token, 24, TimeUnit.HOURS);
                                    HttpContextUtils.set(userStr);
                                    return Result.pass();
                                }else {
                                    return Result.notpass(RespVo.failure(RespCode.USER_TOKEN_INVALID));
                                }
                            }
                        }))
                .after(caller -> {
                    HttpContextUtils.clear();  
                })
                .ready();
    }
}
