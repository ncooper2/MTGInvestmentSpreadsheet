import java.sql.ResultSet;
import java.sql.SQLException;

public class App {

	public static void main(String[] args) {
		
		SQLiteDB sheet = new SQLiteDB();
		
		ResultSet rs;
		
		try {
			sheet.addSet("UMA");
			rs = sheet.displaySets();
			while (rs.next()) {
				System.out.println(rs.getString("id") + ": " + rs.getString("setName"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
