package com.xzj.csdn.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzj.csdn.dto.QuestionDTO;
import com.xzj.csdn.mapper.QuestionMapper;
import com.xzj.csdn.model.Question;
import com.xzj.csdn.model.QuestionExample;
import com.xzj.csdn.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xzj
 * @date 2019/7/24-21:25
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    QuestionMapper questionMapper;
    @GetMapping("/")
    public String index(Model model,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value = "pn",defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5);

        List<QuestionDTO> questionDTOList = questionService.list();

        PageInfo pageInfo = new PageInfo(questionDTOList);

        QuestionExample example = new QuestionExample();
        long count = questionMapper.countByExample(example);

        long i ;

        if (count%5==0){
            i = (count/5);
        }else {
            i = (count/5) + 1;
        }

        model.addAttribute("questionDTOList",questionDTOList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("pn",pn);
        model.addAttribute("count",i);
        return "index";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Question> search(@RequestParam(value = "search") String search){
        List<Question> list = questionService.list(search);
        return list;
    }


}
