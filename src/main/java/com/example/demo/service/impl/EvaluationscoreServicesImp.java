package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.domain.Evaluationscore;
import com.example.demo.mapper.EvaluationscoreMapper;
import com.example.demo.service.EvaluationscoreService;
import org.springframework.stereotype.Service;

@Service
public class EvaluationscoreServicesImp extends ServiceImpl<EvaluationscoreMapper, Evaluationscore> implements EvaluationscoreService {
}
