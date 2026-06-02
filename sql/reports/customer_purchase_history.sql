SELECT
    P.id_cliente,
    P.nombre_cliente,
    P.documento_cliente,
    V.id_venta,
    V.fecha_venta,
    PR.nombre_producto,
    VP.cantidad,
    VP.precio_unitario,
    (VP.cantidad * VP.precio_unitario) AS subtotal,
    V.descuento_venta,
    FP.nombre_forma_pago
FROM PERSONAS P
JOIN VENTAS V
    ON V.per_id_cliente = P.id_cliente
JOIN VENTAS_PRODUCTOS VP
    ON VP.id_venta = V.id_venta
JOIN PRODUCTOS PR
    ON PR.id_producto = VP.id_producto
JOIN FORMAS_PAGOS FP
    ON FP.id_forma_pago = V.id_forma_pago
WHERE P.id_cliente = ?
ORDER BY V.fecha_venta DESC