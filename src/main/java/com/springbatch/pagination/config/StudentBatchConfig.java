package com.springbatch.pagination.config;

import com.springbatch.pagination.listener.StudentJobListener;
import com.springbatch.pagination.domain.Student;
import com.springbatch.pagination.step.StudentDataReader;
import com.springbatch.pagination.step.StudentWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.yml")
public class StudentBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private StudentJobListener studentJobListener;

    @Autowired
    private StudentDataReader studentDataReader;

    @Autowired
    private StudentWriter studentWriter;

    private ItemWriter itemWriter;

    /**
     * Export charity transaction details of last month for all program and charities.
     * @return Job job.
     */
    @Bean(name = "exportStudentDataJob")
    public Job exportDonationDetailsJob(@Autowired @Qualifier("studentReaderStep") Step studentStep ) {
        return jobBuilderFactory.get("studentsReaderJob").incrementer(new RunIdIncrementer()).listener(studentJobListener)
                .flow(studentStep)
                .end().build();
    }

    @Bean("studentReaderStep")
    public Step studentReaderStep(@Autowired @Qualifier("studentDataReader") JdbcPagingItemReader<Student> reader) {
        return stepBuilderFactory.get("studentStep").<Student, Student>chunk(1).reader(reader).writer(studentItemWriter()).build();
    }

    @StepScope
    @Bean("studentDataReader")
    public JdbcPagingItemReader<Student> studentItemReader() {
        return studentDataReader.getPaginationReader();
    }

    @StepScope
    @Bean("studentDataWriter")
    public ItemWriter<Student> studentItemWriter() {
        itemWriter = new AsyncItemWriter();
        ((AsyncItemWriter) itemWriter).setDelegate(studentWriter);
        return itemWriter;
    }
}
