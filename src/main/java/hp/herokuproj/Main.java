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
        AnnosDao annosDao = new AnnosDao();
        
        Spark.get("/*", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineLista", raakaAineDao.findAll());
            map.put("annosLista", annosDao.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
//        Spark.post("/*", (req, res) -> {
//            res.redirect("/raakaAineet");
//            return "";
//        });
        
        Spark.get("/raakaAineet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineLista", raakaAineDao.findAll());
            return new ModelAndView(map, "raakaAineet");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/avaaRaakaAineet", (req, res) -> {
            res.redirect("/raakaAineet");
            return "";
        });
        Spark.get("/raakaAineSivu", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineLista", raakaAineDao.findAll());
            map.put("annosLista", annosDao.findAll());
            return new ModelAndView(map, "raakaAineSivu");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/raakaAineLisays", (req, res) -> {
            if (raakaAineDao.findAll() == null) {
                raakaAineDao.save(new RaakaAine(1, req.queryParams("nimi")));
            } else {
                raakaAineDao.save(new RaakaAine(raakaAineDao.viimeinenId() + 1, req.queryParams("nimi")));
            }
            res.redirect("/*");
            return "";
        });
        
        Spark.post("/annosLisays", (req, res) -> {
            if (annosDao.findAll() == null) {
                annosDao.save(new Annos(1, req.queryParams("nimi"),req.queryParams("ohje")));
            } else {
                annosDao.save(new Annos(annosDao.viimeinenId() + 1, req.queryParams("nimi"),req.queryParams("ohje")));
            }
            res.redirect("/*");
            return "";
        });
        
        Spark.post("/annosPoisto/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            annosDao.delete(id);
            res.redirect("/*");
            return "";
        });
        
        Spark.post("/raakaAinePoisto/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            raakaAineDao.delete(id);
            res.redirect("/raakaAineet");
            return "";
        });
        
        Spark.get("/annokset", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/avaaAnnokset", (req, res) -> {
            res.redirect("/annokset");
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
