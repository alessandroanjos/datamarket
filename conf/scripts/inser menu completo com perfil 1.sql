delete from perfil_funcionalidade

delete from funcionalidade

insert into funcionalidade values (100, NULL, 'Cadastros'                           , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (101,  100, 'Usu�rio'                             , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (102,  101, 'Inserir Usu�rio'                     , 'S', 'usuario/inserirUsuario'                                   , 1024  , 768 )
insert into funcionalidade values (103,  101, 'Manter Usu�rio'                      , 'S', 'usuario/consultarUsuario'                                 , 1024  , 768 )
insert into funcionalidade values (104,  100, 'Perfil'                              , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (105,  104, 'Inserir Perfil'                      , 'S', 'perfil/inserirPerfil'                                     , 1024  , 768 )
insert into funcionalidade values (106,  104, 'Manter Perfil'                       , 'S', 'perfil/consultarPerfil'                                   , 1024  , 768 )
insert into funcionalidade values (107,  100, 'Loja'                                , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (108,  107, 'Inserir Loja'                        , 'S', 'loja/inserirLoja'                                         , 1024  , 768 )
insert into funcionalidade values (109,  107, 'Manter Loja'                         , 'S', 'loja/consultarLoja'                                       , 1024  , 768 )
insert into funcionalidade values (110,  100, 'Estoque'                             , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (111,  110, 'Inserir Estoque'                     , 'S', 'estoque/inserirEstoque'                                   , 1024  , 768 )
insert into funcionalidade values (112,  110, 'Manter Estoque'                      , 'S', 'estoque/consultarEstoque'                                 , 1024  , 768 )
insert into funcionalidade values (113,  100, 'Componente'                          , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (114,  113, 'Inserir Componente'                  , 'S', 'componente/inserirComponente'                             , 1024  , 768 )
insert into funcionalidade values (115,  113, 'Manter Componente'                   , 'S', 'componente/consultarComponente'                           , 1024  , 768 )
insert into funcionalidade values (116,  100, 'Administra��o de Produtos'           , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (117,  116, 'Grupo de Produto'                    , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (118,  117, 'Inserir Grupo Produto'               , 'S', 'grupoProduto/inserirGrupoProduto'                         , 1024  , 768 )
insert into funcionalidade values (119,  117, 'Manter Grupo de Produto'             , 'S', 'grupoProduto/consultarGrupoProduto'                       , 1024  , 768 )
insert into funcionalidade values (120,  116, 'Tipo de Produto'                     , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (121,  120, 'Inserir Tipo de Produto'             , 'S', 'tipoProduto/inserirTipoProduto'                           , 1024  , 768 )
insert into funcionalidade values (122,  120, 'Manter Tipo de Produto'              , 'S', 'tipoProduto/consultarTipoProduto'                         , 1024  , 768 )
insert into funcionalidade values (123,  116, 'Unidade'                             , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (124,  123, 'Inserir Unidade'                     , 'S', 'unidade/inserirUnidade'                                   , 1024  , 768 )
insert into funcionalidade values (125,  123, 'Manter Unidade'                      , 'S', 'unidade/consultarUnidade'                                 , 1024  , 768 )
insert into funcionalidade values (126,  116, 'Imposto'                             , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (127,  126, 'Inserir Imposto'                     , 'S', 'imposto/inserirImposto'                                   , 1024  , 768 )
insert into funcionalidade values (128,  126, 'Manter Imposto'                      , 'S', 'imposto/consultarImposto'                                 , 1024  , 768 )
insert into funcionalidade values (129,  116, 'Produto'                             , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (130,  129, 'Inserir Produto'                     , 'S', 'produto/inserirProduto'                                   , 550   , 768 )
insert into funcionalidade values (131,  129, 'Manter Produto'                      , 'S', 'produto/consultarProduto'                                 , 1024  , 768 )
insert into funcionalidade values (132,  100, 'Forma de Recebimento'                , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (133,  132, 'Inserir Forma de Recebimento'        , 'S', 'formaRecebimento/inserirFormaRecebimento'                 , 1024  , 768 )
insert into funcionalidade values (134,  132, 'Manter Forma de Recebimento'         , 'S', 'formaRecebimento/consultarFormaRecebimento'               , 1024  , 768 )
insert into funcionalidade values (135,  100, 'Planos de Pagamento'                 , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (153,  135, 'Plano � Vista'                       , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (136,  153, 'Inserir Plano'                       , 'S', 'planoPagamento/inserirPlanoPagamento'                     , 1024  , 768 )
insert into funcionalidade values (137,  153, 'Manter Plano'                        , 'S', 'planoPagamento/consultarPlanoPagamento'                   , 1024  , 768 )
insert into funcionalidade values (138,  135, 'Plano � Prazo'                       , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (139,  138, 'Inserir Plano'                       , 'S', 'planoPagamentoAPrazo/inserirPlanoPagamentoAPrazo'         , 1024  , 768 )
insert into funcionalidade values (140,  138, 'Manter Plano'                        , 'S', 'planoPagamentoAPrazo/consultarPlanoPagamentoAPrazo'       , 1024  , 768 )
insert into funcionalidade values (141,  100, 'Clientes'                            , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (142,  141, 'Inserir Cliente'                     , 'S', 'cliente/inserirCliente'                                   , 1024  , 768 )
insert into funcionalidade values (143,  141, 'Manter Cliente'                      , 'S', 'cliente/consultarCliente'                                 , 1024  , 768 )
insert into funcionalidade values (144,  100, 'Autorizadora'                        , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (145,  144, 'Inserir Autorizadora'                , 'S', 'autorizadora/inserirAutorizadora'                         , 1024  , 768 )
insert into funcionalidade values (146,  144, 'Consultar Autorizadora'              , 'S', 'autorizadora/consultarAutorizadora'                       , 1024  , 768 )
insert into funcionalidade values (147,  100, 'Fornecedores'                        , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (148,  147, 'Inserir Fornecedor'                  , 'S', 'fornecedor/inserirFornecedor'                             , 1024  , 768 )
insert into funcionalidade values (149,  147, 'Manter Fornecedor'                   , 'S', 'fornecedor/consultarFornecedor'                           , 1024  , 768 )
insert into funcionalidade values (150,  100, 'Fabricantes'                         , 'S', NULL                                                       , 1024  , 768 )
insert into funcionalidade values (151,  150, 'Inserir Fabricante'                  , 'S', 'fabricante/inserirFabricante'                             , 1024  , 768 )
insert into funcionalidade values (152,  150, 'Manter Fabricante'                   , 'S', 'fabricante/consultarFabricante'                           , 1024  , 768 )

insert into funcionalidade values (200, NULL, 'Opera��es'                           , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (201,  200, 'Liberar Nova Base'                   , 'S', 'cargabase/liberaCargaBase'                                , 1024  , 300 )
insert into funcionalidade values (202,  200, 'Liberar Carga de Dados'              , 'S', 'lote/liberaDados'                                         , 1024  , 768 )
insert into funcionalidade values (203,  200, 'Entrada de Produto'                  , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (204,  203, 'Inserir Entrada de Produtos'         , 'S', 'entradaProduto/inserirEntradaProduto'                     , 1024  , 768 )
insert into funcionalidade values (205,  203, 'Consultar Entrada de Produtos'       , 'S', 'entradaProduto/consultarEntradaProduto'                   , 1024  , 768 )
insert into funcionalidade values (206,  200, 'Movimenta��es'                       , 'S', NULL                                                       , 1024  , 768 )
insert into funcionalidade values (207,  206, 'Produto entre Estoques'              , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (208,  207, 'Inserir Movimenta��o'                , 'S', 'movimentacaoEstoque/inserirMovimentacaoEstoque'           , 1024  , 768 )
insert into funcionalidade values (209,  207, 'Consultar Movimenta��o'              , 'S', 'movimentacaoEstoque/consultarMovimentacaoEstoque'         , 1024  , 768 )
insert into funcionalidade values (210,  206, 'Ajuste de Estoque'                   , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (211,  210, 'Inserir Ajustes de Estoque'          , 'S', 'ajusteEstoque/inserirAjusteEstoque'                       , 1024  , 768 )
insert into funcionalidade values (212,  210, 'Consultar Ajuste de Estoque'         , 'S', 'ajusteEstoque/consultarAjusteEstoque'                     , 1024  , 768 )
insert into funcionalidade values (213,  200, 'Transa��es'                          , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (214,  213, 'Manter Transa��o'                    , 'S', 'transacao/consultarTransacao'                             , 1024  , 770 )
insert into funcionalidade values (215,  200, 'Devolu��es'                          , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (216,  215, 'Inserir Devolu��o'                   , 'S', 'devolucao/inserirDevolucao'                               , 1024  , 768 )
insert into funcionalidade values (217,  215, 'Manter Devolu��o'                    , 'S', 'devolucao/consultarDevolucao'                             , 1024  , 768 )
insert into funcionalidade values (218,  200, 'Pedido'                              , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (219,  218, 'Inserir Pedido'                      , 'S', 'pedido/inserirPedido'                                     , 1024  , 768 )
insert into funcionalidade values (220,  218, 'Manter Pedido'                       , 'S', 'pedido/consultarPedido'                                   , 1024  , 768 )
insert into funcionalidade values(220, 200,   'Liberar Nova Base',                    'S', 'cargabase/liberaCargaBase'                                , 800, 300)

insert into funcionalidade values (768, NULL, 'Relat�rios'                          , 'S', NULL                                                       , 1024  , 768 )
insert into funcionalidade values (301,  768, 'Anal�tico de Entradas'               , 'S', 'reports/relatorioAnaliticoEntrada'                        , 1024  , 768 )
insert into funcionalidade values (302,  768, 'Anal�tico de Vendas'                 , 'S', 'reports/relatorioAnaliticoFechamentoVendas'               , 1024  , 768 )
insert into funcionalidade values (303,  768, 'Anal�tico de Movimenta��o de Estoque', 'S', 'reports/relatorioAnaliticoMovimentacaoEstoque'            , 1024  , 768 )
insert into funcionalidade values (304,  768, 'Anal�tico de Opera��es de Devolu��o' , 'S', 'reports/relatorioAnaliticoOperacoesDevolucao'             , 1024  , 768 )
insert into funcionalidade values (305,  768, 'ABC Vendas'                          , 'S', 'reports/relatorioABCVendas'                               , 1024  , 768 )
insert into funcionalidade values (306,  768, 'Fechamento Caixa'                    , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (307,  306, 'Geral'                               , 'S', 'reports/relatorioFechamentoCaixaGeral'                    , 1024  , 768 )
insert into funcionalidade values (308,  306, 'Operador'                            , 'S', 'reports/relatorioFechamentoCaixaOperador'                 , 1024  , 768 )
insert into funcionalidade values (309,  768, 'Comiss�o Por Vendedor'               , 'S', 'reports/relatorioComissaoPorVendedor'                     , 1024  , 768 )
insert into funcionalidade values (310,  768, 'Estoque Atual'                       , 'S', 'reports/relatorioEstoqueAtual'                            , 1024  , 768 )
insert into funcionalidade values (311,  768, 'Lucro Bruto'                         , 'S', 'reports/relatorioLucroBruto'                              , 1024  , 768 )
insert into funcionalidade values (312,  768, 'Contas � Pagar'                      , 'S', 'reports/relatorioAnaliticoLancamentosContasAPagar'        , 1024  , 768 )
insert into funcionalidade values (313,  768, 'Contas � Receber'                    , 'S', 'reports/relatorioAnaliticoLancamentosContasAReceber'      , 1024  , 768 )

insert into funcionalidade values (400, NULL, 'Financeiro'                          , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (401,  400, 'Lan�amentos'                         , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (402,  401, 'Conta � Pagar'                       , 'S', 'lancamento/inserirLancamentoContaAPagar'                  , 1024  , 768 )
insert into funcionalidade values (403,  401, 'Conta � Receber'                     , 'S', 'lancamento/inserirLancamentoContaAReceber'                , 1024  , 768 )
insert into funcionalidade values (404,  401, 'Consultar Lan�amentos'               , 'S', 'lancamento/consultarLancamento'                           , 1024  , 768 )
insert into funcionalidade values (405,  400, 'Grupo de Lan�amentos'                , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (406,  405, 'Inserir Grupo de Lan�amento'         , 'S', 'grupoLancamento/inserirGrupoLancamento'                   , 1024  , 768 )
insert into funcionalidade values (407,  405, 'Consultar Grupo de Lan�amento'       , 'S', 'grupoLancamento/consultarGrupoLancamento'                 , 1024  , 768 )
insert into funcionalidade values (408,  400, 'Baixa de Lan�amentos'                , 'S', 'baixaLancamento/consultarBaixaLancamento'                 , 1024  , 768 )
insert into funcionalidade values (409,  400, 'Movimenta��o Banc�ria'               , 'S', NULL                                                       , 1024  , 768 )
insert into funcionalidade values (410,  409, 'Inserir Movimenta��o'                , 'S', 'movimentacaoBancaria/inserirMovimentacaoBancaria'         , 1024  , 768 )
insert into funcionalidade values (411,  409, 'Manter Movimenta��o'                 , 'S', 'movimentacaoBancaria/consultarMovimentacaoBancaria'       , 1024  , 768 )
insert into funcionalidade values (412,  400, 'Conta Corrente'                      , 'S', NULL                                                       , 1024  , 768 )
insert into funcionalidade values (413,  412, 'Inserir Conta Corrente'              , 'S', 'contaCorrente/inserirContaCorrente'                       , 1024  , 768 )
insert into funcionalidade values (414,  412, 'Manter Conta Corrente'               , 'S', 'contaCorrente/consultarContaCorrente'                     , 1024  , 768 )
                                
insert into funcionalidade values (500, NULL, 'Produ��o'                            , 'S', NULL                                                       , 0     , 0   )
insert into funcionalidade values (501,  500, 'Inserir Lote'                        , 'S', 'producao/inserirProducao'                                 , 1024  , 768 )
insert into funcionalidade values (502,  500, 'Manter Lote'                         , 'S', 'producao/consultarProducao'                               , 1024  , 768 )
                                                                                                                                                                                                     
insert into funcionalidade values (999, NULL, 'Logout'                              , 'S', 'logout'                                                   , 0     , 0   )

insert into perfil_funcionalidade
select 1, id from funcionalidade