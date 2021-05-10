package com.ly.lucky.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.lucky.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 *  Mapper 接口
 * </p>
 * @author liuyang
 * @since 2021-03-21
 */
public interface AccountMapper extends BaseMapper<Account> {

    /**
     * 分页查询账号
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Account> accountPage(Page<Account> page, @Param(Constants.WRAPPER)Wrapper<Account> wrapper);

    /**
     * 根据id连表（role）查询account
     * @param id
     * @return
     */
    Account selectAccountById(Long id);

}
