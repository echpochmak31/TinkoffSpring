# Проект по курсу Backend разработка на Java от Tinkoff

Микросервисное приложение на Java, которое позволяеть отслеживать обновления для вопросов на
stackoverflow.com и репозиториев на github.com


## Описание
Приложение состоит из 2 сервисов: сервис [telegram бота](https://t.me/L1nkTrackerBot), через который и
осуществлялось взаимодействие с пользователями, и сервис scrapper, который,
используя API stackoverflow и github, периодически проверял обновления для
отслеживаемых ссылок.

Взаимодействие с Telegram Bot API осуществляется не напрямую, а через библиотеку [pengrad
/
java-telegram-bot-api](https://github.com/pengrad/java-telegram-bot-api).





## Особенности:
- Приложение построено на фреймворке Spring Boot
- Docker compose для инфраструктуры приложения
- В качетсве базы данных я выбрал PostgreSQL 15
- Liquibase для версионирование БД
- Межсервисное взаимодействие осуществляется через брокера сообщений RabbitMQ
- Я применял некоторые паттерны ООП (например, цепочка обязанностей в link-parser)
- Тесты с использованием JUnit и Mockito
- GitHub Actions помог автоматизировать процесс разработки (этапы сборки, пуша docker image в реестр,
проверку кода линтером)
- Есть несколько вариантов доступа к данным: через JDBC и JPA (задается в application.yml)
- Для сбора и визуализации метрик я использую связку Prometheus + Grafana

