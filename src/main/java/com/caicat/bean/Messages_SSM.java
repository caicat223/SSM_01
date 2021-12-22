package com.caicat.bean;

import java.util.HashMap;
import java.util.Map;

public class Messages_SSM {
    private int state;//状态码,100:成功，200：失败  300:不允许跳过验证的异常方法
    private String mes;//提示信息
    private Map<String,Object> extend=new HashMap<String, Object>();
    public static Messages_SSM success(){
        Messages_SSM mgs=new Messages_SSM();
        mgs.state=100;
        mgs.mes="操作成功！";
       return mgs;
    }
    public static Messages_SSM fail(){
        Messages_SSM mgs=new Messages_SSM();
        mgs.state=200;
        mgs.mes="操作失败！";
        return mgs;
    }

    public static Messages_SSM Rexexception(){
        Messages_SSM mgs=new Messages_SSM();
        mgs.state=300;
        mgs.mes="不允许跳过验证！";
        return mgs;
    }

    public Messages_SSM add(String key,Object object){
        this.extend.put(key,object);
        return this;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
