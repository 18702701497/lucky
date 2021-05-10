package com.ly.lucky.query;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ly
 * @create 2021/4/6 16:59
 */
@Data
public class AccountQuery {
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 查询时间范围
     */
    private String createTimeRange;
    /**
     * 分页条件
     */
    private Long page;
    /**
     * 每页的条数
     */
    private Long limit;




}
