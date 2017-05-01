package com.pai.dao;

import com.pai.model.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.dao.DataAccessException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * This class implements methods for working
 * with the HSQLDB base of employees (table "TW"."Employee")
 *
 * Class uses  MyBatis framework to access the database
 *
 * Parameters for connecting to the database are described
 * in the files: "mybatis / mybatisconfig.xml", "config.properties"
 *
 * Mapping of objects occurs on SQL queries and described in
 * the file "mybatis / EmployeeMapper.xml"
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */

public class EmployeeMybatisDAO implements EmployeeDAO {

    private SqlSessionFactory sqlSessionFactory;
    private EmployeeDAO employeeMapper;
    private Reader reader = null;

    /**
     * The constructor loads the connection parameters, create the new SQL
     * session and connect to it the interface for mapping
     * @throws IOException this exception occurs if the attempt reading the file
     * with the configuration of MayBatis was unsuccessful
     */
    public EmployeeMybatisDAO() throws IOException {
        reader = Resources.getResourceAsReader("mybatis/mybatisconfig.xml");
        sqlSessionFactory =  new SqlSessionFactoryBuilder().build(reader);
        employeeMapper = sqlSessionFactory.openSession().getMapper(EmployeeDAO.class);
    }

    /**
     * Method searches the employee entity with the same id in table "TW"."Employee"
     * @param id of the target employee
     * @return employee entity
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public Employee getById(int id) throws DataAccessException {
        return employeeMapper.getById(id);
    }

    /**
     * Method returns a list of all employees from the the target department.
     * Search is in the table "TW"."Employee"
     * @param id of the target department
     * @return list of entity employees
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public List<Employee> getByDepartmentId(int id) throws DataAccessException {
        return employeeMapper.getByDepartmentId(id);
    }

    /**
     * Method returns a list of all employees from the table "TW"."Employee"
     * @return list of all entity employees
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public List<Employee> getAll() throws DataAccessException {
        return employeeMapper.getAll();
    }

    /**
     * Method remove employee with the same id from the table "TW"."Employee"
     * @param id of the deletable employee
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public void delete(int id) throws DataAccessException {
        employeeMapper.delete(id);
    }

    /**
     * Method creates a employee in the table "TW"."Employee"
     * @param employee entity of the created employee
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public void create(Employee employee) throws DataAccessException {
        employeeMapper.create(employee);
    }

    /**
     * Method makes changes to employee in the table "TW"."Employee"
     * @param employee entity of the modified employee
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public void update(Employee employee) throws DataAccessException {
        employeeMapper.update(employee);
    }

}