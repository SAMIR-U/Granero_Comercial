CREATE TABLE COMPRAS(
    id_compra     NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    id_proveedor  NUMBER NOT NULL,
    id_forma_pago NUMBER NOT NULL,
    fecha_compra  DATE NOT NULL,
    CONSTRAINT comp_pk_idcomp   PRIMARY KEY (id_compra),
    CONSTRAINT comp_fk_idprov   FOREIGN KEY (id_proveedor) REFERENCES PROVEEDORES(id_proveedor),
    CONSTRAINT comp_fk_idforpag FOREIGN KEY (id_forma_pago) REFERENCES FORMAS_PAGOS(id_forma_pago)
) TABLESPACE TEST_TBS

