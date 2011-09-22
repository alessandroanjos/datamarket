ALTER TABLE `enterprise`.`dados_lote` MODIFY COLUMN `DADO` BLOB;

delete from perfil_funcionalidad

delete from funcionalidade

insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (100, NULL, 'Cadastros'                           , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (101,  100, 'Usuário'                             , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (102,  101, 'Inserir Usuário'                     , 'S', 'usuario/inserirUsuario'                                   , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (103,  101, 'Manter Usuário'                      , 'S', 'usuario/consultarUsuario'                                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (104,  100, 'Perfil'                              , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (105,  104, 'Inserir Perfil'                      , 'S', 'perfil/inserirPerfil'                                     , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (106,  104, 'Manter Perfil'                       , 'S', 'perfil/consultarPerfil'                                   , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (107,  100, 'Loja'                                , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (108,  107, 'Inserir Loja'                        , 'S', 'loja/inserirLoja'                                         , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (109,  107, 'Manter Loja'                         , 'S', 'loja/consultarLoja'                                       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (110,  100, 'Estoque'                             , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (111,  110, 'Inserir Estoque'                     , 'S', 'estoque/inserirEstoque'                                   , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (112,  110, 'Manter Estoque'                      , 'S', 'estoque/consultarEstoque'                                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (113,  100, 'Componente'                          , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (114,  113, 'Inserir Componente'                  , 'S', 'componente/inserirComponente'                             , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (115,  113, 'Manter Componente'                   , 'S', 'componente/consultarComponente'                           , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (116,  100, 'Administração de Produtos'           , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (117,  116, 'Grupo de Produto'                    , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (118,  117, 'Inserir Grupo Produto'               , 'S', 'grupoProduto/inserirGrupoProduto'                         , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (119,  117, 'Manter Grupo de Produto'             , 'S', 'grupoProduto/consultarGrupoProduto'                       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (120,  116, 'Tipo de Produto'                     , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (121,  120, 'Inserir Tipo de Produto'             , 'S', 'tipoProduto/inserirTipoProduto'                           , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (122,  120, 'Manter Tipo de Produto'              , 'S', 'tipoProduto/consultarTipoProduto'                         , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (123,  116, 'Unidade'                             , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (124,  123, 'Inserir Unidade'                     , 'S', 'unidade/inserirUnidade'                                   , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (125,  123, 'Manter Unidade'                      , 'S', 'unidade/consultarUnidade'                                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (126,  116, 'Imposto'                             , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (127,  126, 'Inserir Imposto'                     , 'S', 'imposto/inserirImposto'                                   , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (128,  126, 'Manter Imposto'                      , 'S', 'imposto/consultarImposto'                                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (129,  116, 'Produto'                             , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (130,  129, 'Inserir Produto'                     , 'S', 'produto/inserirProduto'                                   , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (131,  129, 'Manter Produto'                      , 'S', 'produto/consultarProduto'                                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (132,  100, 'Forma de Recebimento'                , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (133,  132, 'Inserir Forma de Recebimento'        , 'S', 'formaRecebimento/inserirFormaRecebimento'                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (134,  132, 'Manter Forma de Recebimento'         , 'S', 'formaRecebimento/consultarFormaRecebimento'               , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (135,  100, 'Planos de Pagamento'                 , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (153,  135, 'Plano à Vista'                       , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (136,  153, 'Inserir Plano'                       , 'S', 'planoPagamento/inserirPlanoPagamento'                     , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (137,  153, 'Manter Plano'                        , 'S', 'planoPagamento/consultarPlanoPagamento'                   , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (138,  135, 'Plano à Prazo'                       , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (139,  138, 'Inserir Plano'                       , 'S', 'planoPagamentoAPrazo/inserirPlanoPagamentoAPrazo'         , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (140,  138, 'Manter Plano'                        , 'S', 'planoPagamentoAPrazo/consultarPlanoPagamentoAPrazo'       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (141,  100, 'Clientes'                            , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (142,  141, 'Inserir Cliente'                     , 'S', 'cliente/inserirCliente'                                   , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (143,  141, 'Manter Cliente'                      , 'S', 'cliente/consultarCliente'                                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (144,  100, 'Autorizadora'                        , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (145,  144, 'Inserir Autorizadora'                , 'S', 'autorizadora/inserirAutorizadora'                         , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (146,  144, 'Consultar Autorizadora'              , 'S', 'autorizadora/consultarAutorizadora'                       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (147,  100, 'Fornecedores'                        , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (148,  147, 'Inserir Fornecedor'                  , 'S', 'fornecedor/inserirFornecedor'                             , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (149,  147, 'Manter Fornecedor'                   , 'S', 'fornecedor/consultarFornecedor'                           , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (150,  100, 'Fabricantes'                         , 'S', NULL                                                       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (151,  150, 'Inserir Fabricante'                  , 'S', 'fabricante/inserirFabricante'                             , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (152,  150, 'Manter Fabricante'                   , 'S', 'fabricante/consultarFabricante'                           , 1024  , 768 );

insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (200, NULL, 'Operações'                           , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (201,  200, 'Liberar Nova Base'                   , 'S', 'cargabase/liberaCargaBase'                                , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (202,  200, 'Liberar Carga de Dados'              , 'S', 'lote/liberaDados'                                         , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (203,  200, 'Entrada de Produto'                  , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (204,  203, 'Inserir Entrada de Produtos'         , 'S', 'entradaProduto/inserirEntradaProduto'                     , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (205,  203, 'Consultar Entrada de Produtos'       , 'S', 'entradaProduto/consultarEntradaProduto'                   , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (206,  200, 'Movimentações'                       , 'S', NULL                                                       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (207,  206, 'Produto entre Estoques'              , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (208,  207, 'Inserir Movimentação'                , 'S', 'movimentacaoEstoque/inserirMovimentacaoEstoque'           , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (209,  207, 'Consultar Movimentação'              , 'S', 'movimentacaoEstoque/consultarMovimentacaoEstoque'         , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (210,  206, 'Ajuste de Estoque'                   , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (211,  210, 'Inserir Ajustes de Estoque'          , 'S', 'ajusteEstoque/inserirAjusteEstoque'                       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (212,  210, 'Consultar Ajuste de Estoque'         , 'S', 'ajusteEstoque/consultarAjusteEstoque'                     , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (213,  200, 'Transações'                          , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (214,  213, 'Manter Transação'                    , 'S', 'transacao/consultarTransacao'                             , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (215,  200, 'Devoluções'                          , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (216,  215, 'Inserir Devolução'                   , 'S', 'devolucao/inserirDevolucao'                               , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (217,  215, 'Manter Devolução'                    , 'S', 'devolucao/consultarDevolucao'                             , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (218,  200, 'Pedido'                              , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (219,  218, 'Inserir Pedido'                      , 'S', 'pedido/inserirPedido'                                     , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (220,  218, 'Manter Pedido'                       , 'S', 'pedido/consultarPedido'                                   , 1024  , 768 );

insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (768, NULL, 'Relatórios'                          , 'S', NULL                                                       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (301,  768, 'Analítico de Entradas'               , 'S', 'reports/relatorioAnaliticoEntrada'                        , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (302,  768, 'Analítico de Vendas'                 , 'S', 'reports/relatorioAnaliticoFechamentoVendas'               , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (303,  768, 'Analítico de Movimentação de Estoque', 'S', 'reports/relatorioAnaliticoMovimentacaoEstoque'            , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (304,  768, 'Analítico de Operações de Devolução' , 'S', 'reports/relatorioAnaliticoOperacoesDevolucao'             , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (305,  768, 'ABC Vendas'                          , 'S', 'reports/relatorioABCVendas'                               , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (306,  768, 'Fechamento Caixa'                    , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (307,  306, 'Geral'                               , 'S', 'reports/relatorioFechamentoCaixaGeral'                    , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (308,  306, 'Operador'                            , 'S', 'reports/relatorioFechamentoCaixaOperador'                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (309,  768, 'Comissão Por Vendedor'               , 'S', 'reports/relatorioComissaoPorVendedor'                     , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (310,  768, 'Estoque Atual'                       , 'S', 'reports/relatorioEstoqueAtual'                            , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (311,  768, 'Lucro Bruto'                         , 'S', 'reports/relatorioLucroBruto'                              , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (312,  768, 'Contas à Pagar'                      , 'S', 'reports/relatorioAnaliticoLancamentosContasAPagar'        , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (313,  768, 'Contas à Receber'                    , 'S', 'reports/relatorioAnaliticoLancamentosContasAReceber'      , 1024  , 768 );

insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (400, NULL, 'Financeiro'                          , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (401,  400, 'Lançamentos'                         , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (402,  401, 'Conta à Pagar'                       , 'S', 'lancamento/inserirLancamentoContaAPagar'                  , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (403,  401, 'Conta à Receber'                     , 'S', 'lancamento/inserirLancamentoContaAReceber'                , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (404,  401, 'Consultar Lançamentos'               , 'S', 'lancamento/consultarLancamento'                           , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (405,  400, 'Grupo de Lançamentos'                , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (406,  405, 'Inserir Grupo de Lançamento'         , 'S', 'grupoLancamento/inserirGrupoLancamento'                   , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (407,  405, 'Consultar Grupo de Lançamento'       , 'S', 'grupoLancamento/consultarGrupoLancamento'                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (408,  400, 'Baixa de Lançamentos'                , 'S', 'baixaLancamento/consultarBaixaLancamento'                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (409,  400, 'Movimentação Bancária'               , 'S', NULL                                                       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (410,  409, 'Inserir Movimentação'                , 'S', 'movimentacaoBancaria/inserirMovimentacaoBancaria'         , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (411,  409, 'Manter Movimentação'                 , 'S', 'movimentacaoBancaria/consultarMovimentacaoBancaria'       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (412,  400, 'Conta Corrente'                      , 'S', NULL                                                       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (413,  412, 'Inserir Conta Corrente'              , 'S', 'contaCorrente/inserirContaCorrente'                       , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (414,  412, 'Manter Conta Corrente'               , 'S', 'contaCorrente/consultarContaCorrente'                     , 1024  , 768 );
                                
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (500, NULL, 'Produção'                            , 'S', NULL                                                       , 0     , 0   );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (501,  500, 'Inserir Lote'                        , 'S', 'producao/inserirProducao'                                 , 1024  , 768 );
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (502,  500, 'Manter Lote'                         , 'S', 'producao/consultarProducao'                               , 1024  , 768 );
                                                                                                                                                                                                     
insert into funcionalidade (id, id_func_superior, descricao, situacao, url,largura, altura  ) values (999, NULL, 'Logout'                              , 'S', 'logout'                                                   , 0     , 0   );


insert into perfil_usuario values ( 1, 'Administrador', null, 0.0 );

insert into perfil_funcionalidade
select 1, id from funcionalidade;

update funcionalidade set largura = 0, altura = 0;

update funcionalidade set largura = 800, altura = 600 where url is not null;

insert into usuario values (1,'Admin', '1',1 );





insert into macro_operacao (id,descricao,tipo_componente) values (101,'Saida Sistema Fechado',2);
insert into macro_operacao (id,descricao,tipo_componente) values (102,'Saida Sistema Aberto',2);
insert into macro_operacao (id,descricao,tipo_componente) values (103,'Saida Sistema Devolucao',2);
insert into macro_operacao (id,descricao,tipo_componente) values (104,'Saida Sistema Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente) values (105,'Abertura de AV',2);
insert into macro_operacao (id,descricao,tipo_componente) values (106,'Volta Fechado',2);
insert into macro_operacao (id,descricao,tipo_componente) values (107,'Saida Separacao',2);
insert into macro_operacao (id,descricao,tipo_componente) values (108,'Volta Separacao',2);
insert into macro_operacao (id,descricao,tipo_componente) values (109,'Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente) values (110,'Volta Fechado',2);
insert into macro_operacao (id,descricao,tipo_componente) values (111,'Desconto Item',2);
insert into macro_operacao (id,descricao,tipo_componente) values (112,'Venda Item',2);
insert into macro_operacao (id,descricao,tipo_componente) values (113,'Excluir Venda Item',2);
insert into macro_operacao (id,descricao,tipo_componente) values (114,'Finaliza Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente) values (115,'Finaliza Separacao',2);
insert into macro_operacao (id,descricao,tipo_componente) values (116,'Separacao',2);
insert into macro_operacao (id,descricao,tipo_componente) values (117,'Separa Item',2);
insert into macro_operacao (id,descricao,tipo_componente) values (118,'Cancela Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente) values (119,'Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente) values (120,'Saida Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente) values (121,'Excluir Item Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente) values (122,'Adicionar Item Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente) values (123,'Finalizar Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente) values (124,'Cancelar Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente) values (125,'Desconto Item Altera Pedido',2);

insert into macro_operacao (id,descricao,tipo_componente) values (39,'Solicita Carga Base',1);
insert into macro_operacao (id,descricao,tipo_componente) values (40,'Resgate de Pedido',1);
insert into macro_operacao (id,descricao,tipo_componente) values (41,'Recebimento em Boleto',1);
insert into macro_operacao (id,descricao,tipo_componente) values (42,'Consulta Pedido',1);
insert into macro_operacao (id,descricao,tipo_componente) values (43,'Número do Pedido',1);
insert into macro_operacao (id,descricao,tipo_componente) values (44,'Recebimento Cartao',1);


insert into macro_operacao (id,descricao,tipo_componente) values (44,'Recebimento Cartao',1);
insert into macro_operacao (id,descricao,tipo_componente) values (45,'Configuracao TEF',1);
insert into macro_operacao (id,descricao,tipo_componente) values (46,'Recebimento Chegue-TEF',1);

insert into macro_operacao (id,descricao,tipo_componente) values (126,'Solicita Carga Base Estado Fechado',2);
insert into macro_operacao (id,descricao,tipo_componente) values (127,'Solicita Carga Base Estado Aberto',2);



insert into macro_operacao (id,descricao,tipo_componente) values ( 1, 'Abertura de Caixa',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 2, 'Saida Sistema Fechado',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 3, 'Saida Sistema Fechado Parcial',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 4, 'Entrada Operador',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 5, 'Saida Sistema Disponivel',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 6, 'Inicio Venda',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 7, 'Venda Item',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 8, 'Total',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 9, 'Recebimento Dinheiro',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 10, 'Cancela Item',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 11, 'Desconto Item',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 12, 'Multiplicacao Item',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 13, 'Sangria',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 14, 'Resuprimento',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 15, 'Pausa',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 16, 'Saida Pausa',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 17, 'Consulta Produto Nome',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 18, 'Volta Venda',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 19, 'Fechamento X',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 20, 'Fechamento Z',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 21, 'Volta Recebimento',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 22, 'Desconto Cupom',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 23, 'Cancela Cupom',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 24, 'Cancela Ultimo Cupom',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 25, 'Recebimento Cheque',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 26, 'Recebimento Cheque Pre',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 27, 'Recebimento Cartao Off',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 28, 'Recebimento Cartao Proprio',1);
insert into macro_operacao (id,descricao,tipo_componente) values ( 29, 'Leitura X',1);

insert into ACUMULADOR_NAO_FISCAL (ID,DESCRICAO) values (1,	'SUPRIMENTO');
insert into ACUMULADOR_NAO_FISCAL (ID,DESCRICAO) values (2,	'SANGRIA');


insert into AUTORIZADORA (id,descricao,desagil,situacao) values (1,	'VISA',	5.00,	'A');
insert into AUTORIZADORA  (id,descricao,desagil,situacao) values (2	,	'MASTERCARD',	5.00	,	'A');
insert into AUTORIZADORA  (id,descricao,desagil,situacao) values (3	,	'HIPERCARD',	5.00	,	'A');

 

insert into LOJA (ID, NOME, NUMERO_IP, NUMERO_PORTA, ID_ESTOQUE_ATUAL, ID_CONTA_CORRENTE) values (1,	'LOJA PILOTO',	'10.0.0.254',	'8000',	null, null);
insert into ESTOQUE (id,id_loja,descricao) values (1, 1,'Estoque Principal');
update LOJA set id_estoque_atual = 1;

insert into usuario_loja (id_usuario, id_loja) values (1,1);


insert into COMPONENTE (ID,DESCRICAO,NUMERO_IP,TIPO_COMPONENTE,VERSAO,NUMERO_PORTA,ID_LOJA) values (1, 'PDV1',	'127.0.0.1',1,'0.1','8000',	1);


 

insert into FORMA_RECEBIMENTO (ID,DESCRICAO,REC_IMPRESSORA,ABRIR_GAVETA,VALOR_LIMITE_SANGRIA, DATA_INICIO_VALIDADE,VALOR_MAX_TROCO,ID_FORMA_TROCO,DATA_FIM_VALIDADE,VALIDA_PAGAMENTO_CONTA,VALIDA_RECEBIMENTO_CONTA) values (1,	'DINHEIRO','DINHEIRO'	,'S'	,1.00,	'2011-09-16 15:41:22',	1.00,	1	,'2015-12-04 00:00:00','N','N');
insert into FORMA_RECEBIMENTO  (ID,DESCRICAO,REC_IMPRESSORA,ABRIR_GAVETA,VALOR_LIMITE_SANGRIA,DATA_INICIO_VALIDADE,VALOR_MAX_TROCO,ID_FORMA_TROCO,DATA_FIM_VALIDADE,VALIDA_PAGAMENTO_CONTA,VALIDA_RECEBIMENTO_CONTA) values (2,	'CHEQUE','CHEQUE'	,'S',	1.00,	'2007-12-04 00:00:00',	1.00,	NULL,	'2015-12-04 00:00:00','N','N');
insert into FORMA_RECEBIMENTO  (ID,DESCRICAO,REC_IMPRESSORA,ABRIR_GAVETA,VALOR_LIMITE_SANGRIA,DATA_INICIO_VALIDADE,VALOR_MAX_TROCO,ID_FORMA_TROCO,DATA_FIM_VALIDADE,VALIDA_PAGAMENTO_CONTA,VALIDA_RECEBIMENTO_CONTA) values (3,	'CHEQUE-PRE','CHEQUE-PRE'	,'S',	1.00,	'2007-12-04 00:00:00.000',	1.00,	NULL	,'2015-12-04 00:00:00.000','N','N');
insert into FORMA_RECEBIMENTO  (ID,DESCRICAO,REC_IMPRESSORA,ABRIR_GAVETA,VALOR_LIMITE_SANGRIA,DATA_INICIO_VALIDADE,VALOR_MAX_TROCO,ID_FORMA_TROCO,DATA_FIM_VALIDADE,VALIDA_PAGAMENTO_CONTA,VALIDA_RECEBIMENTO_CONTA) values (4,	'CARTÃO OFF','CARTAO_OFF','S',	1.00,	'2007-12-04 00:00:00.000',	1.00,	NULL	,'2015-12-04 00:00:00.000','N','N');
insert into FORMA_RECEBIMENTO  (ID,DESCRICAO,REC_IMPRESSORA,ABRIR_GAVETA,VALOR_LIMITE_SANGRIA,DATA_INICIO_VALIDADE,VALOR_MAX_TROCO,ID_FORMA_TROCO,DATA_FIM_VALIDADE,VALIDA_PAGAMENTO_CONTA,VALIDA_RECEBIMENTO_CONTA) values (5,	'CARTÃO PROPRIO','CARTAO_PROPRIO','S',	1.00,	'2007-12-04 00:00:00.000',	1.00,	NULL	,'2015-12-04 00:00:00.000','N','N');

insert into FORMA_RECEBIMENTO  (ID,DESCRICAO,REC_IMPRESSORA,ABRIR_GAVETA,VALOR_LIMITE_SANGRIA,DATA_INICIO_VALIDADE,VALOR_MAX_TROCO,ID_FORMA_TROCO,DATA_FIM_VALIDADE,VALIDA_PAGAMENTO_CONTA,VALIDA_RECEBIMENTO_CONTA) values (6,	'Troca','TROCA','S',	1.00,	'2007-12-04 00:00:00.000',	1.00,	NULL	,'2015-12-04 00:00:00.000','N','N');
insert into FORMA_RECEBIMENTO  (ID,DESCRICAO,REC_IMPRESSORA,ABRIR_GAVETA,VALOR_LIMITE_SANGRIA,DATA_INICIO_VALIDADE,VALOR_MAX_TROCO,ID_FORMA_TROCO,DATA_FIM_VALIDADE,VALIDA_PAGAMENTO_CONTA,VALIDA_RECEBIMENTO_CONTA) values (7,	'Boleto','BOLETO','S',	1.00,	'2007-12-04 00:00:00.000',	1.00,	NULL	,'2015-12-04 00:00:00.000','N','N');
insert into FORMA_RECEBIMENTO  (ID,DESCRICAO,REC_IMPRESSORA,ABRIR_GAVETA,VALOR_LIMITE_SANGRIA,DATA_INICIO_VALIDADE,VALOR_MAX_TROCO,ID_FORMA_TROCO,DATA_FIM_VALIDADE,VALIDA_PAGAMENTO_CONTA,VALIDA_RECEBIMENTO_CONTA) values (8,	'Cartão','CARTAO','S',	1.00,	'2007-12-04 00:00:00.000',	1.00,	NULL	,'2015-12-04 00:00:00.000','N','N');

   
insert into PLANO_PAGAMENTO (ID, DESCRICAO,STATUS, VALOR_MIN,VALOR_MAX, PERCENTUAL_DESC, PERCENTUAL_ACRE, DATA_INICIO_VALIDADE, DATA_FIM_VALIDADE,ID_FORMA ) values   (1,'A VISTA','A',0.01,999999999.99,0.00,0.00,'2007-12-04 00:00:00','2015-12-04 00:00:00',1);
insert into PLANO_PAGAMENTO (ID, DESCRICAO,STATUS, VALOR_MIN,VALOR_MAX, PERCENTUAL_DESC, PERCENTUAL_ACRE, DATA_INICIO_VALIDADE, DATA_FIM_VALIDADE,ID_FORMA ) values   (2,'A VISTA','A',	0.01,999999999.99,0.00,	0.00,'2007-12-04 00:00:00.000','2015-12-04 00:00:00.000',2);
insert into PLANO_PAGAMENTO (ID, DESCRICAO,STATUS, VALOR_MIN,VALOR_MAX, PERCENTUAL_DESC, PERCENTUAL_ACRE, DATA_INICIO_VALIDADE, DATA_FIM_VALIDADE,ID_FORMA ) values   (3,'0 + 1 S/ JUROS','A',0.01,999999999.99,0.00,0.00,'2007-12-04 00:00:00.000','2015-12-04 00:00:00.000',3);
insert into PLANO_PAGAMENTO (ID, DESCRICAO,STATUS, VALOR_MIN,VALOR_MAX, PERCENTUAL_DESC, PERCENTUAL_ACRE, DATA_INICIO_VALIDADE, DATA_FIM_VALIDADE,ID_FORMA ) values   (4,'1 + 1 S/ JUROS','A',0.01,999999999.99,0.00,0.00,'2007-12-04 00:00:00.000','2015-12-04 00:00:00.000',3);
insert into PLANO_PAGAMENTO (ID, DESCRICAO,STATUS, VALOR_MIN,VALOR_MAX, PERCENTUAL_DESC, PERCENTUAL_ACRE, DATA_INICIO_VALIDADE, DATA_FIM_VALIDADE,ID_FORMA ) values   (5,'1 + 2 S/ JUROS','A',0.01,999999999.99,0.00,0.00,'2007-12-04 00:00:00.000','2015-12-04 00:00:00.000',3);
insert into PLANO_PAGAMENTO (ID, DESCRICAO,STATUS, VALOR_MIN,VALOR_MAX, PERCENTUAL_DESC, PERCENTUAL_ACRE, DATA_INICIO_VALIDADE, DATA_FIM_VALIDADE,ID_FORMA ) values   (6,'A VISTA','A',	0.01,999999999.99,0.00,0.00,'2007-12-04 00:00:00.000','2015-12-04 00:00:00.000',4);
insert into PLANO_PAGAMENTO (ID, DESCRICAO,STATUS, VALOR_MIN,VALOR_MAX, PERCENTUAL_DESC, PERCENTUAL_ACRE, DATA_INICIO_VALIDADE, DATA_FIM_VALIDADE,ID_FORMA ) values   (7,'A VISTA','A',	0.01,999999999.99,0.00,0.00,'2007-12-04 00:00:00.000','2015-12-04 00:00:00.000',5);
insert into PLANO_PAGAMENTO (ID, DESCRICAO,STATUS, VALOR_MIN,VALOR_MAX, PERCENTUAL_DESC, PERCENTUAL_ACRE, DATA_INICIO_VALIDADE, DATA_FIM_VALIDADE,ID_FORMA ) values   (10,'1+3 S/Juros','A',0.01,99999.99,0.00,0.00,'2000-01-11 21:00:00.000','2015-11-11 21:00:00.000',3);
insert into PLANO_PAGAMENTO_AVISTA select ID  from PLANO_PAGAMENTO where id in (1,2,6,7);
insert into PLANO_PAGAMENTO_APRAZO (ID_PLANO,PERCENTAGEM_ENTRADA,PARCELA_FIXA_VARIADA,DATA_INFOR_USU_SIST) select ID, 0,'F','U' from PLANO_PAGAMENTO where id in (3,4,5,10);

insert into PARCELA_PLAN_PAGTO_APRAZO (ID_PLANO,NUM_ENTRADA,PERCENTAGEM_PARCELA,DATA_PROGRAMADA,QTD_DIAS) values (3,1, 100.00, 'S',30);
insert into PARCELA_PLAN_PAGTO_APRAZO (ID_PLANO,NUM_ENTRADA,PERCENTAGEM_PARCELA,DATA_PROGRAMADA,QTD_DIAS) values (4,1, 50.00, 'S',1);
insert into PARCELA_PLAN_PAGTO_APRAZO (ID_PLANO,NUM_ENTRADA,PERCENTAGEM_PARCELA,DATA_PROGRAMADA,QTD_DIAS) values (4,2, 50.00, 'S',30);
insert into PARCELA_PLAN_PAGTO_APRAZO (ID_PLANO,NUM_ENTRADA,PERCENTAGEM_PARCELA,DATA_PROGRAMADA,QTD_DIAS) values (5,1, 40.00, 'S',1);
insert into PARCELA_PLAN_PAGTO_APRAZO (ID_PLANO,NUM_ENTRADA,PERCENTAGEM_PARCELA,DATA_PROGRAMADA,QTD_DIAS) values (5,2, 30.00, 'S',30);
insert into PARCELA_PLAN_PAGTO_APRAZO (ID_PLANO,NUM_ENTRADA,PERCENTAGEM_PARCELA,DATA_PROGRAMADA,QTD_DIAS) values (5,3, 30.00, 'S',30);
insert into PARCELA_PLAN_PAGTO_APRAZO (ID_PLANO,NUM_ENTRADA,PERCENTAGEM_PARCELA,DATA_PROGRAMADA,QTD_DIAS) values (10,1, 25.00, 'S',1);
insert into PARCELA_PLAN_PAGTO_APRAZO (ID_PLANO,NUM_ENTRADA,PERCENTAGEM_PARCELA,DATA_PROGRAMADA,QTD_DIAS) values (10,2, 25.00, 'S',30);
insert into PARCELA_PLAN_PAGTO_APRAZO (ID_PLANO,NUM_ENTRADA,PERCENTAGEM_PARCELA,DATA_PROGRAMADA,QTD_DIAS) values (10,3, 25.00, 'S',30);
insert into PARCELA_PLAN_PAGTO_APRAZO (ID_PLANO,NUM_ENTRADA,PERCENTAGEM_PARCELA,DATA_PROGRAMADA,QTD_DIAS) values (10,4, 25.00, 'S',30);

 
insert into IMPOSTO   (ID, DESCRICAO, IMP_IMPRESSORA, PERCENTUAL) values (1,	'ICMS 18%'	,'T1',	18.00);
insert into IMPOSTO  (ID, DESCRICAO, IMP_IMPRESSORA, PERCENTUAL) values (2,	'ICMS 17%'	,'T2',	17.00);
insert into IMPOSTO  (ID, DESCRICAO, IMP_IMPRESSORA, PERCENTUAL) values (3,	'ICMS 15%'	,'T3',	15.00);
insert into IMPOSTO   (ID, DESCRICAO, IMP_IMPRESSORA, PERCENTUAL) values (4,	'ICMS 10%'	,'T4',	10.00);
insert into IMPOSTO   (ID, DESCRICAO, IMP_IMPRESSORA, PERCENTUAL) values (5,	'ISS 5%'	,'T5',	5.00);
insert into IMPOSTO   (ID, DESCRICAO, IMP_IMPRESSORA, PERCENTUAL) values (6,	'ISENTO'	,'IS',	0.00);


insert into parametros values ('COMPONENTE','1');
insert into parametros values ('LOGO_CLIENTE','D:\\workspace\\Datamarket\\WebResources\\WEB-INF\\classes\\logo.png');
insert into parametros values ('LOJA','1');
insert into parametros values ('LOTE','2');
insert into parametros values ('PEDE_VENDEDOR','true');
insert into parametros values ('URL','http://pecajbmsv00:8080');
insert into parametros values ('VERSAO','01.00.00');

insert into PERFIL_MACRO_OPERACAO
select 1, id from macro_operacao;


insert into TIPO_PRODUTO values (1,	'NORMAL');
insert into TIPO_PRODUTO values (2,	'UNIDADE VARIAVEL');

 
insert into TOTALIZADORES_NAO_FISCAIS ( ID,IMPRESSORA,VALOR,CONTADOR )  values (1,	'COMPROVANTE',	0.00,	0);
insert into TOTALIZADORES_NAO_FISCAIS ( ID,IMPRESSORA,VALOR,CONTADOR )  values (2,	'PAGAMENTOS',	0.00,	0);
insert into TOTALIZADORES_NAO_FISCAIS ( ID,IMPRESSORA,VALOR,CONTADOR )  values (3,	'RECEBIMENTOS',	0.00,	0);
insert into TOTALIZADORES_NAO_FISCAIS ( ID,IMPRESSORA,VALOR,CONTADOR )  values (4,	'REEMBOLSO',	0.00,	0);
insert into TOTALIZADORES_NAO_FISCAIS ( ID,IMPRESSORA,VALOR,CONTADOR )  values (5,	'SANGRIA',	0.00,	0);
insert into TOTALIZADORES_NAO_FISCAIS ( ID,IMPRESSORA,VALOR,CONTADOR )  values (6,	'SUPRIMENTO',	50.00,	1);

 
 
insert into UNIDADE   ( ID, DESCRICAO, DESCRICAO_DISPLAY, ABREVIACAO ) values (1,	'UNIDADE',	'Unidade',	'UN');
insert into UNIDADE ( ID, DESCRICAO, DESCRICAO_DISPLAY, ABREVIACAO ) values (2,	'METRO',	'Metragem',	'MT');
insert into UNIDADE ( ID, DESCRICAO, DESCRICAO_DISPLAY, ABREVIACAO ) values (3,	'LITROS',	'25',	'LT');
insert into UNIDADE ( ID, DESCRICAO, DESCRICAO_DISPLAY, ABREVIACAO ) values (4,	'KILOGRAMA',	'Peso',	'KG');
insert into UNIDADE ( ID, DESCRICAO, DESCRICAO_DISPLAY, ABREVIACAO ) values (5,	'CENTIMETRO',	'Centimetro',	'CM');
insert into UNIDADE ( ID, DESCRICAO, DESCRICAO_DISPLAY, ABREVIACAO ) values (20,'POLEGADA',	'10',	'PL');