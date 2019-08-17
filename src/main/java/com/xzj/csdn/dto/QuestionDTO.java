package com.xzj.csdn.dto;

import com.xzj.csdn.model.User;
import lombok.Data;

/**
 * @author xzj
 * @date 2019/7/27-8:54
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
