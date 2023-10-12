package com.hmall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.pojo.user.entity.User;
import com.hmall.user.mapper.UserMapper;
import com.hmall.user.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

}
