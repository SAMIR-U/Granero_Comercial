SELECT pc.id_producto, pc.id_compra, pc.cantidad_prod_compra, pc.precio_unitario_prod_compra, p.nombre_producto 
FROM PRODUCTOS_COMPRAS pc
INNER JOIN PRODUCTOS p ON pc.id_producto = p.id_producto
WHERE pc.id_compra = ?

