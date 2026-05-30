SELECT
    P.id_cliente,
    P.nombre_cliente,
    P.documento_cliente,
    CI.nombre_ciudad,
    COUNT(DISTINCT V.id_venta)              AS total_ventas,
    SUM(VP.cantidad)                        AS total_unidades_compradas,
    SUM(VP.cantidad * VP.precio_unitario)   AS total_gastado
FROM PERSONAS P
JOIN VENTAS V
    ON V.per_id_cliente = P.id_cliente
JOIN VENTAS_PRODUCTOS VP
    ON VP.id_venta = V.id_venta
JOIN CIUDADES CI
    ON CI.id_ciudad = P.id_ciudad
WHERE P.tipo_persona = 'CLIENTE'
GROUP BY
    P.id_cliente,
    P.nombre_cliente,
    P.documento_cliente,
    CI.nombre_ciudad
ORDER BY total_gastado DESC
FETCH FIRST ? ROWS ONLY
-- hola