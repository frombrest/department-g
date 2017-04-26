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
 * class uses the MyBatis framework
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */

public class DepartmentMybatisDAO implements DepartmentDAO {

    private SqlSessionFactory sqlSessionFactory;
    private DepartmentDAO departmentMapper;
    private Reader reader = null;

    public DepartmentMybatisDAO() throws IOException {
        reader = Resources.getResourceAsReader("mybatis/mybatisconfig.xml");
        sqlSessionFactory =  new SqlSessionFactoryBuilder().build(reader);
        departmentMapper = sqlSessionFactory.openSession().getMapper(DepartmentDAO.class);
    }

    @Override
    public Department getById(int id) throws DataAccessException {
        return departmentMapper.getById(id);
    }

    @Override
    public List<Department> getAll() throws DataAccessException {
        return departmentMapper.getAll();
    }

    @Override
    public void delete(int id) throws DataAccessException {
        departmentMapper.delete(id);
    }

    @Override
    public void create(Department department) throws DataAccessException {
        departmentMapper.create(department);
    }

    @Override
    public void update(Department department) throws DataAccessException {
        departmentMapper.update(department);
    }
}
