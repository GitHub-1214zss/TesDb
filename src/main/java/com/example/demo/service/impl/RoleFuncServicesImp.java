package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.RoleFunc;
import com.example.demo.mapper.RoleFuncMapper;
import com.example.demo.service.RoleFuncService;
import org.springframework.stereotype.Service;

@Service
public class RoleFuncServicesImp extends ServiceImpl<RoleFuncMapper, RoleFunc> implements RoleFuncService {
}
