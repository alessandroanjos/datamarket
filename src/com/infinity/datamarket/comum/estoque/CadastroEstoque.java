/**
 * 
 */
package com.infinity.datamarket.comum.estoque;

import java.util.Collection;

import com.infinity.datamarket.comum.repositorymanager.IPropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.Cadastro;
import com.infinity.datamarket.comum.util.Constantes;

/**
 * @author alessandro
 *
 */
public class CadastroEstoque extends Cadastro {
	private static CadastroEstoque instancia;
	private static Class CLASSE = Estoque.class;
	private CadastroEstoque(){}
	public static CadastroEstoque getInstancia(){
		if (instancia == null){
			instancia = new CadastroEstoque();
		}
		return instancia;
	}

	public Estoque consultarPorId(EstoquePK id) throws AppException{
		return (Estoque) getRepositorio().findById(CLASSE, id);
	}

	public Collection consultar(IPropertyFilter filter) throws AppException{
		return getRepositorio().filter(filter, false);
	}
	public Collection consultarTodos() throws AppException{
		return getRepositorio().findAll(CLASSE);
	}
	public void inserir(Estoque componente) throws AppException{
		validaEstoque(componente);
		getRepositorio().insert(componente);
		
	}
	
	public void alterar(Estoque componente) throws AppException{
		validaEstoque(componente);
		getRepositorio().update(componente);
	}
	
	public void excluir(Estoque componente) throws AppException{
		getRepositorio().remove(componente);
	}
	
	private void validaEstoque(Estoque est) throws AppException{
		if (est.estoqueVenda != null && est.estoqueVenda.equals(Constantes.SIM)){
			PropertyFilter filter = new PropertyFilter();
			filter.setTheClass(Estoque.class);
			filter.addProperty("pk.loja", est.getPk().getLoja());
			filter.addProperty("estoqueVenda", Constantes.SIM);
			
			Collection col = (Collection) getRepositorio().filter(filter, true);
			System.out.println("######### "+col.size());
			if (col != null && col.size() > 0){
				throw new AppException("J� existe estoque associada as vendas desta loja");
			}
		}
	}

}
