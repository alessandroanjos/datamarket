CREATE TABLE PARCELA_ENTRADA_PRODUTO(
	ID int NOT NULL,
	ID_ENTRADA_PRODUTO int NOT NULL,
	DATA_VENCIMENTO date NOT NULL,
	VALOR numeric(18, 2) NOT NULL,
 CONSTRAINT PK_PARCELA_ENTRADA_PRODUTO PRIMARY KEY (ID,ID_ENTRADA_PRODUTO);


ALTER TABLE PARCELA_ENTRADA_PRODUTO  ADD  CONSTRAINT FK_PRAC_ENTR_PROD_ENTR_PROD FOREIGN KEY(ID_ENTRADA_PRODUTO)
REFERENCES ENTRADA_PRODUTO (ID);


