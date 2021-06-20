package com.choimory.hellospringbatch.chap3.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class HelloConditionalJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job conditionalJob(){
        //step 1 성공시: step1-> step2-> step3
        //step 2 실패시: step1-> step3
        return jobBuilderFactory.get("chap3.1Job")
                .start(conditionalStep1())
                    .on("FAILED") //FAILED일시
                    .to(conditionalStep3()) //Step 3로 이동
                    .on("*") //이동 후 Step 3의 결과와 관계없이
                    .end() //Flow 종료
                .from(conditionalStep1()) //Step 1이
                    .on("*") //FAILED 외의 모든 경우일때
                    .to(conditionalStep2()) //Step 2로 이동
                    .next(conditionalStep3()) //Step 2가 정상적으로 종료되면 Step 3로 이동
                    .on("*") //이동 후 Step 3의 결과와 관계없이
                    .end() //Flow 종료
                .end() //Job 종료
                .build();
    }

    @Bean
    public Step conditionalStep1(){
        return stepBuilderFactory.get("chap3.1Step1")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">> chapter 3.1, step 1");

                    //ExitStatus를 FAILED로 설정
                    //ExistStatus의 상태를 기준으로 Flow가 결정됨
                    contribution.setExitStatus(ExitStatus.FAILED);

                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step conditionalStep2(){
        return stepBuilderFactory.get("chap3.1Step2")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">> chapter 3.1, step 2");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }

    @Bean
    public Step conditionalStep3(){
        return stepBuilderFactory.get("chap3.1Step3")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">> chapter 3.1, step 3");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
