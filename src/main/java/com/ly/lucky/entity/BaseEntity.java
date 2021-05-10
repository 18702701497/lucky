package com.ly.lucky.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ly
 * @create 2021/3/21 15:07
 */
@Data
public class BaseEntity {
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime modifiedTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createAccountId;

    @TableField(fill = FieldFill.UPDATE)
    private Long modifiedAccountId;

    @TableLogic
    private Integer deleted;
}
