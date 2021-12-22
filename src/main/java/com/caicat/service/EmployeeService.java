package com.caicat.service;

import com.caicat.bean.Employee;
import com.caicat.bean.EmployeeExample;
import com.caicat.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper mapper;

    public List<Employee> getAll() {
        return mapper.selectAllWithDept();
    }

   //新增
    public boolean addEmployee(Employee employee) {

        return mapper.insert(employee)>0;
    }

    public boolean checkUserName(String empName) {
        EmployeeExample example=new EmployeeExample();
        EmployeeExample.Criteria criterion=example.createCriteria();
        criterion.andEmpNameEqualTo(empName);
        long count= mapper.countByExample(example);
        return count>0;
    }

    //根据id查找员工并回写到页面
    public Employee indexEmp(Integer id) {

        return mapper.selectByPrimaryKey(id);
    }

    public boolean editEmp(Employee employee) {
        return mapper.updateByPrimaryKeySelective(employee)>0;
    }

    public boolean delEmp(Integer empId) {

        return mapper.deleteByPrimaryKey(empId)>0;
    }

    public void deleteBatch(List<Integer> empIdList) {
        EmployeeExample example=new EmployeeExample();
        EmployeeExample.Criteria criteria=example.createCriteria();
        criteria.andEmpIdIn(empIdList);
        mapper.deleteByExample(example);
    }
}
