CREATE TABLE CATEGORIAS_PRODUCTOS(
    id_categoria     NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL,
    nombre_categoria VARCHAR2(100) NOT NULL,
    CONSTRAINT catpro_pk_idcat   PRIMARY KEY (id_categoria),
    CONSTRAINT catpro_uq_nombre  UNIQUE (nombre_categoria)
)