package com.ly.lucky.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_resource_id", type = IdType.AUTO)
    private Integer roleResourceId;

    private Long roleId;

    private Integer resourceId;


}
