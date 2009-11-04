package com.infinity.datamarket.comum.boleto;

import java.io.Serializable;

public class PagamentoBoletoPK implements Serializable{

	private static final long serialVersionUID = -3258009781430050304L;
	
	private Long id;
	private Long idArquivoProcessado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public PagamentoBoletoPK(){
		
	}
	
	public PagamentoBoletoPK(Long id, Long idArquivoProcessado){
		setId(id);
		setIdArquivoProcessado(idArquivoProcessado);
	}
	
	@Override
	public int hashCode() {
		StringBuffer sb = new StringBuffer();
		sb.append((id + "").toString());
		sb.append((idArquivoProcessado + "").toString());
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
		final PagamentoBoletoPK other = (PagamentoBoletoPK) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idArquivoProcessado == null) {
			if (other.idArquivoProcessado != null)
				return false;
		} else if (!idArquivoProcessado.equals(other.idArquivoProcessado))
			return false;
		return true;
	}
	public Long getIdArquivoProcessado() {
		return idArquivoProcessado;
	}
	public void setIdArquivoProcessado(Long idArquivoProcessado) {
		this.idArquivoProcessado = idArquivoProcessado;
	}


}
