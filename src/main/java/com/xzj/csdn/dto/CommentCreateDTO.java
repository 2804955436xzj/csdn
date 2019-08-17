package com.xzj.csdn.dto;

import lombok.Data;

/**
 * @author xzj
 * @date 2019/8/1-10:25
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
