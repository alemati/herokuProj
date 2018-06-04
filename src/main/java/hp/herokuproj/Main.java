package hp.herokuproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Redirect;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {
    
    public static void main(String[] args) {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        RaakaAineDao raakaAineDao = new RaakaAineDao();
        
        Spark.get("/ens", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineLista", raakaAineDao.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/ens", (req, res) -> {
            if (raakaAineDao.findAll() == null) {
                raakaAineDao.save(new RaakaAine(1, req.queryParams("nimi")));
            } else {
                raakaAineDao.save(new RaakaAine(raakaAineDao.viimeinenId() + 1, req.queryParams("nimi")));
            }
            res.redirect("/*");
            return "";
        });
        Spark.post("/poisto/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            raakaAineDao.delete(id);
            res.redirect("/*");
            return "";
        });

    }

    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }
        return DriverManager.getConnection("jdbc:sqlite:mysteeri.db");
    }

}
