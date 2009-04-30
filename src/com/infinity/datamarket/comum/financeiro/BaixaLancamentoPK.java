package com.infinity.datamarket.comum.financeiro;

import java.io.Serializable;

public class BaixaLancamentoPK implements Serializable{

	private static final long serialVersionUID = -3258009781430050304L;
	
	private Long id;
	private Long idLancamento;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public BaixaLancamentoPK(){
		
	}
	
	public BaixaLancamentoPK(Long id, Long idLancamento){
		setId(id);
		setIdLancamento(idLancamento);
	}
	
	@Override
	public int hashCode() {
		StringBuffer sb = new StringBuffer();
		sb.append(id.toString());
		sb.append(idLancamento.toString());
		return sb.toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BaixaLancamentoPK other = (BaixaLancamentoPK) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idLancamento == null) {
			if (other.idLancamento != null)
				return false;
		} else if (!idLancamento.equals(other.idLancamento))
			return false;
		return true;
	}
	public Long getIdLancamento() {
		return idLancamento;
	}
	public void setIdLancamento(Long idLancamento) {
		this.idLancamento = idLancamento;
	}	
}