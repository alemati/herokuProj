
package hp.herokuproj;
    
public class AnnosRaakaAine {
    private int annosId;
    private int raakaAineId;
    private String maara;
    private String ohje;
    private RaakaAineDao raDao;
    private AnnosDao aDao;
    public AnnosRaakaAine(int annosId, int raakaAineId, String maara, String ohje) {
        this.annosId = annosId;
        this.raakaAineId = raakaAineId;
        this.maara = maara;
        this.ohje = ohje;
        this.raDao = new RaakaAineDao();
        this.aDao = new AnnosDao();
    }
    public AnnosRaakaAine() {
        
        
        
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
    public String getAnnosNimi() throws Exception{
        return this.aDao.findOneById(this.annosId).getNimi();
    }
    public String getRaakaAineNimi() throws Exception{
        return this.raDao.findOneById(this.raakaAineId).getNimi();
    }
}
