package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.Rlternative;

public interface RIlternativeService extends IService<Rlternative> {
    Rlternative querID(Integer id);
}
