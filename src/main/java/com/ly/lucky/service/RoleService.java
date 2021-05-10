package com.ly.lucky.service;

import com.ly.lucky.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuyang
 * @since 2021-03-21
 */
public interface RoleService extends IService<Role> {

    /**
     * 新增角色和角色所具有的资源
     * @param role
     * @return
     */
    boolean saveRole(Role role);

    /**
     * 修改角色和角色所具有的资源
     * @param role
     * @return
     */
    boolean updateRole(Role role);

}
