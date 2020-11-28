package com.springbatch.pagination.step;

import com.springbatch.pagination.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("studentWriter")
@StepScope
public class StudentWriter implements ItemWriter<Student> {

    private static final Logger log = LoggerFactory.getLogger(StudentWriter.class);

    @Override
    public void write(List<? extends Student> items) {
        log.info("Inside student writer with following data");
        Student student = items.get(0);
        log.info("Student id: {}", student.getId());
        log.info("Student name: {}", student.getName());
    }
}
