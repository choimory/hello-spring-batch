# 개요

- Spring batch 학습 프로젝트

# 스택

- Java 11
- Spring boot 2.3.12
- Spring Web
- Spring Web Services
- Spring Data JPA
- H2
- MariaDB
- Spring batch
- Quartz
- Lombok
- JUnit 5 (Jupiter)
- Spring REST Docs

# 주요사항

> 자세한 사항은 chapters 하위 항목을 참고

## Chap 1.

- 배치 어플리케이션 등록 
  - `@EnableBatchProcessing`
- Job 클래스 생성 
  - `@Configuration`
- [Job] > [Step] > [Tasklet] 혹은 [Chunk (Reader~Processor~Writer)]
- Job 작성 
  - `JobBuilderFactory.get("Job명")`
- Step 작성 
    - `StepBuilderFactory.get("Step명")`
    - Tasklet
        - `Step.tasklet((contribution, chunkContext) -> { 로직, return RepeatStatus.FINISHED})`
    - Chunk
        - ItemReader ~ ItemProcessor ~ ItemWriter

## Chap 2.

- Spring batch 메타 테이블
  - BATCH_JOB_INSTANCE
      - BATCH_JOB_EXECUTION
          - BATCH_JOB_EXECUTION_CONTEXT
          - BATCH_JOB_EXECUTION_PARAMS
          - BATCH_STEP_EXECUTION
            - BATCH_STEP_EXECUTION_CONTEXT

## Chap 3.

- Job 실행분기 (Job Flow)
    - Job.start()
    - Job.next()
    - Job.from(), Job.end(), Job.on(), Job.to()
    - Batch Status, Exit Status
    - JobExecutionDecider
- 지정한 Batch Job만 실행하기
    - yml 설정 `spring.batch.job.names: ${job.name:NONE}`
    - program args `--job.names=Job명 version=버전명`

## Chap 4.

- Job Parameters
    - `@Value("#{jobParameters[param명]}")`
    - Job Param으로 받기 vs 환경 변수, 시스템 변수로 받기
- Scope
    - Job의 실행 시점에, Step에 대한 Bean을 생성하는 `@JobScope`
    - Step의 실행 시점에, Tasklet 혹은 Chunk(Reader~Writer)에 대한 Bean을 생성하는 `@StepScope`

## Chap 5.

- Spring Batch에서 데이터를 처리하는 단위 `Chunk`
    - Chunk 지향 처리
        - `ChunkOrientedTasklet`
            - `ItemReader`
            - `ItemProcessor`
            - `ItemWriter` 
        - Processor와 Writer 로직을 담은 `ChunkProcessor`, `SimpleChunkProcessor`
    - Page Size vs Chunk Size

## Chap 6.

- ItemReader
    - File, XML, JSON 관련 ItemReader (생략)
    - DB 관련 ItemReader
        - Cursor 기반 ItemReader (1 row 단위)
            - JdbcCursorItemReader
            - HibernateCursorItemReader
            - StoredProcedureItemReader
        - Paging 기반 ItemReader (N Row 단위)
            - JdbcPagingItemReader
            - HibernatePagingItemReader
            - JpaPagingItemReader

## Chap 7.

- ItemWriter
    - File, XML, JSON 관련 ItemWriter (생략)
    - DB 관련 ItemWriter
        - 영속성 flush(), session.clear()
        - JdbcBatchItemWriter
        - HibernateItemWriter
        - JpaItemWriter
    - ItemWriter 구현하기

## Chap 8.

- ItemProcessor
    - 기본 사용법 (구현방법)
    - 변환
    - 필터
    - 트랜잭션 범위, 지연 로딩
    - ItemProcessor 기본제공 구현체
        - ItemProcessorAdapter
        - ValidatingItemProcessor
        - CompositeItemProcessor

## Chap 9.

- Spring Batch Test
    - 통합 테스트
    - 단위 테스트

# 출처

> jojoldu.tistory.com