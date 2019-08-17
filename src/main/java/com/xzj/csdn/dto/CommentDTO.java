package com.xzj.csdn.dto;

import com.xzj.csdn.model.User;
import lombok.Data;

/**
 * @author xzj
 * @date 2019/8/8-9:20
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}
