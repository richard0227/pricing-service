# Changelog

All notable changes to this project are documented in this file.

## [Unreleased]

## [0.0.1] - 2026-02-03

### Added

- JWT-based authentication with role-based authorization for the pricing endpoint.
- JPA auditing and soft delete support for `prices` (with Flyway migration).
- Prometheus + Grafana open-source observability stack, plus Micrometer Prometheus registry.
- Spring profile support for `dev` and `prod`.
- Docker Compose profiles for app + observability services.

### Changed

- Centralized authorization rules in `SecurityConfig` instead of controller annotations.

### Fixed

- Flyway migration for audit/soft-delete fields to be compatible with H2.

## [0.0.1] - 2026-01-30

### Added

- Applicable price API: GET /api/v1/prices (brandId, productId, applicationDate)
- Flyway migrations: create PRICES table and seed initial data
- Hexagonal architecture: domain/application/infrastructure separation
- Tests: unit (domain), integration (persistence), e2e (5 required scenarios)
- Docker: Dockerfile + docker-compose

## [0.0.1] - 2026-01-30

### Added

- Repository bootstrap: README, .gitignore, Docker setup