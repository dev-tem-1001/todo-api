## Todo-Api - Учебный REST API проект

Учебный проект, созданный в процессе изучения веб-разработки на Spring Boot. Приложение представляет собой RESTful API сервис для управления задачами (To-Do List).

### 🛠️ Технологии

* **Java 17 + Spring Boot 3.2.3** — основа приложения
* **Spring MVC** — веб-слой и REST контроллеры
* **Spring Data JPA** — работа с базой данных через **Hibernate**
* **PostgreSQL** — реляционная база данных (в **Docker**)
* **Lombok** — сокращение шаблонного кода 
* **SpringDoc OpenAPI (Swagger)** — генерация интерактивной документации
* **Maven** — управление зависимостями
* **MockMvc** — интеграционное тестирование
  
--- 

### 📁 Структура проекта
Model (Сущности)
* `Task` — основная сущность задачи.
Содержит: id, title, description, completed, createdAt.
Использует аудита (@CreatedDate) для автоматического проставления даты создания.

DTO (Data Transfer Objects)
* `TaskRequestDto` — объект для входящих данных (POST/PUT).
Содержит валидацию полей (@NotBlank, @Size).
Не содержит id.
* `TaskResponseDto` — объект для ответа клиенту (GET).
Содержит полный набор данных, включая id и createdAt.

Data (Репозитории)
* `TaskRepository` — интерфейс JpaRepository.
Поддерживает пагинацию через Pageable.
Методы поиска (findById, findAll).

Service (Бизнес-логика)
* `TaskService` — основной сервисный слой.
Маппинг (Entity ↔ DTO).
Логирование действий (@Slf4j).
Бизнес-правила (например, запрет на изменение завершенной задачи).

Web (Контроллеры)
* `TaskController` — REST контроллер.
Обрабатывает HTTP запросы.
Возвращает JSON данные и статусы ответов (200, 201, 204, 404).

Exception (Обработка ошибок)
* `GlobalExceptionHandler` — централизованный перехват исключений.
* `TaskNotFoundException` — ошибка, если задача не найдена (404).
* `TaskCompletedException` — ошибка бизнес-логики (400).

---

### 🚀 Запуск проекта

Перед запуском необходимо:

* Java 17 или новее
* Docker (для запуска PostgreSQL)
* Git

Инструкция по запуску

1. Клонировать репозиторий:
```git 
git clone https://github.com/dev-tem-1001/todo-api.git
```
2. Запустить базу данных (PostgreSQL в Docker):
```docker
docker compose up -d 
```
3. Запустить приложение
```
cd todo-api./mvnw spring-boot:run
```
(Или через IDE IntelliJ IDEA)

4. Открыть документацию API (Swagger UI):

http://localhost:8080/swagger-ui/index.html

---

### 📝 Примеры запросов
Получить список задач (с пагинацией)

    GET /api/tasks?page=0&size=10&sort=createdAt,desc

Создать задачу 

    POST /api/tasks
    {  "title": "Заголовок задачи",  "description": "Описание задачи",  "completed": false}

Обновить задачу

    PUT /api/tasks/{id}

Удалить задачу

    DELETE /api/tasks/{id} 
