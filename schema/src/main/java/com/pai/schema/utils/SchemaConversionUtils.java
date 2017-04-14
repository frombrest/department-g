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
 * This class is a set of methods for
 * converting objects between model and
 * schema representations, as well as
 * methods for converting types of calendar
 * dates
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */

public class SchemaConversionUtils {

    /**
     * Pattern for date format in SOAP messages (XMLGregorianCalendar)
     */
    private final static String FORMATER = "yyyy-MM-dd";

    /**
     * Method for converting date type from java.sql.Date
     * to javax.xml.datatype.XMLGregorianCalendar
     *
     * @param date date in java.sql.Date type
     * @return date in javax.xml.datatype.XMLGregorianCalendar type
     * @throws DatatypeConfigurationException Error converting date
     */
    private static XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws DatatypeConfigurationException{
        DateFormat format = new SimpleDateFormat(FORMATER);
        java.util.Date dt = new java.util.Date(date.getTime());
        XMLGregorianCalendar xmlCalendar;
        xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(dt));
        return xmlCalendar;
    }

    /**
     * Method for converting date type
     * from javax.xml.datatype.XMLGregorianCalendar
     * to java.sql.Date
     *
     * @param xmlGregorianCalendar date in javax.xml.datatype.XMLGregorianCalendar type
     * @return date in java.sql.Date type
     */
    public static Date toSQLDate(XMLGregorianCalendar xmlGregorianCalendar){
        if(xmlGregorianCalendar == null) {
            return null;
        }
        return new java.sql.Date(xmlGregorianCalendar.toGregorianCalendar().getTime().getTime());
    }

    /**
     * Method for converting department entity
     * from model to schema representation
     *
     * @param modelDepartment department entity in model representation
     * @return department entity in schema representation
     */
    public static Department toSchemaType(com.pai.model.Department modelDepartment){
        Department result = new Department();
        result.setId(modelDepartment.getId());
        result.setName(modelDepartment.getName());
        return result;
    }

    /**
     * Method for converting department entity
     * from schema to model representation
     *
     * @param department department entity in schema representation
     * @return department entity in model representation
     */
    public static com.pai.model.Department toModelType(Department department){
        com.pai.model.Department result = new com.pai.model.Department();
        result.setId(department.getId());
        result.setName(department.getName());
        return result;
    }

    /**
     * Method for converting employee entity
     * from model to schema representation
     *
     * @param modelEmployee employee entity in model representation
     * @return employee entity in schema representation
     * @throws DatatypeConfigurationException Error converting date
     */
    public static Employee toSchemaType(com.pai.model.Employee modelEmployee) throws DatatypeConfigurationException{
        Employee result = new Employee();
        result.setId(modelEmployee.getId());
        result.setFullName(modelEmployee.getFull_name());
        result.setDateOfBirth(toXMLGregorianCalendar(modelEmployee.getDate_of_birth()));
        result.setDepartmentId(modelEmployee.getDepartment_id());
        result.setSalary(modelEmployee.getSalary());
        return result;
    }

    /**
     * Method for converting employee entity
     * from schema to model representation
     *
     * @param employee employee entity in schema representation
     * @return employee entity in model representation
     */
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
