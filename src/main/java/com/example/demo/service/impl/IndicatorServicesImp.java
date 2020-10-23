package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Indicator;
import com.example.demo.mapper.IndicatorMapper;
import com.example.demo.service.IndicatorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IndicatorServicesImp extends ServiceImpl<IndicatorMapper, Indicator> implements IndicatorService {
    @Resource
    private IndicatorMapper indicatorMapper;
    @Override
    public Indicator q(Integer id) {
        QueryWrapper<Indicator> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Indicator indicator=indicatorMapper.selectOne(queryWrapper);
        return indicator;

    }

    @Override
    public Indicator qId(String name) {
        QueryWrapper<Indicator> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("indicatorname",name);
        Indicator indicator=indicatorMapper.selectOne(queryWrapper);
        return indicator;
    }
}
