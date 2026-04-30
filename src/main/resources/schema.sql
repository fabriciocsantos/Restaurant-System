CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(150) NOT NULL,
    login VARCHAR(50) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_ultima_alteracao TIMESTAMP NOT NULL,
    rua VARCHAR(150) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(120),
    cidade VARCHAR(100) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL,
    CONSTRAINT uk_usuario_email UNIQUE (email),
    CONSTRAINT uk_usuario_login UNIQUE (login),
    CONSTRAINT ck_usuario_tipo_usuario CHECK (tipo_usuario IN ('CLIENTE', 'DONO_RESTAURANTE'))
);
