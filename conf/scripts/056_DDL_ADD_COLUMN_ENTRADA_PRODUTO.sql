alter table produto_entrada_produto drop constraint FK_PRODUTO_ENTRADA_PRODUTO_ESTOQUE
alter table produto_entrada_produto drop column id_loja
alter table produto_entrada_produto drop column id_estoque

alter table entrada_produto add id_loja numeric(18,0) null
alter table entrada_produto add id_estoque int null


ALTER TABLE [dbo].[ENTRADA_PRODUTO] ADD 
	CONSTRAINT [FK_ENTRADA_PRODUTO_ESTOQUE] FOREIGN KEY 
	(
		[ID_ESTOQUE],
		[ID_LOJA]
	) REFERENCES [dbo].[ESTOQUE] (
		[ID],
		[ID_LOJA]
	)