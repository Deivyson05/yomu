CREATE TABLE Usuarios (
    email VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_nascimento DATE,
    genero VARCHAR(255),
    telefone VARCHAR(255),
    senha VARCHAR(255) NOT NULL,
    user_token VARCHAR(62) NOT NULL
);