select 'insert into funcionalidade (id, id_func_superior, descricao, situacao, url, largura, altura) values(' + 
       convert(varchar, id) + ', ' + convert(varchar,id_func_superior) + ', ''' + descricao + ''', ''' + situacao + ''', ''' + url + ''', ' + 
       convert(varchar,largura) + ', ' + convert(varchar,altura) + ')' as statement
  from funcionalidade
where id_func_superior is not null
and url is not null
--and id >= 74
and id_func_superior = (select id from funcionalidade where descricao = 'Relatórios')
order by id_func_superior, id