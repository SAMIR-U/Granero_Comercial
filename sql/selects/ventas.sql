SELECT v.id_venta, v.id_forma_pago, v.per_id_cliente, v.fecha_venta, v.descuento_venta, 
       COALESCE(SUM(vp.cantidad), 0) AS numero_de_productos 
FROM VENTAS v
LEFT JOIN VENTAS_PRODUCTOS vp ON v.id_venta = vp.id_venta
GROUP BY v.id_venta, v.id_forma_pago, v.per_id_cliente, v.fecha_venta, v.descuento_venta

