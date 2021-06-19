package com.choimory.hellospringbatch.chap1.config;

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
public class ChapterOneConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("chapterOneJob")
                                .start(step1())
                                .build();
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("chapterOneStep1")
                                    .tasklet((contribution, chunkContext) -> {
                                        log.info("chapter one, step 1");
                                        return RepeatStatus.FINISHED;
                                    })
                                    .build();
    }
}
