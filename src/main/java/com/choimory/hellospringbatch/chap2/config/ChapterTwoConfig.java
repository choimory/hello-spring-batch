package com.choimory.hellospringbatch.chap2.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ChapterTwoConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("chapterTwoJob")
                .start(step1(null))
                .build();
    }

    @Bean
    @JobScope
    public Step step1(@Value("#{jobParameters[requestDate]}") String requestDate){
        return stepBuilderFactory.get("chapterTwoStep1")
                .tasklet(((contribution, chunkContext) -> {
                    log.info("chap2 step1 logging");
                    log.info("job param : requestDate -> {}", requestDate);
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
