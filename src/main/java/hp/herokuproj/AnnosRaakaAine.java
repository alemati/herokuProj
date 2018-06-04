
package hp.herokuproj;
    
public class AnnosRaakaAine {
    private int annosId;
    private int raakaAineId;
    private int jarjestys;
    private String maara;
    private String ohje;
    public AnnosRaakaAine(int annosId, int raakaAineId, int jarjestys, String maara, String ohje) {
        this.annosId = annosId;
        this.raakaAineId = raakaAineId;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }
    public int getAnnosId() {
        return this.annosId;
    }
    public int getRaakaAineId() {
        return this.raakaAineId;
    }
    public int getJarjestys() {
        return this.jarjestys;
    }
    public String getMaara() {
        return this.maara;
    }
    public String getOhje() {
        return this.ohje;
    }
}
