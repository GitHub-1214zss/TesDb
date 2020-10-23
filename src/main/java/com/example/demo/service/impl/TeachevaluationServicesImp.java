package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Teachevaluation;
import com.example.demo.mapper.TeachevalationMapper;
import com.example.demo.service.TeachevaluationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeachevaluationServicesImp extends ServiceImpl<TeachevalationMapper, Teachevaluation> implements TeachevaluationService {
    @Resource
    private TeachevalationMapper teachevalationMapper;
    public Teachevaluation typeId(String typename) {
        QueryWrapper<Teachevaluation> q=new QueryWrapper<>();
        q.eq("name",typename);
        Teachevaluation teachevaluation=teachevalationMapper.selectOne(q);
        return teachevaluation;
    }

    @Override
    public Teachevaluation typeName(Integer typeid) {
        QueryWrapper<Teachevaluation> q=new QueryWrapper<>();
        q.eq("id",typeid);
        Teachevaluation teachevaluation=teachevalationMapper.selectOne(q);
        return teachevaluation;
    }
}
