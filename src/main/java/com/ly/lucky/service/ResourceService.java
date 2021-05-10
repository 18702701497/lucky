package com.ly.lucky.service;

import com.ly.lucky.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.lucky.vo.ResourceVO;
import com.ly.lucky.vo.TreeVO;
import sun.reflect.generics.tree.Tree;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuyang
 * @since 2021-03-21
 */
public interface ResourceService extends IService<Resource> {

    /**
     * 根据角色Id，查询出该角色所具有的资源
     * @param roleId
     * @return
     */
    List<ResourceVO> ListResourceByRoleId(int roleId);

    /**
     * 查询所有资源，供前端组件渲染
     * @return
     */
    List<TreeVO> listResource(Long roleId,Long flag);
}
