WITH VentasClasificadas AS (
    SELECT 
        V.id_venta,
        VP.cantidad,
        VP.precio_unitario,
        CASE
            WHEN V.fecha_venta BETWEEN ? AND ? THEN 'Periodo 1'
            WHEN V.fecha_venta BETWEEN ? AND ? THEN 'Periodo 2'
        END AS periodo
    FROM VENTAS V
    JOIN VENTAS_PRODUCTOS VP ON VP.id_venta = V.id_venta
    WHERE V.fecha_venta BETWEEN ? AND ? 
       OR V.fecha_venta BETWEEN ? AND ?
)
SELECT 
    periodo,
    COUNT(DISTINCT id_venta)                   AS total_ventas,
    SUM(cantidad)                              AS total_unidades_vendidas,
    SUM(cantidad * precio_unitario)            AS total_ingresos
FROM VentasClasificadas
WHERE periodo IS NOT NULL
GROUP BY periodo
ORDER BY periodo