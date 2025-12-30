# Optimización SQL en Spring Boot

Este proyecto presenta ejemplos prácticos y explicados sobre cómo optimizar consultas SQL en aplicaciones Spring Boot, con el objetivo de lograr consultas más rápidas, eficientes y escalables.

En el código del proyecto:

- Se tienen dos entidades JPA: [Cliente](/src/main/java/com/chanochoca/app/model/Cliente.java) y [Pedido](/src/main/java/com/chanochoca/app/model/Pedido.java). Se cuenta con un [PedidoDTO](/src/main/java/com/chanochoca/app/model/dto/PedidoDTO.java).
- Las consultas se encuentran en [PedidoRepository](/src/main/java/com/chanochoca/app/repository/PedidoRepository.java).

## Definiciones

### Native Query

SQL puro del motor (MySQL, PostgreSQL, etc) ejecutado desde JPA.

Características:

- Control total del SQL
- Usa funciones, hints y optimizaciones específicas del motor
- No es portable

### JPQL

Lenguaje de consultas orientado a entidades, no a tablas.

Características:

- Portable entre motores
- Usa nombres de clases y atributos
- Hibernate genera el SQL

### Índices

Estructuras de datos que aceleran búsquedas a costa de espacio adicional de escritura y almacenamiento.

Características:

- Aceleran WHERE, JOIN, ORDER BY
- Ralentizan INSERT, UPDATE, DELETE
- Ocupan espacio

#### Tipos de índices

- Clustered: PK (clave primaria)
- Non-clustered: Índices secundarios
- Único: Validación + lookup
- Compuesto: Múltiples columnas
- Cobertura: Evita acceso a la tabla

### Paginación

Traer datos en bloques, no todo de una vez.

Por qué:

- Menos memoria
- Menos tiempo de respuesta
- Escalable
