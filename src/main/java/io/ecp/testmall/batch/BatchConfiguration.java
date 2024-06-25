package io.ecp.testmall.batch;

import io.ecp.testmall.order.entity.Order;
import io.ecp.testmall.order.entity.OrderStatus;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BatchConfiguration {

    private final static int CHUNK_SIZE = 5;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job batchJob() {
        return new JobBuilder("batchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Order, Order>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemReader<Order> itemReader() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("date", LocalDateTime.now().minusDays(3));

        return new JpaPagingItemReaderBuilder<Order>()
                .name("itemReader")
                .pageSize(CHUNK_SIZE)
                .entityManagerFactory(entityManagerFactory)
                .queryString("select o from Order o join fetch o.delivery d where d.deliveryStatus = 'COMP' and d.updateDate < :date")
                .parameterValues(parameters)
                .build();
    }

    @Bean
    public ItemProcessor<Order, Order> itemProcessor() {
        return order -> {
            order.setOrderStatus(OrderStatus.COMP);
            return order;
        };
    }

    @Bean
    public ItemWriter<Order> itemWriter() {
        JpaItemWriter<Order> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
