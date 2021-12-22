package com.caicat.controller;

import com.caicat.bean.Employee;
import com.caicat.bean.Messages_SSM;
import com.caicat.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/index")
    public String testIndex(){
        return "index";
    }
/*
   @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
        PageHelper.startPage(pn,5);
        List<Employee> employeeList= employeeService.getAll();
        PageInfo pageInfo=new PageInfo(employeeList,5);
        model.addAttribute("emps",pageInfo);
        return "forward:/index";
    }*/


    /*@RequestMapping("/emps/{pn}")*/
    public String getEmps(@PathVariable("pn")Integer pn, Model model){
        PageHelper.startPage(pn,5);
        List<Employee> employeeList= employeeService.getAll();
        PageInfo pageInfo=new PageInfo(employeeList,5);
        model.addAttribute("emps",pageInfo);
        return "forward:/index";
    }

    //回应json请求的方法
    @RequestMapping(value = "/emps/{pn}")
    @ResponseBody
    public Messages_SSM getEmployeeList(@PathVariable("pn")Integer pn, Model model){
        PageHelper.startPage(pn,5);
        List<Employee> employeeList= employeeService.getAll();
        PageInfo pageInfo=new PageInfo(employeeList,5);
        return new Messages_SSM().success().add("emps",pageInfo);
    }
    @GetMapping("/emps")
    public String Toindx(){
        return "index";
    }

    @PostMapping(value = "/emps")
    @ResponseBody
    public Messages_SSM addEmp(@Valid Employee employee, BindingResult result){
        Messages_SSM messagesSsm=checkUser(employee.getEmpName());
        //判断用户是否跳过前端验证
        if(messagesSsm.getState()==200){
            return Messages_SSM.Rexexception();
        }
        Map<String,Object> map=new HashMap<String,Object>();
        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                System.out.println("错误的字段名："+fieldError.getField());
                System.out.println("错误信息"+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Messages_SSM.fail().add("errorFields",map);
        }else{
            if(employeeService.addEmployee(employee)){
                Messages_SSM.fail();
            }
            return Messages_SSM.success();
        }
    }

    //验证员工姓名文本框的值在数据库是否已存在
    @RequestMapping("/checkUser")
    @ResponseBody
    public Messages_SSM checkUser(@RequestParam("empName") String empName){
        String rex= "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        //Pattern r = Pattern.compile(rex);
        if(!Pattern.matches(rex,empName)){
            return Messages_SSM.fail().add("va_msg","用户名可以是2-5位中文或者6-16位英文和数字的组合");
        }
        boolean chk= employeeService.checkUserName(empName);
        if(chk){
            return Messages_SSM.fail().add("va_msg","用户名不可用");
        }
        return Messages_SSM.success();
    }

    //修改功能根据员工id向页面回写对应的数据
    @GetMapping("/getEmp/{id}")
    @ResponseBody
    public Messages_SSM getEmp(@PathVariable("id")Integer id){
        Employee employee=employeeService.indexEmp(id);
        return new Messages_SSM().success().add("emp",employee);
    }

    //修改功能完成修改
    //请求路径中的参数也是会参与封装的，路径中和pojo同名的占位符,也会被封装进pojo
    @PutMapping("/emps/{empId}")
    @ResponseBody
    public Messages_SSM editEmp(Employee employee){
        /*System.out.println(employee+"，员工id："+id);*/
        if(chkEmpEmail(employee.getEmpEmail())){
            return Messages_SSM.Rexexception();
        }
        employeeService.editEmp(employee);
        return Messages_SSM.success();
    }

    private boolean chkEmpEmail(String empEmail) {
        String rex= "\"^\\s*\\w+(?:\\.{0,1}[\\w-]+)@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)\\.[a-zA-Z]+\\s*$\"";
        //Pattern r = Pattern.compile(rex);
        if(!Pattern.matches(rex,empEmail)){
            return false;
        }
        return true;
    }

    @DeleteMapping("/emps/{empIds}")
    @ResponseBody
    public Messages_SSM delEmp(@PathVariable("empIds")String empIds){
        List<Integer> empIdList=new ArrayList<Integer>();
        if(empIds.contains("-")){
            String[] ids=empIds.split("-");
            for (String id : ids) {
                empIdList.add(Integer.parseInt(id));
            }
            employeeService.deleteBatch(empIdList);
        }else{
            employeeService.delEmp(Integer.parseInt(empIds));
        }
        return Messages_SSM.success();
    }
}
