SELECT c.id_compra, c.id_proveedor, c.id_forma_pago, c.fecha_compra, 
       prov.nombre_proveedor, fp.nombre_forma_pago,
       COALESCE(SUM(pc.cantidad_prod_compra), 0) AS numero_productos_comprados 
FROM COMPRAS c
INNER JOIN PROVEEDORES prov ON c.id_proveedor = prov.id_proveedor
INNER JOIN FORMAS_PAGOS fp ON c.id_forma_pago = fp.id_forma_pago
LEFT JOIN PRODUCTOS_COMPRAS pc ON c.id_compra = pc.id_compra
GROUP BY c.id_compra, c.id_proveedor, c.id_forma_pago, c.fecha_compra, prov.nombre_proveedor, fp.nombre_forma_pago