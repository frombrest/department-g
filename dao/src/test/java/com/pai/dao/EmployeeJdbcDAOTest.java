package com.pai.dao;

import com.pai.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-dao.xml"})
public class EmployeeJdbcDAOTest {

    @Autowired
    private EmployeeMybatisDAO employeeMybatisDAO;

    @Before
    public void preparation() throws Exception {
        Employee employee = new Employee("Test GetById Method",
                Date.valueOf("1989-01-01"), 3, 545.15);
        employeeMybatisDAO.create(employee);
        employee = new Employee("Test Delete Method",
                Date.valueOf("1989-02-02"), 3, 645.15);
        employeeMybatisDAO.create(employee);
    }

    @Test
    public void getById() throws Exception {
        String testName = "Test GetById Method";
        int i = 0;
        for (Employee employee: employeeMybatisDAO.getAll()) {
            if(employee.getFull_name().equals(testName)){
                i = employee.getId();
            }
        }
        Assert.assertEquals(testName, employeeMybatisDAO.getById(i).getFull_name());
    }

    @Test
    public void getByDepartmentId() throws Exception {
        int count = employeeMybatisDAO.getByDepartmentId(3).size();
        employeeMybatisDAO.create(new Employee("Test GetByDepartmentId Method",
                Date.valueOf("1989-04-04"), 3, 845.15));
        Assert.assertEquals(count+1, employeeMybatisDAO.getByDepartmentId(3).size());
    }

    @Test
    public void getAll() throws Exception {
        int count = employeeMybatisDAO.getAll().size();
        employeeMybatisDAO.create(new Employee("Test GetAll Method",
                Date.valueOf("1989-03-03"), 2, 745.15));
        Assert.assertEquals(count+1, employeeMybatisDAO.getAll().size());
    }

    @Test
    public void delete() throws Exception {
        String testName = "Test Delete Method";
        int i = 0;
        for (Employee employee: employeeMybatisDAO.getAll()) {
            if(employee.getFull_name().equals(testName)){
                i = employee.getId();
            }
        }
        employeeMybatisDAO.delete(i);
    }

    @Test
    public void create() throws Exception {
        Employee employee = new Employee("Test Create Method",
                Date.valueOf("1990-01-01"), 1, 525.15);
        employeeMybatisDAO.create(employee);
        int i = 0;
        for (Employee empl: employeeMybatisDAO.getAll()) {
            if(empl.getFull_name().equals(employee.getFull_name())){
                i = empl.getId();
            }
        }
        Assert.assertEquals(employee.getFull_name(), employeeMybatisDAO.getById(i).getFull_name());
    }

    @Test
    public void update() throws Exception {
        Employee employee = new Employee("Test Update Method Before",
                Date.valueOf("1991-02-03"), 1, 525.15);
        employeeMybatisDAO.create(employee);
        Employee newemployee = new Employee();
        for (Employee empl: employeeMybatisDAO.getAll()) {
            if(empl.getFull_name().equals(employee.getFull_name())){
                newemployee = empl;
                newemployee.setFull_name("Test Update Method After");
                employeeMybatisDAO.update(newemployee);
            }
        }
        employee = employeeMybatisDAO.getById(newemployee.getId());
        Assert.assertEquals(employee.getFull_name(), employeeMybatisDAO.getById(newemployee.getId()).getFull_name());
    }
}