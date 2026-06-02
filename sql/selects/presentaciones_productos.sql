SELECT pp.id_presentacion, pp.id_producto, pp.precio_presentacion, pr.nombre_presentacion 
FROM PRESENTACIONES_PRODUCTOS pp
INNER JOIN PRESENTACIONES pr ON pp.id_presentacion = pr.id_presentacion
WHERE pp.id_producto = ?

