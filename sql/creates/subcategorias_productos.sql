CREATE TABLE SUBCATEGORIAS_PRODUCTOS(
    id_subcategoria     NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    id_categoria        NUMBER NOT NULL,
    nombre_subcategoria VARCHAR2(100) NOT NULL,
    CONSTRAINT subcat_pk_idsubcat    PRIMARY KEY (id_subcategoria),
    CONSTRAINT subcat_fk_idcat       FOREIGN KEY (id_categoria) REFERENCES CATEGORIAS_PRODUCTOS(id_categoria),
    CONSTRAINT subcat_uq_nombre_cat  UNIQUE (nombre_subcategoria, id_categoria)
) TABLESPACE TEST_TBS

