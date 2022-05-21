create table pessoa (
	codigo bigint not null auto_increment,
	nome varchar(50) not null,
	ativo tinyint not null,
	logradouro varchar(50),
	numero varchar(5),
	complemento varchar(50),
	bairro varchar(20),
	cep varchar(10),
	cidade varchar(20),
	estado varchar(20),
	primary key (codigo)
)ENGINE=InnoDB default charset=utf8;

insert into pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado)
values ("Luis Marcelo", 1, "Rua Luiz Scott", "209", "ap 161 bloco B", "Jardim Iracema", "06440-260", "Barueri", "SP");

insert into pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado)
values ("Carolina Rabelo", 1, "Rua Luiz Scott", "209", "ap 161 bloco B", "Jardim Iracema", "06440-260", "Barueri", "SP");

insert into pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado)
values ("Luis Claudio", 1, "Rua Sete Lagoas", "195", "bloco 4 201", "Jacarepagua", "22723-030", "Rio de Janeiro", "RJ");

insert into pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado)
values ("Aldemario da Trindade", 1, "Rua das Flores", "75", "casa 201", "Inhauma", "33723-030", "Rio de Janeiro", "RJ");
