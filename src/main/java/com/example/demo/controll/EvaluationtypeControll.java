package com.example.demo.controll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.comm.RetJson;
import com.example.demo.domain.Evaluationtype;
import com.example.demo.domain.Studengts;
import com.example.demo.service.EvaluationtypeService;
import com.example.demo.service.RclassService;
import com.example.demo.service.StudentsService;
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
@ApiModel("评价类型控制器")
@RequestMapping("/Ptype")
public class EvaluationtypeControll {
    @Autowired
    private EvaluationtypeService evaluationtypeService;
    @Autowired
    private RclassService rclassService;
    @Autowired
    private StudentsService studentsService;
    @GetMapping("/list")
    public String list(){
        return "evaluaetionType";
    }
    @GetMapping("/listType")
    public Object list(HttpSession httpSession){
       Integer id= (int)httpSession.getAttribute("id");
        QueryWrapper<Studengts> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("systemAccount",id);
        List<Studengts> studengts=studentsService.list(queryWrapper);
        if(studengts==null){
            return RetJson.err(100,"抱歉您没有访问的权限");
        }else {
            List<Evaluationtype> list1=evaluationtypeService.list();
            return RetJson.ok(list1);
        }
    }
    @GetMapping("/type")
    @ResponseBody
    public  Object type(Integer page,Integer limit){
        RetJson retJson=new RetJson();
        Page<Evaluationtype> list=evaluationtypeService.page(new Page<>(page,limit));
        retJson.setCode(0);
        retJson.setMsg("ok");
        retJson.setCount((int) list.getTotal());
        retJson.setData(list.getRecords());
        return retJson;
    }
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable Integer id){
        System.out.println("11111111");
        try {
            evaluationtypeService.removeById(id);
            return RetJson.ok("删除成功");
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestBody Evaluationtype evaluationtype){
        try {
            //Rclass rclass1 = new Rclass();
            System.out.println(evaluationtype.toString());
            evaluationtypeService.save(evaluationtype);
            return RetJson.ok(evaluationtype);
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        try{
            QueryWrapper<Evaluationtype> q=new QueryWrapper<>();
            q.eq("id",id);
            List<Evaluationtype> list=evaluationtypeService.list(q);
            System.out.println("2222222");
            for(Evaluationtype evaluationtype:list) {
                model.addAttribute("eType",evaluationtype);
            }


        }catch (Exception e){

        }
        return "Etypedetails";
    }
    @GetMapping("/stype")
    public String stype(){
        return "Etype";
    }
    @PostMapping("/sstype")
    @ResponseBody
    public Object sstype(@RequestBody Evaluationtype evaluationtype){
        List<Evaluationtype> list=evaluationtypeService.list();
        List<Evaluationtype> list1=list.stream().filter(x->x.getName().equals(evaluationtype.getName())).collect(Collectors.toList());
        if(list1.size()==0){
            evaluationtypeService.save(evaluationtype);
        }
        return RetJson.ok(evaluationtype);
    }
}
