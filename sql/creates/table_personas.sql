CREATE TABLE PERSONAS(
    id_cliente        NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    id_ciudad         NUMBER NOT NULL,
    nombre_cliente    VARCHAR2(100) NOT NULL,
    documento_cliente VARCHAR2(100) NOT NULL,
    telefono_cliente  VARCHAR2(100) NOT NULL,
    tipo_persona      VARCHAR2(10) NOT NULL,
    CONSTRAINT per_ck_tipper CHECK(tipo_persona IN ('CLIENTE','VENDEDOR')),
    CONSTRAINT per_pk_idclie  PRIMARY KEY (id_cliente),
    CONSTRAINT per_fk_idciud  FOREIGN KEY (id_ciudad) REFERENCES CIUDADES(id_ciudad)
    CONSTRAINT per_uq_doc UNIQUE (documento_cliente)
)