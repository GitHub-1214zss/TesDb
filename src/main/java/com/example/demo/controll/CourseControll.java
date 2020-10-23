package com.example.demo.controll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.comm.RetJson;
import com.example.demo.domain.Course;
import com.example.demo.domain.Rclass;
import com.example.demo.domain.Staff;
import com.example.demo.domain.Timetable;
import com.example.demo.service.CourseService;
import com.example.demo.service.RclassService;
import com.example.demo.service.StaffService;
import com.example.demo.service.TimetableService;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@ApiModel("课程控制器")
@RequestMapping("/Course")
public class CourseControll {
    @Autowired
    private StaffService staffService;
    @Autowired
    private RclassService rclassService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TimetableService timetableService;
    @RequestMapping("/tian")
    public String Course1(){
       return "admin-course";
    }
    @RequestMapping("/listAll")
    public String coure(){
        return "member-course";
    }
    @GetMapping("/Test")
    @ResponseBody
    public RetJson testHTML(Integer page,Integer limit){
        RetJson retJson = new RetJson();
        System.err.println(page+"--"+limit);
        Page<Course> coursePage = courseService.page(new Page<>(page,limit));
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount((int) coursePage.getTotal());
        retJson.setData(coursePage.getRecords());

        return retJson;
    }
    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestBody Course course){
        try {
            List<Course> list=courseService.list();
            List<Course> courseList=list.stream().filter(x->x.getName().equals(course.getName())&& x.getCode().equals(course.getCode())).collect(Collectors.toList());
            if(courseList.size()==0){
                System.out.println(courseList.toString());
                System.out.println(course.getName()+"===="+course.getCode());
                Course course2=new Course();
                course2.setName(course.getName());
                course2.setCode(course.getCode());
                courseService.save(course2);
            }else {
                System.out.println(courseList.toString()+"===========");
                System.out.println(course.getName()+"===="+course.getCode());
                Course course1=new Course();
                course1.setName(course.getName());
                course1.setId(courseList.get(0).getId());
                course1.setCode(course.getCode());
                courseService.saveOrUpdate(course1);
            }
            return RetJson.ok();
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
    //删除
//    @GetMapping("/delete/{id}")
//    @ResponseBody
//    public Object delete(@PathVariable Integer id, HttpSession session){
//        try{
//            if(!session.getAttribute("id").equals(id)){
//                courseService.removeById(id);
//                return RetJson.ok("删除成功");
//            }else {
//                return  RetJson.err(100,"此课程不能删除");
//            }
//
//        }catch (Exception e){
//            return RetJson.err(100,e.getMessage());
//        }
//
//    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable Integer id){
        System.out.println("11111111");
        try {
            courseService.removeById(id);
            return RetJson.ok("删除成功");
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
    @RequestMapping("/list/{name}")
    @ResponseBody
    public Object ListPage(@PathVariable String name,Model model){
        //分页查询
        System.out.println("22222");
        if(name!=null) {
            QueryWrapper<Course> qw = new QueryWrapper<>();
            //模糊查询
            qw.like("name", name);
            List<Course> data = courseService.list(qw);
//        IPage<SysUserVo> data=sysUserService.page(new Page(sysUserDao.getPageIndex(), sysUserDao.getPageSize()),qw);
//        data.getRecords().forEach(x->{
//            x.setStatusName(x.getStatus()==0?"正常":"禁用");//映射
//        });
            System.out.println(data);
            model.addAttribute("CourseList", data);
            model.addAttribute("Couresize", data.size());
            return RetJson.ok(data);
        }else {
            return RetJson.ok("查询全部数据");
        }
    }
    @PostMapping("/update1")
    @ResponseBody
    public Object update1(@RequestBody Course course,HttpSession session){
        try {
            courseService.saveOrUpdate(course);
            //保存用户角色关系
            //批量保存
            session.setAttribute("courses",course);
            return RetJson.ok(course);
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
    @PostMapping("/update")
    @ResponseBody
    public Object update(@RequestBody Course course){
//        System.out.println(teachevaluation.getStatus());
//        System.out.println(teachevaluation.toString());
        //==========================该课程的时候自动刷新关联课程的表
//        QueryWrapper<Timetable> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("id",course.getId());
//        List <Timetable> timetable=timetableService.list();
//        for(Timetable timetable1:timetable){
//
//        }
        try{
            courseService.saveOrUpdate(course);
            return RetJson.ok();
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        try{
            QueryWrapper<Course> q=new QueryWrapper<>();
            q.eq("id",id);
            List<Course> list=courseService.list(q);
            System.out.println("2222222");
            for(Course course:list) {
                model.addAttribute("CourseType",course);
            }


        }catch (Exception e){

        }
        return "Coursetype";
    }
    @GetMapping("/detailss/{id}")
    public String details1(@PathVariable Integer id, Model model){
        try{
            QueryWrapper<Course> q=new QueryWrapper<>();
            q.eq("id",id);
            List<Course> list=courseService.list(q);
            System.out.println("2222222");
            for(Course course:list) {
                model.addAttribute("Courseinfo",course);
            }


        }catch (Exception e){

        }
        return "Coursedetails";
    }
    @GetMapping("/stype23")
    public String stype23(Model model){
        QueryWrapper<Staff> staffQueryWrapper=new QueryWrapper<>();
        staffQueryWrapper.eq("duty","教师");
        List<Staff> list=staffService.list(staffQueryWrapper);
        List<Course> courseList=courseService.list();
        List<Rclass> rclassList=rclassService.list();

        model.addAttribute("staffLists",list);
        model.addAttribute("Couerselist",courseList);
        model.addAttribute("rclassList",rclassList);
        return "Couesetype2222";
    }
    @GetMapping("/stype")
    public String stype(Model model){
        return "Couesetype";
    }
    @PostMapping("/add3")
    @ResponseBody
    public Object add3(@RequestBody Timetable timetable){
        try {
            System.out.println(timetable.toString());
            List<Timetable> list=timetableService.list();
            List<Timetable> timetables=list.stream().filter(x->x.getClassid().equals(timetable.getClassid())&&x.getTeacherid().equals(timetable.getTeacherid())&&x.getCourseid().equals(timetable.getCourseid())).collect(Collectors.toList());
            if(timetables.size()==0){
                System.out.println(timetable.getTeacherid()+"===="+timetable.getClassid());
                timetableService.save(timetable);
            }
            else {
                Timetable timetable2=new Timetable();
                timetable2.setId(timetables.get(0).getId());
                timetable2.setClassid(timetable.getClassid());
                timetable2.setTeacherid(timetable.getTeacherid());
                timetable2.setCourseid(timetable.getCourseid());
                timetableService.saveOrUpdate(timetable2);
            }

            return RetJson.ok();
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
}
