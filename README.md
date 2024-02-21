#ecom-authorisation-server

Helpful Guide
- [Spring Authorisation Server](https://docs.spring.io/spring-authorization-server/reference/getting-started.html)

- [Nice Blog to get more around authorisation server](https://spring.io/blog/2023/05/24/spring-authorization-server-is-on-spring-initializr)

To test If the client config are correct
```shell
http -f POST :8080/oauth2/token grant_type=client_credentials scope='user.read' -a client:secret
```