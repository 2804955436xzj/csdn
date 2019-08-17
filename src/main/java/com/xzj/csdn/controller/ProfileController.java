package com.xzj.csdn.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.csdn.dto.NotificationDTO;
import com.xzj.csdn.dto.QuestionDTO;
import com.xzj.csdn.mapper.QuestionMapper;
import com.xzj.csdn.mapper.UserMapper;
import com.xzj.csdn.model.Notification;
import com.xzj.csdn.model.QuestionExample;
import com.xzj.csdn.model.User;
import com.xzj.csdn.service.NotificationService;
import com.xzj.csdn.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xzj
 * @date 2019/7/29-18:26
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name="action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "pn",defaultValue = "1") Integer pn){

        User user = (User) request.getSession().getAttribute("user");

        if (user==null){
            return "redirect:/";
        }


        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");

            PageHelper.startPage(pn, 5);
            List<QuestionDTO> questionDTOListById = questionService.listById(user.getAccountId());

            PageInfo pageInfo = new PageInfo(questionDTOListById);

            QuestionExample example = new QuestionExample();
            example.createCriteria().andCreatorEqualTo(user.getAccountId());
            long count = questionMapper.countByExample(example);


            long i ;

            if (count%5==0){
                i = (count/5);
            }else {
                i = (count/5) + 1;
            }

            model.addAttribute("questionDTOListById",questionDTOListById);
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("pn",pn);
            model.addAttribute("count",i);

        }else if ("replies".equals(action)){

            //查询回复
            PageHelper.startPage(pn, 5);
            List<NotificationDTO> notificationList =   notificationService.list(user.getId());
            PageInfo pageInfo = new PageInfo(notificationList);
            //查询条数
            Long count = notificationService.countByExample(user.getId());
            long i ;

            if (count%5==0){
                i = (count/5);
            }else {
                i = (count/5) + 1;
            }

            model.addAttribute("notificationList",notificationList);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("pn",pn);
            model.addAttribute("count",i);
        }

        return "profile";
    }

}
