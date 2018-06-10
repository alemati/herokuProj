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
import static spark.Spark.staticFiles;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        staticFiles.location("/templates");

        RaakaAineDao raakaAineDao = new RaakaAineDao();
        AnnosDao annosDao = new AnnosDao();
        AnnosRaakaAineDao annosRaakaAineDoa = new AnnosRaakaAineDao();

        Spark.get("/index", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineLista", raakaAineDao.findAll());
            map.put("annosLista", annosDao.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/raakaAineLisays", (req, res) -> {
            if (raakaAineDao.findAll() == null) {
                raakaAineDao.save(new RaakaAine(1, req.queryParams("nimi")));
            } else {
                raakaAineDao.save(new RaakaAine(raakaAineDao.viimeinenId() + 1, req.queryParams("nimi")));
            }
            res.redirect("/index");
            return "";
        });

        Spark.post("/annosLisays", (req, res) -> {
            if (annosDao.findAll() == null) {
                annosDao.save(new Annos(1, req.queryParams("nimi"), req.queryParams("ohje")));
            } else {
                annosDao.save(new Annos(annosDao.viimeinenId() + 1, req.queryParams("nimi"), req.queryParams("ohje")));
            }
            res.redirect("/index");
            return "";
        });

        Spark.post("/annosPoisto/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            annosRaakaAineDoa.deleteBasedOnAnnos(annosDao.findOneById(Integer.parseInt(req.params(":id"))));
            annosDao.delete(id);
            res.redirect("/index");
            return "";
        });

        Spark.post("/raakaAinePoisto/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            annosRaakaAineDoa.deleteBasedOnRaakaAine(raakaAineDao.findOneById(Integer.parseInt(req.params(":id"))));
            raakaAineDao.delete(id);
            res.redirect("/index");
            return "";
        });
        Spark.post("/ainePoistoAnnoksesta/:idRA/:idA", (req, res) -> {
            int idRA = Integer.parseInt(req.params(":idRA"));
            int idA = Integer.parseInt(req.params(":idA"));
            annosRaakaAineDoa.deletePair(annosDao.findOneById(idA), raakaAineDao.findOneById(idRA));
            res.redirect("/index3/" + idA);
            return "";
        });
        
        Spark.post("/deletePair/:idRA", (req, res) -> {
            int idRA = Integer.parseInt(req.params(":idRA"));
//            int idA = Integer.parseInt(req.queryParams("annosId"));
//            annosRaakaAineDoa.deletePair(annosDao.findOneById(idA), raakaAineDao.findOneById(idRA));
            res.redirect("/index2/" + idRA);
            return "";
        });

        Spark.post("/annosRaakaAinePoisto", (req, res) -> {
//            int id = Integer.parseInt(req.params(":id"));
//            annosRaakaAineDoa.deleteBasedOnRaakaAine(raakaAineDao.findOneById(Integer.parseInt(req.params(":id"))));
//            raakaAineDao.delete(id);
//            res.redirect("/index");
            return "";
        });

//        Spark.post("/RAlisaysA", (req, res) -> {
//            int annosId = Integer.parseInt(req.params(":id"));
//            String uusiRaakaAine = req.params("uusiRaakaAine");
//            raakaAineDao.findOneByName(uusiRaakaAine).getId();
//            String maara = req.params("maara");
//            String ohje = req.params("ohje");
//            annosRaakaAineDoa.save(new AnnosRaakaAine(raakaAineDao.findOneByName(uusiRaakaAine).getId(), annosId, maara, ohje));
//            res.redirect("/index3/" + annosId);
//            return "";
//        });
        Spark.get("/index2/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer id = Integer.parseInt(req.params(":id"));
            map.put("raakaAine", raakaAineDao.findOneById(id));
            map.put("kaytetty", annosRaakaAineDoa.missaTamaRAonKaytetty(raakaAineDao.findOneById(Integer.parseInt(req.params(":id")))));
            return new ModelAndView(map, "index2");
        }, new ThymeleafTemplateEngine());

        Spark.get("/index3/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer userId = Integer.parseInt(req.params(":id"));
            map.put("annos", annosDao.findOneById(userId));
            map.put("raakaAineet", annosRaakaAineDoa.mitaRAonTassaAnnoksessaOnKaytetty(annosDao.findOneById(Integer.parseInt(req.params(":id")))));
            map.put("vRaakaAineet", raakaAineDao.findAll());
            return new ModelAndView(map, "index3");
        }, new ThymeleafTemplateEngine());

        Spark.post("/index3/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            if (req.queryParams("uusiRaakaAine") != null && req.queryParams("maara") != null && req.queryParams("ohje") != null) {
                Annos annos = annosDao.findOneById(id);
                RaakaAine raakaAine = raakaAineDao.findOneByName(req.queryParams("uusiRaakaAine"));
                AnnosRaakaAine aRa = new AnnosRaakaAine(annos.getId(), raakaAine.getId(), req.queryParams("maara"), req.queryParams("ohje"));
                annosRaakaAineDoa.save(aRa);
            }


//            raakaAineDao.save(new RaakaAine(raakaAineDao.viimeinenId() + 1, req.queryParams("uusiRaakaAine")));
//            
//            String uusiRaakaAine = req.params("uusiRaakaAine");
//            String maara = req.params("maara");
//            String ohje = req.params("ohje");
//            raakaAineDao.findOneByName(uusiRaakaAine).getId();

//            
//            annosRaakaAineDoa.save(annosRaakaAine);
//            
            res.redirect("/index3/" + id);
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
