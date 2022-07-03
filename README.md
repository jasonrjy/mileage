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

### ADD
ADD시 review 테이블에 (userId, placeId)는 중복으로 추가할 수 없습니다.
이는 한 유저는 장소 당 리뷰를 1개만 작성할 수 있습니다.
단, status="delete"일 때는 삭제된 리뷰이므로 추가가 가능합니다.

리뷰가 추가될 때, 텍스트를 작성하였는지(+1), 해당 장소에 첫 리뷰인지 (+1), 사진이 첨부되어있는지(+1)에 따라 점수를 지급합니다.

### MOD
reviewId를 기준으로 content와 photo를 수정해줍니다.
수정된 이후 status="modify"로 바꾸어 수정된 리뷰인지 표시해 줍니다.
이후, 수정된 리뷰의 내용에 따라 점수를 변동합니다.

### DELETE
reviewID에 해당하는 리뷰를 삭제합니다.
이때 테이블에서 직접 삭제하지 않고, status="deleted"로 변경합니다.
삭제된 리뷰의 점수에 따라 유저의 점수도 변경합니다.

------

### 포인트 조회
포인트는 review 이벤트가 발생할 때마다 point_history 테이블에 점수 변동 이력이 기록됩니다.
이 변동 이력을 user 테이블에 적용시켜 현재 보유한 점수를 가지고 있습니다.
이를 get의 "/point?id={userId}" 로 data: {"point" : point }를 받아올 수 있습니다.

## Response
기본적으로 response는 성공하면 {"code": "9200", "message" : "SUCCESS", "data" : data } 을 받습니다.
예외처리된 실패시, code와 message가 적절한 값으로 리턴됩니다.
일반적인 Exception은 message가 "EXCEPTION"이 리턴됩니다.

