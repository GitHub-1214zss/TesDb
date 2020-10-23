package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.comm.StringHelper;
import com.example.demo.domain.*;
import com.example.demo.mapper.RcalssMapper;
import com.example.demo.service.RclassService;
import com.example.demo.service.StudentsService;
import com.example.demo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RclassServicesImp extends ServiceImpl<RcalssMapper, Rclass> implements RclassService {
    @Resource
    private RcalssMapper rcalssMapper;
    @Autowired
    private StudentsService studentsService ;
    @Autowired
    private RclassService rclassService ;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public Rclass queryRclass(Timetable timetable) {
        QueryWrapper<Rclass> q=new QueryWrapper<>();
        q.eq("id",timetable.getClassid());
        Rclass rclass=rcalssMapper.selectOne(q);
        return rclass;
    }

    @Override
    public String readExcelFile(MultipartFile file) throws IOException {
            String result = "";
            //创建处理EXCEL的类
            ReadExcel readExcel = new ReadExcel();
            //解析excel，获取上传的事件单
            List<User> useList = readExcel.getExcelInfo(file);
            //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作,
            //和你具体业务有关,这里不做具体的示范
            List<Rclass> rclasses=rclassService.list();
                for (User user:useList){
                        Studengts studengts=new Studengts();
                        studengts.setName(user.getName());
                        studengts.setSudentno(Integer.parseInt(user.getSex()));
                        List<Rclass> rclassList=rclasses.stream().filter(x->x.getName().equals(user.getAge())).collect(Collectors.toList());
                        studengts.setClassid(rclassList.get(0).getId());
                        SysUser sysUser=new SysUser();
                        sysUser.setStatus(0);
                        sysUser.setAccount("admin"+user.getSex());
                        sysUser.setName("普通用户");
                        sysUser.setPassword(StringHelper.encode("123456"));
                        sysUserService.save(sysUser);
                        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
                        queryWrapper.eq("account","admin"+user.getSex());
                        List<SysUser> sysUserList=sysUserService.list(queryWrapper);
                        studengts.setSystemaccount(sysUserList.get(0).getId());
                        studentsService.save(studengts);
                }


//            System.out.println(useList);
            if (useList != null && !useList.isEmpty()) {
                result = "上传成功";
            } else {
                result = "上传失败";
            }
            return result;
    }
}
