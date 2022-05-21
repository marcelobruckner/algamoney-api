create table categoria (
  codigo bigint not null auto_increment,
  nome varchar(50) not null,
  primary key (codigo)
) ENGINE=InnoDB default chArset=utf8;


insert into categoria (nome) values ('Lazer');
insert into categoria (nome) values ('Alimentação');
insert into categoria (nome) values ('Supermercado');
insert into categoria (nome) values ('Farmácia');
insert into categoria (nome) values ('Outros');