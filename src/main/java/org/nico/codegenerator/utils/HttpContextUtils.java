package org.nico.codegenerator.utils;

import org.nico.codegenerator.model.bo.UserTokenBo;

public class HttpContextUtils {

    private final static ThreadLocal<UserTokenBo> TL = new ThreadLocal<UserTokenBo>();
    
    public static void set(String str) {
        TL.set(JsonUtils.toObject(str, UserTokenBo.class));
    }
    
    public static void clear() {
        TL.remove();
    }
    
    public static UserTokenBo getUser() {
        return TL.get();
    }
    
    public static Long getUserId() {
        UserTokenBo user = getUser();
        if(null != user) {
            return user.getId();
        }
        return null;
    }
    
    public static String getToken() {
        UserTokenBo user = getUser();
        if(null != user) {
            return user.getToken();
        }
        return null;
    }
    
}
