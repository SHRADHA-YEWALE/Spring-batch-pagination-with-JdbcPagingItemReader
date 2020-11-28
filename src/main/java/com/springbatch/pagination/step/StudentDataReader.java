package com.springbatch.pagination.step;

import com.springbatch.pagination.mapper.StudentMapper;
import com.springbatch.pagination.domain.Student;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component("studentDataReader")
@StepScope
public class StudentDataReader {

    @Autowired
    @Qualifier("postgresDatasource")
    private DataSource dataSource;

    private static final String GET_STUDENT_INFO = "SELECT id, name from student_owner.STUDENTS where status = :status ";

    public JdbcPagingItemReader<Student> getPaginationReader() {
        final JdbcPagingItemReader<Student> reader = new JdbcPagingItemReader<>();
        final StudentMapper studentMapper = new StudentMapper();
        reader.setDataSource(dataSource);
        reader.setFetchSize(10);
        reader.setPageSize(10);
        reader.setRowMapper(studentMapper);
        reader.setQueryProvider(createQuery());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("status", "ACTIVE");
        reader.setParameterValues(parameters);
        return reader;
    }

    private PostgresPagingQueryProvider createQuery() {
        final Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
        final PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause(getFromClause());
        queryProvider.setSortKeys(sortKeys);
        return queryProvider;
    }

    private String getFromClause() {
        return "( " + GET_STUDENT_INFO + ")" + " AS RESULT_TABLE ";
    }
}
