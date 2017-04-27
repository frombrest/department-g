package com.pai.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.pai.model.Department;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-dao.xml"})
public class DepartmentMybatisDAOTest {

    @Autowired
    private DepartmentMybatisDAO departmentMybatisDAO;

    @Before
    public void preparation() throws Exception {
        Department department = new Department("TestGetByIdMethod");
        departmentMybatisDAO.create(department);
        department = new Department("TestDeleteMethod");
        departmentMybatisDAO.create(department);
    }

    @Test
    public void create() throws Exception {
        Department department = new Department("TestCreateMethod");
        departmentMybatisDAO.create(department);
        int i = 0;
        for (Department dep: departmentMybatisDAO.getAll()) {
            if(dep.getName().equals(department.getName())){
                i = dep.getId();
            }
        }
        Assert.assertEquals(department.getName(), departmentMybatisDAO.getById(i).getName());
    }

    @Test
    public void update() throws Exception {
        Department department = new Department("TestUpdateMethod_Before");
        departmentMybatisDAO.create(department);
        Department newdepartment = new Department();

        for (Department dep: departmentMybatisDAO.getAll()) {
            if(dep.getName().equals(department.getName())){
                newdepartment.setId(dep.getId());
                newdepartment.setName("TesttUpdateMethod_After");
                departmentMybatisDAO.update(newdepartment);
            }
        }

        department = departmentMybatisDAO.getById(newdepartment.getId());
        Assert.assertEquals(department.getName(), departmentMybatisDAO.getById(newdepartment.getId()).getName());
    }

    @Test
    public void getById() throws Exception {
        String testName = "TestGetByIdMethod";
        int i = 0;
        for (Department dep: departmentMybatisDAO.getAll()) {
            if(dep.getName().equals(testName)){
                i = dep.getId();
            }
        }
        Assert.assertEquals(testName, departmentMybatisDAO.getById(i).getName());
    }

    @Test
    public void getAll() throws Exception {
        int count1 = departmentMybatisDAO.getAll().size();
        departmentMybatisDAO.create(new Department("TestGetAllMethod"));
        Assert.assertEquals(count1+1, departmentMybatisDAO.getAll().size());
    }

    @Test
    public void delete() throws Exception {
        String testName = "TestDeleteMethod";
        int i = 0;
        for (Department dep: departmentMybatisDAO.getAll()) {
            if(dep.getName().equals(testName)){
                i = dep.getId();
            }
        }
        departmentMybatisDAO.delete(i);
    }
}
