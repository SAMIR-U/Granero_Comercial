SELECT COUNT(1) AS es_vendedor
FROM PERSONAS
WHERE documento_cliente = ? 
  AND tipo_persona = 'VENDEDOR'