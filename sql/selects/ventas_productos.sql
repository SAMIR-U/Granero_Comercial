SELECT vp.id_venta, vp.id_producto, vp.cantidad, vp.precio_unitario, p.nombre_producto 
FROM VENTAS_PRODUCTOS vp
INNER JOIN PRODUCTOS p ON vp.id_producto = p.id_producto
WHERE vp.id_venta = ?

