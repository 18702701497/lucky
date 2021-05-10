package com.ly.lucky.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.lucky.dto.LoginDTO;
import com.ly.lucky.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuyang
 * @since 2021-03-21
 */
public interface AccountService extends IService<Account> {

    LoginDTO login(String username,String password);

    /**
     * 分页查询账号
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Account> accountPage(Page<Account> page, Wrapper<Account> wrapper);

    /**
     * 根据id连表（role）查询account
     * @param id
     * @return
     */
    Account getAccountById(Long id);

}
