CREATE TABLE FORMAS_PAGOS(
    id_forma_pago     NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    nombre_forma_pago VARCHAR2(100) NOT NULL,
    CONSTRAINT forpag_pk_idforpag  PRIMARY KEY (id_forma_pago),
    CONSTRAINT forpag_uq_nombre    UNIQUE (nombre_forma_pago)
) TABLESPACE TEST_TBS

