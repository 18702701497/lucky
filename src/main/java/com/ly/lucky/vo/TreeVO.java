package com.ly.lucky.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ly
 * @create 2021/4/12 11:40
 */
@Data
public class TreeVO {

    private String title;

    private Long id;

    private List<TreeVO> children;

    private boolean checked;

}
