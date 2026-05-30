CREATE TABLE CIUDADES(
    id_ciudad     NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    id_pais       NUMBER NOT NULL,
    nombre_ciudad VARCHAR2(100) NOT NULL,
    CONSTRAINT ciud_pk_idciud       PRIMARY KEY (id_ciudad),
    CONSTRAINT ciud_fk_idpais       FOREIGN KEY (id_pais) REFERENCES PAISES(id_pais),
    CONSTRAINT ciud_uq_nombre_pais  UNIQUE (nombre_ciudad, id_pais)
)