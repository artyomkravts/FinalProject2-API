## Проект: Автоматические тесты API для сайта stellar burgers
URL: https://stellarburgers.nomoreparties.site/

### Используемый стек технологий
- Java
- JUnit — для организации тестов
- Rest-Assured — для тестирования REST API
- Maven — управление зависимостями и сборка
- Allure — для генерации отчетов
- Hamcrest Matchers — для написания проверок
- Java Faker, RandomStringUtils — для генерации случайных тестовых данных
- POM (Page Object Model) — для упрощения взаимодействия с API и оптимизации структуры тестов
- POJO — для передачи данных между запросами и ответами

### Основные тест-кейсы
  - Регистрация и авторизация пользователей
  - Изменение данных пользователей
  - Создание заказов с проверкой ингредиентов
  - Проверка статусов и корректности ответов API

### Как установить и запустить
1. Клонируйте репозиторий:  
*git clone https://github.com/artyomkravts/FinalProject2-API.git*   
2. Перейдите в папку проекта:  
*cd <имя папки проекта>*   
3. Выполните сборку проекта:  
*mvn clean install*
4. Запустите тесты:  
*mvn clean test*
5. Сформируйте отчёт  
*mvn allure:report* (или можно посмотреть готовый отчёт в папке site > index.html)
