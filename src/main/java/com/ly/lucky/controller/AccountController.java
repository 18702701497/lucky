package com.ly.lucky.controller;


import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.db.Session;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.lucky.entity.Account;
import com.ly.lucky.entity.Role;
import com.ly.lucky.query.AccountQuery;
import com.ly.lucky.service.AccountService;
import com.ly.lucky.service.RoleService;
import com.ly.lucky.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.sql.Wrapper;
import java.util.List;
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
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    /**
     * 进入账号列表页
     * @return
     */
    @GetMapping("toList")
    public String toList(){
        return "account/accountList";
    }

    @GetMapping("list")
    @ResponseBody
    public R<Map<String,Object>> list(AccountQuery query){
        QueryWrapper<Account> wrapper = Wrappers.<Account>query();
        wrapper.like(StringUtils.isNotBlank(query.getRealName()),"a.real_name",query.getRealName())
                .like(StringUtils.isNotBlank(query.getEmail()),"a.email",query.getEmail());
        String createTimeRange = query.getCreateTimeRange();
        if(StringUtils.isNotBlank(createTimeRange)){
            String[] timeArray = createTimeRange.split(" - ");
            wrapper.ge("a.create_time",timeArray[0]).le("a.create_time",timeArray[1]);
        }
        wrapper.eq("a.deleted",0).or().orderByDesc("a.account_id");
        IPage<Account> myPage = accountService.accountPage(new Page<>(query.getPage(), query.getLimit()), wrapper);
        return ResultUtil.buildPageR(myPage);
    }

    /**
     * 进入新增页面
     * @param model
     * @return
     */
    @GetMapping("toAdd")
    public String toAdd(Model model){
        List<Role> roles = roleService.list(Wrappers.<Role>lambdaQuery().orderByAsc(Role::getRoleId));
        model.addAttribute("roles",roles);
        return "account/accountAdd";
    }

    /**
     * 新增操作
     * @return
     */
    @PostMapping
    @ResponseBody
    public R<Object> add(@RequestBody Account account){
        setPasswordAndSalt(account);
        return ResultUtil.buildR(accountService.save(account));
    }

    /**
     * 进入修改页面
     * @return
     */
    @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Long id,Model model){
        Account account = accountService.getById(id);
        model.addAttribute("account",account);
        List<Role> roles = roleService.list(Wrappers.<Role>lambdaQuery().orderByAsc(Role::getRoleId));
        model.addAttribute("roles",roles);
        return "account/accountUpdate";
    }

    /**
     * 修改功能
     * @param account
     * @return
     */
    @PutMapping
    @ResponseBody
    public R<Object> update(@RequestBody Account account){
        String password = account.getPassword();
        if(StringUtils.isNotBlank(password)){
            setPasswordAndSalt(account);
        }else{
            account.setPassword(null);
        }
        return ResultUtil.buildR(accountService.updateById(account));
    }

    /**
     * 删除功能
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public R<Object> delete(@PathVariable Long id, HttpSession session){
        Account account = (Account) session.getAttribute("account");
        if (account.getAccountId().equals(id)){
            return R.failed("不能删除当前登录账号！");
        }
        return ResultUtil.buildR(accountService.removeById(id));
    }

    /**
     * 进入详情页
     * @param id
     * @param model
     * @return
     */
    @GetMapping("toDetail/{id}")
    public String toDetail(@PathVariable Long id,Model model) {
        Account account = accountService.getAccountById(id);
        model.addAttribute("account",account);
        return "account/accountDetail";
    }

    /**
     * 账号验重功能
     * @param username
     * @param accountId
     * @return
     */
    @GetMapping({"/{username}","/{username}/{accountId}"})
    @ResponseBody
    public R<Object> checkUsername(@PathVariable String username,@PathVariable(required = false)Long accountId){
        Integer count = accountService.lambdaQuery()
                .eq(Account::getUsername, username)
                .ne(accountId!=null,Account::getAccountId,accountId)
                .count();
        return R.ok(count);
    }


    /**
     * 设置加密密码和加密盐
     * @param account
     */
    private void setPasswordAndSalt(Account account){
        String password = account.getPassword();
        String salt = UUID.fastUUID().toString().replaceAll("-", "");
        MD5 md5 = new MD5(salt.getBytes());
        String digestHex = md5.digestHex(password);
        account.setPassword(digestHex);
        account.setSalt(salt);
    }
}
