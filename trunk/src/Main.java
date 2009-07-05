import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		main(" imposto ");
	}	
	public static Map main(String tabela) {
		Map map = new HashMap();
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			
			Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/ENTERPRISE","sa","001100");
			System.out.println("foi");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from " + tabela);
			ResultSetMetaData s  =rs.getMetaData();
			
			for (int i = 1; i <= s.getColumnCount(); i++) {
				int tamanho = s.getColumnDisplaySize(i);
				int scala = s.getScale(i);
				tamanho = tamanho - scala;
				int permite = s.isNullable(i);
				
				Object[] obj = {s.getColumnClassName(i),s.getColumnTypeName(i), tamanho + ((scala == 0)? "": (", " + scala)) , ((permite == 0)? "N": "S")};
				map.put((tabela + "." + s.getColumnName(i)).toUpperCase(), obj);
			}
			rs.close();
			st.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("nao foi");
		}
		return map;
	}
}
