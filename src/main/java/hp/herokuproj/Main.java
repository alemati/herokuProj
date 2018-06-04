package hp.herokuproj;

import java.sql.Connection;
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
        
        Spark.get("*", (req, res) -> {
            
            HashMap map = new HashMap<>();
            map.put("teksti", "spark Get toimii!!");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
    }

}
