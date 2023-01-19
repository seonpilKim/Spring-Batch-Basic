package study.spring.SpringBatchBasic.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import study.spring.SpringBatchBasic.SpringBatchTestConfig;
import study.spring.SpringBatchBasic.domain.account.AccountRepository;
import study.spring.SpringBatchBasic.domain.order.Order;
import study.spring.SpringBatchBasic.domain.order.OrderRepository;

@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {SpringBatchTestConfig.class, TrMigrationJobConfig.class})
class TrMigrationJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void cleanUpEach() {
        orderRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void success_noData() throws Exception {
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // then
        assertEquals(jobExecution.getExitStatus(), ExitStatus.COMPLETED);
        assertEquals(accountRepository.count(), 0);
    }

    @Test
    void success_existData() throws Exception {
        // given
        Order order1 = new Order(null, "kakao_gift", 15000, new Date());
        Order order2 = new Order(null, "naver_gift", 16000, new Date());

        orderRepository.save(order1);
        orderRepository.save(order2);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // then
        assertEquals(jobExecution.getExitStatus(), ExitStatus.COMPLETED);
        assertEquals(accountRepository.count(), 2);
    }

}
