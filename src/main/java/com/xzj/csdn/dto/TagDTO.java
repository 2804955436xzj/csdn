package com.xzj.csdn.dto;

import lombok.Data;

import java.util.List;

/**
 * @author xzj
 * @date 2019/8/11-10:47
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
