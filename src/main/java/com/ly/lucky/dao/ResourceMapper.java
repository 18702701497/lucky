package com.ly.lucky.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ly.lucky.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ly.lucky.vo.ResourceVO;
import com.ly.lucky.vo.TreeVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuyang
 * @since 2021-03-21
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 查询当前登录人的资源
     * @param wrapper
     * @return
     */
    List<ResourceVO> listResource(@Param(Constants.WRAPPER)Wrapper<Resource> wrapper);

    List<TreeVO> listResourceByRoleId(@Param(Constants.WRAPPER)Wrapper<Resource> wrapper,@Param("roleId") Long roleId);


}
