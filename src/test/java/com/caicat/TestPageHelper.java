package com.caicat;

import com.caicat.bean.Employee;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml","classpath*:/SpringMVC.xml" })
@WebAppConfiguration
public class TestPageHelper {
   //Spring-test里面有个Mokmvc可以虚拟mvc的请求
    MockMvc mockMvc;
   //传入SpringMVC的ioc，不能自己装配自己，所以需要@WebAppConfiguration
    @Autowired
    WebApplicationContext context;

    @Before
    public void initMock(){
        mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
//模拟请求拿到返回值

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/emps/5")).andReturn();
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn","5")).andReturn();

        //要运行此测试需要把控制器方法的那个把分页信息放在请求域的方法解注
       MockHttpServletRequest request = mvcResult.getRequest();
        PageInfo pageInfo=(PageInfo)request.getAttribute("emps");
        System.out.println("当前页码："+pageInfo.getPageNum());
        System.out.println("总页码："+pageInfo.getPages());
        System.out.println("总记录数："+pageInfo.getTotal());
        System.out.println("在页面需要连续显示的页码");
        int[] nums=pageInfo.getNavigatepageNums();
        for (int i : nums){
            System.out.print("\t"+i);
        }
        System.out.println();
        List<Employee> list = pageInfo.getList();
        for (Employee employee : list) {
            System.out.println("ID="+employee.getEmpId()+"\tName="+employee.getEmpName());
        }
    }
}
