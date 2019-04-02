create table EMPREGADO
(
	ID INTEGER not null primary key
		GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
	FIRSTNAME VARCHAR(30) not null,
	LASTNAME VARCHAR(30) not null,
	USERNAME VARCHAR(30) not null unique,
	PASSWORD VARCHAR(128) not null,
	SEX CHAR(1) not null,
	DATEOFBIRTH DATE not null,
	ROLE VARCHAR(30) not null,
	CREATED TIMESTAMP,
	MODIFIED TIMESTAMP
);
create table CATEGORIA
(
	ID INTEGER not null primary key
		GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
	NAME VARCHAR(30) not null unique,
    CREATED TIMESTAMP,
	MODIFIED TIMESTAMP
);
create table SUBCATEGORIA
(
	ID INTEGER not null primary key
		GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
	NAME VARCHAR(30) not null,
	CAT_ID INTEGER not null,
    FOREIGN KEY (CAT_ID) REFERENCES CATEGORIA(ID),
    CREATED TIMESTAMP,
	MODIFIED TIMESTAMP
);
--Altera a partir daqui
--Produto ganha price
create table PRODUTO
(
	ID INTEGER not null primary key
		GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
	NAME VARCHAR(30) not null,
	PRICE DECIMAL not null, 
    EMP_ID INTEGER not null,
	SUBCAT_ID INTEGER not null,
    FOREIGN KEY (EMP_ID) REFERENCES EMPREGADO(ID),
	FOREIGN KEY (SUBCAT_ID) REFERENCES SUBCATEGORIA(ID),
	CREATED TIMESTAMP,
	MODIFIED TIMESTAMP
);
--Fatura passa a depender de empregado
create table FATURA
(
	ID INTEGER not null primary key
		GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
	NAME VARCHAR(30) not null unique,
	EMP_ID INTEGER not null,
	FOREIGN KEY (EMP_ID) REFERENCES EMPREGADO(ID),
    CREATED TIMESTAMP,
	MODIFIED TIMESTAMP
);
--LinhasDeFatura deixam de ter value e name e passa a referenciar produto e fatura e repetição
create table LINHASDEFATURA
(
	ID INTEGER not null primary key
		GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
	REP INTEGER not null,
	PROD_ID INTEGER not null,
	FAT_ID INTEGER not null,
	FOREIGN KEY (PROD_ID) REFERENCES PRODUTO(ID),
    FOREIGN KEY (FAT_ID) REFERENCES FATURA(ID),	
    CREATED TIMESTAMP,
	MODIFIED TIMESTAMP
);
