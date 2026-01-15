# Loans Management API

Esta aplicación implementa una API REST de prueba para la gestión de solicitudes de préstamos bancarios, desarrollada como prueba técnica.  

---

## 1) Ejecución del proyecto

### Requisitos
- Java 21
- Maven 3.x

### Cómo arrancar la aplicación

Desde la raíz del proyecto:

```bash
mvn clean spring-boot:run
```

La aplicación se iniciará por defecto en:

```
http://localhost:8080
```

---

## 2) Endpoints disponibles

### Crear una solicitud de préstamo
```
POST /loans
```

**Request body**
```json
{
  "name": "Juan Pérez",
  "amount": 1500.50,
  "currency": "EUR",
  "documentId": "12345678Z"
}
```

**Respuesta**
- `201 Created`
- Header `Location: /loans/{id}`

---

### Obtener todas las solicitudes
```
GET /loans
```

**Respuesta**
- `200 OK`
- Devuelve el listado completo de solicitudes registradas

---

### Obtener una solicitud por ID
```
GET /loans/{id}
```

**Respuesta**
- `200 OK` si la solicitud existe
- `404 Not Found` si no existe

---

### Cambiar el estado de una solicitud
```
PATCH /loans/{id}/status
```

**Request body**
```json
{
  "status": "APPROVED"
}
```

**Respuesta**
- `200 OK` si la transición es válida
- `400 Bad Request` si la transición de estado no está permitida
- `404 Not Found` si la solicitud no existe

---

## 3) Arquitectura y decisiones técnicas

### Tecnologías utilizadas
- Java 21
- Spring Boot
- Spring Web
- Maven
- Bean Validation (Jakarta Validation)
- Springdoc OpenAPI (Swagger)

---

### Estructura del proyecto
```text
src/
├── config/
│   └── Configuración
└── LoansApplication.java
├── controller/
│   └── Controladores REST
├── dto/
│   └── Objetos de transferencia (Request / Response)
├── exception/
│   └── Excepciones de negocio y manejo global de errores
├── mapper/
│   └── Conversión entre DTOs y modelo de dominio
├── model/
│   └── Modelo de dominio y reglas de negocio
├── repository/
│   └── Repositorio en memoria
├── service/
│   └── Lógica de aplicación
└── LoansApplication.java
```

---

### Decisiones de diseño destacadas

- Separación clara de responsabilidades por capas (controller, service, model y repository).
- Lógica de negocio encapsulada en el dominio:
  - Las reglas de transición de estado se validan en el modelo `LoanRequest`.
- Uso de `PATCH` para modificaciones parciales del recurso.
- Identificadores generados en memoria mediante `AtomicLong`.
- DTOs inmutables implementados con `record`.
- Manejo centralizado de errores mediante `@RestControllerAdvice`.
- Persistencia en memoria, sin base de datos, orientada al alcance de la prueba técnica.

---

## 4) Manejo de errores

Todos los errores de la API siguen un formato común:

```json
{
  "error": "INVALID_STATE_TRANSITION",
  "message": "Cannot transition from APPROVED to REJECTED"
}
```

### Códigos de error posibles
- `LOAN_REQUEST_NOT_FOUND`
- `INVALID_STATE_TRANSITION`
- `VALIDATION_ERROR`
- `BAD_REQUEST`
- `INTERNAL_ERROR`

---

## 5) Documentación de la API (Swagger)

La documentación OpenAPI se genera automáticamente a partir del código fuente.

- Swagger UI:  
  http://localhost:8080/swagger-ui.html

- OpenAPI spec (JSON):  
  http://localhost:8080/v3/api-docs

---

## 6) Pruebas

La API puede probarse mediante herramientas como Postman o curl.

Flujo recomendado:
- Crear una solicitud (`POST /loans`)
- Listar solicitudes (`GET /loans`)
- Consultar por ID (`GET /loans/{id}`)
- Aprobar una solicitud (`PATCH /loans/{id}/status`)
- Cancelar una solicitud aprobada (`PATCH /loans/{id}/status`)
- Crear otra solicitud (`POST /loans`)
- Rechazar una solicitud pendiente (`PATCH /loans/{id}/status`)
- Volver a listar (`GET /loans`)

---

## 7) Posibles mejoras

- Persistencia en base de datos.
- Seguridad y autenticación.
- Paginación y filtrado de resultados.
- Reversión de cambios de estado.
- Búsqueda de solicitudes por:
  - fecha
  - documento identificativo
  - divisa
  - cantidad
  - estado
- Modificación de datos del préstamo (importe y divisa).
- Uso de `ZonedDateTime` para control de zonas horarias.
- Versionado de la API.
- Tests unitarios y de integración.