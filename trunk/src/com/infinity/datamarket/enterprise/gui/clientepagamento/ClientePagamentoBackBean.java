package com.infinity.datamarket.enterprise.gui.clientepagamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.infinity.datamarket.comum.cliente.Cliente;
import com.infinity.datamarket.comum.clientepagamento.ClientePagamento;
import com.infinity.datamarket.comum.pagamento.FormaRecebimento;
import com.infinity.datamarket.comum.repositorymanager.ObjectExistentException;
import com.infinity.datamarket.comum.repositorymanager.ObjectNotFoundException;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter.IntervalObject;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.enterprise.gui.util.BackBean;

public class ClientePagamentoBackBean extends BackBean {
	
	public ClientePagamentoBackBean(){
	}
	
	String id;
	Cliente cliente;
	BigDecimal valorPagamento;
	BigDecimal saldoDevedor;
	Date dataPagamento;
	FormaRecebimento formaRecebimento;
	
	Date dataInicial;
	Date dataFinal;
	String idCliente;
	String idFormaRecebimento;
	String nomeCliente;
	String descricaoFormaRecebimento;
		
	Collection clientesPagamentos;
	SelectItem[] clientes;
	SelectItem[] formas;

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
			arrayClientes = new SelectItem[clientes.size()+1];
			int i = 0;
			arrayClientes[i++] = new SelectItem("0", "");
			for(Cliente clienteTmp : clientes){
				SelectItem item = new SelectItem(clienteTmp.getId().toString(), clienteTmp.getTipoPessoa().equals(Cliente.PESSOA_FISICA) ? clienteTmp.getNomeCliente() : clienteTmp.getRazaoSocial());
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

	public void resetBB(){		
		this.setId(null);		
		this.setValorPagamento(null);
		this.setDataPagamento(null);
		this.setFormaRecebimento(null);		
	}
	
	public String consultar(){
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			Map params = context.getExternalContext().getRequestParameterMap();            
			String param = (String)  params.get("id");
			if (param != null && (!"".equals(param) && !"0".equals(param))){
				setId(param);
			}
			if (getId() != null && (!"".equals(getId()) && !"0".equals(getId()))){
				ClientePagamento clientePagamento = getFachada().consultarClientePagamentoPorPK(new Long(getId()));
				this.setId(clientePagamento.getId().toString());
				this.setCliente(clientePagamento.getCliente());
				this.setValorPagamento(clientePagamento.getValorPagamento());
				this.setDataPagamento(clientePagamento.getDataPagamento());
				this.setFormaRecebimento(clientePagamento.getFormaRecebimento());
				
				this.setSaldoDevedor(this.getCliente().getValorLimiteCompras().subtract(this.getCliente().getValorLimiteDisponivel()));
				this.setSaldoDevedor(this.getSaldoDevedor().setScale(2));
				
				return "proxima";
			}else if ((this.getIdCliente() != null && !this.getIdCliente().equals("0"))
					|| (this.getDataInicial() != null && this.getDataFinal() != null)){
				PropertyFilter filter = new PropertyFilter();
				filter.setTheClass(ClientePagamento.class);
				
				filter.addProperty("cliente", this.getCliente());
				
				if(this.getDataInicial().after(this.getDataFinal())){
					throw new Exception("A Data Final deve ser maior que a Data Inicial.");
				}
				
				filter.addPropertyInterval("dataPagamento", this.getDataInicial(), IntervalObject.MAIOR_IGUAL);
				filter.addPropertyInterval("dataPagamento", this.getDataFinal(), IntervalObject.MENOR_IGUAL);
				
				filter.addOrderByProperty("dataPagamento", PropertyFilter.DESC);
				
				Collection col = getFachada().consultarClientePagamento(filter);
				if (col == null || col.size() == 0){
					setExisteRegistros(false);
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
						
						this.setSaldoDevedor(this.getCliente().getValorLimiteCompras().subtract(this.getCliente().getValorLimiteDisponivel()));
						this.setSaldoDevedor(this.getSaldoDevedor().setScale(2));
						
						return "proxima";
					}else{
						setExisteRegistros(true);
						this.setClientesPagamentos(col);						
					}
				}
			}else{
				Collection c = getFachada().consultarTodosClientesPagamentos();
				if(c != null && c.size() > 0){
					this.setClientesPagamentos(c);
					setExisteRegistros(true);
				}else{
					this.setClientesPagamentos(null);
					setExisteRegistros(false);
				}
								
			}
		}catch(ObjectNotFoundException e){
			setExisteRegistros(false);
			this.setClientes(null);
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Nenhum Registro Encontrado", "");
			ctx.addMessage(null, msg);			
		}catch(Exception e){
			setExisteRegistros(false);
			FacesContext ctx = FacesContext.getCurrentInstance();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
			ctx.addMessage(null, msg);
		}
		resetBB();
		return "mesma";
	}

	public String inserir(){
		try {
			ClientePagamento clientePagamento = preencheClientePagamento(INSERIR);
			
			getFachada().inserirClientePagamento(clientePagamento);
			
			// devolve ao limite disponível de compras o valor pago.
			Cliente cli = getFachada().consultarClientePorPK(clientePagamento.getCliente().getId());
			
			if(cli.getValorLimiteDisponivel() == null){
				cli.setValorLimiteDisponivel(BigDecimal.ZERO);
			}
			cli.setValorLimiteDisponivel(cli.getValorLimiteDisponivel().add(clientePagamento.getValorPagamento()));
			
			if(cli.getValorLimiteDisponivel().compareTo(cli.getValorLimiteCompras()) > 0){
				throw new AppException("Valor ultrapassa o valor da dívida.");
			}
			
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
		} catch (AppException e) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getMessage(), "");
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
			ClientePagamento clientePagamento = preencheClientePagamento(ALTERAR);			
								
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
	
	public ClientePagamento preencheClientePagamento(String acao) throws AppException{
		ClientePagamento clientePagamento = new ClientePagamento();
		if(INSERIR.equals(acao)){
			clientePagamento.setId(getFachada().consultarMaxIdClientePagamento());	
		}else if(ALTERAR.equals(acao)){
			clientePagamento.setId(new Long(this.getId()));
		}
		this.setCliente(getFachada().consultarClientePorPK(new Long(this.getIdCliente())));
		clientePagamento.setCliente(this.getCliente());
		clientePagamento.setValorPagamento(this.getValorPagamento());
		clientePagamento.setDataPagamento(this.getDataPagamento());
		this.setFormaRecebimento(getFachada().consultarFormaRecebimentoPorId(new Long(this.getIdFormaRecebimento())));
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

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdFormaRecebimento() {
		return idFormaRecebimento;
	}

	public void setIdFormaRecebimento(String idFormaRecebimento) {
		this.idFormaRecebimento = idFormaRecebimento;
	}

	public String getDescricaoFormaRecebimento() {
		return descricaoFormaRecebimento;
	}

	public void setDescricaoFormaRecebimento(String descricaoFormaRecebimento) {
		this.descricaoFormaRecebimento = descricaoFormaRecebimento;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	private List<FormaRecebimento> carregarFormas() {
		
		List<FormaRecebimento> formas = null;
		try {
			formas = (ArrayList<FormaRecebimento>)getFachada().consultarTodosFormaRecebimento();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return formas;
	}

	public SelectItem[] getFormas() {
		SelectItem[] arrayFormas = null;
		try {
			List<FormaRecebimento> formas = carregarFormas();
			arrayFormas = new SelectItem[formas.size()];
			int i = 0;
//			arrayFormas[i++] = new SelectItem("0", "");
			for(FormaRecebimento formaTmp : formas){
				SelectItem item = new SelectItem(formaTmp.getId().toString(), formaTmp.getDescricao());
				arrayFormas[i++] = item;
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Erro de Sistema!", "");
			ctx.addMessage(null, msg);
		}
		return arrayFormas;

	}


	public void setFormas(SelectItem[] formas) {
		this.formas = formas;
	}

	public BigDecimal getSaldoDevedor() {
		return saldoDevedor;
	}

	public void setSaldoDevedor(BigDecimal saldoDevedor) {
		this.saldoDevedor = saldoDevedor;
	}
	
	
	public void recuperaDadosCliente(ValueChangeEvent event){
        try {
        	UISelectOne select = (UISelectOne) event.getSource();   
            String valor = String.valueOf(select.getValue());
            if(!valor.equals("0")){
    			Cliente cli = getFachada().consultarClientePorPK(new Long(valor));
    			if(cli != null){
    				this.setCliente(cli);
    			}
            }else{
            	this.setCliente(null);
            }
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String voltarConsulta(){
		resetBB();
		consultar();
		return "voltar";
	}
	public String voltarMenu(){
		resetBB();
		return "voltar";
	}

}