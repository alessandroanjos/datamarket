CREATE TABLE BOLE_EVEN_ITEM_TRANS_BOLE(
	LOJA int NOT NULL ,
	COMPONENTE int NOT NULL ,
	NUM_TRANSACAO int NOT NULL  ,
	DATA_TRANSACAO DATE NOT NULL  ,
	NUM_EVENTO int NOT NULL  ,
	BOLETO BIGINT NOT NULL , 
PRIMARY KEY(LOJA, COMPONENTE, NUM_TRANSACAO, DATA_TRANSACAO, NUM_EVENTO, BOLETO)
	)
	
GO

ALTER TABLE dbo.BOLE_EVEN_ITEM_TRANS_BOLE  WITH CHECK ADD  CONSTRAINT FK_BOLE_EVEN_ITEM_TRANS_BOLE FOREIGN KEY(BOLETO)
REFERENCES dbo.BOLETO (ID)
GO
ALTER TABLE dbo.BOLE_EVEN_ITEM_TRANS_BOLE  WITH CHECK ADD  CONSTRAINT FK_TRAN_EVEN_ITEM_TRANS_BOLE FOREIGN KEY(LOJA, COMPONENTE, NUM_TRANSACAO, DATA_TRANSACAO, NUM_EVENTO)
REFERENCES dbo.EVENTO_ITEM_PAGAMENTO_BOLETO (LOJA, COMPONENTE, NUM_TRANSACAO, DATA_TRANSACAO, NUM_EVENTO)
GO