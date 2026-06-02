SELECT p.id_producto, p.id_subcategoria, p.nombre_producto, p.descripcion_producto, p.fecha_vencimiento_producto, 
       s.nombre_subcategoria, c.nombre_categoria 
FROM PRODUCTOS p
INNER JOIN SUBCATEGORIAS_PRODUCTOS s ON p.id_subcategoria = s.id_subcategoria
INNER JOIN CATEGORIAS_PRODUCTOS c ON s.id_categoria = c.id_categoria

