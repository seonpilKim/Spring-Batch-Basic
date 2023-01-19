package study.spring.SpringBatchBasic.scheduler;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SampleScheduler {

    private final Job helloWorldJob;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 */1 * * * *") // 1분마다 구동
    public void helloWorldJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );
        jobLauncher.run(helloWorldJob, jobParameters); // parameter 없이 실행시키면, 동일한 job으로 여기어 batch가 실행되지 않을 수 있다.
    }

}
