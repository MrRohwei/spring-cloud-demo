package com.lvbok.websocket.controller;

import com.lvbok.common.CommonResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class LoginController {

    // all user
    public static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @RequestMapping("/loginPage")
    public String login() {
        return "login";
    }

    @RequestMapping("/login")
    public ModelAndView login(String userName) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("indexV1");
        mv.addObject("userName", userName);
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getAllUser")
    public CommonResponse getAllUser() {
        CommonResponse response = new CommonResponse();
        response.setCode(0);
        List<String> allUserList = new ArrayList<>();
        if (!sessionMap.isEmpty()) {
            allUserList.addAll(sessionMap.keySet());
        }
        response.setData(allUserList);
        return response;
    }

    public static void main(String[] args) {
        String checkType = "man";
        String[] split = StringUtils.split(checkType, ",");
        for (String s : split) {
            System.out.println(s);
        }
    }
}
