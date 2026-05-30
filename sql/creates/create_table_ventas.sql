CREATE TABLE VENTAS(
    id_venta        NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    id_forma_pago   NUMBER NOT NULL,
    per_id_cliente  NUMBER NOT NULL,
    fecha_venta     DATE NOT NULL,
    descuento_venta FLOAT,
    CONSTRAINT ven_pk_idven    PRIMARY KEY (id_venta),
    CONSTRAINT ven_fk_idforpag FOREIGN KEY (id_forma_pago)  REFERENCES FORMAS_PAGOS(id_forma_pago),
    CONSTRAINT ven_fk_idclie   FOREIGN KEY (per_id_cliente) REFERENCES PERSONAS(id_cliente)
)