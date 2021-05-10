package com.ly.lucky.dto;

import com.ly.lucky.entity.Account;
import lombok.Data;

/**
 * @author ly
 * @create 2021/3/22 12:32
 */
@Data
public class LoginDTO {

    /**
     * 验证失败重定向或验证成功跳转的路径
     */
    private String path;

    /**
     * 返回的错误信息
     */
    private String error;

    /**
     * 登录人的信息
     */
    private Account account;
}
