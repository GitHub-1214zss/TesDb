package com.example.demo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.SysMenu;
import com.example.demo.mapper.SysMenuMapper;
import com.example.demo.service.SysMenuService;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServicesImp extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
}
