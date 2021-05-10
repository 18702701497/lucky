package com.ly.lucky.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.lucky.entity.Customer;
import com.ly.lucky.service.CustomerService;
import com.ly.lucky.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuyang
 * @since 2021-03-21
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 进入列表页
     * @return
     */
    @GetMapping("toList")
    public String toList(){
        return "customer/customerList";
    }

    /**
     * 查询方法
     * @param realName
     * @param phone
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    //这里使用的是mp自带的一个API返回结果类（过期了），建议按照这个R类自定义一个API返回结果类使用
    //查询条件需要封装成Query对象，我这里只有姓名和手机号，就直接写了，超过两个需要封装
    public R<Map<String,Object>> list(String realName,String phone,Long page,Long limit){
        LambdaQueryWrapper<Customer> wrapper = Wrappers.<Customer>lambdaQuery()
                .like(StringUtils.isNotBlank(realName),Customer::getRealName,realName)
                .like(StringUtils.isNotBlank(phone),Customer::getPhone,phone)
                .orderByDesc(Customer::getCustomerId);
        Page<Customer> myPage = customerService.page(new Page<>(page,limit),wrapper);
//        Page<Customer> myPage1 = customerService.lambdaQuery()
//                .like(StringUtils.isNotBlank(realName), Customer::getRealName, realName)
//                .like(StringUtils.isNotBlank(phone), Customer::getPhone, phone)
//                .orderByDesc(Customer::getCustomerId).page(new Page<>(page, limit));
        return ResultUtil.buildPageR(myPage);
    }

    /**
     * 进入新增页
     * @return
     */
    @GetMapping("toAdd")
    public String toAdd(){
        return "customer/customerAdd";
    }

    /**
     * 新增客户
     * @param customer
     * @return
     */
    @PostMapping
    @ResponseBody
    public R<Object> add(@RequestBody Customer customer){
        return ResultUtil.buildR(customerService.save(customer));
    }
    /**
     * 进入更新页
     * @return
     */
    @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Long id,Model model){
        Customer customer=customerService.getById(id);
        model.addAttribute("customer",customer);
        return "customer/customerUpdate";
    }

    /**
     * 修改客户
     * @param customer
     * @return
     */
    @PutMapping
    @ResponseBody
    public R<Object> update(@RequestBody Customer customer){
        return ResultUtil.buildR(customerService.updateById(customer));
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public R<Object> delete(@PathVariable Long id){
        return ResultUtil.buildR(customerService.removeById(id));
    }

    @GetMapping("toDetail/{id}")
    public String toDetail(@PathVariable Long id,Model model){
        Customer customer = customerService.getById(id);
        model.addAttribute("customer",customer);
        return "customer/customerDetail";
    }




}
