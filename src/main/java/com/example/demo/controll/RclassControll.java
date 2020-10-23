package com.example.demo.controll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.comm.RetJson;
import com.example.demo.domain.Rclass;
import com.example.demo.service.RclassService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@ApiModel("班级控制器")
@RequestMapping("/Class")
public class RclassControll {
    @Autowired
    private RclassService rclassService;
    @GetMapping("/listAll")
    public String l(){
        return "Test";
    }
//    @ApiOperation(value = "分页查询",notes = "分页查询",response = Rclass.class)
//    @GetMapping("/listAll")
//    @ResponseBody
//    public Object listAll(RclassQo qo, Model model){
//        log.info(qo.toString());
//        QueryWrapper<Rclass> qw = new QueryWrapper<>();
//        if(qo.getName()!=null && !qo.getName().trim().equals("")){
//            qw.like("name",qo.getName().trim());
////            List<Rclass> list=rclassService.list(qw);
////            model.addAttribute("Rclass",list);
////            model.addAttribute("classSize",list.size());
//        }
//        if(qo.getCode()!=null){
//            qw.eq("code",qo.getCode());
////            List<Rclass> list=rclassService.list(qw);
////            model.addAttribute("Rclass",list);
////            model.addAttribute("classSize",list.size());
//        }
////        else {
////            List<Rclass> list=rclassService.list();
////            model.addAttribute("Rclass",list);
////            model.addAttribute("classSize",list.size());
////        }
//        if(qo.getPageIndex()==null)qo.setPageIndex(1);
//        if(qo.getPageSize()==null)qo.setPageSize(10);
//        Page page1 = rclassService.page(new Page(qo.getPageIndex(), qo.getPageSize()),qw);
//        return RetJson.ok(page1);
////        return "member-level";
//    }
    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestBody Rclass rclass){
        try {
            //Rclass rclass1 = new Rclass();
            System.out.println(rclass.toString());
            rclassService.save(rclass);
            return RetJson.ok(rclass);
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
    //删除
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable Integer id){
        System.out.println("11111111");
        try {
            rclassService.removeById(id);
            return RetJson.ok("删除成功");
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }
//    @PostMapping("/ListPage/{name}")
//    @ResponseBody
//    public Object Page(@PathVariable String name){
//        try {
//            if (name != null) {
//                QueryWrapper<Rclass> staffQueryWrapper = new QueryWrapper<>();
//                staffQueryWrapper.like("name", name);
//                List<Rclass> list = rclassService.list(staffQueryWrapper);
//                return RetJson.ok(list);
//            } else {
//                List<Rclass> list = rclassService.list();
//                return RetJson.ok(list);
//            }
//        }catch (Exception e){
//            return RetJson.err(100,"您输入的有误");
//        }
//    }
//    @GetMapping("/update_old")
//    @ResponseBody
//    public Object update_old(@RequestBody Rclass staff, HttpSession session){
//        try{
//            rclassService.saveOrUpdate(staff);
//            session.setAttribute("classinfor",staff);
//            return RetJson.ok();
//        }catch (Exception e){
//            return RetJson.err(100,e.getMessage());
//        }
//    }

    @PostMapping("/update")
    @ApiOperation(value = "新增/修改的提交",response = Rclass.class)
    @ResponseBody
    public Object update(@RequestBody Rclass staff){
        try{
            rclassService.saveOrUpdate(staff);
            return RetJson.ok(staff);
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }
    }


    @GetMapping("/Test")
    @ResponseBody
    public RetJson testHTML(Integer page,Integer limit){
            RetJson retJson = new RetJson();
            System.err.println(page+"--"+limit);
            Page<Rclass> rclasses = rclassService.page(new Page<>(page,limit));
            retJson.setCode(0);
            retJson.setMsg("ok");
            retJson.setCount((int) rclasses.getTotal());
            retJson.setData(rclasses.getRecords());

            return retJson;
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        try{
            QueryWrapper<Rclass> q=new QueryWrapper<>();
            q.eq("id",id);
            List<Rclass> list=rclassService.list(q);
            System.out.println("2222222");
            for(Rclass rclass:list) {
                model.addAttribute("RclassType",rclass);
            }


        }catch (Exception e){

        }
        return "Rclasstype";
    }
    @PostMapping("/type")
    @ResponseBody
    public Object type(@RequestBody Rclass rclass){
        List<Rclass> list=rclassService.list();
        List<Rclass> rclasses=list.stream().filter(x->x.getName().equals(rclass.getName())).collect(Collectors.toList());
        if(rclasses.size()==0){
            rclassService.save(rclass);
        }
        return RetJson.ok(rclass);
    }
    @GetMapping("stype")
    public String stype(){
        return "Classtype";
    }
    @GetMapping("/inportent")
    public String inportent(){
        return "Intpor";
    }
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    @ResponseBody
    public String  upload(@RequestParam(value="file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = rclassService.readExcelFile(file);
        return result;
    }

}
