if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_EST_TEC_EST]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[ESTADO_TECLA] DROP CONSTRAINT FK_EST_TEC_EST
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__MACRO_OPE__ID_ES__7A3223E8]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[MACRO_OPERACAO] DROP CONSTRAINT FK__MACRO_OPE__ID_ES__7A3223E8
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__MACRO_OPE__ID_PR__793DFFAF]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[MACRO_OPERACAO] DROP CONSTRAINT FK__MACRO_OPE__ID_PR__793DFFAF
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_EVENTO_ITEM_REG_EVENTO_PROD_ITEM_REG]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PRODUTO_ITEM_REGISTRADO] DROP CONSTRAINT FK_EVENTO_ITEM_REG_EVENTO_PROD_ITEM_REG
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[evento_pagamento_evento_transacao]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[EVENTO_ITEM_PAGAMENTO] DROP CONSTRAINT evento_pagamento_evento_transacao
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_EVENTO_TRANS_EVENTO_ITEM_REG]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[EVENTO_ITEM_REGISTRADO] DROP CONSTRAINT FK_EVENTO_TRANS_EVENTO_ITEM_REG
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[fk_forma_recebimento_forma_recebimento]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FORMA_RECEBIMENTO] DROP CONSTRAINT fk_forma_recebimento_forma_recebimento
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_PLANO_PAGAMENTO_FORMA_RECEBIMENTO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PLANO_PAGAMENTO] DROP CONSTRAINT FK_PLANO_PAGAMENTO_FORMA_RECEBIMENTO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GRUPO_PRODUTO_GRUPO_PRODUTO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[GRUPO_PRODUTO] DROP CONSTRAINT GRUPO_PRODUTO_GRUPO_PRODUTO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PRODUTO_GRUPO_PRODUTO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PRODUTO] DROP CONSTRAINT PRODUTO_GRUPO_PRODUTO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_PRODUTO_IMPOSTO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PRODUTO] DROP CONSTRAINT FK_PRODUTO_IMPOSTO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_CP_LJ]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[COMPONENTE] DROP CONSTRAINT FK_CP_LJ
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__PERFIL_MA__ID_MA__756D6ECB]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PERFIL_MACRO_OPERACAO] DROP CONSTRAINT FK__PERFIL_MA__ID_MA__756D6ECB
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__MICRO_OPE__ID_MI__7B264821]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[MICRO_OPERACAO_ASSOCIADA] DROP CONSTRAINT FK__MICRO_OPE__ID_MI__7B264821
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__MACRO_OPE__ID_MI__7755B73D]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[MACRO_OPERACAO] DROP CONSTRAINT FK__MACRO_OPE__ID_MI__7755B73D
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__SAIDA_MIC__ID_MI__74794A92]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[SAIDA_MICRO_OPERACAO] DROP CONSTRAINT FK__SAIDA_MIC__ID_MI__74794A92
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__SAIDA_MIC__ID_MI__73852659]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[SAIDA_MICRO_OPERACAO_ASSOCIADA] DROP CONSTRAINT FK__SAIDA_MIC__ID_MI__73852659
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__PERFIL_MA__ID_PE__76619304]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PERFIL_MACRO_OPERACAO] DROP CONSTRAINT FK__PERFIL_MA__ID_PE__76619304
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__PERFIL_US__ID_PE__7D0E9093]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PERFIL_USUARIO] DROP CONSTRAINT FK__PERFIL_US__ID_PE__7D0E9093
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__USUARIO__ID_PERF__7C1A6C5A]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[USUARIO] DROP CONSTRAINT FK__USUARIO__ID_PERF__7C1A6C5A
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__SAIDA_MIC__ID_SA__72910220]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[SAIDA_MICRO_OPERACAO_ASSOCIADA] DROP CONSTRAINT FK__SAIDA_MIC__ID_SA__72910220
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_EST_TEC_TEC]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[ESTADO_TECLA] DROP CONSTRAINT FK_EST_TEC_TEC
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK__MACRO_OPE__ID_TE__7849DB76]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[MACRO_OPERACAO] DROP CONSTRAINT FK__MACRO_OPE__ID_TE__7849DB76
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_PRODUTO_TIPO_PRODUTO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PRODUTO] DROP CONSTRAINT FK_PRODUTO_TIPO_PRODUTO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_TRANS_CANC_TRANS]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[TRANSACAO_CANCELAMENTO] DROP CONSTRAINT FK_TRANS_CANC_TRANS
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_TRANS]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[TRANSACAO_ENTRADA_OPERADOR] DROP CONSTRAINT FK_TRANS
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_TRANS_TRANS_VENDA]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[TRANSACAO_VENDA] DROP CONSTRAINT FK_TRANS_TRANS_VENDA
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_TRANS_VENDA_EVENTO_TRANS]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[EVENTO_TRANSACAO] DROP CONSTRAINT FK_TRANS_VENDA_EVENTO_TRANS
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_PRODUTO_UNIDADE]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PRODUTO] DROP CONSTRAINT FK_PRODUTO_UNIDADE
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[COMPONENTE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[COMPONENTE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ESTADO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ESTADO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ESTADO_TECLA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ESTADO_TECLA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[EVENTO_ITEM_PAGAMENTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[EVENTO_ITEM_PAGAMENTO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[EVENTO_ITEM_REGISTRADO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[EVENTO_ITEM_REGISTRADO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[EVENTO_TRANSACAO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[EVENTO_TRANSACAO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FORMA_RECEBIMENTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FORMA_RECEBIMENTO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GRUPO_PRODUTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[GRUPO_PRODUTO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[IMPOSTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[IMPOSTO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[LOJA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[LOJA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MACRO_OPERACAO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MACRO_OPERACAO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MICRO_OPERACAO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MICRO_OPERACAO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MICRO_OPERACAO_ASSOCIADA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MICRO_OPERACAO_ASSOCIADA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PARAMETROS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PARAMETROS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PERFIL_MACRO_OPERACAO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PERFIL_MACRO_OPERACAO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PERFIL_USUARIO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PERFIL_USUARIO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PLANO_PAGAMENTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PLANO_PAGAMENTO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PRODUTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PRODUTO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PRODUTO_ITEM_REGISTRADO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PRODUTO_ITEM_REGISTRADO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[SAIDA_MICRO_OPERACAO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[SAIDA_MICRO_OPERACAO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[SAIDA_MICRO_OPERACAO_ASSOCIADA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[SAIDA_MICRO_OPERACAO_ASSOCIADA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TECLA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TECLA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TIPO_PRODUTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TIPO_PRODUTO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TOTALIZADORES_NAO_FISCAIS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TOTALIZADORES_NAO_FISCAIS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TRANSACAO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TRANSACAO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TRANSACAO_ABERTURA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TRANSACAO_ABERTURA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TRANSACAO_CANCELAMENTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TRANSACAO_CANCELAMENTO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TRANSACAO_ENTRADA_OPERADOR]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TRANSACAO_ENTRADA_OPERADOR]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TRANSACAO_FECHAMENTO_X]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TRANSACAO_FECHAMENTO_X]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TRANSACAO_FECHAMENTO_Z]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TRANSACAO_FECHAMENTO_Z]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TRANSACAO_RESUPRIMENTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TRANSACAO_RESUPRIMENTO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TRANSACAO_SANGRIA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TRANSACAO_SANGRIA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TRANSACAO_VENDA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TRANSACAO_VENDA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[UNIDADE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[UNIDADE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[USUARIO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[USUARIO]
GO

CREATE TABLE [dbo].[COMPONENTE] (
	[ID] [numeric](18, 0) NOT NULL ,
	[DESCRICAO] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[ID_LOJA] [numeric](18, 0) NULL ,
	[IP] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[VERSAO] [varchar] (50) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ESTADO] (
	[ID] [int] NOT NULL ,
	[DESCRICAO] [varchar] (20) COLLATE Latin1_General_CI_AI NULL ,
	[INPUT_TYPE] [int] NULL ,
	[INPUT_SIZE] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ESTADO_TECLA] (
	[ID_ESTADO] [int] NOT NULL ,
	[ID_TECLA] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[EVENTO_ITEM_PAGAMENTO] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[NUM_EVENTO] [numeric](18, 0) NOT NULL ,
	[VALOR_BRUTO] [numeric](18, 2) NOT NULL ,
	[VALOR_DESCONTO] [numeric](18, 2) NULL ,
	[VALOR_ACRESCIMO] [numeric](18, 2) NULL ,
	[FORMA_IMPRESSORA] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[CODIGO_FORMA] [numeric](18, 0) NOT NULL ,
	[CODIGO_PLANO] [numeric](18, 0) NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[EVENTO_ITEM_REGISTRADO] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[NUM_EVENTO] [numeric](18, 0) NOT NULL ,
	[QUANTIDADE] [numeric](18, 3) NULL ,
	[DESCONTO] [numeric](18, 2) NULL ,
	[PRECO] [numeric](18, 2) NULL ,
	[SITUACAO] [varchar] (50) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[EVENTO_TRANSACAO] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[NUM_EVENTO] [numeric](18, 0) NOT NULL ,
	[TIPO_EVENTO] [numeric](18, 0) NULL ,
	[DATA_HORA_EVENTO] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[FORMA_RECEBIMENTO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[DESCRICAO] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[REC_IMPRESSORA] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[ABRIR_GAVETA] [varchar] (1) COLLATE Latin1_General_CI_AI NOT NULL ,
	[VALOR_LIMITE_SANGRIA] [numeric](18, 2) NOT NULL ,
	[DATA_INICIO_VALIDADE] [datetime] NULL ,
	[VALOR_MAX_TROCO] [numeric](18, 2) NOT NULL ,
	[ID_FORMA_TROCO] [numeric](18, 0) NOT NULL ,
	[DATA_FIM_VALIDADE] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[GRUPO_PRODUTO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[DESCRICAO] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[ID_GRUPO_SUPERIOR] [numeric](18, 0) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[IMPOSTO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[DESCRICAO] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[IMP_IMPRESSORA] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[PERCENTUAL] [numeric](18, 2) NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[LOJA] (
	[ID] [numeric](18, 0) NOT NULL ,
	[NOME] [varchar] (50) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MACRO_OPERACAO] (
	[ID] [int] NOT NULL ,
	[ID_ESTADO_ATUAL] [int] NULL ,
	[ID_PROXIMO_ESTADO] [int] NULL ,
	[ID_TECLA] [int] NULL ,
	[ID_MICRO_OPERACAO_ASSOCIADA] [int] NULL ,
	[DESCRICAO] [varchar] (50) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MICRO_OPERACAO] (
	[ID] [int] NOT NULL ,
	[CLASSE] [varchar] (200) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[MICRO_OPERACAO_ASSOCIADA] (
	[ID] [int] NOT NULL ,
	[ID_MICRO_OPERACAO] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PARAMETROS] (
	[CHAVE] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[VALOR] [varchar] (255) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PERFIL_MACRO_OPERACAO] (
	[ID_PERFIL] [int] NOT NULL ,
	[ID_MACRO_OPERACAO] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PERFIL_USUARIO] (
	[ID] [int] NOT NULL ,
	[ID_PERFIL_SUPERIOR] [int] NULL ,
	[DESCRICAO] [varchar] (20) COLLATE Latin1_General_CI_AI NULL ,
	[PERCENTUAL_DESC] [numeric](18, 2) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PLANO_PAGAMENTO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[DESCRICAO] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[STATUS] [varchar] (1) COLLATE Latin1_General_CI_AI NULL ,
	[VALOR_MIN] [numeric](18, 2) NULL ,
	[VALOR_MAX] [numeric](18, 2) NULL ,
	[PERCENTUAL_DESC] [numeric](18, 2) NULL ,
	[PERCENTUAL_ACRE] [numeric](18, 2) NULL ,
	[DATA_INICIO_VALIDADE] [datetime] NULL ,
	[DATA_FIM_VALIDADE] [datetime] NULL ,
	[ID_FORMA] [numeric](18, 0) NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PRODUTO] (
	[ID] [numeric](18, 0) NULL ,
	[CODIGO_EXTERNO] [varchar] (15) COLLATE Latin1_General_CI_AI NOT NULL ,
	[DESCRICAO_COMPLETA] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[DESCRICAO_COMPACTA] [varchar] (30) COLLATE Latin1_General_CI_AI NOT NULL ,
	[PRECO_PADRAO] [numeric](19, 2) NOT NULL ,
	[PRECO_PROMOCAO] [numeric](19, 2) NULL ,
	[ID_TIPO_PRODUTO] [numeric](18, 0) NOT NULL ,
	[ID_UNIDADE] [numeric](18, 0) NOT NULL ,
	[ID_IMPOSTO] [numeric](18, 0) NOT NULL ,
	[ID_GRUPO_PRODUTO] [numeric](18, 0) NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PRODUTO_ITEM_REGISTRADO] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[NUM_EVENTO] [numeric](18, 0) NOT NULL ,
	[ID_PRODUTO] [numeric](18, 0) NULL ,
	[CODIGO_EXTERNO] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[DESCRICAO_COMPLETA] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[PRECO_PADRAO] [numeric](18, 2) NULL ,
	[PRECO_PRATICADO] [numeric](18, 2) NULL ,
	[IMP_IMPRESSORA] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[PERCENTUAL] [numeric](18, 2) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[SAIDA_MICRO_OPERACAO] (
	[ID] [int] NOT NULL ,
	[SAIDA] [int] NULL ,
	[ID_MICRO_OPERACAO_ASSOCIADA] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[SAIDA_MICRO_OPERACAO_ASSOCIADA] (
	[ID_MICRO_OPERACAO_ASSOCIADA] [int] NOT NULL ,
	[ID_SAIDA_MICRO_OPERACAO] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TECLA] (
	[ID] [int] NOT NULL ,
	[CODIGO_ASCI] [int] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TIPO_PRODUTO] (
	[ID] [numeric](18, 0) NOT NULL ,
	[DESCRICAO] [varchar] (50) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TOTALIZADORES_NAO_FISCAIS] (
	[ID] [numeric](18, 0) NOT NULL ,
	[VALOR] [numeric](18, 2) NOT NULL ,
	[CONTADOR] [numeric](18, 0) NOT NULL ,
	[IMPRESSORA] [varchar] (50) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TRANSACAO] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[TIPO_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[STATUS] [varchar] (2) COLLATE Latin1_General_CI_AI NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TRANSACAO_ABERTURA] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[COD_USU_AUTORIZADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[GT_INICIO] [numeric](18, 2) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TRANSACAO_CANCELAMENTO] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[LOJA_CANC] [numeric](18, 0) NULL ,
	[COMPONENTE_CANC] [numeric](18, 0) NULL ,
	[NUM_TRANSACAO_CANC] [numeric](18, 0) NULL ,
	[DATA_TRANSACAO_CANC] [datetime] NULL ,
	[COD_USU_AUTORIZADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[NUMERO_CUPOM] [varchar] (50) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TRANSACAO_ENTRADA_OPERADOR] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[COD_USU_AUTORIZADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[COD_USU_OPERADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[FUNDO_TROCO] [numeric](18, 2) NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TRANSACAO_FECHAMENTO_X] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[COD_USU_AUTORIZADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[COD_USU_OPERADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TRANSACAO_FECHAMENTO_Z] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[COD_USU_AUTORIZADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[GT_FIM] [numeric](18, 2) NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TRANSACAO_RESUPRIMENTO] (
	[LOJA] [numeric](18, 0) NULL ,
	[COMPONENTE] [numeric](18, 0) NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NULL ,
	[DATA_TRANSACAO] [datetime] NULL ,
	[VALOR] [numeric](18, 2) NULL ,
	[COD_USU_AUTORIZADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[COD_USU_OPERADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[NUMERO_CUPOM] [varchar] (50) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TRANSACAO_SANGRIA] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[COD_USU_AUTORIZADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[COD_USU_OPERADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[VALOR] [numeric](18, 2) NULL ,
	[NUMERO_CUPOM] [varchar] (50) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[TRANSACAO_VENDA] (
	[LOJA] [numeric](18, 0) NOT NULL ,
	[COMPONENTE] [numeric](18, 0) NOT NULL ,
	[NUM_TRANSACAO] [numeric](18, 0) NOT NULL ,
	[DATA_TRANSACAO] [datetime] NOT NULL ,
	[COD_USU_VENDEDOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[COD_USU_OPERADOR] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[DATA_HORA_INICIO] [datetime] NULL ,
	[DATA_HORA_FIM] [datetime] NULL ,
	[VALOR_TROCO] [numeric](18, 2) NULL ,
	[COD_FORMA_TROCO] [numeric](18, 0) NULL ,
	[NUMERO_CUPOM] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[VALOR_CUPOM] [numeric](18, 2) NULL ,
	[DESCONTO_CUPOM] [numeric](18, 2) NULL ,
	[SITUACAO] [varchar] (1) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[UNIDADE] (
	[ID] [numeric](18, 0) NOT NULL ,
	[DESCRICAO] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[DESCRICAO_DISPLAY] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL ,
	[ABREVIACAO] [varchar] (50) COLLATE Latin1_General_CI_AI NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[USUARIO] (
	[ID] [int] NOT NULL ,
	[NOME] [varchar] (50) COLLATE Latin1_General_CI_AI NULL ,
	[ID_PERFIL] [int] NULL ,
	[SENHA] [varchar] (20) COLLATE Latin1_General_CI_AI NULL 
) ON [PRIMARY]
GO

