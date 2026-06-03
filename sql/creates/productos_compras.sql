CREATE TABLE PRODUCTOS_COMPRAS(
    id_producto                 NUMBER NOT NULL,
    id_compra                   NUMBER NOT NULL,
    cantidad_prod_compra        NUMBER NOT NULL,
    precio_unitario_prod_compra FLOAT NOT NULL,
    CONSTRAINT procom_fk_idprod FOREIGN KEY (id_producto) REFERENCES PRODUCTOS(id_producto),
    CONSTRAINT procom_fk_idcomp FOREIGN KEY (id_compra)   REFERENCES COMPRAS(id_compra)
) TABLESPACE TEST_TBS

