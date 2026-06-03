SELECT c.id_ciudad, c.id_pais, c.nombre_ciudad, p.nombre_pais 
FROM CIUDADES c
INNER JOIN PAISES p ON c.id_pais = p.id_pais
WHERE c.id_pais = ?