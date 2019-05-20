#### MY Api Server (#Part 1)
----

Springboot, H2DB, JPA, Mybatis, Lombok 등의 Framework을 갖는 Rest Api 서버입니다.
OAuth 2.0 기반 Google 로그인 연동, JWT 쿠키 기반 Rest API 서비스가 주요 기능이며, Vue로 제작된 간단한 Chart 페이지도 구현되어 있습니다.

API로 제공하는 기능은, 
1. JWT 토큰 발급 및 Authorization 기반 인증.
2. InfluxDB 기반 시계열 데이터 제공. (이 정보 제공을 위해, InfluxDB + Telegraf Agent를 별도로 설정해야 합니다.)
3. 기타 (OAuth 2.0 기반 Google 로그인, Kafka클라이언트 활용 Message Produce Api, 그 외 Admin 기능 등)

----

## 개발 도구
```
OS : Windows 7
개발 언어 : JDK 1.8
IDE : STS(Eclipse) , Visual Studio Code
```


## 빌드 도구
```
Maven : 3.3.9
```


## 인증 방식
```
OAuth 2.0 - Google Account
JWT 토큰 인증 (HS256)
```


## 메인 Framework
```
Springboot : 2.1.5
Lombok : 1.18.8
mybatis-spring-boot : 2.0.1
springboot-jpa: 2.1.5
Vue: 2.5.17
Junit: 4.12
h2db: 1.4
influxdb-java: 2.15
```


## 기타 Library
```
Kafka-client: 2.0.1
Chart-js: 2.7.2
java-jwt: 3.4.0
```


## Agent 도구
```
InfluxDB
Telegraf
Kafka
Zookeeper
Swagger
```


## 기타 사항
```
암호화 기반 HTTPS 통신. 
```

----


#### 실행 환경 Setting

## InfluxDB Agent 시작 (Windows만 작성함)
```
(Source_Root_Directory\ext\start_influxdb.bat
```
----

## Telegraf Agent 시작 
```
(Source_Root_Directory\ext\start_telegraf.bat
```
----

## SpringBoot App 구동
```
Run Java Program : ApiserverApplication.java
```
----

## Localhost 접속 확인 (기본 포트 7443)
```
https://localhost:7443/
```

## Googld 계정 Oauth 2.0 로그인
```
로그인 이 후 https://localhost:7443/ 페이지 방문하면 index.html 확인 가능.
```

----------
