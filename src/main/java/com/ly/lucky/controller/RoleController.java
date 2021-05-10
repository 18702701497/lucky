package com.ly.lucky.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.lucky.entity.Account;
import com.ly.lucky.entity.Role;
import com.ly.lucky.service.AccountService;
import com.ly.lucky.service.CustomerService;
import com.ly.lucky.service.ResourceService;
import com.ly.lucky.service.RoleService;
import com.ly.lucky.util.ResultUtil;
import com.ly.lucky.vo.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

    //进入列表页
    @GetMapping("toList")
    public String toList(){
        return "role/roleList";
    }

    /**
     * 查询列表
     * @param roleName
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public R<Map<String,Object>> list(String roleName,Long page,Long limit){
        LambdaQueryWrapper<Role> wrapper=Wrappers.<Role>lambdaQuery().like(StringUtils.isNotBlank(roleName),Role::getRoleName,roleName)
                .orderByDesc(Role::getRoleId);
        Page<Role> rolePage = roleService.page(new Page<>(page, limit), wrapper);
        return ResultUtil.buildPageR(rolePage);
    }
    //进入新增页
    @GetMapping("toAdd")
    public String toAdd(){
        return "role/roleAdd";
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping
    @ResponseBody
    public R<Object> add(@RequestBody Role role){
        return ResultUtil.buildR(roleService.saveRole(role));
    }

    //进入修改页
    @GetMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable Long id, Model model){
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
        return "role/roleUpdate";
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PutMapping
    @ResponseBody
    public R<Object> update(@RequestBody Role role){
        return ResultUtil.buildR(roleService.updateRole(role));
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public R<Object> delete(@PathVariable Long id){
        Integer count = accountService.lambdaQuery().eq(Account::getRoleId, id).count();
        if(count>0){
            return R.failed("尚有账号拥有该角色，不能删除！");
        }
        return ResultUtil.buildR(roleService.removeById(id));
    }

    // 进入详情页
    @GetMapping("toDetail/{id}")
    public String toDetail(@PathVariable Long id, Model model) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        return "role/roleDetail";
    }

    @GetMapping({"listResource","listResource/{roleId}","listResource/{roleId}/{flag}"})
    @ResponseBody
    public R<List<TreeVO>> listResource(@PathVariable(required = false) Long roleId
            ,@PathVariable(required = false) Long flag){
        return R.ok(resourceService.listResource(roleId,flag));
    }
}
