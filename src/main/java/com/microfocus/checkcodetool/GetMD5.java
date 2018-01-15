package com.microfocus.checkcodetool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by caiy on 2017/11/7.
 */
public class GetMD5 {
    public String getMD5(String data){
        try {
            MessageDigest md=MessageDigest.getInstance("md5");
            md.update(data.getBytes("utf-8"));
            byte [] hashCode=md.digest();
            StringBuffer sb=new StringBuffer();
            for(byte b:hashCode){
                sb.append(Character.forDigit(b>>4&0xf, 16));
                sb.append(Character.forDigit(b&0xf, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
