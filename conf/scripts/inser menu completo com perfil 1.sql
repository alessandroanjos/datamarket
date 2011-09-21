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
select 1, id from funcionalidade

update funcionalidade set largura = 0, altura = 0;

update funcionalidade set largura = 800, altura = 600 where url is not null;

insert into usuario values (1,'Admin', '1',1 );




















update estado set descricao = 'Caixa Livre' where id = 2

    insert into FORMA_RECEBIMENTO (id, descricao, rec_impressora, abrir_gaveta, valor_limite_sangria, data_inicio_validade, valor_max_troco, id_forma_troco, data_fim_validade, VALIDA_PAGAMENTO_CONTA, valida_recebimento_conta); values (7,'BOLETO', 'BOLETO', 'S', 1.00, GETDATE();, 1.00, null, GETDATE();, null, null);

    	insert into PLANO_PAGAMENTO (id, descricao, status, valor_min, valor_max, percentual_desc, percentual_acre, data_inicio_validade, data_fim_validade, id_forma); values (11, 'A VISTA', 'A', 0.1, 999999999.99, 0.00, 0.00, GETDATE();, null, 7);

    	
    	    insert into FORMA_RECEBIMENTO (id, descricao, rec_impressora, abrir_gaveta, valor_limite_sangria, data_inicio_validade, valor_max_troco, id_forma_troco, data_fim_validade, VALIDA_PAGAMENTO_CONTA, valida_recebimento_conta); values (8,'CARTAO', 'CARTAO', 'S', 0.1, GETDATE();, 0.1, null, GETDATE();, null, null);
	insert into PLANO_PAGAMENTO (id, descricao, status, valor_min, valor_max, percentual_desc, percentual_acre, data_inicio_validade, data_fim_validade, id_forma); values (22, 'VISANET', 'A', 0.1, 999999999.99, 0.00, 0.00, GETDATE();, null, 8);
	insert into PLANO_PAGAMENTO (id, descricao, status, valor_min, valor_max, percentual_desc, percentual_acre, data_inicio_validade, data_fim_validade, id_forma); values (23, 'REDECARD', 'A', 0.1, 999999999.99, 0.00, 0.00, GETDATE();, null, 8);
	insert into PLANO_PAGAMENTO (id, descricao, status, valor_min, valor_max, percentual_desc, percentual_acre, data_inicio_validade, data_fim_validade, id_forma); values (24, 'AMEX', 'A', 0.1, 999999999.99, 0.00, 0.00, GETDATE();, null, 8);

	
	insert into macro_operacao (id,descricao,tipo_componente); values (101,'Saida Sistema Fechado',2);
insert into macro_operacao (id,descricao,tipo_componente); values (102,'Saida Sistema Aberto',2);
insert into macro_operacao (id,descricao,tipo_componente); values (103,'Saida Sistema Devolucao',2);
insert into macro_operacao (id,descricao,tipo_componente); values (104,'Saida Sistema Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente); values (105,'Abertura de AV',2);
insert into macro_operacao (id,descricao,tipo_componente); values (106,'Volta Fechado',2);
insert into macro_operacao (id,descricao,tipo_componente); values (107,'Saida Separacao',2);
insert into macro_operacao (id,descricao,tipo_componente); values (108,'Volta Separacao',2);
insert into macro_operacao (id,descricao,tipo_componente); values (109,'Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente); values (110,'Volta Fechado',2);
insert into macro_operacao (id,descricao,tipo_componente); values (111,'Desconto Item',2);
insert into macro_operacao (id,descricao,tipo_componente); values (112,'Venda Item',2);
insert into macro_operacao (id,descricao,tipo_componente); values (113,'Excluir Venda Item',2);
insert into macro_operacao (id,descricao,tipo_componente); values (114,'Finaliza Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente); values (115,'Finaliza Separacao',2);
insert into macro_operacao (id,descricao,tipo_componente); values (116,'Separacao',2);
insert into macro_operacao (id,descricao,tipo_componente); values (117,'Separa Item',2);
insert into macro_operacao (id,descricao,tipo_componente); values (118,'Cancela Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente); values (119,'Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente); values (120,'Saida Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente); values (121,'Excluir Item Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente); values (122,'Adicionar Item Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente); values (123,'Finalizar Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente); values (124,'Cancelar Altera Pedido',2);
insert into macro_operacao (id,descricao,tipo_componente); values (125,'Desconto Item Altera Pedido',2);

insert into macro_operacao (id,descricao,tipo_componente); values (39,'Solicita Carga Base',1);
insert into macro_operacao (id,descricao,tipo_componente); values (40,'Resgate de Pedido',1);
insert into macro_operacao (id,descricao,tipo_componente); values (41,'Recebimento em Boleto',1);
insert into macro_operacao (id,descricao,tipo_componente); values (42,'Consulta Pedido',1);
insert into macro_operacao (id,descricao,tipo_componente); values (43,'Número do Pedido',1);
insert into macro_operacao (id,descricao,tipo_componente); values (44,'Recebimento Cartao',1);


insert into macro_operacao (id,descricao,tipo_componente); values (44,'Recebimento Cartao',1);
insert into macro_operacao (id,descricao,tipo_componente); values (45,'Configuracao TEF',1);
insert into macro_operacao (id,descricao,tipo_componente); values (46,'Recebimento Chegue-TEF',1);

insert into macro_operacao (id,descricao,tipo_componente); values (126,'Solicita Carga Base Estado Fechado',2);
insert into macro_operacao (id,descricao,tipo_componente); values (127,'Solicita Carga Base Estado Aberto',2);

insert into macro_operacao (id,descricao,tipo_componente); values (44,'Recebimento Cartao',1);