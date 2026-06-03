CREATE TABLE PAISES(
    id_pais     NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    nombre_pais VARCHAR2(100) NOT NULL,
    CONSTRAINT pais_pk_idpais  PRIMARY KEY (id_pais),
    CONSTRAINT pais_uq_nombre  UNIQUE (nombre_pais)
) TABLESPACE TEST_TBS

