package com.springbatch.springbatch;

import javax.batch.runtime.JobExecution;

import org.springframework.batch.core.annotation.BeforeJob;

public class JobListener {
    private static String START_MESSAGE = "$s execution";
    private static String END_MESSAGE = "$s has completed, status $s";

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        System.out.println(String.format(START_MESSAGE,
        jobExecution.getJobName()));
    }

    @BeforeJob
    public void afterJob(JobExecution jobExecution) {
        System.out.println(
        String.format(END_MESSAGE,
        jobExecution.getJobName(),
        jobExecution.getBatchStatus()));
    }
}
