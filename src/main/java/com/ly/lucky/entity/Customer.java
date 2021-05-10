package com.ly.lucky.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ly.lucky.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

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
public class Customer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "customer_id", type = IdType.AUTO)
    private Integer customerId;

    private String realName;

    private String sex;

    private Integer age;

    private String email;

    private String phone;

    private String address;


}
