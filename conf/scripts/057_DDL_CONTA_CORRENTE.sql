CREATE TABLE [dbo].[CONTA_CORRENTE] (
  [ID] int NOT NULL,
  [AGENCIA] varchar(10) COLLATE Latin1_General_CI_AI NOT NULL,
  [NUMERO] varchar(10) COLLATE Latin1_General_CI_AI NOT NULL,
  [NOME] varchar(20) COLLATE Latin1_General_CI_AI NOT NULL,
  [SALDO] numeric(18, 2) NOT NULL,
  [SITUACAO] varchar(1) COLLATE Latin1_General_CI_AI NULL,
  [ID_BANCO] int NULL,
  PRIMARY KEY CLUSTERED ([ID]),
  CONSTRAINT [FK_CONTA_BANCO] FOREIGN KEY ([ID_BANCO]) 
  REFERENCES [dbo].[BANCO] ([ID]) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
ON [PRIMARY]
GO 