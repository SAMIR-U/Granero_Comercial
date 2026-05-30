SELECT
    P.id_cliente,
    P.nombre_cliente,
    P.documento_cliente,
    COUNT(DISTINCT V.id_venta)              AS total_ventas,
    SUM(VP.cantidad)                        AS total_unidades_compradas,
    SUM(VP.cantidad * VP.precio_unitario)   AS total_gastado
FROM PERSONAS P
JOIN VENTAS V
    ON V.per_id_cliente = P.id_cliente
JOIN VENTAS_PRODUCTOS VP
    ON VP.id_venta = V.id_venta
WHERE P.id_cliente = ?
GROUP BY
    P.id_cliente,
    P.nombre_cliente,
    P.documento_cliente