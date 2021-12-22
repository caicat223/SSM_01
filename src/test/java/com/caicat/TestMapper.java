package com.caicat;

import com.caicat.bean.Department;
import com.caicat.bean.Employee;
import com.caicat.dao.DepartmentMapper;
import com.caicat.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestMapper {
    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;

    @Test
    public void testMapper(){
        //新增
   /*     for (int i=0;i<=1000;i++){
            String name=UUID.randomUUID().toString().substring(0,5)+i;
            employeeMapper.insert(new Employee(null,name,"M",name+"@163.com",1));
        }*/

        List<Employee> employees = employeeMapper.selectAllWithDept();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
            System.out.println("插入完成！！");
    }
}
