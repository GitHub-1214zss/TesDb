package com.example.demo.controll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.comm.RetJson;
import com.example.demo.domain.*;
import com.example.demo.domain.Vo.IndicatorVo;
import com.example.demo.domain.Vo.ScodeVo;
import com.example.demo.service.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@ApiModel("指标控制器")
@RequestMapping("/Indicator")
public class IndicatorControll {
    @Autowired
    private IndicatorService indicatorService;
    @Autowired
    private RIlternativeService ilternativeService;
    @Autowired
    private EvaluationtypeService evaluationtypeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StudentsService studentsService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private TimetableService timetableService;
    @Autowired
    private RclassService rclassService;
    @Autowired
    private EvaluationscoreService evaluationscoreService;
    @Autowired
    private TeachevaluationService teachevaluationService;
    @Autowired
    private RecordService recordService;
    @GetMapping("/indordetails/{id}")
    public String indordetails(@PathVariable Integer id, Model model) {
        RetJson retJson = new RetJson();
        Indicator indicator=indicatorService.q(id);
        QueryWrapper<Rlternative> q=new QueryWrapper<>();
        q.eq("target",id);
        List<Rlternative> rlternatives=ilternativeService.list(q);
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount(rlternatives.size());
        retJson.setData(rlternatives);
        model.addAttribute("indicatorsss",indicator);
        model.addAttribute("rlternatives",rlternatives);
        return "indordetailsaddd";
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable Integer id) {
        System.out.println("11111111");
        try {
            indicatorService.removeById(id);
            QueryWrapper<Rlternative> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("target",id);
            ilternativeService.remove(queryWrapper);
            return RetJson.ok("删除成功");
        } catch (Exception e) {
            return RetJson.err(100, e.getMessage());
        }
    }
    @GetMapping("/add")
    public String add(Model model){
        List<Evaluationtype> list=evaluationtypeService.list();
        System.out.println(list.toString());
        model.addAttribute("add",list);
        return "add";
    }
    @PostMapping("/addI")
    @ResponseBody
    public Object addI(@RequestBody IndicatorVo indicatorVo, Model model){
        Indicator indicator=new Indicator();
        indicator.setIndicatorname(indicatorVo.getIndicatorname());
        indicator.setSortcode(indicatorVo.getSortcode());
        indicator.setTypeid(indicatorVo.getTypeid());
        indicator.setWeight(indicatorVo.getWeight());
        indicatorService.save(indicator);
        //指标保存
        Indicator indicator1=indicatorService.qId(indicatorVo.getIndicatorname());
        for(int i=0;i<indicatorVo.getBoxIds().length;i++){
                Rlternative rlternative=new Rlternative();
                rlternative.setContent(indicatorVo.getBoxIds()[i]);
                rlternative.setTarget(indicator1.getId());
                rlternative.setScore(Float.parseFloat(indicatorVo.getBoxType()[i]));
                ilternativeService.save(rlternative);
        }
        return RetJson.ok();
    }
    @PostMapping("/updateI")
    @ResponseBody
    public Object updateI(@RequestBody IndicatorVo indicatorVo, Model model){
        Indicator indicator=new Indicator();
        QueryWrapper<Rlternative> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("target",indicatorVo.getId());
        List<Rlternative> list=ilternativeService.list(queryWrapper);
        List<Integer> integerList=list.stream().map(x->x.getId()).collect(Collectors.toList());
        //原有指标的备选项的id
        List<Integer> integers=new ArrayList<>();
        for(int k=0;k<integerList.size();k++){
            if(Arrays.asList(indicatorVo.getBoxType1()).contains(integerList.get(k).toString())){
//                integerList.get(k);
//                integers = integerList.stream().filter(x->!x.equals(s)).collect(Collectors.toList());
                integers.add(integerList.get(k));
            }
        }
        System.out.println(integers.toString());//要修改的ID
        for(Integer id:integers){
            integerList.remove(id);
        }
        ilternativeService.removeByIds(integerList);
        Indicator indicator2=indicatorService.q(indicatorVo.getId());
        indicator.setId(indicatorVo.getId());
        indicator.setIndicatorname(indicatorVo.getIndicatorname());
        indicator.setSortcode(indicatorVo.getSortcode());
        if(indicatorVo.getParentid()!=null){
            indicator.setParentid(indicatorVo.getParentid());
        }
        indicator.setTypeid(indicator2.getTypeid());
        indicator.setWeight(indicatorVo.getWeight());
        indicatorService.saveOrUpdate(indicator);
        //指标保存

        Indicator indicator1=indicatorService.qId(indicatorVo.getIndicatorname());
        for(int i=0;i<indicatorVo.getBoxType1().length;i++){
            if(!indicatorVo.getBoxType1()[i].equals("")){
                Rlternative rlternative=new Rlternative();
                rlternative.setId(Integer.parseInt(indicatorVo.getBoxType1()[i]));
                rlternative.setContent(indicatorVo.getBoxIds()[i]);
                rlternative.setTarget(indicator1.getId());
                rlternative.setScore(Float.parseFloat(indicatorVo.getBoxType()[i]));
                ilternativeService.saveOrUpdate(rlternative);
            }else {
                Rlternative rlternative=new Rlternative();
                rlternative.setContent(indicatorVo.getBoxIds()[i]);
                rlternative.setTarget(indicator1.getId());
                rlternative.setScore(Float.parseFloat(indicatorVo.getBoxType()[i]));
                ilternativeService.save(rlternative);
            }

        }
        return RetJson.ok();
    }
    @GetMapping("/code/{taskId}")
    @ApiModelProperty("111111")
    @ResponseBody
    public Object code(@PathVariable Integer taskId,Model model){
        RetJson retJson=new RetJson();
        QueryWrapper<Staff> q=new QueryWrapper<>();
        q.eq("duty","教师");
        List<Staff> staffList=staffService.list(q);
        //所有老师的ID
        List<Integer> integerList=staffList.stream().map(x->x.getId()).collect(Collectors.toList());
        System.out.println(integerList.toString());
        List<ScodeVo> scodeVoList=new ArrayList<>();
        for (Integer integer:integerList){
            Integer sum=0;
            Integer teacherSum=0;
            float summm=0;
            ScodeVo scodeVo=new ScodeVo();
            List<Staff> staffList1=staffList.stream().filter(x->x.getId().equals(integer)).collect(Collectors.toList());
            //教师名字

            scodeVo.setName(staffList1.get(0).getName());
            //教师部门
            QueryWrapper<Department> departmentQueryWrapper=new QueryWrapper<>();
            departmentQueryWrapper.eq("id",staffList1.get(0).getDepartmentid());
            List<Department> departmentList=departmentService.list(departmentQueryWrapper);
            scodeVo.setDname(departmentList.get(0).getName());

            QueryWrapper<Staff> staffQueryWrapper2 = new QueryWrapper<>();
            staffQueryWrapper2.eq("departmentid", staffList1.get(0).getDepartmentid());
            staffQueryWrapper2.eq("duty","教师");
            List<Staff> studengtsList3 =staffService.list(staffQueryWrapper2);
            List<Staff> studengtsList6 =studengtsList3.stream().filter(x->x.getId()!=integer).collect(Collectors.toList());
            if(studengtsList6.size()==0){
                scodeVo.setThisSum(0);
            }if(studengtsList6.size()>0) {
                scodeVo.setThisSum(studengtsList6.size());
            }
            QueryWrapper<Evaluationscore> evaluationscoreQueryWrapper=new QueryWrapper<>();
            evaluationscoreQueryWrapper.eq("teacherid",integer);
            evaluationscoreQueryWrapper.eq("taskid",taskId);
            List<Evaluationscore> evaluationscoreList=evaluationscoreService.list(evaluationscoreQueryWrapper);

            QueryWrapper<Timetable> timetableQueryWrapper=new QueryWrapper<>();
            timetableQueryWrapper.eq("teacherid",integer);
            List<Timetable> timetableList=timetableService.list(timetableQueryWrapper);
            List<Integer> integerList1=timetableList.stream().map(x->x.getClassid()).distinct().collect(Collectors.toList());
            QueryWrapper<Studengts> queryWrapper=new QueryWrapper<>();
            queryWrapper.in("classid",integerList1);
            List<Studengts> studengtsList=studentsService.list(queryWrapper);

            //应评学生人数
            Integer sun=studengtsList.size();
            scodeVo.setStudentSum(sun);
            System.out.println("应评人数=============="+sun);
            //实评学生人数
            List<Integer> integerList2=timetableList.stream().map(x->x.getCourseid()).distinct().collect(Collectors.toList());
            //几门课程
            if(integerList2.size()>0){
                QueryWrapper<Record> rlternativeQueryWrapper = new QueryWrapper<>();
                rlternativeQueryWrapper.in("courseid", integerList2);
                rlternativeQueryWrapper.select("DISTINCT courseid,userid,taskid");
                List<Record> rlternatives =recordService.list(rlternativeQueryWrapper);
                System.out.println(rlternatives.toString());
                List<Record> rlternatives1 =rlternatives.stream().filter(x->x.getTaskid().equals(taskId)).collect(Collectors.toList());
                System.out.println(rlternatives1.toString());
//                List<Integer> integerList3=rlternatives1.stream().map(x->x.getUserid()).collect(Collectors.toList());
//                System.out.println(integerList3.toString());
//                QueryWrapper<Studengts> studengtsQueryWrapper = new QueryWrapper<>();
//                studengtsQueryWrapper.in("id", integerList3);
//                List<Studengts> studengtsList1 =studentsService.list(studengtsQueryWrapper);
                if (rlternatives1.size()==0){
                    scodeVo.setResultSum(0);
                    scodeVo.setRthisSum(0);
                }if (rlternatives1.size()>0){
                    List<Integer> integerList3=rlternatives1.stream().map(x->x.getUserid()).collect(Collectors.toList());
                    System.out.println(integerList3.toString());
                    QueryWrapper<Studengts> studengtsQueryWrapper = new QueryWrapper<>();
                    studengtsQueryWrapper.in("id", integerList3);
                    List<Studengts> studengtsList1 =studentsService.list(studengtsQueryWrapper);
                    scodeVo.setResultSum(studengtsList1.size());
                    //教师互评人数
                    QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
                    staffQueryWrapper.in("id", integerList3);
                    List<Staff> studengtsList2 =staffService.list(staffQueryWrapper);
                    List<Staff> studengtsList5=studengtsList2.stream().filter(x->x.getId()!=integer && x.getDuty().equals("教师")).collect(Collectors.toList());
                    if(studengtsList5.size()==0){
                        teacherSum=0;
                    }if(studengtsList5.size()>0) {
                        teacherSum = studengtsList5.size();
                    }
                    scodeVo.setRthisSum(teacherSum);
                }
                //实评学生人数
                float wight1=0F;
                float wight2=0F;
                float wight3=0F;
                float wight4=0F;
                float wight5=0F;
                List<Evaluationscore> evaluationscoreList2=evaluationscoreList.stream().filter(x->x.getIndicatorid().equals(2)).collect(Collectors.toList());
                if(evaluationscoreList2.size()==0){
                    scodeVo.setScore(0);
                    wight1=0F;
                }if(evaluationscoreList2.size()>0){
                    scodeVo.setScore(evaluationscoreList2.get(0).getScore().intValue());
                    wight1=(float) 0.3;
                }
                List<Evaluationscore> evaluationscoreList3=evaluationscoreList.stream().filter(x->x.getIndicatorid().equals(1)).collect(Collectors.toList());
                if(evaluationscoreList3.size()==0){
                    scodeVo.setTeacherscore(0);
                    wight2=0F;
                }if(evaluationscoreList3.size()>0){
                    scodeVo.setTeacherscore(evaluationscoreList3.get(0).getScore().intValue());
                    wight2=(float) 0.15;
                }
                List<Evaluationscore> evaluationscoreList4=evaluationscoreList.stream().filter(x->x.getIndicatorid().equals(3)).collect(Collectors.toList());
                if(evaluationscoreList4.size()==0){
                    scodeVo.setThisScore(0);
                    wight3=0F;
                }if(evaluationscoreList4.size()>0){
                    scodeVo.setThisScore(evaluationscoreList4.get(0).getScore().intValue());
                    wight3=(float) 0.15;
                }
                List<Evaluationscore> evaluationscoreList5=evaluationscoreList.stream().filter(x->x.getIndicatorid().equals(4)).collect(Collectors.toList());
                if(evaluationscoreList5.size()==0){
                    scodeVo.setJiaoScore(0);
                    wight4=0F;
                }if(evaluationscoreList5.size()>0){
                    scodeVo.setJiaoScore(evaluationscoreList5.get(0).getScore().intValue());
                    wight4=(float) 0.2;
                }
                List<Evaluationscore> evaluationscoreList6=evaluationscoreList.stream().filter(x->x.getIndicatorid().equals(5)).collect(Collectors.toList());
                if(evaluationscoreList6.size()==0){
                    scodeVo.setSumScore(0);
                    wight5=0F;
                }if(evaluationscoreList6.size()>0){
                    scodeVo.setSumScore(evaluationscoreList6.get(0).getScore().intValue());
                    wight5=(float) 0.25;
                }
                if((wight1+wight2+wight3+wight4+wight5)!=0F){
                summm=(scodeVo.getScore()*wight1+scodeVo.getTeacherscore()*wight2+scodeVo.getThisScore()*wight3+scodeVo.getJiaoScore()*wight4+scodeVo.getSumScore()*wight5)/(wight1+wight2+wight3+wight4+wight5);
                    BigDecimal b  =   new BigDecimal(summm);


                    float   f1   =  b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                scodeVo.setSunCode(f1);
                }else {
                    scodeVo.setSunCode(0F);
                }
//                //教师互评人数
//                QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
//                staffQueryWrapper.in("id", integerList3);
//                List<Staff> studengtsList2 =staffService.list(staffQueryWrapper);
//                List<Staff> studengtsList5=studengtsList2.stream().filter(x->x.getId()!=integer && x.getDuty().equals("教师")).collect(Collectors.toList());
//                if(studengtsList5.size()==0){
//                    teacherSum=0;
//                }if(studengtsList5.size()>0) {
//                    teacherSum = studengtsList5.size();
//                }
//                scodeVo.setRthisSum(teacherSum);
                scodeVoList.add(scodeVo);
            }
        }
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount(scodeVoList.size());
        retJson.setData(scodeVoList);
        return retJson;
    }
    @GetMapping("/Scode")
    public String Scode(Model model){
        QueryWrapper<Teachevaluation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status","0");
        List<Teachevaluation> allTask = teachevaluationService.list(queryWrapper);

        List<Teachevaluation> allTask1=new ArrayList<>();
        Date date=new Date();
        SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime=sdft.format(date);
        System.out.println(nowTime);
        for(Teachevaluation teachevaluation:allTask){
            if(teachevaluation.getEnddata().getTime()>date.getTime()){
                allTask1.add(teachevaluation);
            }
        }
        model.addAttribute("allTask1",allTask1);
        return "Code";
    }
}
