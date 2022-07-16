package com.springbatch.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing // This annotation allows Spring to assemble all the structure needed to run the batch
@SpringBootApplication
public class SpringbatchApplication {

	// jobBuilderFactory e stepBuilderFactory: Injecting the components to build the processes Job and Step
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

	@Bean
	public Step stepOne() {
		return stepBuilderFactory.get("stepOne").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Step one");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	public Step stepTwo() {
		return stepBuilderFactory.get("stepTwo").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Step two");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	// Using a sequence of Steps
	@Bean
	public Job job() {
		return this.jobBuilderFactory
			.get("jobOne")
			.start(stepOne())
			.next(stepTwo())
		//	.incrementer() Setting times of execution
		//	.validator(validator()) Setting the validator to the parameters
			.listener(
				JobListenerFactoryBean.getListener(new JobListener())
			)
			.build();
	}
}
