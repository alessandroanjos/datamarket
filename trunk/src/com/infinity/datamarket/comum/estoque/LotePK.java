package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;
import java.util.Date;

import com.infinity.datamarket.comum.produto.Produto;


public class LotePK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8056447588685486560L;
	
	private Estoque estoque;
	
	private Produto produto;
	

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	private Date vencimento;
	
	public LotePK(Estoque estoque, Produto produto, Date vencimento){
		this.setEstoque(estoque);
		this.setProduto(produto);		
		this.vencimento = vencimento;
	}
	
	public LotePK(){
		
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((estoque == null) ? 0 : estoque.hashCode());
		result = PRIME * result + ((produto == null) ? 0 : produto.hashCode());
		result = PRIME * result + ((vencimento == null) ? 0 : vencimento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LotePK other = (LotePK) obj;
		if (estoque == null) {
			if (other.estoque != null)
				return false;
		} else if (!estoque.equals(other.estoque))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (vencimento == null) {
			if (other.vencimento != null)
				return false;
		} else if (vencimento.compareTo(other.vencimento)!= 0)
			return false;
		return true;
	}
	
	

		
}
