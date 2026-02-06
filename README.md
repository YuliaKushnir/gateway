## Welcome to final task for travel blog project

Даний Gateway є точкою входу до сервісів, створених під час виконання попередніх завдань, а також відповідає за автентифікацію через google.

Згідно з вимогами завдання без логіну через Google юзер не має можливості використовувати додаток. 

### Функції Gateway 
- обробляє автентифікацію користувачів через Google OAuth2
- зберігає сесії користувачів 
- перевіряє наявність cookie у запитах
- проксіює запити до backend-мікросервісів

### /profile, 
До gateway додано endpoint profile, який повертає атрибути користувача (google id, ім'я, email, ...). 
При старті фронтенд робить запит на цей endpoint і якщо він показує помилку 401 unauthorized - фронтенд перенаправляє на сторінку авторизації google.
При успішній автентифікації користувач отримує 200 і дані користувача при повторному запиті до /profile.
Також цей endpoint використовується java-backend - він перевіряє наявність користувача в БД (створеній при виконанні завдання 2) і повертає id або за відсутності створює нового і повертає його id.

## Запуск gateway
Передумови:
- Java 21 
- Запущені backend-сервіси (posts, comments, users тощо)
- Створений OAuth2 client у Google Cloud Console
- Запущений docker (до проекту додано docker-compose.yml)

1. Налаштування змінних середовища

У .env додати змінні:

GOOGLE_CLIENT_ID=your_google_client_id

GOOGLE_CLIENT_SECRET=your_google_client_secret

2. Збірка docker-образів:
у корені проекту виконати команду 

```sh
  docker-compose build
```

3. Запуск контейнерів

```sh
  docker-compose build
```

4. Перевірка роботи gateway

Після успішного запуску gateway доступний за адресою:

http://localhost:1000

### Доступні endpoints:
- GET /profile — отримати дані автентифікованого користувача 
- GET /oauth2/authorization/google — ініціація Google логіну 
- proxy-endpoints до backend-мікросервісів (через spring-cloud-gateway)

### Використані технології
- Java 21 
- Spring Boot 
- Spring Cloud Gateway 
- Spring Security 
- OAuth2 Client (Google)
- WebFlux

#### До проекту додано маніфест для CI
