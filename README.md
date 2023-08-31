## Описание

Создать сервис, который обращается к сервису курсов валют, и отдает в ответ гифку.

Если курс по отношению к USD за сегодня стал выше вчерашнего, отдаем рандомную гифку [отсюда](https://giphy.com/search/broke), если ниже - [отсюда](https://giphy.com/search/rich).

API курсов валют - https://docs.openexchangerates.org/  
API гифок - https://developers.giphy.com/docs/api#quick-start-guide

Must Have  
- [X] Запросы приходят на HTTP endpoint, туда передается код валюты  
- [X] Для взаимодействия с внешними сервисами используется Feign  
- [X] Все параметры (адреса внешних сервисов и т.д.) вынесены в настройки  
- [X] На сервис написаны тесты  
- [X] для мока внешних сервисов можно использовать @mockbean или WireMock  
- [X] Для сборки должен использоваться Gradle  
- [X] Сборка и запуск Docker контейнера с этим сервисом  

## Docker

Получить образ с DockerHub:

```
docker pull a1irice/gif-generator:latest
```

Запуск:

```
docker run -p 8080:8080 a1irice/gif-generator:latest
```

## API

Получить список всех доступных валют

```
GET localhost:8080/api/available-currencies
```

Получить гифку

```
GET localhost:8080/api/get-gif/{currency}
```
Где currency - валюта по которой проверяется курс

## Использованные технологии

- Java 17
- Spring Boot
- JUnit 5
- Mockito
- Jackson
- OpenFeign
- Gradle
- Docker
