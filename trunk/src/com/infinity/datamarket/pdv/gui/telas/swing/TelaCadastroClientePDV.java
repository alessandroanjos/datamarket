package com.infinity.datamarket.pdv.gui.telas.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

import com.infinity.datamarket.pdv.gerenciadorperifericos.components.LimitedTextField;

public class TelaCadastroClientePDV extends javax.swing.JFrame {

	

	    
    /** Creates new form TelaCadastroCliente */
    public TelaCadastroClientePDV() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">                          
    private void initComponents() {
    	
    	try {
			mascaraCPF = new MaskFormatter("###.###.###-##");
			mascaraCNPJ = new MaskFormatter("##.###.###/####-##");
			
			mascaraCNPJ.setValidCharacters("0123456789");
			mascaraCPF.setValidCharacters("0123456789");
						
			mascaraTelefone = new MaskFormatter("(##)####-####");
			mascaraTelefone.setValidCharacters("0123456789");
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new JFormattedTextField(mascaraCPF);
        
        kl = new KeyListener(){
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == e.VK_ENTER){
					
				}else if(e.getKeyCode() == e.VK_ESCAPE){
					fechar();
				}
			}
			public void keyReleased(KeyEvent e) {
				
			}
			public void keyTyped(KeyEvent e) {
				
			}	
        	
        };
        
        
        
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new JFormattedTextField(mascaraTelefone);
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new JFormattedTextField(mascaraTelefone);
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new LimitedTextField(20);
        jLabel5 = new javax.swing.JLabel();
        jTextField6 = new LimitedTextField(20);
        jLabel6 = new javax.swing.JLabel();
        jTextField7 = new LimitedTextField(50);
        jTextField7.setMascara(jTextField2.ALFANUMERICO);
        jLabel7 = new javax.swing.JLabel();
        jTextField8 = new LimitedTextField(20);
        jTextField8.setMascara(jTextField2.ALFANUMERICO);
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextField9 = new LimitedTextField(50);
        jTextField9.setMascara(jTextField2.ALFANUMERICO);
        jComboBox1 = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jTextField10 = new LimitedTextField(50);
        jTextField10.setMascara(jTextField2.ALFANUMERICO);
        jLabel12 = new javax.swing.JLabel();
        jTextField11 = new LimitedTextField(8);
        jTextField12 = new LimitedTextField(6);
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField13 = new LimitedTextField(50);
        jTextField13.setMascara(jTextField2.ALFANUMERICO);
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTextField2 = new LimitedTextField(50);
        jTextField2.setMascara(jTextField2.ALFANUMERICO);
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        
        jLabel1.setText("jLabel1");
        jLabel8.setText("jLabel8");
        jLabel15.setText("jLabel15");

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        
        setResizable(false);
        jPanel2.setLayout(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("CPF/CNPJ"));
        jPanel2.add(jTextField1);
        jTextField1.setBounds(180, 20, 150, 20);

        jRadioButton1.setText("F\u00edsica");
        jRadioButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel2.add(jRadioButton1);
        jRadioButton1.setBounds(20, 20, 60, 15);

        jRadioButton2.setText("Jur\u00eddica");
        jRadioButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel2.add(jRadioButton2);
        jRadioButton2.setBounds(80, 20, 65, 15);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(10, 0, 340, 50);

        jPanel3.setLayout(null);

        jLabel2.setText("Telefone");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(10, 110, 50, 14);

        jPanel3.add(jTextField3);
        jTextField3.setBounds(10, 130, 85, 20);

        jLabel3.setText("Celular");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(105, 110, 45, 14);

        jPanel3.add(jTextField4);
        jTextField4.setBounds(105, 130, 85, 20);

        jLabel4.setText("Inscri\u00e7\u00e3o Estadual");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(10, 60, 120, 15);

        jPanel3.add(jTextField5);
        jTextField5.setBounds(10, 80, 155, 20);

        jLabel5.setText("Inscri\u00e7\u00e3o Munincipal");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(175, 60, 150, 15);

        jPanel3.add(jTextField6);
        jTextField6.setBounds(175, 80, 155, 20);

        jLabel6.setText("Nome Fantasia");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(10, 10, 90, 14);

        jPanel3.add(jTextField7);
        jTextField7.setBounds(10, 30, 320, 20);

        jLabel7.setText("Contato");
        jPanel3.add(jLabel7);
        jLabel7.setBounds(200, 110, 70, 14);

        jPanel3.add(jTextField8);
        jTextField8.setBounds(200, 130, 130, 20);

        jLabel16.setText("Refer\u00eancia Banc\u00e1ria");
        jPanel3.add(jLabel16);
        jLabel16.setBounds(10, 160, 120, 14);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(10, 180, 320, 50);

        jTabbedPane1.addTab("Dados Gerais", jPanel3);

