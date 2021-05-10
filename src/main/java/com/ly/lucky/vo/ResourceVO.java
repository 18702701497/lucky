package com.ly.lucky.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ly
 * @create 2021/3/25 16:33
 */
@Data
public class ResourceVO {
    /**
     * 主键
     */
    private Integer resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 子资源
     */
    private List<ResourceVO> subs;
}
