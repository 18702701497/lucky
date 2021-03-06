package com.ly.lucky.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ly.lucky.dao.RoleResourceMapper;
import com.ly.lucky.entity.Role;
import com.ly.lucky.dao.RoleMapper;
import com.ly.lucky.entity.RoleResource;
import com.ly.lucky.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuyang
 * @since 2021-03-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    /**
     * 新增角色和角色所具有的资源
     * @param role
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRole(Role role) {
        save(role);
        Long roleId=role.getRoleId();
        List<Integer> resourceIds=role.getResourceIds();
        if(CollectionUtils.isNotEmpty(resourceIds)){
            for(int resourceId:resourceIds){
                RoleResource roleResource=new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResourceMapper.insert(roleResource);
            }
        }
        return true;
    }

    /**
     * 修改角色及角色所具有的资源
     * @param role
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(Role role) {
        updateById(role);
        Long roleId=role.getRoleId();

        roleResourceMapper.delete(Wrappers.<RoleResource>lambdaQuery()
            .eq(RoleResource::getRoleId,roleId));
        List<Integer> resourceIds=role.getResourceIds();
        if(CollectionUtils.isNotEmpty(resourceIds)){
            for(int resourceId:resourceIds){
                RoleResource roleResource=new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResourceMapper.insert(roleResource);
            }
        }
        return true;
    }
}
