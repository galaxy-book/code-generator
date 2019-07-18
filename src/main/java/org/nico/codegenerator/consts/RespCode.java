package org.nico.codegenerator.consts;

public enum RespCode {

    SUCCESS(200, "成功"),
    FAILURE(500, "失败"),
    
    
    REGISTER_CODE_INVALID(502, "注册验证码失效, 请重新获取"),
    REGISTER_CODE_ERROR(503, "注册验证码验证错误, 剩余 %s次"),
    REGISTER_USERNAME_ERROR(504, "注册账户格式错误,"),
    MAIL_TYPE_ERROR(505, "邮件发送类型错误,"),
    MAIL_SEND_ERROR(506, "邮件发送失败,"),
    MAIL_CODE_VALIDATION_LIMIT(507, "验证频繁，验证码已失效，请重新发送"),
    USERNAME_ALREADY_EXIST(508, "用户名已存在"),
    MAIL_SEND_BUSY(509, "验证码发送频繁"),
    USERNAME_OR_PASSWORD_ERROR(510, "账号或密码错误"),
    USER_TOKEN_INVALID(511, "用户token失效"),
    FRAME_HANDLER_FINISHED(512, "帧处理完毕"),
    FRAME_HANDLER_NOT_FOUND(513, "没有找到对应的帧处理器"),
    ANIMATION_NOT_FOUND(514, "对应的字符动画不存在"),
    UNAUTHORIZED_OPERATION(515, "操作越权"),
    PROJECT_NOT_EXIST(516, "项目不存在"),
    TEMPLATE_NOT_EXIST(517, "模板不存在"),
    TEMPLATE_PROJECT_NOT_EXIST(518, "模板所属项目不存在"),
    PROJECT_TEMPLATE_NOT_CONFIG(519, "未配置项目模板"),
    GENERATE_ERROR(520, "生成过程发生异常: %s"),
    
    PARAMS_OVERFLOW_LIMIT(901, "%s超出限制%s-%s"),
    PARAMS_ERROR(902, "%s参数错误"),
    PARAMS_ERROR_WITH_REASON(902, "%s参数错误, 原因:%s"),
    
    UPDATE_FAILURE(700, "更新失败"),
    INSERT_FAILURE(701, "插入失败"),
    
    OSS_FILE_NOT_EXIST(801, "oss文件不存在： %s"),
    ;
    
    private int code;
    
    private String msg;

    private RespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
