import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class GeradorXMMaquinaEstado {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		main(" imposto ");
	}	
	public static Map main(String tabela) {
		Map map = new HashMap();
		try {			
			FileOutputStream out = new FileOutputStream(new File("D:\\workspace\\Datamarket\\datamarket\\conf\\fluxoPDV.xml")); 
			out.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?> ".getBytes());
			out.write("\n".getBytes());
			out.write("<maquinaEstado>".getBytes());
			out.write("\n".getBytes());
			
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			
			Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/ENTERPRISE","sa","001100");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from  MICRO_OPERACAO ");
			out.write("  <listaMicroperacao>".getBytes());
			out.write("\n".getBytes());
			while (rs.next()) {
				Class classe = Class.forName(rs.getString("CLASSE"));
				out.write(("    <operacao id=\"" +  classe.getSimpleName() +  "\" classe=\"" + classe.getName() + "\"/>").getBytes());
				out.write("\n".getBytes());
			}
			out.write("  </listaMicroperacao>".getBytes());
			out.write("\n".getBytes());
			rs.close();
			st.close();

			st = conn.createStatement();
			rs = st.executeQuery("select * from  ESTADO ");
			out.write("  <listaEstado>".getBytes());
			out.write("\n".getBytes());
			while (rs.next()) {
				String ID = rs.getString("ID");
				String DESCRICAO = rs.getString("DESCRICAO");
				String INPUT_TYPE = rs.getString("INPUT_TYPE");
				String INPUT_SIZE = rs.getString("INPUT_SIZE");
				out.write(("    <estado codigo=\"" +
						"ESTADO" +ID+
						"\" descricao=\"" +
						DESCRICAO +
						"\" inputType=\"" +
						INPUT_TYPE +
						"\" inputSize=\"" +
						INPUT_SIZE +
						"\"/>").getBytes());
				out.write("\n".getBytes());
			}
			out.write("  </listaEstado>".getBytes());
			out.write("\n".getBytes());
			rs.close();
			st.close();

			st = conn.createStatement();
			rs = st.executeQuery("select * from  TECLA ");
			out.write("  <listaTecla>".getBytes());
			out.write("\n".getBytes());
			while (rs.next()) {
				String ID = rs.getString("ID");
				String CODIGO_ASCI = rs.getString("CODIGO_ASCI");
				int codigoAsci = Integer.parseInt(CODIGO_ASCI);
				String descrica = ""+((char)codigoAsci);
				if (codigoAsci == 10) {
					descrica = "ENTER";
				} else if (codigoAsci == 32) {
					descrica = "ESPACO";
				} else if (codigoAsci == 27) {
					descrica = "ESC";
				}
				out.write(("    <tecla codigo=\"TECLA" +
						ID +
						"\" descricao=\"" +
						descrica +
						"\" codigoASCI=\"" +
						CODIGO_ASCI +
						"\" />").getBytes());
				out.write("\n".getBytes());
			}
			out.write("  </listaTecla>".getBytes());
			out.write("\n".getBytes());
			rs.close();
			st.close();


			st = conn.createStatement();
			rs = st.executeQuery("select * from  MACRO_OPERACAO ");
			out.write("  <listaMacroOperacao>".getBytes());
			out.write("\n".getBytes());
			while (rs.next()) {
				String ID = rs.getString("ID");
				String DESCRICAO = rs.getString("DESCRICAO");
				String ID_ESTADO_ATUAL = rs.getString("ID_ESTADO_ATUAL");
				String ID_PROXIMO_ESTADO = rs.getString("ID_PROXIMO_ESTADO");
				String ID_TECLA = rs.getString("ID_TECLA");
				String ID_MICRO_OPERACAO_ASSOCIADA = rs.getString("ID_MICRO_OPERACAO_ASSOCIADA");
				out.write(("    <macroOperacao estadoAtual=\"ESTADO" +
						ID_ESTADO_ATUAL +
						"\" proximoEstado=\"ESTADO" +
						ID_PROXIMO_ESTADO +
						"\" tecla=\"TECLA" +
						ID_TECLA +
						"\" codigoMicroOperacaoAssociada=\"subFluxo" +
						ID_MICRO_OPERACAO_ASSOCIADA +
						"\" descricao=\"" +
						DESCRICAO +
						"\">").getBytes());
				out.write("\n".getBytes());

				consultarMicroOperacaoAssociada(out, conn, ID_MICRO_OPERACAO_ASSOCIADA, new ArrayList());
				
				out.write(("    </macroOperacao>").getBytes());
				out.write("\n".getBytes());
				
			}
			out.write("  </listaMacroOperacao>".getBytes());
			out.write("\n".getBytes());
			rs.close();
			st.close();

			conn.close();
			out.write("\n".getBytes());
			out.write("</maquinaEstado>".getBytes());
			out.write("\n".getBytes());
			out.close();
			System.out.println("foi");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("nao foi");
		}
		return map;
	}
	private static void consultarMicroOperacaoAssociada(FileOutputStream out, Connection conn, String ID_MICRO_OPERACAO_ASSOCIADA, Collection collMicroOperacaoAssociada) throws SQLException, ClassNotFoundException, IOException {
		if (collMicroOperacaoAssociada.contains(ID_MICRO_OPERACAO_ASSOCIADA)) {
			return;
		}
		collMicroOperacaoAssociada.add(ID_MICRO_OPERACAO_ASSOCIADA);
		Statement stOp = conn.createStatement();
		ResultSet rsOp = stOp.executeQuery(" select classe from MICRO_OPERACAO_ASSOCIADA moa, MICRO_OPERACAO  mo where mo.id = moa.id_micro_operacao and moa.id = " + ID_MICRO_OPERACAO_ASSOCIADA);
		if (rsOp.next()) {
			Class classe = Class.forName(rsOp.getString("CLASSE"));
			out.write(("      <microOperacaoAssociada codigoMicroOperacaoAssociada=\"subFluxo" +
				ID_MICRO_OPERACAO_ASSOCIADA + "\" operacao=\"" +
					classe.getSimpleName() +
					"\">").getBytes());
			out.write("\n".getBytes());
			
		}

		Statement stM = conn.createStatement();
		ResultSet rsM = stM.executeQuery(	" select moa.id, mo.classe, smo.saida, smo.id_micro_operacao_associada from MICRO_OPERACAO_ASSOCIADA moa, MICRO_OPERACAO  mo , SAIDA_MICRO_OPERACAO_associada smoa, SAIDA_MICRO_OPERACAO smo " +
				 							" where moa.id = " +
				 							ID_MICRO_OPERACAO_ASSOCIADA +
				 							" and mo.id = moa.id_micro_operacao and " +
											" moa.id = smoa.id_micro_operacao_associada and smoa.id_saida_micro_operacao = smo.id "
				 						);
		Collection collMicroOpeeracaoAssociada = new ArrayList();
		while (rsM.next()) {
			String classe = rsM.getString("classe");
			String saida = rsM.getString("saida");
			String microOperacaoAssociada = rsM.getString("id_micro_operacao_associada");
			out.write(("      	<saidaMicroOperacaoAssociada tipoSaida=\"" +
					saida +
					"\" codigoMicroOperacaoAssociadaDestino=\"" +
					"subFluxo" + microOperacaoAssociada +
					"\" />").getBytes());
			out.write("\n".getBytes());
			collMicroOpeeracaoAssociada.add(microOperacaoAssociada);
		}
		out.write(("      </microOperacaoAssociada>").getBytes());
		out.write("\n".getBytes());
		stM.close();
		rsM.close();
		Iterator it = collMicroOpeeracaoAssociada.iterator();
		while(it.hasNext()) {
			String codigoMicroOpeeracaoAssociada = it.next().toString();
			consultarMicroOperacaoAssociada(out, conn, codigoMicroOpeeracaoAssociada, collMicroOperacaoAssociada);
		}
	}
}
