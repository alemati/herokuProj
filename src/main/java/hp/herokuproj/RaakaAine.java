
package hp.herokuproj;

import java.sql.Connection;

public class RaakaAine {
    private int id;
    private String nimi;
    public RaakaAine(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    public int getId() {
        return this.id;
    }
    public String getNimi() {
        return this.nimi;
        
    }
    
}
