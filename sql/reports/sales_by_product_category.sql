SELECT
    CP.id_categoria,
    CP.nombre_categoria,
    COUNT(DISTINCT V.id_venta)              AS total_ventas,
    SUM(VP.cantidad)                        AS total_unidades_vendidas,
    SUM(VP.cantidad * VP.precio_unitario)   AS total_ingresos
FROM CATEGORIAS_PRODUCTOS CP
JOIN SUBCATEGORIAS_PRODUCTOS SP
    ON SP.id_categoria = CP.id_categoria
JOIN PRODUCTOS PR
    ON PR.id_subcategoria = SP.id_subcategoria
JOIN VENTAS_PRODUCTOS VP
    ON VP.id_producto = PR.id_producto
JOIN VENTAS V
    ON V.id_venta = VP.id_venta
WHERE CP.id_categoria = ?
GROUP BY
    CP.id_categoria,
    CP.nombre_categoria