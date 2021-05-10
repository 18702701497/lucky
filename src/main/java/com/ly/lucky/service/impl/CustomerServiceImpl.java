package com.ly.lucky.service.impl;

import com.ly.lucky.entity.Customer;
import com.ly.lucky.dao.CustomerMapper;
import com.ly.lucky.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuyang
 * @since 2021-03-21
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
