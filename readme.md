# Optimización SQL en Spring Boot

En el proyecto doy ejemplos explicados de como optimizar consultas SQL con el objetivo de consultas más rápidas y eficientes.

## Native Query

SQL puro del motor (MySQL, PostgreSQL, etc) ejecutado desde JPA.

Características:

- Control total del SQL
- Usa funciones, hints y optimizaciones específicas del motor
- No es portable

## JPQL

Lenguaje de consultas orientado a entidades, no a tablas.

Características:

- Portable entre motores
- Usa nombres de clases y atributos
- Hibernate genera el SQL

# Índices

Estructuras de datos que aceleran búsquedas a costa de espacio adicional de escritura y almacenamiento.

Características:

- Aceleran WHERE, JOIN, ORDER BY
- Ralentizan INSERT, UPDATE, DELETE
- Ocupan espacio

### Tipos de índices

- Clustered: PK (clave primaria)
- Non-clustered: Índices secundarios
- Único: Validación + lookup
- Compuesto: Múltiples columnas
- Cobertura: Evita acceso a la tabla

# Paginación

Traer datos en bloques, no todo de una vez.

Por qué:

- Menos memoria
- Menos tiempo de respuesta
- Escalable
