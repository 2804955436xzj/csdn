package com.xzj.csdn.dto;

import lombok.Data;

/**
 * @author xzj
 * @date 2019/7/25-16:53
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

}
