package com.example.demo.controll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.comm.RetJson;
import com.example.demo.domain.*;
import com.example.demo.domain.DuiXiang.TeachType;
import com.example.demo.domain.Vo.IndicatorVo;
import com.example.demo.service.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@ApiModel("班级控制器")
@RequestMapping("/Pingjia")
public class TeachervaluationControll {
    @Autowired
    private TeachevaluationService teachevaluationService;
    @Autowired
    private EvaluationtypeService evaluationtypeService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private TimetableService timetableService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private RclassService rclassService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private IndicatorService indicatorService;
    @Autowired
    private RIlternativeService rIlternativeService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private EvaluationscoreService evaluationscoreService;

    @GetMapping("/Teachertask")
    @ApiModelProperty(value = "2222")
    public String Teachertask(HttpSession session, Model model, Integer courseId, Integer teacherId, Integer taskId) {
//        System.out.println(teachType.getCoursename()+"=========================="+teachType.getStaffname());
        List<IndicatorVo> ret = new ArrayList<>();
        Evaluationtype evaluationtype = evaluationtypeService.queryName("学生评价");
        System.out.println(evaluationtype.toString());

        QueryWrapper<Indicator> qwIndicator = new QueryWrapper<>();
        qwIndicator.eq("typeid", evaluationtype.getId());
        List<Indicator> indicators = indicatorService.list(qwIndicator);
        System.out.println(indicators.toString());

        QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
        qwRlternative.in("target", indicators.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Rlternative> allRlternative = rIlternativeService.list(qwRlternative);

        int index = 0;
        for (Indicator indicator : indicators) {
            index++;
            IndicatorVo vo = new IndicatorVo();
            BeanUtils.copyProperties(indicator, vo);
            vo.setIndicatorname(index + "." + vo.getIndicatorname());
            List<Rlternative> items = allRlternative.stream().filter(x -> x.getTarget().equals(indicator.getId())).collect(Collectors.toList());
            int xh = 65;
            for (Rlternative item : items) {
                char c = (char) xh;
                item.setContent("(" + c + ")" + item.getContent());
                xh++;
            }

            vo.setItems(items);

            ret.add(vo);
        }
        model.addAttribute("typeId", evaluationtype.getId());
        session.setAttribute("vos", ret);
        model.addAttribute("courseId", courseId);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("taskId", taskId);
        return "Teachertask";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        try {
            QueryWrapper<Teachevaluation> q = new QueryWrapper<>();
            q.eq("id", id);
            List<Teachevaluation> list = teachevaluationService.list(q);
            System.out.println("2222222");
            for (Teachevaluation teachevaluation : list) {
                if (teachevaluation.getStatus().equals("0")) {
                    teachevaluation.setStatus("正常");
                }
                if (teachevaluation.getStatus().equals("1")) {
                    teachevaluation.setStatus("失效");
                }
                model.addAttribute("teachevaluation", teachevaluation);
            }


        } catch (Exception e) {

        }
        return "details";
    }

    @GetMapping("/teacher")
    public String admin() {
        System.out.println("进入");
        return "Teachertype";
    }
    @GetMapping("/zhibiao")
    public String zhibiao() {
        return "member-zhibiao";
    }
    @GetMapping("/allallzhibiao")
    @ResponseBody
    public Object allallzhibiao(Integer page,Integer limit) {
        RetJson retJson = new RetJson();
        Page<Indicator> page1=indicatorService.page(new Page<>(page,limit));
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount((int) page1.getTotal());
        retJson.setData(page1.getRecords());

        return retJson;
    }
    @GetMapping("/indordetails/{id}")
    public String indordetails(@PathVariable Integer id,Model model) {
        RetJson retJson = new RetJson();
        Indicator indicator=indicatorService.q(id);
        QueryWrapper<Rlternative> q=new QueryWrapper<>();
        q.eq("target",id);
        List<Rlternative> rlternatives=rIlternativeService.list(q);
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount(rlternatives.size());
        retJson.setData(rlternatives);
        model.addAttribute("indicatorsss",indicator);
        model.addAttribute("rlternatives",rlternatives);
        return "indordetails";
    }







    @GetMapping("/teacher1")
    public String admin1(Model model) {
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
        model.addAttribute("allTask", allTask1);
        return "Teachertype-member";
    }

    @GetMapping("/addtype")
    public String addtype(Model model) {
        List<Evaluationtype> list = evaluationtypeService.list();
//        for (Evaluationtype e:list){
//
//        }
        model.addAttribute("evalua", list);
        return "teactype-add";
    }

    @GetMapping("/type3/{typeId}")
    @ApiModelProperty(value = "及记录")
    @ResponseBody
    public Object teach(@PathVariable Integer typeId, HttpSession session) {
        RetJson retJson = new RetJson();
        List<TeachType> list = new ArrayList();
        if (typeId != null) {
            Studengts studengts = (Studengts) session.getAttribute("students");
            QueryWrapper<Timetable> timetableQueryWrapper=new QueryWrapper<>();
            timetableQueryWrapper.eq("classid",studengts.getClassid());
            List<Timetable> timetables = timetableService.list(timetableQueryWrapper);
            List<Rclass> allRclass = rclassService.list();
//            Record record= (Record) session.getAttribute("recode");
//            //用户ID
//            System.out.println(record);
//            Integer setUserid=record.getUserid();
            QueryWrapper<Record> qw = new QueryWrapper();
            qw.eq("taskId", typeId);
            if (studengts != null) {
                Integer students = studengts.getId();
                qw.eq("userId", students);
                System.out.println(students);
            }
            List<Record> allRecord = recordService.list(qw);

            for (Timetable timetable : timetables) {
                Rclass rclass = allRclass.stream().filter(x -> x.getId().equals(timetable.getClassid())).findFirst().orElse(new Rclass());
                Course course = courseService.queryCourse(timetable);
                Staff staff = staffService.queryStaff(timetable);
                //Department department = departmentService.queryDepartment(staff);
                TeachType teachType = new TeachType();
                BeanUtils.copyProperties(timetable, teachType);

                teachType.setClassname(rclass.getName());
                teachType.setCoursename(course.getName());
                teachType.setStaffname(staff.getName());
                List<Record> find = allRecord.stream().filter(x ->
                        x.getCourseid().equals(timetable.getCourseid())
                                && x.getTeacherid().equals(timetable.getTeacherid())).collect(Collectors.toList());
                teachType.setExistHis(find.size() == 0 ? 0 : 1);
                list.add(teachType);
                System.out.println(list.toString());
            }
        }
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount(list.size());
        retJson.setData(list);
        return retJson;
    }

    @GetMapping("/type")
    @ResponseBody
    public Object type(Integer page, Integer limit) {
        RetJson retJson = new RetJson();
        Page<Teachevaluation> list = teachevaluationService.page(new Page<>(page, limit));
        Date now=new Date();

        list.getRecords().forEach(x->{
            x.setStatus(x.getEnddata().before(now) ?"失效":"正常");
        });

        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount((int) list.getTotal());
        retJson.setData(list.getRecords());
        return retJson;
    }

    @GetMapping("/type1")
    @ResponseBody
    public Object type1(Integer page, Integer limit, HttpSession model) {
        RetJson retJson = new RetJson();
        List list1 = new ArrayList();
        Page<Teachevaluation> list = teachevaluationService.page(new Page<>(page, limit));
        for (Teachevaluation teachevaluation : list.getRecords()) {
            list1.add(teachevaluation.getStatus());

        }
        model.setAttribute("status", list1);
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount((int) list.getTotal());
        retJson.setData(list.getRecords());
        return retJson;
    }

    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestBody Teachevaluation teachevaluation) {
        try {
            teachevaluation.setStatus("0");
//            Date afterDate = new Date(teachevaluation.getStartdata().getTime()-time);//60秒后的时间
//            teachevaluation.setStartdata(afterDate);
//            Date beforeDate = new Date(teachevaluation.getEnddata().getTime()-time);//60秒前的时间
//            teachevaluation.setEnddata(beforeDate);
            teachevaluationService.save(teachevaluation);
            return RetJson.ok(teachevaluation);
        } catch (Exception e) {
            return RetJson.err(100, e.getMessage());
        }
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable Integer id) {
        System.out.println("11111111");
        try {
            teachevaluationService.removeById(id);
            return RetJson.ok("删除成功");
        } catch (Exception e) {
            return RetJson.err(100, e.getMessage());
        }
    }

