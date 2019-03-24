package com.cobra.thymeleaf.controller;

import com.cobra.thymeleaf.domain.User;
import com.cobra.thymeleaf.service.StaticService;
import com.cobra.thymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2019/3/24 15:25
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StaticService staticService;

    /**
     * 获取所有用户
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String index(Model model) {
        Collection<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "list";
    }

    /**
     * 根据id获取单个用户详情
     *
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/detail")
    public String detail(@RequestParam Integer userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "detail";
    }

    /**
     * 根据id获取单个用户详情
     *
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/{userId}")
    public String getDetail(@PathVariable("userId") Integer userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "detail";
    }

    /**
     * 返回添加用户页
     *
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return "add";
    }

    /**
     * 添加用户操作
     *
     * @param user
     * @return
     */
    @PostMapping("/save")
    public String save(User user) {
        userService.add(user);
        staticService.createIndexHtml();
        return "redirect:/user/list";
    }


}
