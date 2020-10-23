package com.example.demo.controll;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.comm.RetJson;
import com.example.demo.comm.StringHelper;
import com.example.demo.comm.VerifyUtil;
import com.example.demo.domain.Po.SysUserPo;
import com.example.demo.domain.RestVo.SysUserPassword;
import com.example.demo.domain.*;
import com.example.demo.domain.Vo.SysUserVo;
import com.example.demo.domain.Yo.SysUserYo;
import com.example.demo.service.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@ApiModel("用户控制器")
@RequestMapping("/SysUser")
public class SysUserControll {
    @Resource
    private SysUserService sysUserService;
    @Autowired
    private UserRoleService userRoleService;
    @Resource
    private SysRoleService sysRoleService;
    @Autowired
    private StudentsService studentsService;
    @Autowired
    private StaffService staffService;
    //分页列表
    @RequestMapping("/index")
    public String index(){
        System.out.println("==============");
        return "index";
    }
    @RequestMapping("/Loging")
    public String Loging(){
        return "login";
    }
    @RequestMapping("/password")
    public String password(){
        return "member-password";
    }
    @RequestMapping("/member-list")
    @ApiModelProperty(value = "所有用户")
    public String member(Model model){
        List<SysUser> data= sysUserService.list();
//        System.out.println(data);
        model.addAttribute("UserList",data);
        model.addAttribute("size",data.size());
        return "member-list";
    }

//    @RequestMapping("/member-list1")
//    @ApiModelProperty(value = "所有用户")
//    public String member4(Model model){
//       List<SysUser> sysUser=  (List<SysUser>) model.getAttribute("UserList");
//        System.out.println(sysUser);
//       model.addAttribute("UserList",sysUser);
//       model.addAttribute("size",sysUser.size());
//        return "member-list";
//    }

    //@PostMapping("/member-password")
    @ResponseBody
    @GetMapping("/member-password/{id}")
    public Object member2(@PathVariable Integer id){
        System.out.println(id);
        System.out.println("进入重置密码");
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        SysUser sysUser=new SysUser();
        List<SysUser> sysUser1=sysUserService.list(queryWrapper);
        for (SysUser x:sysUser1){
            x.setPassword(StringHelper.encode("123456"));
            sysUserService.saveOrUpdate(x);
        }
        return RetJson.ok();

    }