    @PostMapping("/update")
    @ResponseBody
    public Object update(@RequestBody Teachevaluation teachevaluation) {
        System.out.println(teachevaluation.getStatus());
        System.out.println(teachevaluation.toString());
        try {
            if (teachevaluation.getStatus().equals("正常")) {
                teachevaluation.setStatus("0");
            }
            if (teachevaluation.getStatus().equals("失效")) {
                teachevaluation.setStatus("1");
            }
            teachevaluationService.saveOrUpdate(teachevaluation);
            return RetJson.ok();
        } catch (Exception e) {
            return RetJson.err(100, e.getMessage());
        }

    }

    @PostMapping("/saveRode")
    @ResponseBody
    public Object saveRode(String name, HttpSession session, Integer courseId, Integer teacherId, Integer taskId, Integer typeId) {
        System.out.println(name);
        String[] splitAddress = name.split(",|\\|");
        System.out.println(splitAddress);
        //答案备选项ID
        List list = new ArrayList();
        //指标问题ID
        List list1 = new ArrayList();
        for (int i = 0; i < splitAddress.length; i++) {
            if (i % 2 != 0) {
                list.add(splitAddress[i]);
            } else {
                list1.add(splitAddress[i]);
            }
        }
        Integer id = Integer.parseInt(String.valueOf(list1.get(0)));
        Rlternative rlternative = rIlternativeService.querID(id);
        //任务类型ID
        Studengts studengts = (Studengts) session.getAttribute("students");
        Date currentTime = new Date();
        List<Record> list2 = new ArrayList();
        for (int k = 0; k < list1.size(); k++) {
            Record record1 = new Record();
            record1.setUserid(studengts.getId());
            record1.setTeacherid(teacherId);
            record1.setCourseid(courseId);
            record1.setEvaluatetime(currentTime);
            record1.setTaskid(taskId);
            Integer s1 = Integer.parseInt(String.valueOf(list1.get(k)));
            record1.setTargetid(s1);
            Integer s = Integer.parseInt(String.valueOf(list.get(k)));
            record1.setItemid(s);
            list2.add(record1);
        }
        recordService.saveBatch(list2);
        calculateScore(teacherId,taskId,typeId);
        return RetJson.ok("评教成功");
    }

