# Pricing Service

Technical test solution (Java 17, Spring Boot, Hexagonal Architecture, Flyway, tests, Docker).
A Spring Boot 3.5 (Java 17) service that exposes a price lookup endpoint and demonstrates a production-ready setup:
hexagonal architecture, JWT security with roles, JPA auditing + soft delete, Flyway migrations, and open-source
observability (Prometheus + Grafana).

## Architecture

This project follows **Hexagonal Architecture (Ports & Adapters)**:

- **Domain** (`src/main/java/com/zara/pricing_service/domain`)
    - Core business logic and models, isolated from frameworks.
- **Application** (`src/main/java/com/zara/pricing_service/application`)
    - Use cases, DTOs, and orchestration of domain logic.
- **Infrastructure** (`src/main/java/com/zara/pricing_service/infrastructure`)
    - Adapters for persistence (JPA), REST controllers, security, and configuration.

**Persistence layer** uses JPA + Flyway with soft delete and auditing.
**Security** is JWT-based (HS256), with role-based authorization in **prod**.
**Observability** is enabled through Spring Boot Actuator + Micrometer (Prometheus registry).

## Prerequisites

- Java 17
- Docker (optional, for Docker Compose + Prometheus/Grafana)

## Running locally

### Option A: Maven (dev profile by default)

```bash
./mvnw clean test
./mvnw spring-boot:run
```

### Option B: Docker Compose (dev)

```bash
docker compose --profile dev up --build
```

### Option C: Docker Compose (prod)

```bash
export JWT_SECRET='change-me-change-me-change-me-change-me'
export SPRING_DATASOURCE_URL='jdbc:postgresql://.../pricing'
export SPRING_DATASOURCE_USERNAME='...'
export SPRING_DATASOURCE_PASSWORD='...'

docker compose --profile prod up --build
```

## Profiles: dev vs prod

To simplify evaluation, **dev profile does not enforce authentication**.
The **prod profile enforces JWT + roles**.

- **dev**: intended for local evaluation and easier testing.
- **prod**: intended to demonstrate full security controls.

> If you want to test security locally, run with `SPRING_PROFILES_ACTIVE=prod`.

## Authentication (JWT)

**Prod profile only**: the API requires a **Bearer JWT** with:

- **Issuer**: `pricing-service`
- **Algorithm**: `HS256`
- **Secret**: value of `JWT_SECRET` (defaults to `change-me-change-me-change-me-change-me` in dev)
- **Roles claim**: `roles` must include `PRICING_READ`

Example JWT payload:

```json
{
  "sub": "demo-user",
  "iss": "pricing-service",
  "roles": [
    "PRICING_READ"
  ],
  "iat": 1710000000,
  "exp": 1890000000
}
```

Use any JWT generator (e.g. jwt.io) with the secret above, then set:

```bash
export TOKEN='<your.jwt.token>'
```

## API Endpoint

`GET /api/v1/prices`

Query parameters:

- `applicationDate` (ISO-8601 datetime)
- `productId` (long)
- `brandId` (long)

## Five required scenarios (curl)

Assuming:

```bash
export TOKEN='<your.jwt.token>'
BASE_URL='http://localhost:8080'
```

> **Dev profile**: omit the `Authorization` header.
> **Prod profile**: include the `Authorization: Bearer ${TOKEN}` header.

1) **2020-06-14 10:00**

```bash
curl -s -H "Authorization: Bearer ${TOKEN}" \
  "${BASE_URL}/api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

2) **2020-06-14 16:00**

```bash
curl -s -H "Authorization: Bearer ${TOKEN}" \
  "${BASE_URL}/api/v1/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1"
```

3) **2020-06-14 21:00**

```bash
curl -s -H "Authorization: Bearer ${TOKEN}" \
  "${BASE_URL}/api/v1/prices?applicationDate=2020-06-14T21:00:00&productId=35455&brandId=1"
```

4) **2020-06-15 10:00**

```bash
curl -s -H "Authorization: Bearer ${TOKEN}" \
  "${BASE_URL}/api/v1/prices?applicationDate=2020-06-15T10:00:00&productId=35455&brandId=1"
```

5) **2020-06-16 21:00**

```bash
curl -s -H "Authorization: Bearer ${TOKEN}" \
  "${BASE_URL}/api/v1/prices?applicationDate=2020-06-16T21:00:00&productId=35455&brandId=1"
```

## Observability (Open Source)

### Actuator endpoints

- Health: `GET /actuator/health`
- Metrics catalog: `GET /actuator/metrics`
- Prometheus scrape: `GET /actuator/prometheus`

### Prometheus + Grafana (Docker Compose)

When running `docker compose --profile dev|prod up`, you get:

- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000` (default login: `admin` / `admin`)

Prometheus scrapes `GET /actuator/prometheus` using the config in `prometheus.yml`.

## Traceability & Soft Delete

Auditing fields are stored on the `prices` table:

- `created_at`, `updated_at`, `created_by`, `updated_by`
- Soft delete: `deleted`, `deleted_at`, `deleted_by`

To inspect in dev (H2):

1. Open the H2 console: `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:mem:pricing`
3. Run:

```sql
SELECT id,
       created_at,
       created_by,
       updated_at,
       updated_by,
       deleted,
       deleted_at,
       deleted_by
FROM prices;
```

## Troubleshooting

- **401 Unauthorized**: ensure your JWT has `roles: ["PRICING_READ"]` and `iss: "pricing-service"`.
- **Prometheus shows no data**: verify `/actuator/prometheus` is reachable and your service container name
  matches the targets in `prometheus.yml`.

## License

This project is provided as a technical exercise.