    @PostMapping("/rest-password")
    @ApiModelProperty(value = "更改密码")
    @ResponseBody
    public Object member2(@RequestBody SysUserPassword sysUserPassword){
        System.out.println("进入修改密码");

            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account", sysUserPassword.getAccount()).eq("password",StringHelper.encode(sysUserPassword.getPassword()));
            List<SysUser> list = sysUserService.list(queryWrapper);
            if(list.size()==0){
                return RetJson.err(100,"您输入的旧密码有误");
            }else {
                for (SysUser x : list) {
                    x.setPassword(StringHelper.encode(sysUserPassword.getPass()));
                    sysUserService.saveOrUpdate(x);
                }
                return RetJson.ok();
            }


    }
    @RequestMapping("/member-edit")
    public String member3(){
        return "member-edit";
    }
    @RequestMapping("/member-show")
    public String member1(){
        return "member-show";
    }
    @GetMapping("/admin-add")
    public String beforeAdd(){
        return "admin-add";
    }
    @RequestMapping("/welcome")
    public String Welcome(){
        return "welcome";
    }
    @RequestMapping("/list/{name}")
    @ResponseBody
    public Object ListPage(@PathVariable String name,Model model){
        //分页查询
        System.out.println("22222");
        if(name!=null) {
            QueryWrapper<SysUser> qw = new QueryWrapper<>();
            //模糊查询
            qw.like("account", name);
            List<SysUser> data = sysUserService.list(qw);
            model.addAttribute("UserList", data);
            model.addAttribute("size", data.size());
            return RetJson.ok(data);
        }else {
            List<SysUser> data = sysUserService.list();
            model.addAttribute("UserList", data);
            model.addAttribute("size", data.size());
            return RetJson.ok(data);
        }
    }
    //新增
    @GetMapping("/before")
    public Object before(){
            List<SysRole> allRole=sysRoleService.list();
            return new RetJson(allRole);
    }
    @PostMapping("/add")
    @ApiModelProperty(value = "添加验证")
    @ResponseBody
    public Object add(@RequestBody SysUserPo sysUserPo){
        try {
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(sysUserPo, sysUser);
            sysUser.setPassword(StringHelper.encode(sysUser.getPassword()));//密码加密
            //保存用户
            sysUser.setStatus(0);
            sysUserService.save(sysUser);
            //保存用户角色关系
            //批量保存
            List<UserRole> list = new ArrayList<>();
            if(sysUserPo.getRoleIdList()!=null) {
                System.out.println(sysUserPo.getRoleIdList());
                sysUserPo.getRoleIdList().forEach(x -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserid(sysUser.getId());
                    userRole.setRoleId(x);
                    list.add(userRole);
                });
                userRoleService.saveBatch(list);
            }
            sysUserPo.setId(sysUser.getId());
            //po->vo
            return RetJson.ok(sysUser);
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
    //删除
    @GetMapping("/delete/{id}")
    @ResponseBody
    public Object delete(@PathVariable Integer id,HttpSession session){
        try{
            if(!session.getAttribute("id").equals(id)){
                sysUserService.removeById(id);
                return RetJson.ok("删除成功");
            }else {
                return  RetJson.err(100,"此账号不能删除");
            }

        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
    //修改
    @GetMapping("/beforeUpdate")
    public Object beforeUpdate(Integer id){
        if(id==null){
            return  RetJson.err(100,"id不能为null");
        }
        SysUser mo=sysUserService.getById(id);
//        List<SysRole> allRole=sysRoleService.list();
        if(mo==null){
            return  RetJson.err(100,"mo="+id+"不能为null");
        }
        SysUserPo sysUserPo=new SysUserPo();
        //复制user的属性
        BeanUtils.copyProperties(mo,sysUserPo);
        //当前用户担任角色
        QueryWrapper<UserRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userId",id);
        List<UserRole> list=userRoleService.list(queryWrapper);
        //映射roleId为新的list
        List<Integer> roleList=list.stream().map(x->x.getRoleId()).collect(Collectors.toList());
        //赋值
        sysUserPo.setRoleIdList(roleList);
        return RetJson.ok(sysUserPo);
    }
    @PostMapping("/update")
    public Object update(SysUserPo sysUserPo){
        try {
            SysUser sysUser = new SysUser();

            BeanUtils.copyProperties(sysUserPo, sysUser);
            sysUser.setPassword(StringHelper.encode(sysUser.getPassword()));//密码加密
            //保存用户
            sysUserService.saveOrUpdate(sysUser);
            //保存用户角色关系
            //批量保存
            List<UserRole> list = new ArrayList<>();
            sysUserPo.getRoleIdList().forEach(x -> {
                UserRole userRole = new UserRole();
                userRole.setUserid(sysUser.getId());
                userRole.setRoleId(x);
                //if (!list.contains(userRole)) {
                list.add(userRole);
                //}

            });
            userRoleService.saveBatch(list);
            sysUserPo.setId(sysUser.getId());
            //po->vo
            return RetJson.ok(sysUserPo);
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
    @PostMapping("/update1")
    @ResponseBody
    public Object update1(@RequestBody SysUser sysUser,HttpSession session){
        try {
            sysUserService.saveOrUpdate(sysUser);
            //保存用户角色关系
            //批量保存
            session.setAttribute("users",sysUser);
            return RetJson.ok(sysUser);
        }catch (Exception e){
            return RetJson.err(100,e.getMessage());
        }

    }
    //查看详情
    @GetMapping("/details")
    public Object details(Integer id){
            SysUserVo sysUserVo=new SysUserVo();
            SysUser mo=sysUserService.getById(id);
            BeanUtils.copyProperties(mo,sysUserVo);
            sysUserVo.setStatusName(sysUserVo.getStatus()==0?"正常":"禁用");
            QueryWrapper<UserRole> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("userId",id);
            List<UserRole> list=userRoleService.list(queryWrapper);
            sysUserVo.setList(list.stream().map(x->x.getRoleId()).collect(Collectors.toList()));
            return RetJson.ok(sysUserVo);
    }
    //登录
    @RequestMapping("/Login")
    @ApiModelProperty(value = "登录验证")
    @ResponseBody
    public Object Login(@RequestBody SysUserYo mo,HttpSession session){
            //检查账号对不对
//        log.info(mo.toString());
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",mo.getAccount());
        List<SysUser> list= sysUserService.list(queryWrapper);
        if(list.size()==0){
           return RetJson.err(100,"账号错误");
            //return "login";
        }
        //检查密码对不对
        SysUser loginUser=null;
        boolean isRight=false;
//        System.out.println(list);
        for (SysUser x : list) {
//            System.out.println(x.getPassword()+"======================="+mo.getPassword());
            if (x.getPassword().equals(StringHelper.encode(mo.getPassword()))) {
            //if (x.getPassword().equals(StringHelper.encode(mo.getPassword()))) {
                //密码正确
                isRight = true;
                loginUser=  x;
                break;
            }
        }
        if(isRight==false){
            return RetJson.err(100,"密码错误");
            //return "login";
        }
        //验证码判断
        if(!session.getAttribute("imgcode").toString().equals(mo.getCode())) {

            return RetJson.err(100,"验证码错误");
        }
        //检查状态对不对
        if(loginUser.getStatus().equals("1")){
            return RetJson.err(100,"账号被禁");
            //return "login";
        }
        QueryWrapper<Studengts> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("systemaccount",loginUser.getId());
        List<Studengts> list1=studentsService.list(queryWrapper1);
        QueryWrapper<Staff> queryWrapper2=new QueryWrapper<>();
        queryWrapper2.eq("systemaccount",loginUser.getId());
        List<Staff> list2=staffService.list(queryWrapper2);
        if(list1.size()!=0){
            Studengts studengts1=null;
            Staff staff1=null;
            for(Studengts studengts:list1){
                studengts1=studengts;
            }
                session.setAttribute("id",loginUser.getId());
                session.setAttribute("students",studengts1);
                session.setAttribute("staff",staff1);
                System.out.println(studengts1.getName());
                session.setAttribute("users", loginUser);//返回登录的对象，前端可以用
                return RetJson.ok(studengts1);
        }
        if(list2.size()!=0){
            Studengts studengts1=null;
            Staff staff1=null;
            for(Staff staff:list2){
                staff1=staff;
            }
                session.setAttribute("id",loginUser.getId());
                session.setAttribute("staff",staff1);
                session.setAttribute("students",studengts1);
                session.setAttribute("users", loginUser);//返回登录的对象，前端可以用
                return RetJson.ok(staff1);
        }
        else {
                Studengts studengts1=null;
                Staff staff1=null;
            System.out.println(loginUser.toString());
                session.setAttribute("id", loginUser.getId());
                session.setAttribute("students",studengts1);
                session.setAttribute("staff",staff1);
                session.setAttribute("users", loginUser);//返回登录的对象，前端可以用
            }
            return RetJson.ok(loginUser);
    }
    //密码重置
//    @PostMapping("/resetPassword")
//    public Object resetPassword(Integer id){
//        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("id",id);
//        SysUser sysUser=new SysUser();
//        sysUser.setPassword(StringHelper.encode("123456"));
//        return RetJson.ok();
//    }
//    @RequestMapping("/user")
//    public String regest(SysUser sysUser, String code, HttpSession session) {
//        //验证码判断
//        if(session.getAttribute("imgcode").toString().equals(code)){
//            sysUserService.save(sysUser);
//            System.out.println("注册成功");
//
//            return "login";
//        }else{
//            return "regist.html";
//        }
//        //通过之后注册
//        //验证码错误不通过，回到注册页面
//
//
//    }
    //生成验证码
    @GetMapping("/getImg")
    protected void createImg(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //1.生成随机的验证码及图片
        Object[] objs = VerifyUtil.createImage();
        //2.将验证码存入  session
        String imgcode = (String) objs[0];
        HttpSession session = req.getSession();
        System.out.println(session);
        session.setAttribute("imgcode", imgcode);
        //3.将图片输出给浏览器
        BufferedImage img = (BufferedImage) objs[1];
        res.setContentType("image/png");
        //服务器自动创建输出流，目标指向浏览器
        OutputStream os = res.getOutputStream();
        System.out.println(os);
        ImageIO.write(img, "png", os);
        os.close();
    }
}
