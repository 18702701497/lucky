package com.ly.lucky;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author ly
 * @create 2021/4/12 13:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test001 {

    @Test
    public void m() {
        int[] arr={2,-99,67,-1,-159,33};
        for(int i=1;i<arr.length;i++){
            for(int j=0;j<arr.length-1;j++){
                if(arr[i]<arr[j]){
                    int number=arr[i];
                    arr[i]=arr[j];
                    arr[j]=number;
                }
            }
        }
        System.out.println(arr[0]);
        System.out.println(arr[1]);
        System.out.println(arr[2]);
        System.out.println(arr[3]);
        System.out.println(arr[4]);
        System.out.println(arr[5]);
    }
}
