package com.ly.lucky;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ly
 * @create 2021/4/21 21:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test002 {

    @Test
    public void test(){
    int[][] arr={{1,2,3},{4,5,6},{7,8,9}};
    System.out.println("长度"+arr.length);
    System.out.println(arr[0][0]);
    }
}
