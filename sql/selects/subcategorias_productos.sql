SELECT s.id_subcategoria, s.id_categoria, s.nombre_subcategoria, c.nombre_categoria 
FROM SUBCATEGORIAS_PRODUCTOS s
INNER JOIN CATEGORIAS_PRODUCTOS c ON s.id_categoria = c.id_categoria

