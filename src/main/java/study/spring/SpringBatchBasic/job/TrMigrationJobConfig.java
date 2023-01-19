package study.spring.SpringBatchBasic.job;

import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import study.spring.SpringBatchBasic.domain.account.Account;
import study.spring.SpringBatchBasic.domain.account.AccountRepository;
import study.spring.SpringBatchBasic.domain.order.Order;
import study.spring.SpringBatchBasic.domain.order.OrderRepository;

@Configuration
@RequiredArgsConstructor
public class TrMigrationJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;

    @Bean
    public Job trMigrationJob(Step trMigrationStep) {
        return jobBuilderFactory.get("trMigrationJob")
                .incrementer(new RunIdIncrementer())
                .start(trMigrationStep)
                .build();
    }

    @Bean
    @JobScope
    public Step trMigrationStep(ItemReader<Order> trOrderReader, ItemProcessor<Order, Account> trOrderProcessor, ItemWriter<Account> trAccountWriter) {
        return stepBuilderFactory.get("trMigrationStep")
                .<Order, Account>chunk(5) // 5개의 데이터 단위(chunk)로 처리. <Read type, Writer type>
                .reader(trOrderReader)
                .processor(trOrderProcessor)
                .writer(trAccountWriter)
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<Account> trOrderWriter() {
        return new RepositoryItemWriterBuilder<Account>()
                .repository(accountRepository)
                .methodName("save")
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Account> trOrderWriterV2() {
        return items -> items.forEach(accountRepository::save);
    }

    @Bean
    @StepScope
    public ItemProcessor<Order, Account> trOrderProcessor() {
        return Account::of;
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Order> trOrderReader() {
        return new RepositoryItemReaderBuilder<Order>()
                .name("trOrderReader")
                .repository(orderRepository)
                .methodName("findAll")
                .pageSize(5)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

}
