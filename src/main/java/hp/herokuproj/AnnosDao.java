
package hp.herokuproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnosDao {
    
    public AnnosDao() {
        
    }
    
    public Annos findOneById(Integer key) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos WHERE id=(?)");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Annos ra = new Annos(rs.getInt("id"), rs.getString("nimi"),rs.getString("ohje"));
        stmt.close();
        rs.close();
        conn.close();
        return ra;
    }
    
    public Annos findOneByName(String key) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos WHERE nimi=(?)");
        stmt.setString(1, key);
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Annos ra = new Annos(rs.getInt("id"), rs.getString("nimi"),rs.getString("ohje"));
        stmt.close();
        rs.close();
        conn.close();
        return ra;
    }
    
    public void save(Annos a) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt
                = conn.prepareStatement("INSERT INTO Annos (nimi, ohje) VALUES (?,?)");
        stmt.setString(1, a.getNimi());
        stmt.setString(2, a.getOhje());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public List<Annos> findAll() throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos");
        ResultSet rs = stmt.executeQuery();
        
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        List<Annos> lista = new ArrayList<>();
        Annos t = null;
        t = new Annos(rs.getInt("id"), rs.getString("nimi"),rs.getString("ohje"));
        lista.add(t);
        while (rs.next()) {
        t = new Annos(rs.getInt("id"), rs.getString("nimi"),rs.getString("ohje"));
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
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE id =" + key);
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
        List<Annos> lista = findAll();
        int viimeinen = 0; 
        if (lista==null || lista.isEmpty()) {
            return viimeinen;
        }
        for (Annos annos : lista) {
            if(annos.getId()>viimeinen) {
                viimeinen = annos.getId();
            }
        }
        return viimeinen;
    }
    
}
