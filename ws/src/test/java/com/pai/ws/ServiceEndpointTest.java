package com.pai.ws;


import com.pai.model.Department;
import com.pai.model.Employee;
import com.pai.schema.*;
import com.pai.schema.utils.SchemaConversionUtils;
import com.pai.service.DepartmentService;
import com.pai.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

/**
 * Created by frombrest on 15.4.17.
 *
 * Test's for endpoint
 */

public class ServiceEndpointTest {

    private ServiceEndpoint serviceEndpoint;

    private DepartmentService departmentService;

    private EmployeeService employeeService;

    private ObjectFactory objectFactory;

    @Before
    public void setUp() throws Exception {

        departmentService = createMock(DepartmentService.class);
        employeeService = createMock(EmployeeService.class);

        serviceEndpoint = new ServiceEndpoint();
        serviceEndpoint.DepartmentService(departmentService);
        serviceEndpoint.EmployeeService(employeeService);

        objectFactory = new ObjectFactory();
    }


    @Test
    public void getAllDepartments() throws Exception {

        List<Department> test_list_department = new ArrayList<>();

        test_list_department.add(new Department(3,"name_3"));
        test_list_department.add(new Department(4, "name_4"));
        test_list_department.add(new Department(1, "name_1"));

        expect(departmentService.getAllDepartments()).andReturn(test_list_department);
        replay(departmentService);

        GetAllDepartmentsResponse response = serviceEndpoint.getAllDepartments();

        List<Department> respons_model = new ArrayList<>();

        for (com.pai.schema.Department department:response.getDepartment()){
            respons_model.add(SchemaConversionUtils.toModelType(department));
        }

        Assert.assertTrue(respons_model.size() == test_list_department.size() && test_list_department.containsAll(respons_model));

        verify(departmentService);
    }

    @Test
    public void getDepartmentById() throws Exception {

        Department test_dep = new Department(3, "Test name");

        Department resp_dep;

        expect(departmentService.getDepartmentById(test_dep.getId())).andReturn(test_dep);
        replay(departmentService);

        GetDepartmentByIdRequest request = objectFactory.createGetDepartmentByIdRequest();
        request.setDepartmentId(test_dep.getId());

        GetDepartmentByIdResponse response = serviceEndpoint.getDepartmentById(request);

        resp_dep = SchemaConversionUtils.toModelType(response.getDepartment());

        Assert.assertTrue(test_dep.getId() == resp_dep.getId());
        Assert.assertEquals(test_dep.getName(),resp_dep.getName());

        verify(departmentService);
    }

    @Test
    public void addDepartment() throws Exception {

        Department test_dep = new Department("test_name");

        departmentService.addDepartment(test_dep);
        expectLastCall();
        replay(departmentService);

        AddDepartmentRequest request = new AddDepartmentRequest();
        request.setName(test_dep.getName());

        JAXBElement<String> response = serviceEndpoint.addDepartment(request);

        Assert.assertEquals(response.getValue(), "Success");

        verify(departmentService);
    }

    @Test
    public void updateDepartment() throws Exception {

        Department test_dep = new Department(1,"test_name");

        departmentService.updateDepartment(test_dep);
        expectLastCall();
        replay(departmentService);

        UpdateDepartmentRequest request = new UpdateDepartmentRequest();
        request.setDepartment(SchemaConversionUtils.toSchemaType(test_dep));

        JAXBElement<String> response = serviceEndpoint.updateDepartment(request);

        Assert.assertEquals(response.getValue(), "Success");

        verify(departmentService);
    }

    @Test
    public void deleteDepartment() throws Exception {

        departmentService.deleteDepartment(3);
        expectLastCall();
        replay(departmentService);

        DeleteDepartmentRequest request = new DeleteDepartmentRequest();
        request.setDepartmentId(3);

        JAXBElement<String> response = serviceEndpoint.deleteDepartment(request);

        Assert.assertEquals(response.getValue(), "Success");

        verify(departmentService);
    }

