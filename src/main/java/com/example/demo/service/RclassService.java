package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.domain.Rclass;
import com.example.demo.domain.Timetable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RclassService extends IService<Rclass> {
    Rclass queryRclass(Timetable timetable);
    /**
     * 读取excel中的数据,生成list
     */
    String readExcelFile( MultipartFile file) throws IOException;
}
