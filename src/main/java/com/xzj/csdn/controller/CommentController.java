package com.xzj.csdn.controller;

import com.xzj.csdn.dto.CommentCreateDTO;
import com.xzj.csdn.dto.CommentDTO;
import com.xzj.csdn.dto.ResultDTO;
import com.xzj.csdn.enums.CommentTypeEnum;
import com.xzj.csdn.exception.CustomizeErrorCode;
import com.xzj.csdn.model.Comment;
import com.xzj.csdn.model.User;
import com.xzj.csdn.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xzj
 * @date 2019/8/1-10:13
 */
@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (commentCreateDTO.getContent()==null||commentCreateDTO.getContent()==""){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_COMMENT);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setCommentCount(0);
        comment.setLikeCount(0L);
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }

    //获取二级评论
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTO = commentService.findCommentByid(id, CommentTypeEnum.COMMENT);

        return ResultDTO.okOf(commentDTO);

    }
}
