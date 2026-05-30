CREATE TABLE PRODUCTOS(
    id_producto                NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    id_subcategoria            NUMBER NOT NULL,
    nombre_producto            VARCHAR2(100) NOT NULL,
    descripcion_producto       VARCHAR2(250),
    fecha_vencimiento_producto DATE NOT NULL,
    CONSTRAINT prod_pk_idprod    PRIMARY KEY (id_producto),
    CONSTRAINT prod_fk_idsubcat  FOREIGN KEY (id_subcategoria) REFERENCES SUBCATEGORIAS_PRODUCTOS(id_subcategoria),
    CONSTRAINT prod_uq_nombre    UNIQUE (nombre_producto)
)