package com.example.demo.controll;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.comm.RetJson;
import com.example.demo.domain.Department;
import com.example.demo.service.DepartmentService;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@ApiModel("部门控制器")
@RequestMapping("/department")
public class DepartmentControll {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/department")
    public String department(){
        return "menber-department";
    }
    @GetMapping("/type")
    @ResponseBody
    public  Object type(Integer page,Integer limit){
        RetJson retJson=new RetJson();
        Page<Department> list=departmentService.page(new Page<>(page,limit));
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount((int) list.getTotal());
        retJson.setData(list.getRecords());
        return retJson;
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable Integer id, HttpSession session){
        try{
            if(!session.getAttribute("id").equals(id)){
                departmentService.removeById(id);
                return RetJson.ok("删除成功");
            }else {
                return  RetJson.err(100,"此课程不能删除");
            }

        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestBody Department department){
        try {
            //Rclass rclass1 = new Rclass();
            System.out.println(department.toString());
            departmentService.save(department);
            return RetJson.ok(department);
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
}
