package com.example.demo.controll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.comm.RetJson;
import com.example.demo.config.ReportDataBean;
import com.example.demo.domain.Evaluationscore;
import com.example.demo.domain.Evaluationtype;
import com.example.demo.domain.Staff;
import com.example.demo.domain.Teachevaluation;
import com.example.demo.domain.Vo.EvaluationscoreVo;
import com.example.demo.service.EvaluationscoreService;
import com.example.demo.service.EvaluationtypeService;
import com.example.demo.service.StaffService;
import com.example.demo.service.TeachevaluationService;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@ApiModel("班级控制器")
@RequestMapping("/Score")
public class EvaluationscoreControll {
    @Autowired
    private EvaluationscoreService evaluationscoreService;
    @Autowired
    private EvaluationtypeService evaluationtypeService;
    @Autowired
    private TeachevaluationService teachevaluationService;
    @Autowired
    private StaffService staffService;
    @GetMapping("/score")
    public String score(){
        return "member-score";
    }
    @GetMapping("/echarts1")
    public String echarts1(){
        return "echarts1";
    }
    @GetMapping("/Scorerecode")
    @ResponseBody
    public Object Scorerecode(){
        Map<String,List<Float>> map=new HashMap<String,List<Float>>();
        List<Evaluationtype> list=evaluationtypeService.list();
        List<String> integerList=list.stream().map(x->x.getName()).collect(Collectors.toList());
        ReportDataBean reportDataBean=new ReportDataBean();
        reportDataBean.setCategories(integerList);
        List<Integer> integerList1=list.stream().map(x->x.getId()).collect(Collectors.toList());
        System.out.println(integerList1.toString());
        QueryWrapper<Evaluationscore> qw=new QueryWrapper<>();
        qw.in("indicatorid",integerList1);
        List<Evaluationscore> evaluationscoreList=evaluationscoreService.list(qw);
        List<Float> floats=new ArrayList<>();
        for(Integer name:integerList1){
          List<Evaluationscore> list3=  evaluationscoreList.stream().filter(x->x.getIndicatorid().equals(name)).collect(Collectors.toList());
          List<Float> integerList4=list3.stream().map(x->x.getScore()).collect(Collectors.toList());
            floats.add(integerList4.get(0));
            map.put("s"+name.toString(),integerList4);
        }
        reportDataBean.setData(floats);
        System.out.println(reportDataBean.getCategories().toString());
        System.out.println(reportDataBean.getData().toString());
        RetJson retJson=new RetJson();
        retJson.setData(map);

        return retJson;
    }
    @GetMapping("/echarts2")
    public String echarts2(){
        return "echarts2";
    }
    @GetMapping("/allrecode")
    @ResponseBody
    public RetJson allrecode(Integer page,Integer limit){
            RetJson retJson = new RetJson();
            System.err.println(page+"--"+limit);
            Page<Evaluationscore> coursePage = evaluationscoreService.page(new Page<>(page,limit));
            System.out.println(coursePage.getRecords().toString());
            List<EvaluationscoreVo> list=new ArrayList<>();
            for(Evaluationscore evaluationscore:coursePage.getRecords()){
                    EvaluationscoreVo evaluationscoreVo=new EvaluationscoreVo();
                    BeanUtils.copyProperties(evaluationscore, evaluationscoreVo);
                Evaluationtype evaluationtype=evaluationtypeService.qName(evaluationscore.getIndicatorid());
                Teachevaluation teachevaluation=  teachevaluationService.typeName(evaluationscore.getTaskid());
                Staff staff=  staffService.queryName(evaluationscore.getTeacherid());

                  evaluationscoreVo.setTaskname(teachevaluation.getName());
                  evaluationscoreVo.setTeachername(staff.getName());
                  evaluationscoreVo.setIndicatorname(evaluationtype.getName());
                  list.add(evaluationscoreVo);
            }
            retJson.setCode(0);
            retJson.setMsg("ok");
            retJson.setCount((int) coursePage.getTotal());
            retJson.setData(list);
            return retJson;
        }
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        try{
            QueryWrapper<Evaluationscore> q=new QueryWrapper<>();
            q.eq("id",id);
            List<Evaluationscore> list=evaluationscoreService.list(q);
            List<EvaluationscoreVo> list1=new ArrayList<>();
            for(Evaluationscore evaluationscore:list){
                EvaluationscoreVo evaluationscoreVo=new EvaluationscoreVo();
                BeanUtils.copyProperties(evaluationscore, evaluationscoreVo);
                Evaluationtype evaluationtype=evaluationtypeService.qName(evaluationscore.getIndicatorid());
                Teachevaluation teachevaluation=  teachevaluationService.typeName(evaluationscore.getTaskid());
                Staff staff=  staffService.queryName(evaluationscore.getTeacherid());

                evaluationscoreVo.setTaskname(teachevaluation.getName());
                evaluationscoreVo.setTeachername(staff.getName());
                evaluationscoreVo.setIndicatorname(evaluationtype.getName());
                list1.add(evaluationscoreVo);
            }
            model.addAttribute("SoreType",list1);
        }catch (Exception e){
        }
        return "Scoreinfo";
    }
    @PostMapping("/update")
    @ResponseBody
    public Object update(@RequestBody Evaluationscore evaluationscore){
        try{
            evaluationscoreService.saveOrUpdate(evaluationscore);
            return RetJson.ok();
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable Integer id){
        System.out.println("11111111");
        try {
            evaluationscoreService.removeById(id);
            return RetJson.ok("删除成功");
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
}
