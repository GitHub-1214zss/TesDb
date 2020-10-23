package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.SysFunc;
import com.example.demo.mapper.SysFuncMapper;
import com.example.demo.service.SysFuncService;
import org.springframework.stereotype.Service;

@Service
public class SysFuncServicesImp extends ServiceImpl<SysFuncMapper, SysFunc> implements SysFuncService {
}
