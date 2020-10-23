package com.example.demo.controll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.comm.RetJson;
import com.example.demo.comm.StringHelper;
import com.example.demo.domain.*;
import com.example.demo.service.*;
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
@ApiModel("员工控制器")
@RequestMapping("/Staff")
public class StaffControll {
    @Autowired
    private StaffService staffService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TimetableService timetableService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EvaluationscoreService evaluationscoreService;
    @GetMapping("/staff")
    public String Staff(){
        return "member-Staff";
    }
    @GetMapping("/lisaAll")
    @ResponseBody
    public Object staff(Integer page,Integer limit){
        RetJson retJson=new RetJson();
        Page<Staff> staffPage = staffService.page(new Page<>(page,limit));
        retJson.setCode(0);
        retJson.setMsg("");
        retJson.setData(staffPage.getRecords());
        retJson.setCount((int) staffPage.getTotal());
        return retJson;
    }
    @GetMapping("staffInfo")
    @ResponseBody
    public Object update(@RequestBody Staff staff, HttpSession session){
        try{
        staffService.saveOrUpdate(staff);
        session.setAttribute("satffinfor",staff);
        return RetJson.ok();
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable Integer id){
        try {
            QueryWrapper<Staff> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",id);
            List<Staff> staffList=staffService.list();

            List<SysUser> sysUserList=sysUserService.list();
            List<Integer> integerList5=sysUserList.stream().filter(x->x.getId().equals(staffList.get(0).getSystemaccount())).map(x->x.getId()).collect(Collectors.toList());
            sysUserService.removeByIds(integerList5);
            staffService.removeById(id);


            List<Timetable> timetableList=timetableService.list();
            List<Integer> integerList=timetableList.stream().filter(x->x.getTeacherid().equals(id)).map(x->x.getId()).collect(Collectors.toList());
            timetableService.removeByIds(integerList);

            List<Record> recordList=recordService.list();
            List<Integer> integerList1=recordList.stream().filter(x->x.getTeacherid().equals(id)||x.getUserid().equals(id)).map(x->x.getId()).collect(Collectors.toList());
            recordService.removeByIds(integerList1);

            List<Evaluationscore> evaluationscoreList=evaluationscoreService.list();
            List<Integer> integerList3=evaluationscoreList.stream().filter(x->x.getTeacherid().equals(id)).map(x->x.getId()).collect(Collectors.toList());
            evaluationscoreService.removeByIds(integerList3);

            return RetJson.ok("删除成功");
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestBody Staff staff){
        try {
            SysUser sysUser=new SysUser();
            sysUser.setStatus(0);
            sysUser.setAccount("admin"+staff.getSystemaccount());
            sysUser.setName("普通用户");
            sysUser.setPassword(StringHelper.encode("123456"));
            sysUserService.save(sysUser);
            QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("account","admin"+staff.getSystemaccount());
            List<SysUser> list=sysUserService.list(queryWrapper);
            List<Staff> list3=staffService.list();
            Staff staff1=new Staff();
            staff1.setDepartmentid(staff.getDepartmentid());
            staff1.setDuty(staff.getDuty());
            staff1.setName(staff.getName());
            staff1.setWorkno("000"+list3.size()+1);
            staff1.setSystemaccount(list.get(0).getId());
            staffService.save(staff1);
            return RetJson.ok(staff1);
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
    @GetMapping("/stype")
    public String stype(Model model){
        List<Department> list=departmentService.list();
        List<Department> staffList=list.stream().filter(x->x.getParentid()!=null).collect(Collectors.toList());
        model.addAttribute("staffListadd",staffList);
        return "Stafftype";
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        try{
            QueryWrapper<Staff> q=new QueryWrapper<>();
            q.eq("id",id);
            List<Staff> list=staffService.list(q);
            System.out.println("2222222");
            for(Staff studengts:list) {
                model.addAttribute("Staffinffor",studengts);
            }


        }catch (Exception e){

        }
        return "Staffdetails";
    }
}
