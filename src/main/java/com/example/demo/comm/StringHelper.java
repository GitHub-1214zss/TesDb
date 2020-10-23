package com.example.demo.comm;

import java.security.MessageDigest;

public class StringHelper {
    public static  String encode(String crude){
        System.out.println("实现加密");
                    if(crude==null)
                        return null;
                try{
                    MessageDigest messageDigest=MessageDigest.getInstance("md5");
                    byte[] arr=messageDigest.digest(crude.getBytes());
                    String target="";
                    for (byte b:arr){
                        Integer i=b & 255;
                        if(i>=0&&i<16){
                            target+="0"+Integer.toHexString(i);
                        }else {
                            target+=Integer.toHexString(i);
                        }
                    }
                    return target;
                }catch (Exception e){
                    return crude;
                }
    }

}
