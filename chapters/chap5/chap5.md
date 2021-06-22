# Chap 5.

# Chunk

![999A513E5B814C4A12.png](999A513E5B814C4A12.png)

- 데이터 덩어리
- 각 커밋 사이에 처리되는 데이터 건수
- Chunk 지향 처리란 한번에 하나씩 데이터를 읽어 Chunk라는 데이터 덩어리를 만든뒤 Chunk 단위로 트랜잭션을 다루는것
- 도중 실패시 해당 Chunk만큼만 롤백이 되고, 이전 Chunk까지는 반영이 된다
- Chunk는 [ItemReader(조회) + ItemProcessor(처리 및 가공, 생략가능) + ItemWriter(작성)]으로 구성되어 있다
    - Reader와 Processor는 1건(하나의 Item)단위로 작업을 하여 별도로 모아둔다
    - Writer는 그렇게 모은 데이터를 Chunk 단위로 밀어넣는다
    - 정리: Reader와 Processor는 1건씩, Writer는 Chunk 단위로 처리한다

# ChunkOrientedTasklet

- Chunk 지향 처리의 전체 로직을 다루는 클래스
- 자세한 내용 일단 생략

# SimpleChunkProcessor

- 자세한 내용 일단 생략

# Page size, Chunk size

- Page size: 한번에 조회될 Item의 양
- Chunk size: 한번에 트랜잭션 처리될 양
- 보통 Page와 Chunk의 Size를 일치시켜 작업하는것이 보편적으로 좋은 방법이다. 왠만해선 일치시켜 사용하자