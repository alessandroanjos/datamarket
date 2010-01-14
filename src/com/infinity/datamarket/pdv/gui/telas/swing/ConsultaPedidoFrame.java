package com.infinity.datamarket.pdv.gui.telas.swing;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.infinity.datamarket.comum.Fachada;
import com.infinity.datamarket.comum.operacao.OperacaoPedido;
import com.infinity.datamarket.comum.produto.Produto;
import com.infinity.datamarket.comum.repositorymanager.PropertyFilter;
import com.infinity.datamarket.comum.util.AppException;
import com.infinity.datamarket.comum.util.StringUtil;

/**
 *
 * @author  wagner.medeiros
 */

public class ConsultaPedidoFrame extends javax.swing.JDialog{

	private Collection c;
	
    public ConsultaPedidoFrame(JFrame owner, Collection c) {
        super(owner, "Consulta Produtos",true);
        setSize(700, 500);
        this.c = c;
        initComponents();
    }

    public void play() {
    	this.setVisible(true);
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    private void initComponents() {
    	pnTable = new JPanel();
        jTable1 = new javax.swing.JTable();
        
        dtm = new InternalDefaultTableModel(new String[][]{},new String[]{"Data","Numero","Cliente", "Valor"});

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setBackground(new Color(226, 252, 252));
        pnTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 150), 5));
        jTable1.setFont(new java.awt.Font("MS Sans Serif", 0, 20));
        jTable1.setRowHeight(30);
        jTable1.setModel(dtm);

        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        getContentPane().add(pnTable);

        scrollTable = new javax.swing.JScrollPane();

        scrollTable.setViewportView(jTable1);
        pnTable.setLayout(new java.awt.GridLayout(1, 0));
        pnTable.setBounds(10, 10, 770, 475);
        pnTable.add(scrollTable);


        jTable1.getColumnModel().getColumn(0).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(0).setResizable(false);

        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(1).setResizable(false);

        jTable1.getColumnModel().getColumn(2).setPreferredWidth(400);
        jTable1.getColumnModel().getColumn(2).setResizable(false);

        jTable1.getColumnModel().getColumn(3).setPreferredWidth(132);        
        jTable1.getColumnModel().getColumn(3).setResizable(false);

        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);



        
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
        	public void keyReleased(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == evt.VK_ENTER){
                	retornoTela = BUTTON_OK;
                	if (jTable1.getSelectedRow() == 0){
                		valor = (OperacaoPedido) dtm.getValueAt(jTable1.getRowCount()-1, 1);
                	}else{
                		valor = (OperacaoPedido) dtm.getValueAt(jTable1.getSelectedRow()-1, 1);
                	}
                    fechar();
                }else if (evt.getKeyCode() == evt.VK_ESCAPE){
                	retornoTela = BUTTON_CANCEL;
                	valor = null;
                    fechar();
                }

            }
       });
        
      	
		removeTodos();
		adicionar(c);
    	
                
		jTable1.requestFocus();
        

        pack();
    }

    private void fechar(){
    	this.dispose();
    }

    private void removeTodos(){
    	int linhas = jTable1.getRowCount();
    	for (int i = 0; i < linhas ;i++){
    		try{
    			dtm.removeRow(0);
    		}catch(ArrayIndexOutOfBoundsException e){

    		}
    	}
    }

    private void adicionar(Collection c){
    	Iterator i = c.iterator();
    	for (int n = 0 ;i.hasNext(); n++){
    		OperacaoPedido pedido = (OperacaoPedido) i.next();

    		DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    		String data = f.format(pedido.getData());
    		String nomeCliente = "Sem Cliente";
    		if (pedido !=null && pedido.getCliente()  !=null && pedido.getCliente().getNomeCliente() != null ){
    			nomeCliente = pedido.getCliente().getNomeCliente();
    		}
    		
    		String valor = "0.0";
    		if (pedido != null && pedido.getValor() != null) {
    			valor = pedido.getValor().setScale(2).toString();
    		}
    		dtm.insertRow(n,new Object[]{data,pedido,nomeCliente ,valor});
    	}
    }



   
    public static void main(String args[]) {
//    	JFrame f = new JFrame();
//    	f.setSize(800, 600);
//    	f.setVisible(true);
//    	ConsultaPedidoFrame c = new ConsultaPedidoFrame(f);
//    	c.setSize(800, 500);
//    	c.play();
//    	if (c.getRetornoTela() == c.BUTTON_OK){
//    		System.out.println(c.getValor());
//    	}
    }

    private int retornoTela = BUTTON_OK;
    private OperacaoPedido valor = null;

    public int getRetornoTela() {
        return retornoTela;
    }


    public OperacaoPedido getValor() {
        return valor;
    }

    public static final int BUTTON_OK	= 0;
	public static final int BUTTON_CANCEL	= 1;


    private javax.swing.JTable jTable1;
    private javax.swing.table.DefaultTableModel dtm;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JPanel pnTable;

    class InternalDefaultTableModel extends DefaultTableModel {
    	public InternalDefaultTableModel(Object[][] data, Object[] title){
    		super(data,title);
    	}
    	public boolean isCellEditable(int row, int col) {
    		return false;
        }
    }

}
