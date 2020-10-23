package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.Teachevaluation;

public interface TeachevaluationService extends IService<Teachevaluation> {
    Teachevaluation typeId(String typename);
    Teachevaluation typeName(Integer typeid);
}
