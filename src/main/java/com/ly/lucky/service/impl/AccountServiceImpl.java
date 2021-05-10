package com.ly.lucky.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.lucky.dto.LoginDTO;
import com.ly.lucky.entity.Account;
import com.ly.lucky.dao.AccountMapper;
import com.ly.lucky.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuyang
 * @since 2021-03-21
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public LoginDTO login(String username, String password) {
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setPath("redirect:/");

        Account account = lambdaQuery().eq(Account::getUsername, username).one();
        if(account==null){
            loginDTO.setError("用户名不存在！");
            return loginDTO;
        }
        MD5 md5=new MD5(account.getSalt().getBytes());
        String digestHex = md5.digestHex(password);
        if (!digestHex.equals(account.getPassword())){
            loginDTO.setError("密码错误请重新输入！");
            return loginDTO;
        }
        loginDTO.setAccount(account);
        loginDTO.setPath("login/main");
        return loginDTO;
    }

    /**
     * 分页查询账号
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public IPage<Account> accountPage(Page<Account> page, Wrapper<Account> wrapper) {
        return baseMapper.accountPage(page,wrapper);
    }

    @Override
    public Account getAccountById(Long id) {
        return baseMapper.selectAccountById(id);
    }
}
