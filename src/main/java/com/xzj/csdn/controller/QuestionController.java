package com.xzj.csdn.controller;

import com.xzj.csdn.dto.CommentCreateDTO;
import com.xzj.csdn.dto.CommentDTO;
import com.xzj.csdn.dto.QuestionDTO;
import com.xzj.csdn.enums.CommentTypeEnum;
import com.xzj.csdn.service.CommentService;
import com.xzj.csdn.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author xzj
 * @date 2019/7/30-15:16
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        //点击问题
        QuestionDTO questionDTO =  questionService.findByid(id);
        //查询相关问题
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        //查询评论
        List<CommentDTO> comments = commentService.findCommentByid(id, CommentTypeEnum.QUESTION);
        //添加评论数

        questionService.incView(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
