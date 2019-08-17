package com.xzj.csdn.dto;

import lombok.Data;

/**
 * @author xzj
 * @date 2019/7/25-10:51
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
