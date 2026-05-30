CREATE TABLE VENTAS_PRODUCTOS(
    id_venta        NUMBER NOT NULL,
    id_producto     NUMBER NOT NULL,
    cantidad        NUMBER NOT NULL,
    precio_unitario FLOAT NOT NULL,
    CONSTRAINT venpro_fk_idven  FOREIGN KEY (id_venta)    REFERENCES VENTAS(id_venta),
    CONSTRAINT venpro_fk_idprod FOREIGN KEY (id_producto) REFERENCES PRODUCTOS(id_producto)
)