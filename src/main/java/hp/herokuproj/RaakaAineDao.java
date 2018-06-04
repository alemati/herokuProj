
package hp.herokuproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RaakaAineDao {
    
    public RaakaAineDao() {
        
    }
    
    public RaakaAine findOneById(Integer key) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE id=(?)");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        stmt.close();
        rs.close();
        conn.close();
        return ra;
    }
    
    public RaakaAine findOneByName(String key) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE nimi=(?)");
        stmt.setString(1, key);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        stmt.close();
        rs.close();
        conn.close();
        return ra;
    }
    
    public void save(RaakaAine ra) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt
                = conn.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
        stmt.setString(1, ra.getNimi());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public List<RaakaAine> findAll() throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine");
        ResultSet rs = stmt.executeQuery();
        
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        List<RaakaAine> lista = new ArrayList<>();
        RaakaAine t = null;
        t = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        lista.add(t);
        while (rs.next()) {
        t = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        lista.add(t);
            System.out.println(t);
        }
        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }
    
    public void delete(Integer key) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id =" + key);
//        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }
        return DriverManager.getConnection("jdbc:sqlite:mysteeri.db");
    }
    
    public int viimeinenId() throws SQLException, Exception {
        List<RaakaAine> lista = findAll();
        int viimeinen = 0; 
        if (lista==null || lista.isEmpty()) {
            return viimeinen;
        }
        for (RaakaAine raakaAine : lista) {
            if(raakaAine.getId()>viimeinen) {
                viimeinen = raakaAine.getId();
            }
        }
        return viimeinen;
    }
    
}
