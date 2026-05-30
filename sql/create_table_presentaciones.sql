CREATE TABLE PRESENTACIONES(
    id_presentacion     NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    nombre_presentacion VARCHAR2(100) NOT NULL,
    CONSTRAINT pres_pk_idpres  PRIMARY KEY (id_presentacion),
    CONSTRAINT pres_uq_nombre  UNIQUE (nombre_presentacion)
)