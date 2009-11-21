
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.KeyValue;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
import org.hibernate.type.ComponentType;

import com.infinity.datamarket.comum.repositorymanager.RepositoryManagerHibernateUtil;

public class TabelaEntidade {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			File arquivo = new File("C:\\index.html");
			//deletando o arquivo			
			if (arquivo.exists()) arquivo.delete();
			//criando o novo arquivo
			arquivo.createNewFile();
			FileOutputStream out = new FileOutputStream(arquivo);
			try {
				
				// processar cada linha para e colocando no arquivo fisico
	
				out.write('\n');out.write(("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">").getBytes());
				out.write('\n');out.write(("<html xmlns=\"http://www.w3.org/1999/xhtml\">").getBytes());
				out.write('\n');out.write(("<head>").getBytes());
				out.write('\n');out.write(("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />").getBytes());
				out.write('\n');out.write(("<title>Untitled Document</title></head>").getBytes());
	
				out.write('\n');out.write(("<body>").getBytes());
				
				Iterator it = RepositoryManagerHibernateUtil.getInstancia().getConfiguration().getClassMappings();
				while(it.hasNext()){
					Object obj = it.next();
					
					String nomeTabela = "";
					Class classe = null;
					if (obj instanceof RootClass) { 
						RootClass rootClass = (RootClass) obj;
						classe = rootClass.getMappedClass();
						nomeTabela = rootClass.getTable().getName();

					} else if (obj instanceof org.hibernate.mapping.JoinedSubclass) {
						org.hibernate.mapping.JoinedSubclass rootClass = (org.hibernate.mapping.JoinedSubclass) obj;
						classe = rootClass.getMappedClass();
						nomeTabela = rootClass.getTable().getName();
					}
					
					out.write('\n');out.write(("<table width=\"600\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">").getBytes());
					out.write('\n');out.write(("  <tr>").getBytes());
					out.write('\n');out.write((("    <td colspan=\"2\" width=\"300\" valign=\"top\"><p align=\"center\"><strong>" + classe) +"</strong></td>").getBytes());
					out.write('\n');out.write(("    <td colspan=\"4\" width=\"300\" valign=\"top\"><p align=\"center\"><strong>" + nomeTabela + "</strong></td>").getBytes());
					out.write('\n');out.write(("  </tr>").getBytes());

					out.write('\n');out.write(("  <tr>").getBytes());
					out.write('\n');out.write(("  <td valign=\"top\">Atributo</td>").getBytes());
					out.write('\n');out.write(("  <td valign=\"top\">Tipo</td>").getBytes());
					out.write('\n');out.write(("  <td valign=\"top\">Coluna</td>").getBytes());
					out.write('\n');out.write(("  <td valign=\"top\">Tipo</td>").getBytes());
					out.write('\n');out.write(("  <td valign=\"top\">Tamanho</td>").getBytes());
					out.write('\n');out.write(("  <td valign=\"top\">Perm. Null</td>").getBytes());
					out.write('\n');out.write(("  </tr>").getBytes());

					Map map = Main.main(nomeTabela);
					
					PersistentClass pClass = RepositoryManagerHibernateUtil.getInstancia().getConfiguration().getClassMapping(classe.getName());
					if (pClass != null) {
						Property hibProp = pClass.getIdentifierProperty();
						if (hibProp != null) {
							Iterator ittt = hibProp.getColumnIterator();
							while (ittt.hasNext()) {
								Column col = (Column) ittt.next();
		
								String tipoJava = " &nbsp; ";
								String tipoBanco = " &nbsp; ";
								String tamanho = " &nbsp; ";
								String permiteNull = " &nbsp; ";
try {
								tipoJava = ((Object[])map.get((nomeTabela + "." + col.getName()).toUpperCase()))[0] + "";
} catch (Exception e) {}
try {
								tipoBanco = ((Object[])map.get((nomeTabela + "." + col.getName()).toUpperCase()))[1] + "";
} catch (Exception e) {}
try {
								tamanho = ((Object[])map.get((nomeTabela + "." + col.getName()).toUpperCase()))[2] + "";
} catch (Exception e) {}
try {
								permiteNull = ((Object[])map.get((nomeTabela + "." + col.getName()).toUpperCase()))[3] + "";
} catch (Exception e) {}

								out.write('\n');out.write(("  <tr>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+hibProp.getName()+"</td>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+tipoJava+"</td>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+col.getName()+"</td>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+tipoBanco+"</td>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+tamanho+"</td>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+permiteNull+"</td>").getBytes());
								out.write('\n');out.write(("  </tr>").getBytes());
								break;
							}
						} else {
						
						KeyValue sv = pClass.getIdentifier();
							
							String[] array = ((ComponentType) sv.getType()).getPropertyNames();
							try {
								for (int i = 0; i < array.length; i++) {
									String name = array[i].toString();
									try {
										Iterator itColumn = sv.getColumnIterator();
										int cont = 0;	
										while(itColumn.hasNext()) {
											Column c = (Column) itColumn.next();
											if (cont == i) {
												name = c.getName();
											}
											cont++;
										}
									} catch (Exception e) {
									}

									String tipoJava = " &nbsp; ";
									String tipoBanco = " &nbsp; ";
									String tamanho = " &nbsp; ";
									String permiteNull = " &nbsp; ";
	try {
									tipoJava = ((Object[])map.get((nomeTabela + "." + name).toUpperCase()))[0] + "";
	} catch (Exception e) {}
	try {
									tipoBanco = ((Object[])map.get((nomeTabela + "." + name).toUpperCase()))[1] + "";
	} catch (Exception e) {}
	try {
									tamanho = ((Object[])map.get((nomeTabela + "." + name).toUpperCase()))[2] + "";
	} catch (Exception e) {}
	try {
									permiteNull = ((Object[])map.get((nomeTabela + "." + name).toUpperCase()))[3] + "";
	} catch (Exception e) {}

									out.write('\n');out.write(("  <tr>").getBytes());
									out.write('\n');out.write(("  <td valign=\"top\">"+array[i].toString()+"</td>").getBytes());
									out.write('\n');out.write(("  <td valign=\"top\">"+tipoJava+"</td>").getBytes());
									out.write('\n');out.write(("  <td valign=\"top\">"+name+"</td>").getBytes());
									out.write('\n');out.write(("  <td valign=\"top\">"+tipoBanco+"</td>").getBytes());
									out.write('\n');out.write(("  <td valign=\"top\">"+tamanho+"</td>").getBytes());
									out.write('\n');out.write(("  <td valign=\"top\">"+permiteNull+"</td>").getBytes());
									out.write('\n');out.write(("  </tr>").getBytes());
								}
							} catch (Exception e) {
							}
						}
						
						Iterator itt = pClass.getPropertyIterator();
						while (itt.hasNext()) {
							hibProp = (Property) itt.next();
							
							Iterator ittt = hibProp.getColumnIterator();
							while (ittt.hasNext()) {
								Column col = (Column) ittt.next();


								String tipoJava = " &nbsp; ";
								String tipoBanco = " &nbsp; ";
								String tamanho = " &nbsp; ";
								String permiteNull = " &nbsp; ";
try {
								tipoJava = ((Object[])map.get((nomeTabela + "." + col.getName()).toUpperCase()))[0] + "";
} catch (Exception e) {}
try {
								tipoBanco = ((Object[])map.get((nomeTabela + "." + col.getName()).toUpperCase()))[1] + "";
} catch (Exception e) {}
try {
								tamanho = ((Object[])map.get((nomeTabela + "." + col.getName()).toUpperCase()))[2] + "";
} catch (Exception e) {}
try {
								permiteNull = ((Object[])map.get((nomeTabela + "." + col.getName()).toUpperCase()))[3] + "";
} catch (Exception e) {}


								out.write('\n');out.write(("  <tr>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+hibProp.getName()+"</td>").getBytes());
									out.write('\n');out.write(("  <td valign=\"top\">"+tipoJava+"</td>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+col.getName()+"</td>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+tipoBanco+"</td>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+tamanho+"</td>").getBytes());
								out.write('\n');out.write(("  <td valign=\"top\">"+permiteNull+"</td>").getBytes());
								out.write('\n');out.write(("  </tr>").getBytes());
								break;
							}
						}
						out.write('\n');out.write(("</table>").getBytes());
					}
					out.write('\n');out.write(("  <br>").getBytes());
				}
				out.write('\n');out.write(("</body>").getBytes());
				out.write('\n');out.write(("</html>").getBytes());
			} finally {
				if (out != null) out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}