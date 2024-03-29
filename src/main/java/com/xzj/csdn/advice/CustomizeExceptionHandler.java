package com.xzj.csdn.advice;

import com.xzj.csdn.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @author xzj
 * @date 2019/8/2-22:05
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model){
           HttpStatus status = getStatus(request);

           if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
           }else {
               model.addAttribute("message","服务冒烟了,要不稍后试试！！！");
           }


           return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
            Integer statusCode =  (Integer)request.getAttribute("javax.servlet.error.status_code");
            if (statusCode ==null){
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
            return HttpStatus.valueOf(statusCode);
    }


}
