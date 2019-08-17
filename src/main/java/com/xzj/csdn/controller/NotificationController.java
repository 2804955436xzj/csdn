package com.xzj.csdn.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.csdn.dto.NotificationDTO;
import com.xzj.csdn.dto.QuestionDTO;
import com.xzj.csdn.enums.NotificationTypeEnum;
import com.xzj.csdn.model.QuestionExample;
import com.xzj.csdn.model.User;
import com.xzj.csdn.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xzj
 * @date 2019/8/13-21:10
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name="id") Long id,
                          HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");

        if (user==null){
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLT_COMMENT.getType()==notificationDTO.getType()
            || NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();

        }else {
            return "redirect:/";
        }
    }
}
