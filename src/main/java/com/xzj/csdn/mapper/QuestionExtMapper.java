package com.xzj.csdn.mapper;

import com.xzj.csdn.model.Question;
import com.xzj.csdn.model.QuestionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);

    List<Question> selectSearch(String search);
}