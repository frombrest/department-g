package com.pai.ws;

import com.pai.model.Department;
import com.pai.model.Employee;
import com.pai.schema.*;
import com.pai.schema.utils.SchemaConversionUtils;
import com.pai.service.DepartmentService;
import com.pai.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;


/**
 * The class represents the SOAP endpoint
 * implemented on the Spring-WS framework
 *
 * The class provides basic CRUD operations
 * for work with entities Department and Employee
 *
 * Endpoint handles the service messages
 * using JAXB2 marshalling
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */

@Endpoint
public class ServiceEndpoint {

    /**
     * log4j Logger object
     */
    private final static Logger logger = LogManager.getLogger(ServiceEndpoint.class);

    /**
     * Field for basic URI of service
     */
    private static final String namespaceURI = "http://localhost:8080/department-soap/messages";

    /**
     * Factory object for creating responses
     */
    private final ObjectFactory objectFactory = new ObjectFactory();

    /**
     * Field for injection department service bean
     */
    private DepartmentService departmentService;

    /**
     * Field for injection employee service bean
     */
    private EmployeeService employeeService;

    /**
     * Method for injection service-layer bean
     * @param departmentService Department service implementation
     */
    @Autowired
    public void DepartmentService (DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Method for injection service-layer bean
     * @param employeeService Employee service implementation
     */
    @Autowired
    public void EmployeeService (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Method of processing a SOAP operation
     * 'GetAllDepartmentsRequest' and return
     * all departments from database
     *
     * @return List of all departments in XML
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "GetAllDepartmentsRequest", namespace = namespaceURI)
    public @ResponsePayload GetAllDepartmentsResponse getAllDepartments() throws DataAccessException {
        GetAllDepartmentsResponse response = objectFactory.createGetAllDepartmentsResponse();
        for (Department department: departmentService.getAllDepartments()) {
            response.getDepartment().add(SchemaConversionUtils.toSchemaType(department));
        }
        return response;
    }

    /**
     * Method of processing a SOAP operation
     * 'GetDepartmentByIdRequest' and return
     * department entity from database
     *
     * @param request ID of the target department
     * @return Department entity in XML
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "GetDepartmentByIdRequest", namespace = namespaceURI)
    public @ResponsePayload GetDepartmentByIdResponse getDepartmentById(@RequestPayload GetDepartmentByIdRequest request)
                                        throws DataAccessException{
        logger.debug("endpoint:" + request.getDepartmentId());
        GetDepartmentByIdResponse response = new GetDepartmentByIdResponse();
        Department department = departmentService.getDepartmentById(request.getDepartmentId());
        response.setDepartment(SchemaConversionUtils.toSchemaType(department));
        return response;
    }

    /**
     * Method of processing a SOAP operation
     * 'AddDepartmentRequest' and append in database
     * new department
     *
     * @param request Department name in XML
     * @return result of the operation or call an exception
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "AddDepartmentRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> addDepartment(@RequestPayload AddDepartmentRequest request)
                throws DataAccessException {
        Department department = new Department(request.getName());
        departmentService.addDepartment(department);
        return objectFactory.createAddDepartmentResponse("Success");
    }

    /**
     * Method of processing a SOAP operation
     * 'UpdateDepartmentRequest' and update department
     * in databese
     *
     * @param request Department entity in XML
     * @return result of the operation or call an exception
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "UpdateDepartmentRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> updateDepartment(@RequestPayload UpdateDepartmentRequest request)
                throws DataAccessException {
        Department department = SchemaConversionUtils.toModelType(request.getDepartment());
        departmentService.updateDepartment(department);
        return objectFactory.createUpdateDepartmentResponse("Success");
    }

    /**
     * Method of processing a SOAP operation
     * 'DeleteDepartmentRequest' and delete department
     * entity from database with target ID
     *
     * @param request ID of the target department
     * @return result of the operation or call an exception
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "DeleteDepartmentRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> deleteDepartment(@RequestPayload DeleteDepartmentRequest request)
                throws DataAccessException {
        departmentService.deleteDepartment(request.getDepartmentId());
        return objectFactory.createDeleteDepartmentResponse("Success");
    }

    /**
     * Method of processing a SOAP operation
     * 'GetAllEmployeesRequest' and return all
     * employees from database
     *
     * @return  List of all employees in XML
     * @throws DatatypeConfigurationException Error converting date
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "GetAllEmployeesRequest", namespace = namespaceURI)
    public @ResponsePayload GetAllEmployeesResponse getAllEmployees() throws DatatypeConfigurationException, DataAccessException {
        GetAllEmployeesResponse response = objectFactory.createGetAllEmployeesResponse();
        for (Employee employee: employeeService.getAllEmployees()) {
            response.getEmployee().add(SchemaConversionUtils.toSchemaType(employee));
        }
        return response;
    }

    /**
     * Method of processing a SOAP operation
     * 'GetEmployeesByDepartmentIdRequest' and return
     * all employees from target department
     *
     * @param request ID of the target department
     * @return List of employees in XML
     * @throws DatatypeConfigurationException Error converting date
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "GetEmployeesByDepartmentIdRequest", namespace = namespaceURI)
    public @ResponsePayload GetEmployeesByDepartmentIdResponse getEmployeesByDepartmentId(
            @RequestPayload GetEmployeesByDepartmentIdRequest request) throws DatatypeConfigurationException, DataAccessException {
        GetEmployeesByDepartmentIdResponse response = objectFactory.createGetEmployeesByDepartmentIdResponse();
        for (Employee employee: employeeService.getEmployeesByDepartmentId(request.getDepartmentId())){
            response.getEmployee().add(SchemaConversionUtils.toSchemaType(employee));
        }
        return response;
    }

    /**
     * Method of processing a SOAP operation
     * 'GetEmployeesByIdRequest' and return
     * employee with target ID
     *
     * @param request ID of the target employee
     * @return Employee entity in XML
     * @throws DatatypeConfigurationException Error converting date
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "GetEmployeesByIdRequest", namespace = namespaceURI)
    public @ResponsePayload GetEmployeesByIdResponse getEmployeesById(
            @RequestPayload GetEmployeesByIdRequest request) throws DatatypeConfigurationException, DataAccessException {
        GetEmployeesByIdResponse response = new GetEmployeesByIdResponse();
        Employee employee = employeeService.getEmployeeById(request.getEmployeeId());
        response.setEmployee(SchemaConversionUtils.toSchemaType(employee));
        return response;
    }

    /**
     * Method of processing a SOAP operation
     * 'AddEmployeeRequest' and append employee
     * in database
     *
     * @param request Full name, date of birth, department id and salary in XML
     * @return result of the operation or call an exception
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "AddEmployeeRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> addEmployee(@RequestPayload AddEmployeeRequest request)
                    throws DataAccessException{
        Employee employee = new Employee();
        employee.setFull_name(request.getFullName());
        employee.setDate_of_birth(SchemaConversionUtils.toSQLDate(request.getDateOfBirth()));
        employee.setDepartment_id(request.getDepartmentId());
        employee.setSalary(request.getSalary());
        employeeService.addEmployee(employee);
        return objectFactory.createAddEmployeeResponse("Success");
    }

    /**
     * Method of processing a SOAP operation
     * 'UpdateEmployeeRequest' and update employee entity
     * in database
     *
     * @param request employee entity in XML
     * @return result of the operation or call an exception
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "UpdateEmployeeRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> updateEmployee(@RequestPayload UpdateEmployeeRequest request)
                throws DataAccessException {
        Employee employee = SchemaConversionUtils.toModelType(request.getEmployee());
        employeeService.updateEmployee(employee);
        return objectFactory.createUpdateEmployeeResponse("Success");
    }

    /**
     * Method of processing a SOAP operation
     * 'DeleteEmployeeRequest' and delete employee
     * with target ID from database
     *
     * @param request  ID of the target employee
     * @return result of the operation or call an exception
     * @throws DataAccessException Error accessing the database
     */
    @PayloadRoot(localPart = "DeleteEmployeeRequest", namespace = namespaceURI)
    public @ResponsePayload JAXBElement<String> deleteEmployee(@RequestPayload DeleteEmployeeRequest request)
                throws DataAccessException {
        employeeService.deleteEmployee(request.getEmployeeId());
        return objectFactory.createDeleteEmployeeResponse("Success");
    }

}