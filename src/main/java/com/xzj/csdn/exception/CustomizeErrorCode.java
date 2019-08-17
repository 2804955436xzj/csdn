package com.xzj.csdn.exception;

/**
 * @author xzj
 * @date 2019/8/2-22:48
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不存在，要不换个试试？？？"),
    TARGET_PARAM_NOT_FOUND(2002,"为选择任何问题或评论进行回复"),
    NO_LOGIN(2003,"您未登录，请先登录"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在，要不换个试试？？？"),
    NO_COMMENT(2007,"请输入评论！！！"),
    READ_NOTIFICATION_FAIL(2008,"兄弟你这是读别人的信息呢？？"),
    FILE_UPLOAD_FAIL(2009,"图片上传失败")


    ;


    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
