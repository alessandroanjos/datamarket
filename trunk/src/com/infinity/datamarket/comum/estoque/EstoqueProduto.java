package com.infinity.datamarket.comum.estoque;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;

public class EstoqueProduto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5553641702848574308L;

	private EstoqueProdutoPK pk;
	
	private Collection lotes;

	public EstoqueProdutoPK getPk() {
		return pk;
	}
	
	private static final Date DATA_NULL;
	
	static{
		Calendar cal = new GregorianCalendar();
		cal.set(cal.DAY_OF_MONTH, 1);
		cal.set(cal.MONTH, 0);
		cal.set(cal.YEAR, 1800);
		cal.set(cal.HOUR_OF_DAY, 0);
		cal.set(cal.MINUTE, 0);
		cal.set(cal.SECOND, 0);		
		cal.set(cal.MILLISECOND, 0);
		DATA_NULL = cal.getTime();
	}

	public void setPk(EstoqueProdutoPK pk) {
		this.pk = pk;
	}

	public BigDecimal getQuantidade() {
		if (lotes != null && lotes.size() > 0){
			Iterator i = lotes.iterator();
			BigDecimal qtd = BigDecimal.ZERO;
			while(i.hasNext()){
				Lote lote = (Lote) i.next();
				qtd = qtd.add(lote.getQuantidade());
			}
			return qtd;
		}else{
			return BigDecimal.ZERO;
		}		
	}
	
	public void adicionarQuantidade(BigDecimal qtd, Date vencimento){
		if (vencimento == null){
			vencimento = DATA_NULL;
		}else{
			Calendar cal = new GregorianCalendar();
			cal.setTime(vencimento);
			cal.set(cal.HOUR, 0);
			cal.set(cal.MINUTE, 0);
			cal.set(cal.SECOND, 0);
			cal.set(cal.MILLISECOND, 0);
			vencimento = cal.getTime();
		}
		if (lotes != null){
			Iterator i = lotes.iterator();			
			Boolean achou = false;
			while(i.hasNext()){
				Lote lote = (Lote) i.next();
				if (lote.getPk().getVencimento().compareTo(vencimento)==0){
					lote.setQuantidade(lote.getQuantidade().add(qtd));
					achou = true;
				}
			}
			if (!achou){
				LotePK pk = new LotePK(this.pk.getEstoque(),this.pk.getProduto(),vencimento);
				Lote lote = new Lote();
				lote.setPk(pk);				
				lote.setQuantidade(qtd);				
				lotes.add(lote);
			}
			
		}else{
			lotes = new HashSet();
			LotePK pk = new LotePK(this.pk.getEstoque(),this.pk.getProduto(),vencimento);
			Lote lote = new Lote();
			lote.setPk(pk);				
			lote.setQuantidade(qtd);			
			lotes.add(lote);
		}
	}
	
	public void subtrairQuantidade(BigDecimal qtd, Date vencimento){		
		if (vencimento == null){
			vencimento = DATA_NULL;
		}else{
			Calendar cal = new GregorianCalendar();
			cal.setTime(vencimento);
			cal.set(cal.HOUR, 0);
			cal.set(cal.MINUTE, 0);
			cal.set(cal.SECOND, 0);
			cal.set(cal.MILLISECOND, 0);
			vencimento = cal.getTime();
		}		
		if (lotes != null){
			Iterator i = lotes.iterator();			
			Boolean achou = false;
			while(i.hasNext()){
				Lote lote = (Lote) i.next();
				if (lote.getPk().getVencimento().compareTo(vencimento)==0){
					lote.setQuantidade(lote.getQuantidade().subtract(qtd));
					achou = true;
				}
			}
			if (!achou){
				LotePK pk = new LotePK(this.pk.getEstoque(),this.pk.getProduto(),vencimento);
				Lote lote = new Lote();
				lote.setPk(pk);				
				lote.setQuantidade(qtd.negate());
				lotes.add(lote);
			}
			
		}else{
			lotes = new HashSet();
			LotePK pk = new LotePK(this.pk.getEstoque(),this.pk.getProduto(),vencimento);
			Lote lote = new Lote();
			lote.setPk(pk);				
			lote.setQuantidade(qtd.negate());
			lotes.add(lote);
		}
	}

	public Collection getLotes() {
		return lotes;
	}

	public void setLotes(Collection lotes) {
		this.lotes = lotes;
	}

	
	
}