    @Test
    public void getAllEmployees() throws Exception {

        List<Employee> test_list_employee = new ArrayList<>();

        test_list_employee.add(new Employee(22,"Full_name_22",Date.valueOf("1995-10-04"),3,232.33));
        test_list_employee.add(new Employee(23,"Full_name_23",Date.valueOf("1990-01-22"),2,532.33));
        test_list_employee.add(new Employee(54,"Full_name_54",Date.valueOf("1996-12-03"),5,212.32));

        expect(employeeService.getAllEmployees()).andReturn(test_list_employee);
        replay(employeeService);

        GetAllEmployeesResponse response = serviceEndpoint.getAllEmployees();

        List<Employee> resp_model = new ArrayList<>();

        for (com.pai.schema.Employee employee:response.getEmployee()) {
                resp_model.add(SchemaConversionUtils.toModelType(employee));
        }

        Assert.assertTrue(resp_model.size() == test_list_employee.size() && test_list_employee.containsAll(resp_model));

        verify(employeeService);
    }

    @Test
    public void getEmployeesByDepartmentId() throws Exception {

        List<Employee> test_list_employee = new ArrayList<>();

        test_list_employee.add(new Employee(22,"Full_name_22",Date.valueOf("1995-10-04"),3,232.33));
        test_list_employee.add(new Employee(23,"Full_name_23",Date.valueOf("1990-01-22"),3,532.33));
        test_list_employee.add(new Employee(54,"Full_name_54",Date.valueOf("1996-12-03"),3,212.32));

        expect(employeeService.getEmployeesByDepartmentId(3)).andReturn(test_list_employee);
        replay(employeeService);

        GetEmployeesByDepartmentIdRequest request = new GetEmployeesByDepartmentIdRequest();
        request.setDepartmentId(3);
        GetEmployeesByDepartmentIdResponse response = serviceEndpoint.getEmployeesByDepartmentId(request);

        List<Employee> resp_model = new ArrayList<>();

        for (com.pai.schema.Employee employee:response.getEmployee()) {
            resp_model.add(SchemaConversionUtils.toModelType(employee));
        }

        Assert.assertTrue(resp_model.size() == test_list_employee.size() && test_list_employee.containsAll(resp_model));

        verify(employeeService);
    }

    @Test
    public void getEmployeesById() throws Exception {

        Employee test_employee = new Employee(3,"Full_name_3",Date.valueOf("1992-10-04"),1,232.33);
        Employee resp_employee;

        expect(employeeService.getEmployeeById(test_employee.getId())).andReturn(test_employee);
        replay(employeeService);

        GetEmployeesByIdRequest request = new GetEmployeesByIdRequest();
        request.setEmployeeId(3);

        GetEmployeesByIdResponse response = serviceEndpoint.getEmployeesById(request);

        resp_employee = SchemaConversionUtils.toModelType(response.getEmployee());

        Assert.assertTrue(test_employee.equals(resp_employee));

        verify(employeeService);
    }

    @Test
    public void addEmployee() throws Exception {

        Employee test_employee = new Employee("Full_name_3",Date.valueOf("1992-10-04"),1,232.33);
        com.pai.schema.Employee test_schema_employee = SchemaConversionUtils.toSchemaType(test_employee);

        employeeService.addEmployee(test_employee);
        expectLastCall();
        replay(employeeService);

        AddEmployeeRequest request = new AddEmployeeRequest();
        request.setFullName(test_schema_employee.getFullName());
        request.setDateOfBirth(test_schema_employee.getDateOfBirth());
        request.setDepartmentId(test_schema_employee.getDepartmentId());
        request.setSalary(test_schema_employee.getSalary());

        JAXBElement<String> response = serviceEndpoint.addEmployee(request);

        Assert.assertEquals(response.getValue(), "Success");

        verify(employeeService);

    }

    @Test
    public void updateEmployee() throws Exception {

        Employee test_employee = new Employee(3,"Full_name_3",Date.valueOf("1992-10-04"),1,232.33);
        com.pai.schema.Employee test_schema_employee = SchemaConversionUtils.toSchemaType(test_employee);

        employeeService.updateEmployee(test_employee);
        expectLastCall();
        replay(employeeService);

        UpdateEmployeeRequest request = new UpdateEmployeeRequest();
        request.setEmployee(test_schema_employee);

        JAXBElement<String> response = serviceEndpoint.updateEmployee(request);

        Assert.assertEquals(response.getValue(), "Success");

        verify(employeeService);
    }

    @Test
    public void deleteEmployee() throws Exception {

        employeeService.deleteEmployee(3);
        expectLastCall();
        replay(employeeService);

        DeleteEmployeeRequest request = new DeleteEmployeeRequest();
        request.setEmployeeId(3);

        JAXBElement<String> response = serviceEndpoint.deleteEmployee(request);

        Assert.assertEquals(response.getValue(), "Success");

        verify(employeeService);
    }

}