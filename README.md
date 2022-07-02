# mileage

# 환경 설정
1. 최신 Mysql을 설치한다. (https://www.mysql.com/downloads/)

2. build.gradle에 있는 종속성을 설치한다.

3. src/main/resources 폴더에 application.properties를 생성한다.
### 예시

```
server.servlet.encoding.charset=UTF-8
server.servlet.encoding.force=true

## MySQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

## DB Source URL
spring.datasource.url=jdbc:mysql://{IP}:{Port}/{DB명}

## DB username
spring.datasource.username={username}

## DB password
spring.datasource.password={password}

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true

logging.level.org.hibernate.type.descriptor.sql=TRACE
```　
{}에 적절한 값을 넣어준다.

이후 실행하면 정상 실행됩니다.

# 실행 방법
## Request
### 리뷰
```
{
"type": "REVIEW",
"action": "ADD",
"reviewId": "test1_1",
"content": "테스트11",
"attachedPhotoIds": ["photo1", "photo2"],
"userId": "test1",
"placeId": "place1"
}
```
와 같은 유형으로 request를 보낼 수 있습니다. action에는 "MOD", "DELETE"가 각각 있습니다.
MOD를 하면 content와 photo를 그 값으로 UPDATE해줍니다.
DELETE는 해당 reviewId의 리뷰를 삭제합니다.

### 포인트 조회
포인트는 review 이벤트가 발생할 때마다 point_history 테이블에 점수 변동 이력이 기록됩니다.
이 변동 이력을 user 테이블에 적용시켜 현재 보유한 점수를 가지고 있습니다.
이를 get의 /point?id={userId} 로 data: {"point" : point }를 받아올 수 있습니다.

## Response
기본적으로 response는 성공하면 {"code": "9200", "message" : "SUCCESS", "data" : data } 을 받습니다.
예외처리된 실패시, code와 message가 적절한 값으로 리턴됩니다.
일반적인 Exception은 message가 "EXCEPTION"이 리턴됩니다.

