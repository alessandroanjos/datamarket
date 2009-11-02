IF NOT EXISTS (select * from FORMA_RECEBIMENTO where id = 7) BEGIN
    insert into FORMA_RECEBIMENTO (id, descricao, rec_impressora, abrir_gaveta, valor_limite_sangria, data_inicio_validade, valor_max_troco, id_forma_troco, data_fim_validade, VALIDA_PAGAMENTO_CONTA, valida_recebimento_conta) values (7,'BOLETO', 'BOLETO', 'S', 1.00, GETDATE(), 1.00, null, GETDATE(), null, null);
END

IF NOT EXISTS (select * from PLANO_PAGAMENTO where id = 11)  BEGIN
	insert into PLANO_PAGAMENTO (id, descricao, status, valor_min, valor_max, percentual_desc, percentual_acre, data_inicio_validade, data_fim_validade, id_forma) values (11, 'A VISTA', 'A', 0.1, 999999999.99, 0.00, 0.00, GETDATE(), null, 7);
END
