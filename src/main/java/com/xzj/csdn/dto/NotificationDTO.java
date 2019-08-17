package com.xzj.csdn.dto;

import com.xzj.csdn.model.User;
import lombok.Data;

/**
 * @author xzj
 * @date 2019/8/12-21:31
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;

    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
