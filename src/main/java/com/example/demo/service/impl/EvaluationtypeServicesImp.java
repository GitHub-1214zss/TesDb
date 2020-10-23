package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Evaluationtype;
import com.example.demo.mapper.EvaluationtypeMapper;
import com.example.demo.service.EvaluationtypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EvaluationtypeServicesImp extends ServiceImpl<EvaluationtypeMapper, Evaluationtype> implements EvaluationtypeService {
    @Resource
    private EvaluationtypeMapper evaluationtypeMapper;
    @Override
    public Evaluationtype queryName(String qryName) {
        QueryWrapper<Evaluationtype> q=new QueryWrapper<>();
        q.eq("name",qryName);
        Evaluationtype evaluationtype=evaluationtypeMapper.selectOne(q);
        return evaluationtype;
    }

    @Override
    public Evaluationtype qName(Integer id) {
        QueryWrapper<Evaluationtype> q=new QueryWrapper<>();
        q.eq("id",id);
        Evaluationtype evaluationtype=evaluationtypeMapper.selectOne(q);
        return evaluationtype;
    }
}
