# Документация:
1. [План тестирования]()
# Задача:
Автоматизировать позитивные и негативные сценарии покупки тура в комплексном сервисе, взаимодействующем с СУБД и API Банка.
![image](https://raw.githubusercontent.com/netology-code/qa-diploma/master/pic/service.png)
Приложение — это веб-сервис, который предлагает купить тур по определённой цене двумя способами:
1. Обычная оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.

# Инструкция по подключению СУБД и запуска SUT:
1. Клонировать проект из репозитория командой: `git clone`
2. Открыть склонированный проект в Intellij IDEA
3. Запуск тестов:
    - С использованием MySQL:
        - для запуска контейнеров с MySQL и Node.js ввести в терминале команды:
            - сначала: `docker-compose up node_app -d --force-recreate`
            - затем: `docker-compose up mysql_service -d --force-recreate`
        - для запуска SUT ввести в терминале команду: `java -jar artifacts/aqa-shop.jar`
        - для запуска тестов и получения отчета Allure в браузере использовать команду: `./gradlew allureserve`
    - С использованием PostgreSQL:
        - для запуска контейнеров с PostgreSQL и Node.js ввести в терминале команды:
            - сначала: `docker-compose up node_app -d --force-recreate`
            - затем: `docker-compose up postgres_service -d --force-recreate`
        - для запуска SUT ввести в терминале команду: `java -jar artifacts/aqa-shop.jar`
        - для запуска тестов и получения отчета Allure в браузере использовать команду: `./gradlew allureserve`
4. После окончания тестов завершить работу приложения (Ctrl + C), остановить контейнеры командой: `docker-compose down`