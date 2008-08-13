package com.infinity.datamarket.report;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Uniao {

	private JRBeanCollectionDataSource arrayColPagamentos;
	private JRBeanCollectionDataSource arrayColItensRegistrado;
		public Uniao(ArrayList colPagamentos, ArrayList colItensRegistrados) {

			this.arrayColPagamentos =
				new JRBeanCollectionDataSource(colPagamentos);

			this.arrayColItensRegistrado =
				new JRBeanCollectionDataSource(colItensRegistrados);
			
			this.colPagamentos = colPagamentos;
			this.colItensRegistrados = colItensRegistrados;
		}
		
		ArrayList colPagamentos = new ArrayList();
		ArrayList colItensRegistrados =  new ArrayList();

		public Collection getColPagamentos() {
			return colPagamentos;
		}

		public Collection getColcolItensRegistrados() {
			return colPagamentos;
		}

		public ArrayList getColItensRegistrados() {
			return colItensRegistrados;
		}

		public void setColItensRegistrados(ArrayList colItensRegistrados) {
			this.colItensRegistrados = colItensRegistrados;
		}

		public void setColPagamentos(ArrayList colPagamentos) {
			this.colPagamentos = colPagamentos;
		}

		public JRBeanCollectionDataSource getArrayColItensRegistrado() {
			return arrayColItensRegistrado;
		}

		public void setArrayColItensRegistrado(
				JRBeanCollectionDataSource arrayColItensRegistrado) {
			this.arrayColItensRegistrado = arrayColItensRegistrado;
		}

		public JRBeanCollectionDataSource getArrayColPagamentos() {
			return arrayColPagamentos;
		}

		public void setArrayColPagamentos(JRBeanCollectionDataSource arrayColPagamentos) {
			this.arrayColPagamentos = arrayColPagamentos;
		}

}
