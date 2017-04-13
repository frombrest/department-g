package com.pai.schema.utils;

import com.pai.schema.Department;
import com.pai.schema.Employee;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by frombrest on 11.4.17.
 */
public class SchemaConversionUtils {

    private final static String FORMATER = "yyyy-MM-dd";

    private static XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws DatatypeConfigurationException{
        DateFormat format = new SimpleDateFormat(FORMATER);
        java.util.Date dt = new java.util.Date(date.getTime());
        XMLGregorianCalendar xmlCalendar;
        xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(dt));
        return xmlCalendar;
    }

    public static Date toSQLDate(XMLGregorianCalendar xmlGregorianCalendar){
        if(xmlGregorianCalendar == null) {
            return null;
        }
        return new java.sql.Date(xmlGregorianCalendar.toGregorianCalendar().getTime().getTime());
    }

    public static Department toSchemaType(com.pai.model.Department modelDepartment){
        Department result = new Department();
        result.setId(modelDepartment.getId());
        result.setName(modelDepartment.getName());
        return result;
    }

    public static com.pai.model.Department toModelType(Department department){
        com.pai.model.Department result = new com.pai.model.Department();
        result.setId(department.getId());
        result.setName(department.getName());
        return result;
    }

    public static Employee toSchemaType(com.pai.model.Employee modelEmployee) throws DatatypeConfigurationException{
        Employee result = new Employee();
        result.setId(modelEmployee.getId());
        result.setFullName(modelEmployee.getFull_name());
        result.setDateOfBirth(toXMLGregorianCalendar(modelEmployee.getDate_of_birth()));
        result.setDepartmentId(modelEmployee.getDepartment_id());
        result.setSalary(modelEmployee.getSalary());
        return result;
    }

    public static com.pai.model.Employee toModelType(Employee employee){
        com.pai.model.Employee result = new com.pai.model.Employee();
        result.setId(employee.getId());
        result.setFull_name(employee.getFullName());
        result.setDate_of_birth(toSQLDate(employee.getDateOfBirth()));
        result.setDepartment_id(employee.getDepartmentId());
        result.setSalary(employee.getSalary());
        return result;
    }
}
