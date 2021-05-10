package com.ly.lucky.controller;

import com.ly.lucky.dto.LoginDTO;
import com.ly.lucky.service.AccountService;
import com.ly.lucky.service.ResourceService;
import com.ly.lucky.vo.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ly
 * @create 2021/3/21 18:09
 */
@Controller
@RequestMapping("auth")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ResourceService resourceService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public String Login(String username, String password, HttpSession session,
                        RedirectAttributes attributes, Model model){
        LoginDTO loginDTO=accountService.login(username,password);
        String error=loginDTO.getError();
        if (error==null){
            session.setAttribute("account",loginDTO.getAccount());
            List<ResourceVO> resourceVOS = resourceService
                    .ListResourceByRoleId(loginDTO.getAccount().getRoleId());
            model.addAttribute("resource",resourceVOS);
        }else{
            attributes.addFlashAttribute("error",error);
        }
        return loginDTO.getPath();
    }

    @GetMapping("logout")
    public String Logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
