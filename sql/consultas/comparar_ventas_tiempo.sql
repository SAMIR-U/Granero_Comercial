SELECT
    CASE
        WHEN V.fecha_venta BETWEEN ? AND ? THEN 'Periodo 1'
        WHEN V.fecha_venta BETWEEN ? AND ? THEN 'Periodo 2'
    END AS periodo,
    COUNT(DISTINCT V.id_venta)              AS total_ventas,
    SUM(VP.cantidad)                        AS total_unidades_vendidas,
    SUM(VP.cantidad * VP.precio_unitario)   AS total_ingresos
FROM VENTAS V
JOIN VENTAS_PRODUCTOS VP
    ON VP.id_venta = V.id_venta
WHERE
    V.fecha_venta BETWEEN ? AND ?
    OR V.fecha_venta BETWEEN ? AND ?
GROUP BY
    CASE
        WHEN V.fecha_venta BETWEEN ? AND ? THEN 'Periodo 1'
        WHEN V.fecha_venta BETWEEN ? AND ? THEN 'Periodo 2'
    END
ORDER BY periodo;