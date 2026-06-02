SELECT c.id_compra, c.id_proveedor, c.id_forma_pago, c.fecha_compra, 
       COALESCE(SUM(pc.cantidad_prod_compra), 0) AS numero_productos_comprados 
FROM COMPRAS c
LEFT JOIN PRODUCTOS_COMPRAS pc ON c.id_compra = pc.id_compra
GROUP BY c.id_compra, c.id_proveedor, c.id_forma_pago, c.fecha_compra

