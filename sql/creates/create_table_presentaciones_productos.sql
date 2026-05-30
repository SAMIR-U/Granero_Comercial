CREATE TABLE PRESENTACIONES_PRODUCTOS(
    id_presentacion     NUMBER NOT NULL,
    id_producto         NUMBER NOT NULL,
    precio_presentacion FLOAT NOT NULL,
    CONSTRAINT prepro_pk_idprepro PRIMARY KEY (id_presentacion, id_producto),
    CONSTRAINT prepro_fk_idpres   FOREIGN KEY (id_presentacion) REFERENCES PRESENTACIONES(id_presentacion),
    CONSTRAINT prepro_fk_idprod   FOREIGN KEY (id_producto)     REFERENCES PRODUCTOS(id_producto)
)