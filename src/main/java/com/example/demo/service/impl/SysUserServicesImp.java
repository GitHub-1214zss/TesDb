package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.SysUser;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServicesImp extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
