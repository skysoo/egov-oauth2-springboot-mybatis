# Resource Server
Spring Security 를 이용한 리소스 서버 구축

## application.yml
```yml
# springboot
server:
  port: 8081
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

security:
  oauth2:
    client:
      client-id: clientId
      client-secret: testSecret
    resource:
      token-info-uri: http://localhost:8080/oauth/check_token

      # keyCloak 오픈소스 인증 서버 활용시
      #token-info-uri: http://<keycloak IP>:8080/auth/realms/epozen/protocol/openid-connect/token/introspect
      #user-info-uri: http://<keycloak IP>:8080/auth/realms/epozen/protocol/openid-connect/userinfo

# 리소스 관련 커스텀 설정
common:
  client-url: http://localhost:8082/client/total-cmd

logging:
  level:
    org:
      springframework: DEBUG
```

