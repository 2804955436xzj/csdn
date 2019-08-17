package com.xzj.csdn.mapper;

import com.xzj.csdn.model.Comment;
import com.xzj.csdn.model.CommentExample;
import com.xzj.csdn.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment record);
}