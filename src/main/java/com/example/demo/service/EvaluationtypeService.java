package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.Evaluationtype;

public interface EvaluationtypeService extends IService<Evaluationtype> {

    Evaluationtype queryName(String qryName);
    Evaluationtype qName(Integer id);
}
