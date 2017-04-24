package com.pai.ClientService;

import com.pai.model.Department;
import com.pai.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.xml.transform.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.xml.transform.StringSource;
import org.springframework.ws.test.client.MockWebServiceServer;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.ws.test.client.RequestMatchers.*;
import static org.springframework.ws.test.client.ResponseCreators.*;
import static org.junit.Assert.*;

/**
 * Created by frombrest on 23.4.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class WebAppServiceImplTest {

    @Autowired
    private WebAppServiceImpl webAppService;

    private MockWebServiceServer mockServer;

    private static final String MESSAGES_NS = "http://localhost:8080/department-soap/messages";

    private static final String TYPES_NS = "http://localhost:8080/department-soap/types";

    @Before
    public void setUp() throws Exception {
        mockServer = MockWebServiceServer.createServer(webAppService);
    }

    @Test
    public void getAverageSalary() throws Exception {

        Source getDepartmentsRequest = new StringSource(
                "<GetAllDepartmentsRequest xmlns=\"" + MESSAGES_NS + "\">test</GetAllDepartmentsRequest>"
        );

        Source getDepartmentsResponse = new StringSource(
                "<ns3:GetAllDepartmentsResponse xmlns:ns2=\"" + TYPES_NS + "\" xmlns:ns3=\"" + MESSAGES_NS + "\">"+
                        "<ns3:department>"+
                        "<ns2:id>0</ns2:id>"+
                        "<ns2:name>Системной интеграции</ns2:name>"+
                        "</ns3:department>"+
                        "<ns3:department>"+
                        "<ns2:id>1</ns2:id>"+
                        "<ns2:name>Системной интеграции</ns2:name>"+
                        "</ns3:department>"+
                        "</ns3:GetAllDepartmentsResponse>");

        Source getEmployeesByDepartmentIdRequest1 = new StringSource(
                "<GetEmployeesByDepartmentIdRequest xmlns=\"" + MESSAGES_NS + "\">"+
                        "<department_id>0</department_id>"+
                        "</GetEmployeesByDepartmentIdRequest>"
        );

        Source getEmployeesByDepartmentIdResponse1 = new StringSource(
                "<ns3:GetEmployeesByDepartmentIdResponse xmlns:ns2=\""+TYPES_NS+"\" xmlns:ns3=\""+MESSAGES_NS+"\">"+
                        "<ns3:employee>"+
                        "<ns2:id>0</ns2:id>"+
                        "<ns2:full_name>wertwertwer</ns2:full_name>"+
                        "<ns2:date_of_birth>1989-02-24</ns2:date_of_birth>"+
                        "<ns2:department_id>2</ns2:department_id>"+
                        "<ns2:salary>475.14</ns2:salary>"+
                        "</ns3:employee>"+
                        "<ns3:employee>"+
                        "<ns2:id>2</ns2:id>"+
                        "<ns2:full_name>werterfdfddf</ns2:full_name>"+
                        "<ns2:date_of_birth>1992-03-10</ns2:date_of_birth>"+
                        "<ns2:department_id>2</ns2:department_id>"+
                        "<ns2:salary>414.26</ns2:salary>"+
                        "</ns3:employee>"+
                        "</ns3:GetEmployeesByDepartmentIdResponse>"
        );

        Source getEmployeesByDepartmentIdRequest2 = new StringSource(
                "<GetEmployeesByDepartmentIdRequest xmlns=\"" + MESSAGES_NS + "\">"+
                        "<department_id>1</department_id>"+
                        "</GetEmployeesByDepartmentIdRequest>"
        );

        Source getEmployeesByDepartmentIdResponse2 = new StringSource(
                "<ns3:GetEmployeesByDepartmentIdResponse xmlns:ns2=\""+TYPES_NS+"\" xmlns:ns3=\""+MESSAGES_NS+"\">"+
                        "<ns3:employee>"+
                        "<ns2:id>0</ns2:id>"+
                        "<ns2:full_name>wertwertwer</ns2:full_name>"+
                        "<ns2:date_of_birth>1989-02-24</ns2:date_of_birth>"+
                        "<ns2:department_id>2</ns2:department_id>"+
                        "<ns2:salary>475</ns2:salary>"+
                        "</ns3:employee>"+
                        "<ns3:employee>"+
                        "<ns2:id>2</ns2:id>"+
                        "<ns2:full_name>werterfdfddf</ns2:full_name>"+
                        "<ns2:date_of_birth>1992-03-10</ns2:date_of_birth>"+
                        "<ns2:department_id>2</ns2:department_id>"+
                        "<ns2:salary>845</ns2:salary>"+
                        "</ns3:employee>"+
                        "</ns3:GetEmployeesByDepartmentIdResponse>"
        );

        mockServer.expect(payload(getDepartmentsRequest)).andRespond(withPayload(getDepartmentsResponse));
        mockServer.expect(payload(getEmployeesByDepartmentIdRequest1)).andRespond(withPayload(getEmployeesByDepartmentIdResponse1));
        mockServer.expect(payload(getEmployeesByDepartmentIdRequest2)).andRespond(withPayload(getEmployeesByDepartmentIdResponse2));
        Map<Integer,Double> averageSalary = webAppService.getAverageSalary();
        Assert.assertTrue(averageSalary.get(0)==444.7);
        Assert.assertTrue(averageSalary.get(1)==660.0);
        mockServer.verify();
    }

    @Test
    public void searchEmployeesByDateOfBirth() throws Exception {

        Source getEmployeesByDepartmentIdRequest = new StringSource(
                "<GetEmployeesByDepartmentIdRequest xmlns=\"" + MESSAGES_NS + "\">"+
                        "<department_id>0</department_id>"+
                        "</GetEmployeesByDepartmentIdRequest>"
        );

        Source getEmployeesByDepartmentIdResponse = new StringSource(
                "<ns3:GetEmployeesByDepartmentIdResponse xmlns:ns2=\""+TYPES_NS+"\" xmlns:ns3=\""+MESSAGES_NS+"\">"+
                        "<ns3:employee>"+
                        "<ns2:id>0</ns2:id>"+
                        "<ns2:full_name>wertwertwer</ns2:full_name>"+
                        "<ns2:date_of_birth>1989-02-24</ns2:date_of_birth>"+
                        "<ns2:department_id>2</ns2:department_id>"+
                        "<ns2:salary>475.14</ns2:salary>"+
                        "</ns3:employee>"+
                        "<ns3:employee>"+
                        "<ns2:id>2</ns2:id>"+
                        "<ns2:full_name>werterfdfddf</ns2:full_name>"+
                        "<ns2:date_of_birth>1992-03-10</ns2:date_of_birth>"+
                        "<ns2:department_id>2</ns2:department_id>"+
                        "<ns2:salary>414.26</ns2:salary>"+
                        "</ns3:employee>"+
                        "</ns3:GetEmployeesByDepartmentIdResponse>"
        );

        mockServer.expect(payload(getEmployeesByDepartmentIdRequest)).andRespond(withPayload(getEmployeesByDepartmentIdResponse));
        List<Employee> employees = webAppService.searchEmployeesByDateOfBirth(0,"1989-02-24");
        Assert.assertTrue(employees.size()==1);
        mockServer.verify();
    }

    @Test
    public void searchEmployeesByIntervalOfBirthdates() throws Exception {

        Source getEmployeesByDepartmentIdRequest = new StringSource(
                "<GetEmployeesByDepartmentIdRequest xmlns=\"" + MESSAGES_NS + "\">"+
                        "<department_id>2</department_id>"+
                        "</GetEmployeesByDepartmentIdRequest>"
        );

        Source getEmployeesByDepartmentIdResponse = new StringSource(
                "<ns3:GetEmployeesByDepartmentIdResponse xmlns:ns2=\""+TYPES_NS+"\" xmlns:ns3=\""+MESSAGES_NS+"\">"+
                        "<ns3:employee>"+
                        "<ns2:id>0</ns2:id>"+
                        "<ns2:full_name>wertwertwer</ns2:full_name>"+
                        "<ns2:date_of_birth>1989-02-24</ns2:date_of_birth>"+
                        "<ns2:department_id>2</ns2:department_id>"+
                        "<ns2:salary>475.14</ns2:salary>"+
                        "</ns3:employee>"+
                        "<ns3:employee>"+
                        "<ns2:id>2</ns2:id>"+
                        "<ns2:full_name>werterfdfddf</ns2:full_name>"+
                        "<ns2:date_of_birth>1992-03-10</ns2:date_of_birth>"+
                        "<ns2:department_id>2</ns2:department_id>"+
                        "<ns2:salary>414.26</ns2:salary>"+
                        "</ns3:employee>"+
                        "<ns3:employee>"+
                        "<ns2:id>3</ns2:id>"+
                        "<ns2:full_name>hjkhjkhjkhj</ns2:full_name>"+
                        "<ns2:date_of_birth>1995-04-17</ns2:date_of_birth>"+
                        "<ns2:department_id>2</ns2:department_id>"+
                        "<ns2:salary>314.26</ns2:salary>"+
                        "</ns3:employee>"+
                        "</ns3:GetEmployeesByDepartmentIdResponse>"
        );

        mockServer.expect(payload(getEmployeesByDepartmentIdRequest)).andRespond(withPayload(getEmployeesByDepartmentIdResponse));
        mockServer.expect(payload(getEmployeesByDepartmentIdRequest)).andRespond(withPayload(getEmployeesByDepartmentIdResponse));
        mockServer.expect(payload(getEmployeesByDepartmentIdRequest)).andRespond(withPayload(getEmployeesByDepartmentIdResponse));

        List<Employee> employees_all = webAppService.searchEmployeesByIntervalOfBirthdates(2,"1988-01-01","1999-01-01");
        List<Employee> employees_two = webAppService.searchEmployeesByIntervalOfBirthdates(2,"1990-01-01","1999-01-01");
        List<Employee> employees_one = webAppService.searchEmployeesByIntervalOfBirthdates(2,"1988-01-01","1990-01-01");

        Assert.assertTrue(employees_all.size()==3);
        Assert.assertTrue(employees_two.size()==2);
        Assert.assertTrue(employees_one.size()==1);
        Assert.assertEquals(employees_one.get(0).getFull_name(),"wertwertwer");

    }

    @Test
    public void getDepartments() throws Exception {

        Source getDepartmentsRequest = new StringSource(
         "<GetAllDepartmentsRequest xmlns=\"" + MESSAGES_NS + "\">test</GetAllDepartmentsRequest>"
        );

        Source getDepartmentsResponse = new StringSource(
         "<ns3:GetAllDepartmentsResponse xmlns:ns2=\"" + TYPES_NS + "\" xmlns:ns3=\"" + MESSAGES_NS + "\">"+
         "<ns3:department>"+
         "<ns2:id>0</ns2:id>"+
         "<ns2:name>Системной интеграции</ns2:name>"+
         "</ns3:department>"+
         "</ns3:GetAllDepartmentsResponse>");

        mockServer.expect(payload(getDepartmentsRequest)).andRespond(withPayload(getDepartmentsResponse));
        Department[] result = webAppService.getDepartments();
        Assert.assertEquals(result[0].getName(),"Системной интеграции");
        mockServer.verify();
    }

    @Test
    public void getDepartmentById() throws Exception {

        Source getDepartmentByIdRequest = new StringSource(
                "<GetDepartmentByIdRequest xmlns=\"" + MESSAGES_NS + "\">"+
                    "<department_id>1</department_id>"+
                "</GetDepartmentByIdRequest>"
        );

        Source getDepartmentByIdResponse = new StringSource(
                "<ns3:GetDepartmentByIdResponse  xmlns:ns2=\"" + TYPES_NS + "\" xmlns:ns3=\"" + MESSAGES_NS + "\">"+
                            "<ns3:department>"+
                                "<ns2:id>1</ns2:id>"+
                                "<ns2:name>Систем видеонаблюдения и контроля доступа</ns2:name>"+
                            "</ns3:department>"+
                        "</ns3:GetDepartmentByIdResponse>"
        );

        mockServer.expect(payload(getDepartmentByIdRequest)).andRespond(withPayload(getDepartmentByIdResponse));
        Department result = webAppService.getDepartmentById(1);
        Assert.assertEquals(result.getName(),"Систем видеонаблюдения и контроля доступа");
        mockServer.verify();
    }

    @Test
    public void createDepartment() throws Exception {

        Department department = new Department("Test department");

        Source createDepartmentRequest = new StringSource(
                "<AddDepartmentRequest xmlns=\"" + MESSAGES_NS + "\">"+
                            "<name>Test department</name>"+
                        "</AddDepartmentRequest>"
        );

        Source createDepartmentResponse = new StringSource(
                "<AddDepartmentResponse xmlns=\"" + MESSAGES_NS + "\">Success</AddDepartmentResponse>"
        );

        mockServer.expect(payload(createDepartmentRequest)).andRespond(withPayload(createDepartmentResponse));
        webAppService.createDepartment(department);
        mockServer.verify();
    }

    @Test
    public void updateDepartment() throws Exception {

        Department department = new Department(3,"Test_department ...");

        Source updateDepartmentRequest = new StringSource(
            "<UpdateDepartmentRequest xmlns=\"" + MESSAGES_NS + "\"  xmlns:typ=\"" + TYPES_NS + "\">"+
                "<department>"+
                    "<typ:id>3</typ:id>"+
                    "<typ:name>Test_department ...</typ:name>"+
                "</department>"+
            "</UpdateDepartmentRequest>"
        );

        Source updateDepartmentResponse = new StringSource(
                "<UpdateDepartmentResponse xmlns=\"" + MESSAGES_NS + "\">Success</UpdateDepartmentResponse>"
        );

        mockServer.expect(payload(updateDepartmentRequest)).andRespond(withPayload(updateDepartmentResponse));
        webAppService.updateDepartment(department);
        mockServer.verify();
    }

    @Test
    public void deleteDepartmentById() throws Exception {

        Source deleteDepartmentByIdRequest = new StringSource(
                "<DeleteDepartmentRequest xmlns=\"" + MESSAGES_NS + "\">"+
                            "<department_id>4</department_id>"+
                        "</DeleteDepartmentRequest>"
        );

        Source deleteDepartmentByIdResponse = new StringSource(
                "<DeleteDepartmentResponse xmlns=\"" + MESSAGES_NS + "\">Success</DeleteDepartmentResponse>"
        );

        mockServer.expect(payload(deleteDepartmentByIdRequest)).andRespond(withPayload(deleteDepartmentByIdResponse));
        webAppService.deleteDepartmentById(4);
        mockServer.verify();
    }

    @Test
    public void createEmployee() throws Exception {

        Department department = new Department("Test department");

        Employee employee = new Employee("Test Employee",Date.valueOf("1990-11-19"),2,505.25);

        Source createEmployeeRequest = new StringSource(
                "<AddEmployeeRequest xmlns=\"" + MESSAGES_NS + "\">"+
                    "<full_name>Test Employee</full_name>"+
                    "<date_of_birth>1990-11-19</date_of_birth>"+
                    "<department_id>2</department_id>"+
                    "<salary>505.25</salary>"+
                    "</AddEmployeeRequest>"
        );

        Source createEmployeeResponse = new StringSource(
                "<AddEmployeeResponse xmlns=\"" + MESSAGES_NS + "\">Success</AddEmployeeResponse>"
        );

        mockServer.expect(payload(createEmployeeRequest)).andRespond(withPayload(createEmployeeResponse));
        webAppService.createEmployee(employee);
        mockServer.verify();
    }

    @Test
    public void getEmployeeById() throws Exception {

        Source getEmployeeByIdRequest = new StringSource(
                "<GetEmployeesByIdRequest xmlns=\"" + MESSAGES_NS + "\">"+
                        "<employee_id>4</employee_id>"+
                "</GetEmployeesByIdRequest>"
        );

        Source getEmployeeByIdResponse = new StringSource(
                "<ns3:GetEmployeesByIdResponse xmlns:ns2=\""+TYPES_NS+"\" xmlns:ns3=\""+MESSAGES_NS+"\">"+
                        "<ns3:employee>"+
                        "<ns2:id>4</ns2:id>"+
                        "<ns2:full_name>Test name_343</ns2:full_name>"+
                        "<ns2:date_of_birth>1992-03-14</ns2:date_of_birth>"+
                        "<ns2:department_id>1</ns2:department_id>"+
                        "<ns2:salary>454.26</ns2:salary>"+
                        "</ns3:employee>"+
                        "</ns3:GetEmployeesByIdResponse>"
                );

        mockServer.expect(payload(getEmployeeByIdRequest)).andRespond(withPayload(getEmployeeByIdResponse));
        Employee employee = webAppService.getEmployeeById(4);
        Assert.assertEquals(employee.getFull_name(),"Test name_343");
        mockServer.verify();
    }

    @Test
    public void getEmployeesByDepartmentId() throws Exception {

        Source getEmployeesByDepartmentIdRequest = new StringSource(
                "<GetEmployeesByDepartmentIdRequest xmlns=\"" + MESSAGES_NS + "\">"+
                    "<department_id>2</department_id>"+
                "</GetEmployeesByDepartmentIdRequest>"
        );

        Source getEmployeesByDepartmentIdResponse = new StringSource(
                "<ns3:GetEmployeesByDepartmentIdResponse xmlns:ns2=\""+TYPES_NS+"\" xmlns:ns3=\""+MESSAGES_NS+"\">"+
                "<ns3:employee>"+
                "<ns2:id>0</ns2:id>"+
                "<ns2:full_name>wertwertwer</ns2:full_name>"+
                "<ns2:date_of_birth>1989-02-24</ns2:date_of_birth>"+
                "<ns2:department_id>2</ns2:department_id>"+
                "<ns2:salary>475.14</ns2:salary>"+
                "</ns3:employee>"+
                "<ns3:employee>"+
                "<ns2:id>2</ns2:id>"+
                "<ns2:full_name>werterfdfddf</ns2:full_name>"+
                "<ns2:date_of_birth>1992-03-10</ns2:date_of_birth>"+
                "<ns2:department_id>2</ns2:department_id>"+
                "<ns2:salary>414.26</ns2:salary>"+
                "</ns3:employee>"+
                "</ns3:GetEmployeesByDepartmentIdResponse>"
        );

        mockServer.expect(payload(getEmployeesByDepartmentIdRequest)).andRespond(withPayload(getEmployeesByDepartmentIdResponse));
        Employee[] employees = webAppService.getEmployeesByDepartmentId(2);
        Assert.assertTrue(employees.length==2);
        mockServer.verify();
    }

    @Test
    public void updateEmployee() throws Exception {

        Employee employee = new Employee(3, "Full_test_name", Date.valueOf("1991-11-11"),2,453.22);

        Source updateEmployeeRequest = new StringSource(
                "<UpdateEmployeeRequest xmlns=\"" + MESSAGES_NS + "\"  xmlns:typ=\"" + TYPES_NS + "\">"+
                        "<employee>"+
                        "<typ:id>3</typ:id>"+
                        "<typ:full_name>Full_test_name</typ:full_name>"+
                        "<typ:date_of_birth>1991-11-11</typ:date_of_birth>"+
                        "<typ:department_id>2</typ:department_id>"+
                        "<typ:salary>453.22</typ:salary>"+
                        "</employee>"+
                        "</UpdateEmployeeRequest>"
        );

        Source updateEmployeeResponse = new StringSource(
                "<UpdateEmployeeResponse xmlns=\"" + MESSAGES_NS + "\">Success</UpdateEmployeeResponse>"
        );

        mockServer.expect(payload(updateEmployeeRequest)).andRespond(withPayload(updateEmployeeResponse));
        webAppService.updateEmployee(employee);
        mockServer.verify();

    }

    @Test
    public void deleteEmployeeById() throws Exception {

        Source deleteEmployeeByIdRequest = new StringSource(
                "<DeleteEmployeeRequest xmlns=\"" + MESSAGES_NS + "\">"+
                "<employee_id>4</employee_id>"+
                "</DeleteEmployeeRequest>"
        );

        Source deleteEmployeeByIdResponse = new StringSource(
                "<DeleteEmployeeResponse xmlns=\"" + MESSAGES_NS + "\">Success</DeleteEmployeeResponse>"
        );

        mockServer.expect(payload(deleteEmployeeByIdRequest)).andRespond(withPayload(deleteEmployeeByIdResponse));
        webAppService.deleteEmployeeById(4);
        mockServer.verify();
    }


}