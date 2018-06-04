package hp.herokuproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
    
    public static void main(String[] args) {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        RaakaAineDao opiskelijaDao = new RaakaAineDao();
        
        Spark.get("*", (req, res) -> {
            HashMap map = new HashMap<>();
            List lista = opiskelijaDao.findAll();
            if (lista != null) {
                map.put("onkoNULL", "EI OLE NULL");
            }
            if (!lista.isEmpty()) {
                map.put("onkoTyhja", "Eikä ole tyhjä... hmmm...");
            }
            map.put("teksti", "spark Get toimii!!");
            map.put("raakaAineLista", opiskelijaDao.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

    }

    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }
        return DriverManager.getConnection("jdbc:sqlite:mysteeri.db");
    }

}
