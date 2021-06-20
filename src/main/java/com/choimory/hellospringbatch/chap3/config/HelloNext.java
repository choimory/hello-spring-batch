package com.choimory.hellospringbatch.chap3.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class HelloNext {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chapterThreeJob(){
        return jobBuilderFactory.get("chap3Job")
                .start(chapterThreeStep1())
                .next(chapterThreeStep2())
                .next(chapterThreeStep3())
                .build();
    }

    @Bean
    public Step chapterThreeStep1(){
        return stepBuilderFactory.get("chap3Step1").tasklet(((contribution, chunkContext) -> {
            log.info(">> chap3Step1");
            return RepeatStatus.FINISHED;
        }))
        .build();
    }

    @Bean
    public Step chapterThreeStep2(){
        return stepBuilderFactory.get("chap3Step2").tasklet(((contribution, chunkContext) -> {
            log.info(">> chap3Step2");
            return RepeatStatus.FINISHED;
        }))
        .build();
    }

    @Bean
    public Step chapterThreeStep3(){
        return stepBuilderFactory.get("chap3Step3").tasklet(((contribution, chunkContext) -> {
            log.info(">> chap3Step3");
            return RepeatStatus.FINISHED;
        }))
        .build();
    }
}
