package com.example.demo.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.UserRole;
import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServicesImp extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
