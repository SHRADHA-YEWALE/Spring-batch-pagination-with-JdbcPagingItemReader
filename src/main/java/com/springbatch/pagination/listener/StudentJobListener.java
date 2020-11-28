package com.springbatch.pagination.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("studentJobListener")
public class StudentJobListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(StudentJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job is Started with status - {}.\n StartTime {}", jobExecution.getStatus(), jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        final Date startTime = jobExecution.getStartTime();
        final Date endTime = jobExecution.getEndTime();

        long executionTime = endTime.getTime() - startTime.getTime();
        log.info("Job is completed with status - {}. Total time to execute the job - {}.\n StartTime - {}\n EbdTime - {} ",
                jobExecution.getStatus(), executionTime, startTime, endTime);
    }
}
