package com.ly.lucky.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ly
 * @create 2021/3/29 16:16
 */
public class ResultUtil {

    /**
     * 构建分页查询的返回结果类型
     * @param page
     * @return
     */
    public static R<Map<String,Object>> buildPageR(IPage<?> page){
        HashMap<String,Object> data=new HashMap<>();
        data.put("count",page.getTotal());
        data.put("records",page.getRecords());
        return R.ok(data);
    }
    public static R<Object> buildR(Boolean success){
        if(success){
            return R.ok(success);
        }
        return R.failed("操作失败");
    }
}
