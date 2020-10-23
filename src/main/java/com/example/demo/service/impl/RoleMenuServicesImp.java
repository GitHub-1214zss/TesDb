package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.RoleMenu;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.service.RoleMenuService;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuServicesImp extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
}
