
package hp.herokuproj;
    
public class AnnosRaakaAine {
    private int annosId;
    private int raakaAineId;
    private String maara;
    private String ohje;
    public AnnosRaakaAine(int annosId, int raakaAineId, String maara, String ohje) {
        this.annosId = annosId;
        this.raakaAineId = raakaAineId;
        this.maara = maara;
        this.ohje = ohje;
    }
    public int getAnnosId() {
        return this.annosId;
    }
    public int getRaakaAineId() {
        return this.raakaAineId;
    }
    public String getMaara() {
        return this.maara;
    }
    public String getOhje() {
        return this.ohje;
    }
}
