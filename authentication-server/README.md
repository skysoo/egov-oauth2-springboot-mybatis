# Authentication Server
Spring Security 를 이용한 인증 서버 구축


## application.yml
```yml
# springboot
server:
  port: 8080
  tomcat:
    max-http-form-post-size: -1

# SPRING 설정
spring:
  datasource:
    url: jdbc:postgresql://<DB IP:PORT>/postgres
    driver-class-name: org.postgresql.Driver
    username: <DB USER NAME>
    password: <DB PASSWORD>
    hikari:
      schema: <DB SCHEMA NAME>
  servlet:
    multipart:
      max-file-size: 200MB
      max-request-size: 200MB

# 인증서버 관련 커스텀 설정
common:
  # token-type : JWT or BEARER
  token-type: JWT
  client-id: clientId
  client-secret: testSecret

logging:
  level:
    org:
      springframework: DEBUG
```