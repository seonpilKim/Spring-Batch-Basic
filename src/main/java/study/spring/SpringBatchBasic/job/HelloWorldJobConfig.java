package study.spring.SpringBatchBasic.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job helloWorldJob() {
		return jobBuilderFactory.get("helloWorldJob")
			.incrementer(new RunIdIncrementer()) // Job Sequence 1부터 증가
			.start(helloWorldStep())
			.build();
	}

	@Bean
	@JobScope
	public Step helloWorldStep() {
		return stepBuilderFactory.get("helloWorldStep")
			.tasklet(helloWorldTasklet()) // Read/Write가 없는 단순한 step
			.build();
	}

	@Bean
	@StepScope
	public Tasklet helloWorldTasklet() {
		return (contribution, chunkContext) -> {
			System.out.println("Hello World Spring Batch");
			return RepeatStatus.FINISHED; // Indicates that processing is finished (either successful or unsuccessful)
		};
	}

}
