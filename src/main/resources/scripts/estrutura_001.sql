CREATE TABLE produto (
  id INT AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  categoria VARCHAR(50) NOT NULL,
  preco DECIMAL(10, 2) NOT NULL, 
  quantidade INT NOT NULL, 
  CONSTRAINT pk_produto PRIMARY KEY(id)
);

CREATE TABLE usuario ();
