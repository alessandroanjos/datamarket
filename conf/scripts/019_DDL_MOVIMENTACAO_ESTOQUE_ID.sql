USE [pdv]
GO
/****** Object:  Table [dbo].[MOVIMENTACAO_ESTOQUE]    Script Date: 05/27/2008 14:07:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MOVIMENTACAO_ESTOQUE](
	[ID_ESTOQUE_SAI] [numeric](18, 0) NULL,
	[ID_ESTOQUE_ENT] [numeric](18, 0) NULL,
	[DATA] [datetime] NULL,
	[ID] [numeric](18, 0) NOT NULL,
	[ID_USUARIO] [int] NULL,
	[ID_LOJA_SAI] [numeric](18, 0) NULL,
	[ID_LOJA_ENT] [numeric](18, 0) NULL,
 CONSTRAINT [PK_MOVIMENTACAO_ESTOQUE] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
