package com.caicat.controller;

import com.caicat.bean.Department;
import com.caicat.bean.Messages_SSM;
import com.caicat.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService deptService;

    @RequestMapping("/getDepts")
    @ResponseBody
    public Messages_SSM getDepts(){
        List<Department> list=deptService.getDepts();
        return new Messages_SSM().success().add("depts",list);
    }
}