        jPanel4.setLayout(null);

        jLabel9.setText("Logradouro");
        jPanel4.add(jLabel9);
        jLabel9.setBounds(10, 60, 70, 14);

        jPanel4.add(jTextField9);
        jTextField9.setBounds(10, 80, 310, 20);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS","MT","PA","PB","PE","PI","PR","RJ","RN","RO","RR","RS","SC","SE","SP","TO" }));
        jPanel4.add(jComboBox1);
        jComboBox1.setBounds(10, 130, 50, 20);

        jLabel11.setText("Cidade");
        jPanel4.add(jLabel11);
        jLabel11.setBounds(70, 110, 50, 14);

        jPanel4.add(jTextField10);
        jTextField10.setBounds(70, 130, 250, 20);

        jLabel12.setText("Cep");
        jPanel4.add(jLabel12);
        jLabel12.setBounds(10, 10, 25, 14);

        jPanel4.add(jTextField11);
        jTextField11.setBounds(10, 30, 70, 20);

        jPanel4.add(jTextField12);
        jTextField12.setBounds(10, 180, 50, 20);

        jLabel13.setText("N\u00famero");
        jPanel4.add(jLabel13);
        jLabel13.setBounds(10, 160, 80, 14);

        jLabel14.setText("Complemento");
        jPanel4.add(jLabel14);
        jLabel14.setBounds(70, 160, 90, 14);

        jPanel4.add(jTextField13);
        jTextField13.setBounds(70, 180, 250, 20);

        jLabel10.setText("UF");
        jPanel4.add(jLabel10);
        jLabel10.setBounds(10, 110, 40, 14);

        jTabbedPane1.addTab("Endere\u00e7o", jPanel4);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 110, 340, 270);
        jTabbedPane1.getAccessibleContext().setAccessibleName("tab2");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nome"));
        
        jPanel1.setLayout(null);
        jPanel1.add(jTextField2);
        jTextField2.setBounds(10, 20, 320, 20);
        
        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 50, 340, 50);

        jButton1.setText("OK");
        getContentPane().add(jButton1);
        jButton1.setBounds(260, 390, 90, 23);

        jButton2.setText("Cancelar");
        getContentPane().add(jButton2);
        jButton2.setBounds(160, 390, 90, 23);

        jRadioButton1.setSelected(true);
        
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        
        jRadioButton1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickRadio();
				
			}

			
        	
        });
        
        jRadioButton2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickRadio();
				
			}

			
        	
        });
        
        jButton2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				fechar();
				
			}

			
        	
        });
        
        jTextField1.addKeyListener(kl);
        jTextField2.addKeyListener(kl);
        jTextField3.addKeyListener(kl);
        jTextField4.addKeyListener(kl);
        jTextField5.addKeyListener(kl);
        jTextField6.addKeyListener(kl);
        jTextField7.addKeyListener(kl);
        jTextField8.addKeyListener(kl);
        jTextField9.addKeyListener(kl);
        jTextField10.addKeyListener(kl);
        jTextField11.addKeyListener(kl);
        jTextField12.addKeyListener(kl);
        jTextField13.addKeyListener(kl);
        jTextArea1.addKeyListener(kl);
        jRadioButton1.addKeyListener(kl);
        jRadioButton2.addKeyListener(kl);
        jTabbedPane1.addKeyListener(kl);
        

        
    }// </editor-fold>    
    
    private void fechar(){
    	this.setVisible(false);
    }
    
    private void clickRadio(){
    	jPanel2.remove(jTextField1);
    	if (jRadioButton1.isSelected()){
    		jTextField1 = new JFormattedTextField(mascaraCPF);
    	}else{
    		jTextField1 = new JFormattedTextField(mascaraCNPJ);    		
    	}
    	jPanel2.add(jTextField1);
    	jTextField1.setBounds(180, 20, 150, 20);
    	jPanel2.repaint();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaCadastroClientePDV tela =  new TelaCadastroClientePDV();
                tela.setSize(370, 460);
                tela.setVisible(true);
            }
        });
    }
    
    private KeyListener kl;    
    // Variables declaration - do not modify                     
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private JFormattedTextField jTextField1;
    private LimitedTextField jTextField10;
    private LimitedTextField jTextField11;
    private LimitedTextField jTextField12;
    private LimitedTextField jTextField13;
    private LimitedTextField jTextField2;
    private JFormattedTextField jTextField3;
    private JFormattedTextField jTextField4;
    private LimitedTextField jTextField5;
    private LimitedTextField jTextField6;
    private LimitedTextField jTextField7;
    private LimitedTextField jTextField8;
    private LimitedTextField jTextField9;

	
	private MaskFormatter mascaraCPF;
	private MaskFormatter mascaraCNPJ;
	private MaskFormatter mascaraTelefone;
	
}
