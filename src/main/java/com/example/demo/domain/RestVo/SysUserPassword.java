package com.example.demo.domain.RestVo;

import com.example.demo.domain.SysUser;
import lombok.Data;

@Data
public class SysUserPassword extends SysUser {
    private String pass;
}
