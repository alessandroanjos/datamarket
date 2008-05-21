package com.infinity.datamarket.enterprise.gui.clientepagamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.clientepagamento.ClientePagamento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class ClientePagamentoBackBean extends BackBean {
	
	public ClientePagamentoBackBean(){
	}
	
	String id;
	Cliente cliente;
	BigDecimal valorPagamento;
	Date dataPagamento;
	FormaRecebimento formaRecebimento;
	
	Date dataInicial;
	Date dataFinal;
		
	Collection clientesPagamentos;
	SelectItem[] clientes;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	private List<Cliente> carregarClientes() {
		
		List<Cliente> clientes = null;
		try {
			clientes = (ArrayList<Cliente>)getFachada().consultarTodosClientes();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return clientes;
	}

	public SelectItem[] getClientes() {
		SelectItem[] arrayClientes = null;
		try {
			List<Cliente> clientes = carregarClientes();
			arrayClientes = new SelectItem[clientes.size()];
			int i = 0;
			for(Cliente clienteTmp : clientes){
				SelectItem item = new SelectItem(clienteTmp.getId().toString(), clienteTmp.getTipoPessoa() == Cliente.PESSOA_FISICA ? clienteTmp.getNomeCliente() : clienteTmp.getRazaoSocial());
				arrayClientes[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayClientes;

	}

	public void setClientes(SelectItem[] clientes) {
		this.clientes = clientes;
	}
	
	public FormaRecebimento getFormaRecebimento() {
		return formaRecebimento;
	}

	public void setFormaRecebimento(FormaRecebimento formaRecebimento) {
		this.formaRecebimento = formaRecebimento;
	}

	public Collection getClientesPagamentos() {
		return clientesPagamentos;
	}

	public void setClientesPagamentos(Collection clientesPagamentos) {
		this.clientesPagamentos = clientesPagamentos;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getValorPagamento() {
		return valorPagamento;
	}

	public void setValorPagamento(BigDecimal valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

	public String resetBB(){		
		this.setId(null);		
		this.setValorPagamento(null);
		this.setDataPagamento(null);
		this.setFormaRecebimento(null);
		
		return "mesma";
	}
	
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();            
			String param = (String)  params.get("id");
			if (param != null && !"".equals(param)){
				setId(param);
			}
			if (getId() != null && !"".equals(getId())){
				ClientePagamento clientePagamento = getFachada().consultarClientePagamentoPorPK(new Long(getId()));
				this.setId(clientePagamento.getId().toString());
				this.setCliente(clientePagamento.getCliente());
				this.setValorPagamento(clientePagamento.getValorPagamento());
				this.setDataPagamento(clientePagamento.getDataPagamento());
				this.setFormaRecebimento(clientePagamento.getFormaRecebimento());
				
				return "proxima";
			}else if (this.getCliente() != null
					|| (this.getDataInicial() != null && this.getDataFinal() != null)){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(ClientePagamento.class);
				
				filter.addProperty("cliente", this.getCliente());
				
				if(this.getDataInicial().after(this.getDataFinal())){
					throw new Exception("A Data Final deve ser maior que a Data Inicial.");
				}
				
				filter.addPropertyInterval("dataPagamento", this.getDataInicial(), IntervalObject.MAIOR_IGUAL);
				filter.addPropertyInterval("dataPagamento", this.getDataFinal(), IntervalObject.MENOR_IGUAL);
				
				Collection col = getFachada().consultarClientePagamento(filter);
				if (col == null || col.size() == 0){
					this.setClientesPagamentos(col);
					FacesContext ctx = FacesContext.getCurrentInstance();
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Nenhum Registro Encontrado", "");
					ctx.addMessage(null, msg);					
				}else if (col != null){
					if(col.size() == 1){
						ClientePagamento clientePagamento = (ClientePagamento)col.iterator().next();
						this.setId(clientePagamento.getId().toString());
						this.setCliente(clientePagamento.getCliente());
						this.setValorPagamento(clientePagamento.getValorPagamento());
						this.setDataPagamento(clientePagamento.getDataPagamento());
						this.setFormaRecebimento(clientePagamento.getFormaRecebimento());
						return "proxima";
					}else{
						this.setClientesPagamentos(col);
					}
				}
			}else{
				this.setClientesPagamentos(getFachada().consultarTodosClientesPagamentos());
			}
		}catch(ObjectNotFoundException e){
			this.setClientes(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);			
		}catch(Exception e){
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		resetBB();
		return "mesma";
	}

	public String inserir(){
		try {
			ClientePagamento clientePagamento = preencheClientePagamento();
			
			getFachada().inserirClientePagamento(clientePagamento);
			
			// devolve ao limite disponível de compras o valor pago.
			Cliente cli = getFachada().consultarClientePorPK(clientePagamento.getCliente().getId());
			cli.setValorLimiteDisponivel(cli.getValorLimiteDisponivel().add(clientePagamento.getValorPagamento()));
			getFachada().alterarCliente(cli);
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
		} catch (ObjectExistentException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Pagamento já Existente!", "");
			ctx.addMessage(null, msg);
		} catch (Exception e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		resetBB();
		return "mesma";
	}
	
	public String alterar(){
		try {		
			ClientePagamento clientePagamento = preencheClientePagamento();			
								
			getFachada().alterarClientePagamento(clientePagamento);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
			this.setClientes(null);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return "mesma";
	}
	
	public String excluir(){
		try {
			ClientePagamento clientePagamento = new ClientePagamento();
			
			clientePagamento.setId(new Long(this.getId()));
			
			getFachada().excluirClientePagamento(clientePagamento);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Operação Realizada com Sucesso!", "");
			ctx.addMessage(null, msg);
			resetBB();
			this.setClientes(null);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		resetBB();
		return "mesma";
	}
	
	public ClientePagamento preencheClientePagamento(){
		ClientePagamento clientePagamento = new ClientePagamento();
		
		clientePagamento.setId(new Long(this.getId()));
		clientePagamento.setCliente(this.getCliente());
		clientePagamento.setValorPagamento(this.getValorPagamento());
		clientePagamento.setDataPagamento(this.getDataPagamento());
		clientePagamento.setFormaRecebimento(this.getFormaRecebimento());
		
		return clientePagamento;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
}