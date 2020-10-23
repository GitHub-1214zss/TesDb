package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Rlternative;
import com.example.demo.mapper.RlternativeMapper;
import com.example.demo.service.RIlternativeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RlternativeServicesImp extends ServiceImpl<RlternativeMapper, Rlternative> implements RIlternativeService {
    @Resource RlternativeMapper rlternativeMapper;
    @Override
    public Rlternative querID(Integer id) {
        QueryWrapper<Rlternative> q=new QueryWrapper<>();
        q.eq("id",id);
        Rlternative rlternative= rlternativeMapper.selectOne(q);
        return rlternative;
    }
}
