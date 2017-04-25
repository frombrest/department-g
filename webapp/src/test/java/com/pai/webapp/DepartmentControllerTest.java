package com.pai.webapp;

import com.pai.ClientService.WebAppService;
import com.pai.model.Department;
import com.pai.model.Employee;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

/**
 * Created by frombrest on 24.4.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:controller-test-Context.xml")
public class DepartmentControllerTest {

    @Autowired
    private DepartmentController departmentController;
    private MockMvc mockMvc;

    @Autowired
    private WebAppService webappService;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        resetToDefault(webappService);
    }

    @Test
    public void allDepartments() throws Exception {

        Department[] departments = new Department[2];
        departments[0] = new Department(0,"test_1");
        departments[1] = new Department(1,"test_2");

        Map<Integer, Double> salarys = new HashMap<>();
        salarys.put(0, 532.32);
        salarys.put(1, 432.11);

        expect(webappService.getDepartments()).andReturn(departments).times(2);
        expect(webappService.getAverageSalary()).andReturn(salarys).times(2);
        replay(webappService);

        mockMvc.perform(get("/departments"))
                .andExpect(model().attribute("aversalary", webappService.getAverageSalary()))
                .andExpect(model().attribute("departments", webappService.getDepartments()))
                .andExpect(view().name("departments"));
        verify(webappService);
    }

    @Test
    public void deleteDepartment() throws Exception {

        webappService.deleteDepartmentById(4);
        expectLastCall();
        replay(webappService);

        mockMvc.perform(get("/departments/delete-department/4"))
                .andExpect(redirectedUrl("/departments"));

        verify(webappService);
    }

    @Test
    public void deleteEmployee() throws Exception {

        webappService.deleteEmployeeById(4);
        expectLastCall();
        replay(webappService);

        mockMvc.perform(get("/departments/6/delete-employee/4"))
                .andExpect(redirectedUrl("/departments/6"));

        verify(webappService);
    }

    @Test
    public void showEmployees() throws Exception {

        Department department = new Department(6, "test department six");

        Employee[] employees = new Employee[2];
        employees[0] = new Employee();
        employees[1] = new Employee();

        expect(webappService.getEmployeesByDepartmentId(6)).andReturn(employees).times(2);
        expect(webappService.getDepartmentById(6)).andReturn(department).times(2);
        replay(webappService);

        mockMvc.perform(get("/departments/6"))
                .andExpect(model().attribute("employees", webappService.getEmployeesByDepartmentId(6)))
                .andExpect(model().attribute("department", webappService.getDepartmentById(6)))
                .andExpect(view().name("employees"));

        verify(webappService);
    }

    @Test
    public void showEmployees1() throws Exception {

        Department department = new Department(6, "test department six");
        List<Employee> employees1 = new ArrayList<>();

        expect(webappService.searchEmployeesByDateOfBirth(6, "1989-02-02")).andReturn(employees1).times(2);
        expect(webappService.getDepartmentById(6)).andReturn(department).times(2);
        replay(webappService);

        mockMvc.perform(get("/departments/6?date_of_birth=1989-02-02"))
                .andExpect(model().attribute("date_of_birth", "1989-02-02"))
                .andExpect(model().attribute("employees", webappService.searchEmployeesByDateOfBirth(6, "1989-02-02")))
                .andExpect(model().attribute("department", webappService.getDepartmentById(6)))
                .andExpect(view().name("employees"));
        mockMvc.perform(get("/departments/6?date_of_birth="))
                .andExpect(redirectedUrl("/departments/6"));

        verify(webappService);
    }


    @Test
    public void showEmployees2() throws Exception {

        Department department = new Department(6, "test department six");
        List<Employee> employees1 = new ArrayList<>();

        expect(webappService.searchEmployeesByIntervalOfBirthdates(6, "1989-02-03", "1989-02-02")).andReturn(employees1).times(2);
        expect(webappService.getDepartmentById(6)).andReturn(department).times(2);
        replay(webappService);

        mockMvc.perform(get("/departments/6?date_from=1989-02-03&date_to=1989-02-02"))
                .andExpect(model().attribute("date_from", "1989-02-03"))
                .andExpect(model().attribute("date_to", "1989-02-02"))
                .andExpect(model().attribute("employees", webappService.searchEmployeesByIntervalOfBirthdates(6, "1989-02-03", "1989-02-02")))
                .andExpect(model().attribute("department", webappService.getDepartmentById(6)))
                .andExpect(view().name("employees"));
        mockMvc.perform(get("/departments/6?date_from=&date_to="))
                .andExpect(redirectedUrl("/departments/6"));

        verify(webappService);
    }

    @Test
    public void newDepartment() throws Exception {
        mockMvc.perform(get("/departments/add-department"))
                .andExpect(view().name("add-department"));
    }

    @Test
    public void newDepartment1() throws Exception {
        mockMvc.perform(post("/departments/add-department"))
                .andExpect(redirectedUrl("/departments"));
    }

    @Test
    public void editDepartment() throws Exception {
        Department department = new Department(6, "test department six");

        expect(webappService.getDepartmentById(6)).andReturn(department).times(2);
        replay(webappService);

        mockMvc.perform(get("/departments/edit-department/6"))
                .andExpect(model().attribute("dep", webappService.getDepartmentById(6)))
                .andExpect(view().name("edit-department"));

        verify(webappService);
    }

    @Test
    public void editDepartment1() throws Exception {
        mockMvc.perform(post("/departments/edit-department"))
                .andExpect(redirectedUrl("/departments"));
    }

    @Test
    public void editEmployee() throws Exception {

        Employee employee = new Employee();
        Department[] departments = new Department[3];

        expect(webappService.getEmployeeById(6)).andReturn(employee).times(2);
        expect(webappService.getDepartments()).andReturn(departments).times(2);
        replay(webappService);

        mockMvc.perform(get("/departments/6/edit-employee/6"))
                .andExpect(model().attribute("employee", webappService.getEmployeeById(6)))
                .andExpect(model().attribute("departments",webappService.getDepartments()))
                .andExpect(view().name("edit-employee"));

        verify(webappService);
    }

    @Test
    public void editEmployee1() throws Exception {
        mockMvc.perform(post("/departments/6/edit-employee/6"))
                .andExpect(redirectedUrl("/departments/6"));
    }

    @Test
    public void newEmployee() throws Exception {
        Department[] departments = new Department[3];

        expect(webappService.getDepartments()).andReturn(departments).times(2);
        replay(webappService);

        mockMvc.perform(get("/departments/6/add-employee"))
                .andExpect(model().attribute("departments", webappService.getDepartments()))
                .andExpect(model().attribute("current_department", 6))
                .andExpect(view().name("add-employee"));

        verify(webappService);
    }

    @Test
    public void newEmployee1() throws Exception {
        mockMvc.perform(post("/departments/6/add-employee"))
                .andExpect(redirectedUrl("/departments/6"));
    }



}