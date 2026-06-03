SELECT p.id_cliente, p.id_ciudad, p.nombre_cliente, p.documento_cliente, p.telefono_cliente, p.tipo_persona,
       c.nombre_ciudad, pa.nombre_pais
FROM PERSONAS p
INNER JOIN CIUDADES c ON p.id_ciudad = c.id_ciudad
INNER JOIN PAISES pa ON c.id_pais = pa.id_pais