package com.pai.schema.utils;

import com.pai.model.Department;
import com.pai.model.Employee;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Created by frombrest on 14.4.17.
 */
public class SchemaConversionUtilsTest {

    @Test
    public void toSQLDate() throws Exception {
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar("1970-11-11");
        Date date2 = Date.valueOf("1970-11-11");
        Assert.assertEquals(date2.toString(),SchemaConversionUtils.toSQLDate(date).toString());
    }

    @Test
    public void toSchemaType() throws Exception {
        Department dep_model = new Department();
        dep_model.setId(14);
        dep_model.setName("test_department");

        com.pai.schema.Department dep_schema = new com.pai.schema.Department();
        dep_schema.setId(14);
        dep_schema.setName("test_department");

        com.pai.schema.Department dep_after_convertion = SchemaConversionUtils.toSchemaType(dep_model);

        Assert.assertEquals(dep_schema.getId(),dep_after_convertion.getId());
        Assert.assertEquals(dep_schema.getName(),dep_after_convertion.getName());
    }

    @Test
    public void toModelType() throws Exception {
        Department dep_model = new Department();
        dep_model.setId(15);
        dep_model.setName("test_department_2");

        com.pai.schema.Department dep_schema = new com.pai.schema.Department();
        dep_schema.setId(15);
        dep_schema.setName("test_department_2");

        Department dep_after_convertion_2 = SchemaConversionUtils.toModelType(dep_schema);

        Assert.assertEquals(dep_model.getId(), dep_after_convertion_2.getId());
        Assert.assertEquals(dep_model.getName(), dep_after_convertion_2.getName());
    }

    @Test
    public void toSchemaType1() throws Exception {
        Employee employee_model = new Employee();
        employee_model.setId(12);
        employee_model.setFull_name("Test Name Employee");
        employee_model.setDate_of_birth(Date.valueOf("1970-11-04"));
        employee_model.setDepartment_id(4);
        employee_model.setSalary(234.21);

        com.pai.schema.Employee employee_schema = new com.pai.schema.Employee();
        employee_schema.setId(12);
        employee_schema.setFullName("Test Name Employee");
        employee_schema.setDateOfBirth(DatatypeFactory.newInstance().newXMLGregorianCalendar("1970-11-04"));
        employee_schema.setDepartmentId(4);
        employee_schema.setSalary(234.21);

        com.pai.schema.Employee employee_after_convertion = SchemaConversionUtils.toSchemaType(employee_model);

        Assert.assertEquals(employee_schema.getId(),employee_after_convertion.getId());
        Assert.assertEquals(employee_schema.getFullName(), employee_after_convertion.getFullName());
        Assert.assertEquals(employee_schema.getDateOfBirth(),employee_after_convertion.getDateOfBirth());
        Assert.assertEquals(employee_schema.getDepartmentId(),employee_after_convertion.getDepartmentId());
        Assert.assertTrue(employee_schema.getSalary() == employee_after_convertion.getSalary());

    }

    @Test
    public void toModelType1() throws Exception {
        Employee employee_model = new Employee();
        employee_model.setId(18);
        employee_model.setFull_name("Test Name Employee_2");
        employee_model.setDate_of_birth(Date.valueOf("1970-11-05"));
        employee_model.setDepartment_id(6);
        employee_model.setSalary(204.20);

        com.pai.schema.Employee employee_schema = new com.pai.schema.Employee();
        employee_schema.setId(18);
        employee_schema.setFullName("Test Name Employee_2");
        employee_schema.setDateOfBirth(DatatypeFactory.newInstance().newXMLGregorianCalendar("1970-11-05"));
        employee_schema.setDepartmentId(6);
        employee_schema.setSalary(204.20);

        Employee employee_after_convertion_2 = SchemaConversionUtils.toModelType(employee_schema);

        Assert.assertEquals(employee_model.getId(),employee_after_convertion_2.getId());
        Assert.assertEquals(employee_model.getFull_name(), employee_after_convertion_2.getFull_name());
        Assert.assertEquals(employee_model.getDate_of_birth(),employee_after_convertion_2.getDate_of_birth());
        Assert.assertEquals(employee_model.getDepartment_id(),employee_after_convertion_2.getDepartment_id());
        Assert.assertTrue(employee_model.getSalary() == employee_after_convertion_2.getSalary());

    }

}