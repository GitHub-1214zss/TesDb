package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.SysRole;
import com.example.demo.mapper.SysRoleMapper;
import com.example.demo.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServicesImp extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
