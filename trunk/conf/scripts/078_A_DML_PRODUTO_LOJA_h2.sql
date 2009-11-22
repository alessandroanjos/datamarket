

CREATE TABLE PRODUTO_LOJA(
	ID_PRODUTO bigint NOT NULL,
	ID_LOJA bigint NOT NULL,
  PRIMARY KEY   
(
	ID_PRODUTO ,
	ID_LOJA 
)
) 

GO
USE ENTERPRISE
GO
ALTER TABLE dbo.PRODUTO_LOJA  WITH CHECK ADD  CONSTRAINT FK_LOJA_PRODUTO_LOJA FOREIGN KEY(ID_LOJA)
REFERENCES dbo.LOJA (ID)
GO
ALTER TABLE dbo.PRODUTO_LOJA  WITH CHECK ADD  CONSTRAINT FK_PRODUTO_PRODUTO_LOJA FOREIGN KEY(ID_PRODUTO)
REFERENCES dbo.PRODUTO (ID)
GO
ALTER TABLE dbo.PRODUTO_LOJA  WITH CHECK ADD  CONSTRAINT FK4318FBA0BBABC7F5 FOREIGN KEY(ID_PRODUTO)
REFERENCES dbo.PRODUTO (ID)
GO
ALTER TABLE dbo.PRODUTO_LOJA  WITH CHECK ADD  CONSTRAINT FK4318FBA0EDB68DBC FOREIGN KEY(ID_LOJA)
REFERENCES dbo.LOJA (ID)