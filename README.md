# security-oauth2-authentication

SpringSecurity 를 이용하여 OAuth2 인증 서버 자체 구현한 프로젝트

# egov-oauth2-springboot-mybatis

전자 정부 프레임워크 공식 사이트에서 제공하는 개발 환경은 이클립스 환경이며, Spring 기반으로 작성되었는데

Intellij 환경에서 SpringBoot 위에 전자 정부 프레임워크를 기동해야하는 상황이 생겨서 관련 코드를 작성해봤다.

먼저 전자 정부 프레임워크의 적용 기준 핵심은 크게 아래 두가지 규칙을 지키면 Application 프레임워크가 Spring 이든 SpringBoot 든 상관없다.

1. 전자 정부 프레임워크의 실행 환경 추가
    - egov~ 로 시작하는 .jar 파일을 프로젝트에 추가해줘야 하며, 반드시 하나 이상의 Dao 클래스와 하나 이상의 Service 클래스가 구현되어야 한다.

2. Service Class는 EgovAbstractServiceImpl 클래스를 상속 받아야 하고, Dao 클래스는 Mybatis의 경우 @Mapper 어노테이션이 필수다.

# 1. Application 정상 기동에 필요한 사전 준비 사항

## 1-1 . OAuth2.0 - clientId, clientSecret 발급 
OAuth2.0의 정상 적용을 위해 구글과 네이버의 개발자 센터에서 client-id와 client-secret 값을 발급 받아서 application.yml에 기입해야 한다.

## 1-2. Database Table 생성

아래 사이트의 *2-2. 인증 정보 DB 테이블* 참고해라. 

>https://velog.io/@skysoo/%EB%B0%B1%EC%97%94%EB%93%9C-%EA%B0%9C%EB%B0%9C%EC%9E%90-%EB%A1%9C%EB%93%9C%EB%A7%B5-%EB%94%B0%EB%9D%BC%EA%B0%80%EA%B8%B0-10.-OAuth2.0

## 1-3. 비대칭키 생성 (JWT 토큰의 무결성 검증에 사용할 Private Key, Public Key)

비대칭 키 방식은 인증 서버가 비밀키와 공개키 1쌍을 만든다. 데이터는 공개키로 암호화되고, 비밀키로만 복호화가 가능하다.

인증 서버는 리소스 서버에게 공개키를 공유하고 해당 키로 암호화하여 데이터를 전달해달라고한다.

1. CMD에서 아래 명령어를 치면 oauth2jwt.jks 파일이 생성된다.
```
λ keytool -genkeypair -alias oauth2jwt -keyalg RSA -keypass oauth2jwtpass -keystore oauth2jwt.jks -storepass oauth2jwtpass
```

2. authentication-server
> oauth2jwt.jks 파일 src/main/resources 경로에 넣기

3. resource-server
> publicKey.txt 파일 src/main/resources 경로에 넣기
```
-----BEGIN PUBLIC KEY-----
<public key value>
-----END PUBLIC KEY-----
```

## 1-4. 프로젝트 정상 구동 확인

기동 로그에서 아래와 같이 확인 가능하다.
 

# 2. Application 구현 기능 목록 

## 2-1. 보안 - OAuth2.0
    - 입력 파라미터 정규 표현식으로 검증
    - Spring Security + OAuth2.0 적용 (구글, 네이버)

## 2-2. Producer-Consumer 패턴에 PriorityBlockingQueue 적용
    - 목적별로 작업을 구분하여 데이터 흐름을 통제하고 입력 데이터 증가에 따른 대처 가능
    
    - 우선 순위를 고려한 Queue 사용
        
## 2-3. MyBatis 사용
    - SQL Session 관리가 용이함
    - 복잡한 JDBC 코드를 없애고 간결한 코드 작성 가능
    - SQL 쿼리를 소스 코드와 완전히 분리하여 유지 보수 효율 증대
    
## 2-4. Lambda 내 Exception 발생시 처리 Handler 구현
    - 기본적으로 Lambda 내부에서 Exception 이 발생하게 되면 try-catch 로 처리해주게 되는데 이 때는 코드가 지저분해지기 때문에 Handler를 작성하여 코드를 간결하게 작성할 수 있다.
    
## 2-5. Spring 스케줄러 사용 
    
## 2-6. Log4j2 사용
    - 로그 관리 효율 증대
    
    
![OAuth2.0 인증 흐름](./99.Img/OAuth2.0인증서버구성도.png)    

## OAuth2.0 승인 종류
이 프로젝트에서 구현한 승인 방식은 아래 4가지 중 4번 클라이언트 자격 증명 방식이다.

클라이언트 자격 증명 방식이란, 쉽게 말해 서버간(리소스서버-클라이언트서버) 통신을 의미한다. 

1. 권한 코드 승인 (Authorization Code Grant Type)
2. 암시적 승인 (Implicit Grant)
3. 리소스 소유자 비밀번호 자격 증명 (Resource Owner Password Credentials Grant)
4. 클라이언트 자격 증명 (Client Credentials Grant Type)

## 토큰 종류
### 1. Bearer Token
### 2. Json Web Token(JWT) 

> JWT 방식은 아래와 같은 특징 있다.
```
 * JWT 방식은 Json String이 암호화된 방식으로 토큰 + 특정 정보 (사용자 정보)를 같이 셋팅
 * 이렇게 되면 DB를 사용하지 않는다. 이유는 토큰 자체로 인증 정보를 관리하기 때문이다.
 * 인증 서버로의 토큰 추가 검증 요청이 없기 때문에 인증 서버 요청 오버헤드를 줄일 수 있다.
 * JWT 는 Stateless 로 쿠키나 세션을 가지고 있지 않기 때문에 관리 포인트도 줄어든다.
```        
    
# application.yml 
~~~yaml
# springboot
server:
  port: 8080
  tomcat:
    max-http-form-post-size: -1

# SPRING 관련 설정
spring:
  datasource:
    url: jdbc:postgresql://<DB IP:PORT>/postgres
    driver-class-name: org.postgresql.Driver
    username: <DB USER NAME>
    password: <DB PASSWORD>
  servlet:
    multipart:
      max-file-size: 200MB
      max-request-size: 200MB
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: <CLIENT-ID>
            client-secret: <CLIENT-SECRET>
            access-token-uri: https://www.googleapis.com/oauth2/v4/token
            user-authorization-uri: https://accounts.google.com/o/oauth2/v2/auth
            client-authentication-scheme: form
            scope: email, profile
          naver:
            client-name: Naver
            client-id: <CLIENT-ID>
            client-secret: <CLIENT-SECRET>
            redirect-uri: http://localhost:8080/login/oauth2/code/naver
            authorization-grant-type: authorization_code
            scope: name,email
        provider:
          naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token_uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user_name_attribute: response

# Application 관련 커스텀 설정 
common:
  file-path: filePath
  file-save-path: fileSavePath

# 로그 관련 설정 
logging:
  level:
    org:
      springframework: INFO
~~~