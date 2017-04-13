package com.pai.ws;

import com.pai.model.Department;
import com.pai.model.Employee;
import com.pai.schema.*;
import com.pai.schema.utils.SchemaConversionUtils;
import com.pai.service.DepartmentService;
import com.pai.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;


/**
 * Created by frombrest on 10.4.17.
 */

@Endpoint
public class ServiceEndpoint {

    private final static Logger logger = LogManager.getLogger(ServiceEndpoint.class);

    private static final String namespaceURI = "http://localhost:8080/department-soap/messages";

    private final ObjectFactory objectFactory = new ObjectFactory();

    private DepartmentService departmentService;
    private EmployeeService employeeService;

    @Autowired
    public void DepartmentService (DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void EmployeeService (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PayloadRoot(localPart = "GetAllDepartmentsRequest", namespace = namespaceURI)
    public @ResponsePayload GetAllDepartmentsResponse getAllDepartments(){
        GetAllDepartmentsResponse response = objectFactory.createGetAllDepartmentsResponse();
        for (Department department: departmentService.getAllDepartments()) {
            response.getDepartment().add(SchemaConversionUtils.toSchemaType(department));
        }
        return response;
    }

    @PayloadRoot(localPart = "GetDepartmentByIdRequest", namespace = namespaceURI)
    public @ResponsePayload GetDepartmentByIdResponse getDepartmentById(@RequestPayload GetDepartmentByIdRequest request){
        logger.debug("endpoint:" + request.getDepartmentId());
        GetDepartmentByIdResponse response = new GetDepartmentByIdResponse();
        Department department = departmentService.getDepartmentById(request.getDepartmentId());
        response.setDepartment(SchemaConversionUtils.toSchemaType(department));
        return response;
    }

    @PayloadRoot(localPart = "AddDepartmentRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> addDepartment(@RequestPayload AddDepartmentRequest request){
        Department department = new Department(request.getName());
        departmentService.addDepartment(department);
        return objectFactory.createAddDepartmentResponse("Success");
    }

    @PayloadRoot(localPart = "UpdateDepartmentRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> updateDepartment(@RequestPayload UpdateDepartmentRequest request){
        Department department = SchemaConversionUtils.toModelType(request.getDepartment());
        departmentService.updateDepartment(department);
        return objectFactory.createUpdateDepartmentResponse("Success");
    }

    @PayloadRoot(localPart = "DeleteDepartmentRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> deleteDepartment(@RequestPayload DeleteDepartmentRequest request){
        departmentService.deleteDepartment(request.getDepartmentId());
        return objectFactory.createDeleteDepartmentResponse("Success");
    }

    @PayloadRoot(localPart = "GetAllEmployeesRequest", namespace = namespaceURI)
    public @ResponsePayload GetAllEmployeesResponse getAllEmployees() throws DatatypeConfigurationException {
        GetAllEmployeesResponse response = objectFactory.createGetAllEmployeesResponse();
        for (Employee employee: employeeService.getAllEmployees()) {
            response.getEmployee().add(SchemaConversionUtils.toSchemaType(employee));
        }
        return response;
    }

    @PayloadRoot(localPart = "GetEmployeesByDepartmentIdRequest", namespace = namespaceURI)
    public @ResponsePayload GetEmployeesByDepartmentIdResponse getEmployeesByDepartmentId(
            @RequestPayload GetEmployeesByDepartmentIdRequest request) throws DatatypeConfigurationException {
        GetEmployeesByDepartmentIdResponse response = objectFactory.createGetEmployeesByDepartmentIdResponse();
        for (Employee employee: employeeService.getEmployeesByDepartmentId(request.getDepartmentId())){
            response.getEmployee().add(SchemaConversionUtils.toSchemaType(employee));
        }
        return response;
    }

    @PayloadRoot(localPart = "GetEmployeesByIdRequest", namespace = namespaceURI)
    public @ResponsePayload GetEmployeesByIdResponse getEmployeesById(
            @RequestPayload GetEmployeesByIdRequest request) throws DatatypeConfigurationException {
        GetEmployeesByIdResponse response = new GetEmployeesByIdResponse();
        Employee employee = employeeService.getEmployeeById(request.getEmployeeId());
        response.setEmployee(SchemaConversionUtils.toSchemaType(employee));
        return response;
    }

    @PayloadRoot(localPart = "AddEmployeeRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> addEmployee(@RequestPayload AddEmployeeRequest request){
        Employee employee = new Employee();
        employee.setFull_name(request.getFullName());
        employee.setDate_of_birth(SchemaConversionUtils.toSQLDate(request.getDateOfBirth()));
        employee.setDepartment_id(request.getDepartmentId());
        employee.setSalary(request.getSalary());
        employeeService.addEmployee(employee);
        return objectFactory.createAddEmployeeResponse("Success");
    }

    @PayloadRoot(localPart = "UpdateEmployeeRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> updateEmployee(@RequestPayload UpdateEmployeeRequest request){
        Employee employee = SchemaConversionUtils.toModelType(request.getEmployee());
        employeeService.updateEmployee(employee);
        return objectFactory.createUpdateEmployeeResponse("Success");
    }

    @PayloadRoot(localPart = "DeleteEmployeeRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> deleteEmployee(@RequestPayload DeleteEmployeeRequest request){
        employeeService.deleteEmployee(request.getEmployeeId());
        return objectFactory.createDeleteEmployeeResponse("Success");
    }

}
