package com.example.demo.domain.Vo;

import com.example.demo.domain.Evaluationscore;
import lombok.Data;

@Data
public class EvaluationscoreVo extends Evaluationscore {
    private String taskname;
    private String teachername;
    private String indicatorname;

}
