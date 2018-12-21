CREATE TABLE pessoa(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	ativo BOOLEAN NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--DML
INSERT INTO pessoa (nome, ativo) values ('Marcelo', true);
INSERT INTO pessoa (nome, ativo) values ('Eduardo', true);
INSERT INTO pessoa (nome, ativo) values ('Crescencio', true);
