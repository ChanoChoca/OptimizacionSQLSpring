package com.chanochoca.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chanochoca.app.model.Pedido;
import com.chanochoca.app.model.dto.PedidoDTO;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Query no optimizada: 1 pedido + N clientes (N+1)
    
    // No ocurre en el repositorio, ocurre en el servicio cuando se hace lo siguiente por ejemplo:
    /* List<Pedido> pedidos = repository.findByEstado("CONFIRMADO");
    for (Pedido p: pedidos) {
        System.out.println(p.getCliente().getNombre());
    } */
    List<Pedido> findByEstado(String estado);

    // Query optimizada para este caso: evita N+1
    @Query("""
        SELECT p
        FROM Pedido p
        JOIN FETCH p.cliente
        WHERE p.estado = :estado
    """)
    List<Pedido> findPedidosConCliente(@Param("estado") String estado);

    // Query optimizada (mejor): Proyección DTO
    @Query("""
        SELECT new PedidoDTO(
            p.total,
            c.nombre
        )
        FROM Pedido p
        JOIN p.cliente c
        WHERE p.estado = :estado
    """)
    List<PedidoDTO> findPedidosOptimizado(@Param("estado") String estado);

    // Subconsulta no optimizada: JOIN (Native)
    @Query(
        value = """
            SELECT *
            FROM pedidos p
            WHERE p.cliente_id IN (
                SELECT id FROM clientes WHERE activo = 1
            )
        """,
        nativeQuery = true
    )
    List<Pedido> findPedidosClienteActivoIn();

    // Subconsulta optimizada: Usar EXISTS (Native)
    @Query(
        value = """
            SELECT *
            FROM pedidos p
            WHERE EXISTS (
                SELECT 1
                FROM clientes c
                WHERE c.id = p.cliente_id
                  AND c.activo = 1
            )
        """,
        nativeQuery = true
    )
    List<Pedido> findPedidosClienteActivoExists();

    // Subconsulta optimizada: Alternativa JPQL
    @Query("""
        SELECT p
        FROM Pedido p
        WHERE EXISTS (
            SELECT 1
            FROM Cliente c
            WHERE c = p.cliente
              AND c.activo = true
        )
    """)
    List<Pedido> findPedidosClienteActivoJpql();

    // Query no optimizada: JPQL usando función sobre columna indexada
    @Query("""
        SELECT p
        FROM Pedido p
        WHERE FUNCTION('YEAR', p.fecha) = :year
          AND p.estado = :estado
    """)
    List<Pedido> findPedidosPorYearRompeIndice(
        @Param("year") int year,
        @Param("estado") String estado
    );

    // Query optimizada: Usa índice
    @Query("""
        SELECT p
        FROM Pedido p
        WHERE p.fecha BETWEEN :desde AND :hasta
          AND p.estado = :estado
    """)
    List<Pedido> findPedidosPorRangoFechaUsaIndice(
        @Param("desde") LocalDate desde,
        @Param("hasta") LocalDate hasta,
        @Param("estado") String estado
    );
}