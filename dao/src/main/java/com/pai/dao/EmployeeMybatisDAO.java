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
 * class uses the MyBatis framework
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */

public class EmployeeMybatisDAO implements EmployeeDAO {

    private SqlSessionFactory sqlSessionFactory;
    private EmployeeDAO employeeMapper;
    private Reader reader = null;

    public EmployeeMybatisDAO() throws IOException {
        reader = Resources.getResourceAsReader("mybatis/mybatisconfig.xml");
        sqlSessionFactory =  new SqlSessionFactoryBuilder().build(reader);
        employeeMapper = sqlSessionFactory.openSession().getMapper(EmployeeDAO.class);
    }

    @Override
    public Employee getById(int id) throws DataAccessException {
        return employeeMapper.getById(id);
    }

    @Override
    public List<Employee> getByDepartmentId(int id) throws DataAccessException {
        return employeeMapper.getByDepartmentId(id);
    }

    @Override
    public List<Employee> getAll() throws DataAccessException {
        return employeeMapper.getAll();
    }

    @Override
    public void delete(int id) throws DataAccessException {
        employeeMapper.delete(id);
    }

    @Override
    public void create(Employee employee) throws DataAccessException {
        employeeMapper.create(employee);
    }

    @Override
    public void update(Employee employee) throws DataAccessException {
        employeeMapper.update(employee);
    }
}
