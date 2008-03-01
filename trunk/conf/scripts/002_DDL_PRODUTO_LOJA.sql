if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PRODUTO_LOJA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PRODUTO_LOJA]
GO

CREATE TABLE [dbo].[PRODUTO_LOJA] (
	[ID_PRODUTO] [numeric](18, 0) NOT NULL ,
	[ID_LOJA] [numeric](18, 0) NOT NULL 
) ON [PRIMARY]

ALTER TABLE PRODUTO_LOJA ADD CONSTRAINT FK_PRODUTO_PRODUTO_LOJA FOREIGN KEY(ID_PRODUTO) REFERENCES PRODUTO(ID)

ALTER TABLE PRODUTO_LOJA ADD CONSTRAINT FK_LOJA_PRODUTO_LOJA FOREIGN KEY(ID_LOJA) REFERENCES LOJA(ID)

GO
