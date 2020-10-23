package com.example.demo.controll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.comm.RetJson;
import com.example.demo.comm.StringHelper;
import com.example.demo.domain.Rclass;
import com.example.demo.domain.Studengts;
import com.example.demo.domain.SysUser;
import com.example.demo.service.RclassService;
import com.example.demo.service.StudentsService;
import com.example.demo.service.SysUserService;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@ApiModel("学生控制器")
@RequestMapping("/students")
public class StudentsControll {
    @Autowired
    private StudentsService studentsService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RclassService rclassService;
    @GetMapping("/list")
    public String list(){
        return "member-students";
    }
    @GetMapping("/stype")
    public String stype(Model model){
        List<Rclass> rclassList=rclassService.list();
        model.addAttribute("rclassList1",rclassList);

        return "Studenttype";
    }
    @GetMapping("/lisaAll")
    @ResponseBody
    public Object students(Integer page,Integer limit){
        RetJson retJson=new RetJson();
        Page<Studengts> studengts = studentsService.page(new Page<>(page,limit));
        retJson.setCode(0);
        retJson.setMsg("");
        retJson.setData(studengts.getRecords());
        retJson.setCount((int) studengts.getTotal());
        return retJson;
    }
//    @GetMapping("/delete/{id}")
//    @ResponseBody
//    public Object Delete(@PathVariable Integer id, HttpSession session){
//        try{
//            if(!session.getAttribute("id").equals(id)){
//                studentsService.removeById(id);
//                return RetJson.ok("删除成功");
//            }else {
//                return  RetJson.err(100,"此账号不能删除");
//            }
//
//        }catch (Exception e){
//            return RetJson.err(100,e.getMessage());
//        }
//    }
@GetMapping("/delete/{id}")
@ResponseBody
public Object delete(@PathVariable Integer id){
    System.out.println("11111111");
    try {
        studentsService.removeById(id);
        return RetJson.ok("删除成功");
    }catch (Exception e){
        return RetJson.err(100,e.getMessage());
    }
}
    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestBody Studengts studengts){
        try {
            SysUser sysUser=new SysUser();
            sysUser.setStatus(0);
            sysUser.setAccount("admin"+studengts.getSystemaccount());
            sysUser.setName("普通用户");
            sysUser.setPassword(StringHelper.encode("123456"));
            sysUserService.save(sysUser);
            studentsService.save(studengts);
            return RetJson.ok(studengts);
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
    @PostMapping("/ListPage/{name}")
    @ResponseBody
    public Object Page(@PathVariable String name){
        try {
            if (name != null) {
                QueryWrapper<Studengts> staffQueryWrapper = new QueryWrapper<>();
                staffQueryWrapper.like("name", name);
                List<Studengts> list = studentsService.list(staffQueryWrapper);
                return RetJson.ok(list);
            } else {
                List<Studengts> list = studentsService.list();
                return RetJson.ok(list);
            }
        }catch (Exception e){
            return RetJson.err(100,"您输入的有误");
        }
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        try{
            QueryWrapper<Studengts> q=new QueryWrapper<>();
            q.eq("id",id);
            List<Studengts> list=studentsService.list(q);
            System.out.println("2222222");
            for(Studengts studengts:list) {
                model.addAttribute("Studengtsinfo",studengts);
            }


        }catch (Exception e){

        }
        return "Studentsdetails";
    }
    @PostMapping("/update")
    @ResponseBody
    public Object update(@RequestBody Studengts teachevaluation){
//        System.out.println(teachevaluation.getStatus());
//        System.out.println(teachevaluation.toString());
        try{
            studentsService.saveOrUpdate(teachevaluation);
            return RetJson.ok();
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
}
