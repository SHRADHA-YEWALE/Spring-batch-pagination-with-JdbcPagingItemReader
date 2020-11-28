package com.springbatch.pagination.mapper;

import com.springbatch.pagination.domain.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        Student student = new Student ();
        student.setId(rs.getString("id"));
        student.setName(rs.getString("name"));
        return student;
    }
}