    //查看已经评论列表================================================
    @GetMapping("/Teachertask1")
    public Object Teachertask1(Integer courseId, Integer teacherId, Integer taskId, HttpSession session, Model model) {
        List<IndicatorVo> ret = new ArrayList<>();
        Evaluationtype evaluationtype = evaluationtypeService.queryName("学生评价");
        System.out.println(evaluationtype.toString());

        QueryWrapper<Indicator> qwIndicator = new QueryWrapper<>();
        qwIndicator.eq("typeid", evaluationtype.getId());
        List<Indicator> indicators = indicatorService.list(qwIndicator);
        System.out.println(indicators.toString());

        QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
        qwRlternative.in("target", indicators.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Rlternative> allRlternative = rIlternativeService.list(qwRlternative);

        int index = 0;
        for (Indicator indicator : indicators) {
            index++;
            IndicatorVo vo = new IndicatorVo();
            BeanUtils.copyProperties(indicator, vo);
            vo.setIndicatorname(index + "." + vo.getIndicatorname());
            List<Rlternative> items = allRlternative.stream().filter(x -> x.getTarget().equals(indicator.getId())).collect(Collectors.toList());
            int xh = 65;
            for (Rlternative item : items) {
                char c = (char) xh;
                item.setContent("(" + c + ")" + item.getContent());
                xh++;
            }

            vo.setItems(items);

            ret.add(vo);
        }
        model.addAttribute("vos7", ret);
        Studengts studengts = (Studengts) session.getAttribute("students");
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courseId", courseId);
        queryWrapper.eq("teacherId", teacherId);
        queryWrapper.eq("taskid", taskId);
        queryWrapper.eq("userid", studengts.getId());
        List<Record> list = recordService.list(queryWrapper);
        System.out.println(list.toString());
        List list1 = new ArrayList();
        for (Record record1 : list) {
            list1.add(record1.getTargetid() + "|" + record1.getItemid());
        }
        System.out.println(list1.toString());
        model.addAttribute("eidet", list1);
        return "Teachertask1";
    }

    ////==================================
    //教师评价
    @GetMapping("/teacher2")
    public String admin2(Model model) {
        QueryWrapper<Teachevaluation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status","0");
        List<Teachevaluation> allTask = teachevaluationService.list(queryWrapper);
        List<Teachevaluation> allTask1=new ArrayList<>();
        Date date=new Date();
        SimpleDateFormat sdft=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime=sdft.format(date);
        System.out.println(nowTime);

        for(Teachevaluation teachevaluation:allTask){
            System.out.println(sdft.format(teachevaluation.getEnddata()).toString());
            if(teachevaluation.getEnddata().getTime()>date.getTime()){
                allTask1.add(teachevaluation);
            }
        }
        model.addAttribute("allTask1", allTask1);
        return "Teacherthis";
    }

    @GetMapping("/type4/{typeId}")
    @ApiModelProperty(value = "老师自评")
    @ResponseBody
    public Object teachone(@PathVariable Integer typeId, HttpSession session) {
        RetJson retJson = new RetJson();
        List<TeachType> list = new ArrayList();
        if (typeId != null) {
            List<Rclass> allRclass = rclassService.list();
//            Record record= (Record) session.getAttribute("recode");
//            //用户ID
//            System.out.println(record);
//            Integer setUserid=record.getUserid();
            Staff staff1 = (Staff) session.getAttribute("staff");
            QueryWrapper<Record> qw = new QueryWrapper();
            qw.eq("taskId", typeId);
            if (staff1 != null) {
                Integer students = staff1.getId();
                qw.eq("userId", students);
                QueryWrapper<Timetable> q = new QueryWrapper();
                q.eq("teacherId", students);
                List<Timetable> timetables = timetableService.list(q);
                List<Record> allRecord = recordService.list(qw);
                for (Timetable timetable : timetables) {
                    Rclass rclass = allRclass.stream().filter(x -> x.getId().equals(timetable.getClassid())).findFirst().orElse(new Rclass());
                    Course course = courseService.queryCourse(timetable);
                    Staff staff = staffService.queryStaff(timetable);
                    //Department department = departmentService.queryDepartment(staff);
                    TeachType teachType = new TeachType();
                    BeanUtils.copyProperties(timetable, teachType);
                    teachType.setClassname(rclass.getName());
                    teachType.setCoursename(course.getName());
                    teachType.setStaffname(staff.getName());
                    List<Record> find = allRecord.stream().filter(x ->
                            x.getCourseid().equals(timetable.getCourseid())
                                    && x.getTeacherid().equals(timetable.getTeacherid())).collect(Collectors.toList());
                    teachType.setExistHis(find.size() == 0 ? 0 : 1);
                    list.add(teachType);
                    System.out.println(list.toString());
                }
            }
        }
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount(list.size());
        retJson.setData(list);
        return retJson;
    }

    @GetMapping("/Teachertask2")
    @ApiModelProperty(value = "2222")
    public String Teachertask2(HttpSession model, Model model1, Integer courseId, Integer teacherId, Integer taskId) {
//        System.out.println(teachType.getCoursename()+"=========================="+teachType.getStaffname());
        List<IndicatorVo> ret = new ArrayList<>();
        Evaluationtype evaluationtype = evaluationtypeService.queryName("教师自评");
        QueryWrapper<Indicator> qwIndicator = new QueryWrapper<>();
        qwIndicator.eq("typeid", evaluationtype.getId());
        List<Indicator> indicators = indicatorService.list(qwIndicator);
        System.out.println(indicators.toString());
        QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
        qwRlternative.in("target", indicators.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Rlternative> allRlternative = rIlternativeService.list(qwRlternative);
        int index = 0;
        for (Indicator indicator : indicators) {
            index++;
            IndicatorVo vo = new IndicatorVo();
            BeanUtils.copyProperties(indicator, vo);
            vo.setIndicatorname(index + "." + vo.getIndicatorname());
            List<Rlternative> items = allRlternative.stream().filter(x -> x.getTarget().equals(indicator.getId())).collect(Collectors.toList());
            int xh = 65;
            for (Rlternative item : items) {
                char c = (char) xh;
                item.setContent("(" + c + ")" + item.getContent());
                xh++;
            }

            vo.setItems(items);

            ret.add(vo);
        }
        model1.addAttribute("typeId", evaluationtype.getId());
        model.setAttribute("vos2", ret);
        model1.addAttribute("courseId", courseId);
        model1.addAttribute("teacherId", teacherId);
        model1.addAttribute("taskId", taskId);
        return "Teachertask2";
    }

    @PostMapping("/saveRode1")
    @ResponseBody
    public Object saveRode1(String name, HttpSession session, Integer courseId, Integer teacherId, Integer taskId, Integer typeId) {
        String[] splitAddress = name.split(",|\\|");
        //答案备选项ID
        List list = new ArrayList();
        //指标问题ID
        List list1 = new ArrayList();
        for (int i = 0; i < splitAddress.length; i++) {
            if (i % 2 != 0) {
                list.add(splitAddress[i]);
            } else {
                list1.add(splitAddress[i]);
            }
        }
        Integer id = Integer.parseInt(String.valueOf(list1.get(0)));
        Rlternative rlternative = rIlternativeService.querID(id);
        //任务类型ID
        Staff studengts = (Staff) session.getAttribute("staff");
        Date currentTime = new Date();
        List<Record> list2 = new ArrayList();
        for (int k = 0; k < list1.size(); k++) {
            Record record1 = new Record();
            record1.setUserid(studengts.getId());
            record1.setTeacherid(teacherId);
            record1.setCourseid(courseId);
            record1.setEvaluatetime(currentTime);
            record1.setTaskid(taskId);
            Integer s1 = Integer.parseInt(String.valueOf(list1.get(k)));
            record1.setTargetid(s1);
            Integer s = Integer.parseInt(String.valueOf(list.get(k)));
            record1.setItemid(s);
            list2.add(record1);
        }
        recordService.saveBatch(list2);
        calculateScore(teacherId,taskId,typeId);
        return RetJson.ok("自评成功");
    }

    //查看已经评论列表================================================
    @GetMapping("/Teachertask3")
    public Object Teachertask3(HttpSession session, Integer courseId, Integer teacherId, Integer taskId, Model model) {
        List<IndicatorVo> ret = new ArrayList<>();
        Evaluationtype evaluationtype = evaluationtypeService.queryName("教师自评");
        QueryWrapper<Indicator> qwIndicator = new QueryWrapper<>();
        qwIndicator.eq("typeid", evaluationtype.getId());
        List<Indicator> indicators = indicatorService.list(qwIndicator);
        System.out.println(indicators.toString());
        QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
        qwRlternative.in("target", indicators.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Rlternative> allRlternative = rIlternativeService.list(qwRlternative);
        int index = 0;
        for (Indicator indicator : indicators) {
            index++;
            IndicatorVo vo = new IndicatorVo();
            BeanUtils.copyProperties(indicator, vo);
            vo.setIndicatorname(index + "." + vo.getIndicatorname());
            List<Rlternative> items = allRlternative.stream().filter(x -> x.getTarget().equals(indicator.getId())).collect(Collectors.toList());
            int xh = 65;
            for (Rlternative item : items) {
                char c = (char) xh;
                item.setContent("(" + c + ")" + item.getContent());
                xh++;
            }

            vo.setItems(items);

            ret.add(vo);
        }
        model.addAttribute("vos8", ret);
        Staff studengts = (Staff) session.getAttribute("staff");
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courseId", courseId);
        queryWrapper.eq("teacherId", teacherId);
        queryWrapper.eq("taskid", taskId);
        queryWrapper.eq("userid", studengts.getId());
        List<Record> list = recordService.list(queryWrapper);
        System.out.println(list.toString());
        List list1 = new ArrayList();
        for (Record record1 : list) {
            list1.add(record1.getTargetid() + "|" + record1.getItemid());
        }
        System.out.println(list1.toString());
        model.addAttribute("eidet2", list1);
        return "Teachertask3";
    }


    //===================================================
    //教师互评
    @GetMapping("/teacher3")
    public String teacher3(Model model) {
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
        model.addAttribute("allTask3", allTask1);
        return "Teacherthisself";
    }

    @GetMapping("/type5/{typeId}")
    @ApiModelProperty(value = "老师互评")
    @ResponseBody
    public Object teachself(@PathVariable Integer typeId, HttpSession session) {
        RetJson retJson = new RetJson();
        List<TeachType> list = new ArrayList();
        if (typeId != null) {
            List<Rclass> allRclass = rclassService.list();
            Staff staff1 = (Staff) session.getAttribute("staff");
            QueryWrapper<Record> qw = new QueryWrapper();
            qw.eq("taskId", typeId);
            if (staff1 != null) {
                Integer students = staff1.getId();
                //知道哪个人评教的
                qw.eq("userId", students);
                //教师互评的查询
                Integer staff1Id = staff1.getDepartmentid();//部门ID
                QueryWrapper<Staff> qw1 = new QueryWrapper<>();
                qw1.eq("departmentid", staff1Id);  //同一部门
                qw1.ne("id", students);//除了自己之外的老师
                qw1.eq("duty", staff1.getDuty());//是同一个部门老师的
                List<Staff> staffList = staffService.list(qw1);
                //
                List<Record> allRecord = recordService.list(qw);
                for (Staff staff2 : staffList) {
                    //根据不同老师的ID查询他们教的课程
                    QueryWrapper<Timetable> q = new QueryWrapper();
                    q.eq("teacherId", staff2.getId());
                    List<Timetable> timetables = timetableService.list(q);
                    for (Timetable timetable : timetables) {
                        Rclass rclass = allRclass.stream().filter(x -> x.getId().equals(timetable.getClassid())).findFirst().orElse(new Rclass());
                        Course course = courseService.queryCourse(timetable);
                        Staff staff = staffService.queryStaff(timetable);
                        //Department department = departmentService.queryDepartment(staff);
                        TeachType teachType = new TeachType();
                        BeanUtils.copyProperties(timetable, teachType);
                        teachType.setClassname(rclass.getName());
                        teachType.setCoursename(course.getName());
                        teachType.setStaffname(staff.getName());
                        List<Record> find = allRecord.stream().filter(x ->
                                x.getCourseid().equals(timetable.getCourseid())
                                        && x.getTeacherid().equals(timetable.getTeacherid())).collect(Collectors.toList());
                        teachType.setExistHis(find.size() == 0 ? 0 : 1);
                        list.add(teachType);
                        System.out.println(list.toString());
                    }
                }
            }
        }
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount(list.size());
        retJson.setData(list);
        return retJson;
    }

    @GetMapping("/Teachertask4")
    @ApiModelProperty(value = "2222")
    public String Teachertask4(HttpSession model, Model model1, Integer courseId, Integer teacherId, Integer taskId) {
//        System.out.println(teachType.getCoursename()+"=========================="+teachType.getStaffname());
        List<IndicatorVo> ret = new ArrayList<>();
        Evaluationtype evaluationtype = evaluationtypeService.queryName("同行评价");
        QueryWrapper<Indicator> qwIndicator = new QueryWrapper<>();
        qwIndicator.eq("typeid", evaluationtype.getId());
        List<Indicator> indicators = indicatorService.list(qwIndicator);
        System.out.println(indicators.toString());
        QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
        qwRlternative.in("target", indicators.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Rlternative> allRlternative = rIlternativeService.list(qwRlternative);
        int index = 0;
        for (Indicator indicator : indicators) {
            index++;
            IndicatorVo vo = new IndicatorVo();
            BeanUtils.copyProperties(indicator, vo);
            vo.setIndicatorname(index + "." + vo.getIndicatorname());
            List<Rlternative> items = allRlternative.stream().filter(x -> x.getTarget().equals(indicator.getId())).collect(Collectors.toList());
            int xh = 65;
            for (Rlternative item : items) {
                char c = (char) xh;
                item.setContent("(" + c + ")" + item.getContent());
                xh++;
            }
            vo.setItems(items);
            ret.add(vo);
        }
        model1.addAttribute("typeId", evaluationtype.getId());
        model1.addAttribute("vos4", ret);
        model1.addAttribute("courseId", courseId);
        model1.addAttribute("teacherId", teacherId);
        model1.addAttribute("taskId", taskId);
        return "Teachertask4";
    }

    @PostMapping("/saveRode4")
    @ResponseBody
    public Object saveRode4(String name, Integer courseId, Integer teacherId, Integer taskId, Integer typeId, HttpSession session) {
        String[] splitAddress = name.split(",|\\|");
        System.out.println(splitAddress);
        //答案备选项ID
        List list = new ArrayList();
        //指标问题ID
        List list1 = new ArrayList();
        for (int i = 0; i < splitAddress.length; i++) {
            if (i % 2 != 0) {
                list.add(splitAddress[i]);
            } else {
                list1.add(splitAddress[i]);
            }
        }
        Integer id = Integer.parseInt(String.valueOf(list1.get(0)));
        Rlternative rlternative = rIlternativeService.querID(id);
        System.out.println(rlternative);
        //任务类型ID
        Staff studengts = (Staff) session.getAttribute("staff");
        //课程ID
        Date currentTime = new Date();
        List<Record> list2 = new ArrayList();
        for (int k = 0; k < list1.size(); k++) {
            Record record1 = new Record();
            record1.setUserid(studengts.getId());
            record1.setTeacherid(teacherId);
            record1.setCourseid(courseId);
            record1.setEvaluatetime(currentTime);
            record1.setTaskid(taskId);
            Integer s1 = Integer.parseInt(String.valueOf(list1.get(k)));
            record1.setTargetid(s1);
            Integer s = Integer.parseInt(String.valueOf(list.get(k)));
            record1.setItemid(s);
            list2.add(record1);
        }

        recordService.saveBatch(list2);
        calculateScore(teacherId,taskId,typeId);
        return RetJson.ok("互评成功");
    }

    @GetMapping("/Teachertask5")
    public String Teachertask5(Integer courseId, Integer teacherId, Integer taskId, HttpSession session, Model model) {
        List<IndicatorVo> ret = new ArrayList<>();
        Evaluationtype evaluationtype = evaluationtypeService.queryName("同行评价");
        QueryWrapper<Indicator> qwIndicator = new QueryWrapper<>();
        qwIndicator.eq("typeid", evaluationtype.getId());
        List<Indicator> indicators = indicatorService.list(qwIndicator);
        System.out.println(indicators.toString());
        QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
        qwRlternative.in("target", indicators.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Rlternative> allRlternative = rIlternativeService.list(qwRlternative);
        int index = 0;
        for (Indicator indicator : indicators) {
            index++;
            IndicatorVo vo = new IndicatorVo();
            BeanUtils.copyProperties(indicator, vo);
            vo.setIndicatorname(index + "." + vo.getIndicatorname());
            List<Rlternative> items = allRlternative.stream().filter(x -> x.getTarget().equals(indicator.getId())).collect(Collectors.toList());
            int xh = 65;
            for (Rlternative item : items) {
                char c = (char) xh;
                item.setContent("(" + c + ")" + item.getContent());
                xh++;
            }
            vo.setItems(items);
            ret.add(vo);
        }
        model.addAttribute("vos9", ret);
        Staff studengts = (Staff) session.getAttribute("staff");
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courseId", courseId);
        queryWrapper.eq("teacherId", teacherId);
        queryWrapper.eq("taskid", taskId);
        queryWrapper.eq("userid", studengts.getId());
        List<Record> list = recordService.list(queryWrapper);
        System.out.println(list.toString());
        List list1 = new ArrayList();
        for (Record record1 : list) {
            list1.add(record1.getTargetid() + "|" + record1.getItemid());
        }
        System.out.println(list1.toString());
        model.addAttribute("eidet4", list1);
        return "Teachertask5";
    }

    //教研室评价
    @GetMapping("/teacher4")
    public String teacher4(Model model) {
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
        model.addAttribute("allTask", allTask1);
        return "TeachertJiaoX";
    }

    @GetMapping("/type7/{typeId}")
    @ApiModelProperty(value = "教研室评价")
    @ResponseBody
    public Object type7(@PathVariable Integer typeId, HttpSession session) {
        System.out.println("2222222222222222222221111111111111111111111111111");
        RetJson retJson = new RetJson();
        List<TeachType> list = new ArrayList();
        if (typeId != null) {
            List<Rclass> allRclass = rclassService.list();
            Staff staff1 = (Staff) session.getAttribute("staff");
            QueryWrapper<Record> qw = new QueryWrapper();
            qw.eq("taskId", typeId);
            Integer students = staff1.getId();
            //知道哪个人评教的
            qw.eq("userId", students);
            //教研室评价的查询
            Integer staff1Id = staff1.getDepartmentid();//部门ID
            QueryWrapper<Staff> qw1 = new QueryWrapper<>();
            qw1.eq("departmentid", staff1Id);  //同一部门
            qw1.ne("id", students);//除了自己之外的老师
            qw1.eq("duty", "教师");//是同一个部门老师的
            List<Staff> staffList = staffService.list(qw1);
            //
            List<Record> allRecord = recordService.list(qw);
            for (Staff staff2 : staffList) {
                //根据不同老师的ID查询他们教的课程
                QueryWrapper<Timetable> q = new QueryWrapper();
                q.eq("teacherId", staff2.getId());
                List<Timetable> timetables = timetableService.list(q);
                for (Timetable timetable : timetables) {
                    Rclass rclass = allRclass.stream().filter(x -> x.getId().equals(timetable.getClassid())).findFirst().orElse(new Rclass());
                    Course course = courseService.queryCourse(timetable);
                    Staff staff = staffService.queryStaff(timetable);
                    //Department department = departmentService.queryDepartment(staff);
                    TeachType teachType = new TeachType();
                    BeanUtils.copyProperties(timetable, teachType);
                    teachType.setClassname(rclass.getName());
                    teachType.setCoursename(course.getName());
                    teachType.setStaffname(staff.getName());
                    List<Record> find = allRecord.stream().filter(x ->
                            x.getCourseid().equals(timetable.getCourseid())
                                    && x.getTeacherid().equals(timetable.getTeacherid())).collect(Collectors.toList());
                    teachType.setExistHis(find.size() == 0 ? 0 : 1);
                    list.add(teachType);
                    System.out.println(list.toString());
                }
            }
        }

        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount(list.size());
        retJson.setData(list);
        return retJson;
    }

    @GetMapping("/Teachertask6")
    @ApiModelProperty(value = "教研室评价")
    public String Teachertask6(Model model, Integer courseId, Integer teacherId, Integer taskId) {
        int typeId = 4;
        model.addAttribute("courseId", courseId);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("typeId", typeId);

        List<IndicatorVo> ret = new ArrayList<>();
        //查询所有的指标
        QueryWrapper<Indicator> qwIndicator = new QueryWrapper<>();
        qwIndicator.eq("typeid", typeId);
        List<Indicator> indicators = indicatorService.list(qwIndicator);
        //找到说有指标的备选项  30---42的数据
        QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
        //映射查询所有匹配指标的备选项
        qwRlternative.in("target", indicators.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Rlternative> rlternatives = rIlternativeService.list(qwRlternative);

        //第一级指标
        List<Indicator> first = indicators.stream().filter(x -> x.getParentid() == null).sorted(Comparator.comparing(Indicator::getSortcode)).collect(Collectors.toList());
        int xh = 0;
        for (Indicator target : first) {
            xh++;
            addSellfAndChild(xh + ".", target, indicators, rlternatives, ret);
        }
//反射取值
        int sum = 0;
        for (int i = 0; i < ret.size(); i++) {
            //Field[] fields = ret.get(i).getClass().getDeclaredFields();反射
            IndicatorVo oi = ret.get(i);
            if (oi.getItems().isEmpty()) {
                sum = sum + 1;
            }
        }
        model.addAttribute("vot", ret);

        model.addAttribute("votSize", (ret.size() - sum));
        System.out.println(ret.toString());
        return "Teachertask6";
    }

    private void addSellfAndChild(String pref, Indicator mo, List<Indicator> allIndicator, List<Rlternative> allRlternative, List<IndicatorVo> ret) {
        //add self
        IndicatorVo vo = new IndicatorVo();
        BeanUtils.copyProperties(mo, vo);
        vo.setIndicatorname(pref + mo.getIndicatorname());
        //父亲的指标选项
        vo.setItems(allRlternative.stream().filter(x -> x.getTarget().equals(mo.getId())).collect(Collectors.toList()));
        ret.add(vo);
        //children
        List<Indicator> children = allIndicator.stream().filter(x -> x.getParentid() != null && x.getParentid().equals(mo.getId())).sorted(Comparator.comparing(Indicator::getSortcode)).collect(Collectors.toList());
        int xh = 0;
        for (Indicator target : children) {
            xh++;
            addSellfAndChild(pref + xh + ".", target, allIndicator, allRlternative, ret);
        }
    }

    @PostMapping("/saveRode7")
    @ResponseBody
    public Object saveRode7(String name, Integer courseId, Integer teacherId, Integer taskId, Integer typeId, HttpSession session) {
        String[] splitAddress = name.split(",|\\|");
        System.out.println(splitAddress);
        //答案备选项ID
        List list = new ArrayList();
        //指标问题ID
        List list1 = new ArrayList();
        for (int i = 0; i < splitAddress.length; i++) {
            if (i % 2 != 0) {
                list.add(splitAddress[i]);
            } else {
                list1.add(splitAddress[i]);
            }
        }
        Integer id = Integer.parseInt(String.valueOf(list1.get(0)));
        Rlternative rlternative = rIlternativeService.querID(id);
        System.out.println(rlternative);
        //任务类型ID
        Staff studengts = (Staff) session.getAttribute("staff");
        //课程ID
        Date currentTime = new Date();
        List<Record> list2 = new ArrayList();
        for (int k = 0; k < list1.size(); k++) {
            Record record1 = new Record();
            record1.setUserid(studengts.getId());
            record1.setTeacherid(teacherId);
            record1.setCourseid(courseId);
            record1.setEvaluatetime(currentTime);
            record1.setTaskid(taskId);
            Integer s1 = Integer.parseInt(String.valueOf(list1.get(k)));
            record1.setTargetid(s1);
            Integer s = Integer.parseInt(String.valueOf(list.get(k)));
            record1.setItemid(s);
            list2.add(record1);
        }

        recordService.saveBatch(list2);
        calculateScore(teacherId,taskId,typeId);
        return RetJson.ok("互评成功");
    }

    @GetMapping("/Teachertask7")
    public String Teachertask7(Integer courseId, Integer teacherId, Integer taskId, HttpSession session, Model model) {

        int typeId = 4;
        model.addAttribute("taskId", taskId);
        List<IndicatorVo> ret = new ArrayList<>();
        //查询所有的指标
        QueryWrapper<Indicator> qwIndicator = new QueryWrapper<>();
        qwIndicator.eq("typeid", typeId);
        List<Indicator> indicators = indicatorService.list(qwIndicator);
        //找到说有指标的备选项  30---42的数据
        QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
        //映射查询所有匹配指标的备选项
        qwRlternative.in("target", indicators.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Rlternative> rlternatives = rIlternativeService.list(qwRlternative);

        //第一级指标
        List<Indicator> first = indicators.stream().filter(x -> x.getParentid() == null).sorted(Comparator.comparing(Indicator::getSortcode)).collect(Collectors.toList());
        int xh = 0;
        for (Indicator target : first) {
            xh++;
            addSellfAndChild(xh + ".", target, indicators, rlternatives, ret);
        }

        model.addAttribute("vot1", ret);
        Staff studengts = (Staff) session.getAttribute("staff");
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courseId", courseId);
        queryWrapper.eq("teacherId", teacherId);
        queryWrapper.eq("taskid", taskId);
        queryWrapper.eq("userid", studengts.getId());
        List<Record> list = recordService.list(queryWrapper);
        System.out.println(list.toString());
        List list1 = new ArrayList();
        for (Record record1 : list) {
            list1.add(record1.getTargetid() + "|" + record1.getItemid());
        }
        System.out.println(list1.toString());
        model.addAttribute("eidet7", list1);
        return "Teachertask7";
    }

    //系部评价
    @GetMapping("/teacher5")
    public String teacher5(Model model) {
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
        model.addAttribute("allTask", allTask1);
        return "TeachertXX";
    }

    @GetMapping("/type9/{typeId}")
    @ApiModelProperty(value = "系主任评价")
    @ResponseBody
    public Object type9(@PathVariable Integer typeId, HttpSession session) {
        RetJson retJson = new RetJson();
        List<TeachType> list = new ArrayList();
        if (typeId != null) {
            List<Rclass> allRclass = rclassService.list();
            Staff staff1 = (Staff) session.getAttribute("staff");
            QueryWrapper<Record> qw = new QueryWrapper();
            qw.eq("taskId", typeId);
            Integer students = staff1.getId();
            //知道哪个人评教的
            qw.eq("userId", students);
            //系主任评价的查询
            Integer staff1Id = staff1.getDepartmentid();//部门ID
            //管的所有部门
            QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
            departmentQueryWrapper.eq("parentid", staff1Id);
            List<Department> departmentList = departmentService.list();
            //该系所有研究室，下面所有老师
            QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
            staffQueryWrapper.in("departmentid", departmentList.stream().map(x -> x.getId()).collect(Collectors.toList()));
            staffQueryWrapper.eq("duty", "教师");
            List<Staff> staffList = staffService.list(staffQueryWrapper);
            List<Record> allRecord = recordService.list(qw);
            for (Staff staff2 : staffList) {
                //根据不同老师的ID查询他们教的课程
                QueryWrapper<Timetable> q = new QueryWrapper();
                q.eq("teacherId", staff2.getId());
                List<Timetable> timetables = timetableService.list(q);
                for (Timetable timetable : timetables) {
                    Rclass rclass = allRclass.stream().filter(x -> x.getId().equals(timetable.getClassid())).findFirst().orElse(new Rclass());
                    Course course = courseService.queryCourse(timetable);
                    Staff staff = staffService.queryStaff(timetable);
                    //Department department = departmentService.queryDepartment(staff);
                    TeachType teachType = new TeachType();
                    BeanUtils.copyProperties(timetable, teachType);
                    teachType.setClassname(rclass.getName());
                    teachType.setCoursename(course.getName());
                    teachType.setStaffname(staff.getName());
                    List<Record> find = allRecord.stream().filter(x ->
                            x.getCourseid().equals(timetable.getCourseid())
                                    && x.getTeacherid().equals(timetable.getTeacherid())).collect(Collectors.toList());
                    teachType.setExistHis(find.size() == 0 ? 0 : 1);
                    list.add(teachType);
                    System.out.println(list.toString());
                }
            }
        }

        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount(list.size());
        retJson.setData(list);
        return retJson;
    }

    @GetMapping("/Teachertask8")
    @ApiModelProperty(value = "111")
    public String Teachertask8(Model model, Integer courseId, Integer teacherId, Integer taskId) throws IllegalAccessException {
        int typeId = 5;
        model.addAttribute("courseId", courseId);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("typeId", typeId);

        List<IndicatorVo> ret = new ArrayList<>();
        //查询所有的指标
        QueryWrapper<Indicator> qwIndicator = new QueryWrapper<>();
        qwIndicator.eq("typeid", typeId);
        List<Indicator> indicators = indicatorService.list(qwIndicator);
        //找到说有指标的备选项  30---42的数据
        QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
        //映射查询所有匹配指标的备选项
        qwRlternative.in("target", indicators.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Rlternative> rlternatives = rIlternativeService.list(qwRlternative);
        //第一级指标   sorted(Comparator.comparing(Indicator::getSortcode))  排序
        List<Indicator> first = indicators.stream().filter(x -> x.getParentid() == null).sorted(Comparator.comparing(Indicator::getSortcode)).collect(Collectors.toList());
        int xh = 0;
        for (Indicator target : first) {
            xh++;
            addSellfAndChild(xh + ".", target, indicators, rlternatives, ret);
        }
        //反射取值
        int sum = 0;
        for (int i = 0; i < ret.size(); i++) {
            //Field[] fields = ret.get(i).getClass().getDeclaredFields();反射
            IndicatorVo oi = ret.get(i);
            if (oi.getItems().isEmpty()) {
                sum = sum + 1;
            }
        }
        model.addAttribute("vot2", ret);
        model.addAttribute("votSize1", (ret.size() - sum));
        return "Teachertask8";
    }

    @PostMapping("/saveRode9")
    @ResponseBody
    public Object saveRode9(String name, Integer courseId, Integer teacherId, Integer taskId, Integer typeId, HttpSession session) {
        String[] splitAddress = name.split(",|\\|");
        System.out.println(splitAddress);
        //答案备选项ID
        List list = new ArrayList();
        //指标问题ID
        List list1 = new ArrayList();
        for (int i = 0; i < splitAddress.length; i++) {
            if (i % 2 != 0) {
                list.add(splitAddress[i]);
            } else {
                list1.add(splitAddress[i]);
            }
        }
        Integer id = Integer.parseInt(String.valueOf(list1.get(0)));
        Rlternative rlternative = rIlternativeService.querID(id);
        System.out.println(rlternative);
        //任务类型ID
        Staff studengts = (Staff) session.getAttribute("staff");
        //课程ID
        Date currentTime = new Date();
        List<Record> list2 = new ArrayList();
        for (int k = 0; k < list1.size(); k++) {
            Record record1 = new Record();
            record1.setUserid(studengts.getId());
            record1.setTeacherid(teacherId);
            record1.setCourseid(courseId);
            record1.setEvaluatetime(currentTime);
            record1.setTaskid(taskId);
            Integer s1 = Integer.parseInt(String.valueOf(list1.get(k)));
            record1.setTargetid(s1);
            Integer s = Integer.parseInt(String.valueOf(list.get(k)));
            record1.setItemid(s);
            list2.add(record1);
        }
        recordService.saveBatch(list2);
        calculateScore(teacherId,taskId,typeId);
        //保存分数=-========================================================
//        int score = 0;
//        Evaluationscore evaluationscore = new Evaluationscore();
//        //
//        List<Integer> menuIdList = new ArrayList<Integer>();
//        CollectionUtils.collect(list1, new Transformer() {
//            @Override
//            public Object transform(Object o) {
//                return Integer.valueOf(o.toString());
//            }
//        }, menuIdList);//转成int集合
//        List<Indicator> indicatorList1 = indicatorService.listByIds(menuIdList);
//        System.out.println(indicatorList1.toString());
        return RetJson.ok("系主任评价成功");
    }



//    public void addIn(Float score, List<Indicator> list, Integer typeId, Indicator io) {
//        for (Indicator indicator : list) {
//            List<Record> indicatorList2 = recordService.list().stream().filter(x -> x.getTargetid().equals(indicator.getId())).collect(Collectors.toList());
//            if (indicatorList2 != null) {
//                Rlternative rlternative = rIlternativeService.querID(indicatorList2.get(0).getItemid());
//                score = indicator.getWeight() * rlternative.getScore();
//            }
//
//        }
//
//    }


    @GetMapping("/Teachertask9")
    public String Teachertask9(Integer courseId, Integer teacherId, Integer taskId, HttpSession session, Model model) {

        int typeId = 5;
        model.addAttribute("taskId", taskId);
        List<IndicatorVo> ret = new ArrayList<>();
        //查询所有的指标
        QueryWrapper<Indicator> qwIndicator = new QueryWrapper<>();
        qwIndicator.eq("typeid", typeId);
        List<Indicator> indicators = indicatorService.list(qwIndicator);
        //找到说有指标的备选项  30---42的数据
        QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
        //映射查询所有匹配指标的备选项
        qwRlternative.in("target", indicators.stream().map(x -> x.getId()).collect(Collectors.toList()));
        List<Rlternative> rlternatives = rIlternativeService.list(qwRlternative);

        //第一级指标
        List<Indicator> first = indicators.stream().filter(x -> x.getParentid() == null).sorted(Comparator.comparing(Indicator::getSortcode)).collect(Collectors.toList());
        int xh = 0;
        for (Indicator target : first) {
            xh++;
            addSellfAndChild(xh + ".", target, indicators, rlternatives, ret);
        }

        model.addAttribute("vot3", ret);
        Staff studengts = (Staff) session.getAttribute("staff");
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("courseId", courseId);
        queryWrapper.eq("teacherId", teacherId);
        queryWrapper.eq("taskid", taskId);
        queryWrapper.eq("userid", studengts.getId());
        List<Record> list = recordService.list(queryWrapper);
        System.out.println(list.toString());
        List list1 = new ArrayList();
        for (Record record1 : list) {
            list1.add(record1.getTargetid() + "|" + record1.getItemid());
        }
        System.out.println(list1.toString());
        model.addAttribute("eidet9", list1);
        return "Teachertask9";
    }

    //查询某一个被评老师的记录
//    @PostMapping("/teacherRcorde")
//    @ApiModelProperty(value = "教师不同命记录")
//    @ResponseBody
    private void calculateScore(int teacherId, int taskId, int typeId) {
        QueryWrapper<Record> qwRecord = new QueryWrapper<>();
        qwRecord.eq("teacherid", teacherId);
        qwRecord.eq("taskid", taskId);
        qwRecord.inSql("targetid", "select id from indicator where typeId=" + typeId);
        List<Record> allRecord = recordService.list(qwRecord);
        //找到所有各个类型评价的指标的记录
        List<Rlternative> allItem = new ArrayList<>();

        List<Integer> itemIdRange = allRecord.stream().map(x -> x.getItemid()).collect(Collectors.toList());
        if (itemIdRange.size() > 0) {
            QueryWrapper<Rlternative> qwRlternative = new QueryWrapper<>();
            qwRlternative.in("id", itemIdRange);
            //选中答案的集合
            allItem = rIlternativeService.list(qwRlternative);
        }

        QueryWrapper<Indicator> qwTarget = new QueryWrapper<>();
        qwTarget.eq("typeid", typeId);
        List<Indicator> allTarget = indicatorService.list(qwTarget);

        long courseCount=1L;
//        if(typeId==2){
            courseCount=allRecord.stream().map(x->x.getCourseid()).distinct().count();
            if(courseCount==0L) courseCount=1L;
//        }

        long userIdCount=allRecord.stream().map(x->x.getUserid()).distinct().count();
        if(userIdCount==0L) userIdCount=1L;

        float sum = 0;
        for (Record record : allRecord) {
            float score = allItem.stream().filter(x -> x.getId().equals(record.getItemid())).findFirst().orElse(new Rlternative()).getScore();
            float weight = findWeight(record.getTargetid(), allTarget);
            sum += score * weight;
        }
        QueryWrapper<Evaluationscore> evaluationscoreQueryWrapper=new QueryWrapper<>();
        evaluationscoreQueryWrapper.eq("taskid",taskId);
        evaluationscoreQueryWrapper.eq("teacherid",teacherId);
        evaluationscoreQueryWrapper.eq("indicatorid",typeId);
        List<Evaluationscore> evaluationscoreList=evaluationscoreService.list(evaluationscoreQueryWrapper);
        Evaluationscore mo = new Evaluationscore();
        BigDecimal b   =   new   BigDecimal(sum/userIdCount/courseCount);
        float f1   =  (float) b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        mo.setScore(f1);
        mo.setTaskid(taskId);
        mo.setTeacherid(teacherId);
        mo.setIndicatorid(typeId);

        System.out.println("================"+mo.getIndicatorid());
        if(evaluationscoreList.size()>0){
            mo.setId(evaluationscoreList.get(0).getId());
        }
        evaluationscoreService.saveOrUpdate(mo);
    }

    private float findWeight(int targetId, List<Indicator> allTarget) {
        Indicator self = allTarget.stream().filter(x -> x.getId().equals(targetId)).findFirst().orElse(new Indicator());

        if (self.getParentid() == null) {
            List<Indicator> brother = allTarget.stream().filter(x ->x.getParentid()==null).collect(Collectors.toList());
            float sum=0;
            for (Indicator target:brother){
                sum +=target.getWeight();
            }
            if(sum==0) sum=1;

            return self.getWeight()/sum;
        } else {
            List<Indicator> brother = allTarget.stream().filter(x ->x.getParentid()!=null && x.getParentid().equals(self.getParentid())).collect(Collectors.toList());
            float sum=0;
            for (Indicator target:brother){
                sum +=target.getWeight();
            }
            if(sum==0) sum=1;
            return self.getWeight()/sum * findWeight(self.getParentid(), allTarget);
        }
    }
}
