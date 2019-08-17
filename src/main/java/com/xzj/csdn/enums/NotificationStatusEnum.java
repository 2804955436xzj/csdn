package com.xzj.csdn.enums;

/**
 * @author xzj
 * @date 2019/8/12-21:02
 */
public enum  NotificationStatusEnum {
    UNREAD(0),READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

     NotificationStatusEnum(int status) {
        this.status = status;
    }
}
