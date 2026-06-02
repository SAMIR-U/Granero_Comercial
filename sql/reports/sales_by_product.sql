SELECT
    PR.id_producto,
    PR.nombre_producto,
    SP.nombre_subcategoria,
    CP.nombre_categoria,
    SUM(VP.cantidad)                        AS total_unidades_vendidas,
    SUM(VP.cantidad * VP.precio_unitario)   AS total_ingresos
FROM PRODUCTOS PR
JOIN VENTAS_PRODUCTOS VP
    ON VP.id_producto = PR.id_producto
JOIN SUBCATEGORIAS_PRODUCTOS SP
    ON SP.id_subcategoria = PR.id_subcategoria
JOIN CATEGORIAS_PRODUCTOS CP
    ON CP.id_categoria = SP.id_categoria
WHERE PR.id_producto = ?
GROUP BY
    PR.id_producto,
    PR.nombre_producto,
    SP.nombre_subcategoria,
    CP.nombre_categoria