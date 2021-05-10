package com.ly.lucky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ly.lucky.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuyang
 * @since 2021-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "account_id", type = IdType.AUTO)
    private Long accountId;

    private Integer roleId;

    @TableField(exist = false)
    private String roleName;

    private String username;

    private String password;

    private String salt;

    private String realName;

    private String sex;

    private String email;


}
