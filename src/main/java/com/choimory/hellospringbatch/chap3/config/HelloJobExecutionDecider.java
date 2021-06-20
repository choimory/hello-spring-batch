package com.choimory.hellospringbatch.chap3.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class HelloJobExecutionDecider {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
     * 구현한 홀짝 decider 반환 메소드
     */
    @Bean
    public JobExecutionDecider decider(){
        return new OddDecider();
    }

    /**
     * 별도의 홀짝 구분 decider 구현
     */
    public static class OddDecider implements JobExecutionDecider{
        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            Random random = new Random();

            int randomNumber = random.nextInt(50)+1;
            log.info(">> Decider said, 숫자 -> {}", randomNumber);

            if(randomNumber % 2 == 0){
                return new FlowExecutionStatus("EVEN");
            } else {
                return new FlowExecutionStatus("ODD");
            }
        }
    }

    @Bean
    public Job deciderJob(){
        return jobBuilderFactory.get("chap3.2Job")
                .start(startStep())
                .next(decider())
                .from(decider())
                    .on("ODD")
                    .to(oddStep())
                .from(decider())
                    .on("EVEN")
                    .to(evenStep())
                .end()
                .build();
    }

    @Bean
    public Step startStep(){
        return stepBuilderFactory.get("chap3.2StartStep")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">> step start");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step evenStep(){
        return stepBuilderFactory.get("chap3.2EvenStep")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">> 짝수 Step said -> 짝수입니다");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step oddStep(){
        return stepBuilderFactory.get("chap3.2OddStep")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">> 홀수 Step said -> 홀수입니다");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
