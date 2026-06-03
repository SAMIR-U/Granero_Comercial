CREATE TABLE PROVEEDORES(
    id_proveedor        NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    nombre_proveedor    VARCHAR2(100) NOT NULL,
    documento_proveedor VARCHAR2(100) NOT NULL,
    celular_proveedor   VARCHAR2(100) NOT NULL,
    CONSTRAINT prov_pk_idprov PRIMARY KEY (id_proveedor)
) TABLESPACE TEST_TBS

