package com.pai.dao;

import com.pai.model.Department;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.springframework.dao.DataAccessException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * This class implements methods for working
 * with the HSQLDB base of departments (table "TW"."Department")
 *
 * Class uses  MyBatis framework to access the database
 *
 * Parameters for connecting to the database are described
 * in the files: "mybatis / mybatisconfig.xml", "config.properties"
 *
 * Mapping of objects occurs on SQL queries and described in
 * the file "mybatis / DepartmentMapper.xml"
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */

public class DepartmentMybatisDAO implements DepartmentDAO {

    private SqlSessionFactory sqlSessionFactory;
    private DepartmentDAO departmentMapper;
    private Reader reader = null;

    /**
     * The constructor loads the connection parameters, create the new SQL
     * session and connect to it the interface for mapping
     * @throws IOException this exception occurs if the attempt reading the file
     * with the configuration of MayBatis was unsuccessful
     */
    public DepartmentMybatisDAO() throws IOException {
        reader = Resources.getResourceAsReader("mybatis/mybatisconfig.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        departmentMapper = sqlSessionFactory.openSession().getMapper(DepartmentDAO.class);
    }

    /**
     * Method searches the department entity with the same id in table "TW"."Department"
     * @param id of the target department
     * @return department entity
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public Department getById(int id) throws DataAccessException {
        return departmentMapper.getById(id);
    }

    /**
     * Method returns a list of all departments from the table "TW"."Department"
     * @return list of all entity departments
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public List<Department> getAll() throws DataAccessException {
        return departmentMapper.getAll();
    }

    /**
     * Method remove department with the same id from the table "TW"."Department"
     * @param id of the deletable department
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public void delete(int id) throws DataAccessException {
        departmentMapper.delete(id);
    }

    /**
     * Method creates a department in the table "TW"."Department"
     * @param department entity of the created department
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public void create(Department department) throws DataAccessException {
        departmentMapper.create(department);
    }

    /**
     * Method makes changes to department in the table "TW"."Department"
     * @param department entity of the modified department
     * @throws DataAccessException this exception occurs if the database fails to access
     */
    @Override
    public void update(Department department) throws DataAccessException {
        departmentMapper.update(department);
    }

}
