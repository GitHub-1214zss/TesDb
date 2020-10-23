package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.Indicator;

public interface IndicatorService extends IService<Indicator> {
    Indicator q(Integer id);
    Indicator qId(String name);
}
