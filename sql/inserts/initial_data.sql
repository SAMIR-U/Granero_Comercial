DECLARE
    v_id_pais   NUMBER;
    v_id_ciudad NUMBER;
BEGIN
    INSERT INTO PAISES (nombre_pais) 
    VALUES ('Colombia') 
    RETURNING id_pais INTO v_id_pais;

    INSERT INTO CIUDADES (id_pais, nombre_ciudad) 
    VALUES (v_id_pais, 'Siachoque') 
    RETURNING id_ciudad INTO v_id_ciudad;

    INSERT INTO PERSONAS (id_ciudad, nombre_cliente, documento_cliente, telefono_cliente, tipo_persona) 
    VALUES (v_id_ciudad, 'Maela Quemba', '123', '3123333333', 'VENDEDOR');
END;