
package hp.herokuproj;

import static hp.herokuproj.RaakaAineDao.getConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnosRaakaAineDao {
    
    public AnnosRaakaAineDao() {
        
    }
    
    public List<AnnosRaakaAine> missaTamaRAonKaytetty(RaakaAine ra) throws SQLException, Exception {
        AnnosDao aDao = new AnnosDao();
        RaakaAineDao raDao = new RaakaAineDao();
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE raakaaine_id=(?)");
        stmt.setInt(1, ra.getId());
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        List<Annos> aLista = new ArrayList<>();
        List<AnnosRaakaAine> lista = new ArrayList<>();
        AnnosRaakaAine t = null;
        t = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"),rs.getString("maara"),rs.getString("ohje"));
        lista.add(t);
        aLista.add(aDao.findOneById(rs.getInt("annos_id")));
        while (rs.next()) {
        t = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"),rs.getString("maara"),rs.getString("ohje"));
        lista.add(t);
        aLista.add(aDao.findOneById(rs.getInt("annos_id")));
        }
        stmt.close();
        rs.close();
        conn.close();
        return lista;
    }
    
    public List<AnnosRaakaAine> mitaRAonTassaAnnoksessaOnKaytetty(Annos annos) throws SQLException, Exception {
        AnnosDao aDao = new AnnosDao();
        RaakaAineDao raDao = new RaakaAineDao();
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE annos_id=(?)");
        stmt.setInt(1, annos.getId());
        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        List<AnnosRaakaAine> lista = new ArrayList<>();
        List<RaakaAine> raLista = new ArrayList<>();
        AnnosRaakaAine t = null;
        t = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"),rs.getString("maara"),rs.getString("ohje"));
        lista.add(t);
        raLista.add(raDao.findOneById(rs.getInt("raakaaine_id")));
        while (rs.next()) {
        t = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"),rs.getString("maara"),rs.getString("ohje"));
        lista.add(t);
        raLista.add(raDao.findOneById(rs.getInt("raakaaine_id")));
        }
        stmt.close();
        rs.close();
        conn.close();
        return lista;
    }
    
    public void deleteBasedOnRaakaAine(RaakaAine ra) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE raakaaine_id = (?);");
        stmt.setInt(1, ra.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public void deleteBasedOnAnnos(Annos ra) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = (?);");
        stmt.setInt(1, ra.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public void deletePair(Annos a, RaakaAine ra) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = (?) AND raakaAine_id = (?);");
        stmt.setInt(1, a.getId());
        stmt.setInt(2, ra.getId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    public void save(AnnosRaakaAine ra) throws SQLException, Exception {
        Connection conn = getConnection();
        PreparedStatement stmt
                = conn.prepareStatement("INSERT INTO AnnosRaakaAine (annos_id, raakaAine_id, maara, ohje) VALUES (?,?,?,?);");
        stmt.setInt(1, ra.getAnnosId());
        stmt.setInt(2, ra.getRaakaAineId());
        stmt.setString(3, ra.getMaara());
        stmt.setString(4, ra.getOhje());
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
    
//    public RaakaAine findOneByName(String key) throws SQLException, Exception {
//        Connection conn = getConnection();
//        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE nimi=(?)");
//        stmt.setString(1, key);
//        ResultSet rs = stmt.executeQuery();
//        boolean hasOne = rs.next();
//        if (!hasOne) {
//            return null;
//        }
//        RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
//        stmt.close();
//        rs.close();
//        conn.close();
//        return ra;
//    }
//    
//    public void save(RaakaAine ra) throws SQLException, Exception {
//        Connection conn = getConnection();
//        PreparedStatement stmt
//                = conn.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
//        stmt.setString(1, ra.getNimi());
//        stmt.executeUpdate();
//        stmt.close();
//        conn.close();
//    }
//    
//    public List<RaakaAine> findAll() throws SQLException, Exception {
//        Connection conn = getConnection();
//        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine");
//        ResultSet rs = stmt.executeQuery();
//        
//        boolean hasOne = rs.next();
//        if (!hasOne) {
//            return null;
//        }
//        List<RaakaAine> lista = new ArrayList<>();
//        RaakaAine t = null;
//        t = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
//        lista.add(t);
//        while (rs.next()) {
//        t = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
//        lista.add(t);
//            System.out.println(t);
//        }
//        rs.close();
//        stmt.close();
//        conn.close();
//        return lista;
//    }
//    
//    public void delete(Integer key) throws SQLException, Exception {
//        Connection conn = getConnection();
//        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id =" + key);
////        stmt.setInt(1, key);
//        stmt.executeUpdate();
//        stmt.close();
//        conn.close();
//    }
//    
//    
//    
//    
//    
//    public static Connection getConnection() throws Exception {
//        String dbUrl = System.getenv("JDBC_DATABASE_URL");
//        if (dbUrl != null && dbUrl.length() > 0) {
//            return DriverManager.getConnection(dbUrl);
//        }
//        return DriverManager.getConnection("jdbc:sqlite:mysteeri.db");
//    }
    
}
