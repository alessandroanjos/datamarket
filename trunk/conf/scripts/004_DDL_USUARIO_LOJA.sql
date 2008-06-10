USE [PDV]
GO
/****** Object:  Table [dbo].[USUARIO_LOJA]    Script Date: 03/02/2008 09:01:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[USUARIO_LOJA](
	[ID_USUARIO] [int] NOT NULL,
	[ID_LOJA] [numeric](18, 0) NOT NULL,
 CONSTRAINT [PK_USUARIO_LOJA] PRIMARY KEY CLUSTERED 
(
	[ID_USUARIO] ASC,
	[ID_LOJA] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[USUARIO_LOJA]  WITH CHECK ADD  CONSTRAINT [FK_LOJA_USUARIO_LOJA] FOREIGN KEY([ID_LOJA])
REFERENCES [dbo].[LOJA] ([ID])
GO
ALTER TABLE [dbo].[USUARIO_LOJA] CHECK CONSTRAINT [FK_LOJA_USUARIO_LOJA]
GO
ALTER TABLE [dbo].[USUARIO_LOJA]  WITH CHECK ADD  CONSTRAINT [FK_USU_USUARIO_LOJA] FOREIGN KEY([ID_USUARIO])
REFERENCES [dbo].[USUARIO] ([ID])
GO
ALTER TABLE [dbo].[USUARIO_LOJA] CHECK CONSTRAINT [FK_USU_USUARIO_LOJA]