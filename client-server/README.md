# Client Server
인증 서버로부터 리소스 서버로의 접근 가능한 권한(Token)을 부여 받고 리소스 서버와 통신

## application.yml
```yml
# springboot
server:
  port: 8082
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


# Oauth 관련 커스텀 설정
oauth:
  client-id: clientId
  client-secret: testSecret
  # scope 지정 - openid, read
  scope: read 
  grant-type: client_credentials
  authentication-server-url: http://localhost:8080/oauth/token

  # keyCloak 오픈소스 인증 서버 활용시
  # authentication-server-url: http://<keycloak IP>:8080/auth/realms/epozen/protocol/openid-connect/token

# 클라이언트 관련 커스텀 설정
common:
  gw-url: http://localhost:8081/resource/gw-upload-data

logging:
  level:
    org:
      springframework: DEBUG

```