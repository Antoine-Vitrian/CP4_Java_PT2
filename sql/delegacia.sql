CREATE TABLE delegacia (
    id NUMBER PRIMARY KEY,
    nome VARCHAR2(255) NOT NULL,
    endereco VARCHAR2(500) NOT NULL
);

CREATE SEQUENCE seq_delegacia START WITH 1 INCREMENT BY 1;

CREATE TABLE policial (
    id NUMBER PRIMARY KEY,
    nome VARCHAR2(255) NOT NULL,
    cpf VARCHAR2(20) NOT NULL,
    matricula VARCHAR2(50) NOT NULL,
    cargo VARCHAR2(100) NOT NULL,
    delegacia_id NUMBER NOT NULL,
    CONSTRAINT uk_policial_cpf UNIQUE (cpf),
    CONSTRAINT uk_policial_matricula UNIQUE (matricula),
    CONSTRAINT fk_policial_delegacia FOREIGN KEY (delegacia_id) REFERENCES delegacia(id)
);

CREATE SEQUENCE seq_policial START WITH 1 INCREMENT BY 1;

INSERT INTO delegacia (id, nome, endereco)
VALUES (seq_delegacia.NEXTVAL, 'Delegacia de demonstracao', 'Endereco de demonstracao');
