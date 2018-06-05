/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hp.herokuproj;


public class Annos {
    private int id;
    private String nimi;
    private String ohje;
    public Annos(int id, String nimi, String ohje) {
        this.id = id;
        this.nimi = nimi;
        this.ohje = ohje;
    }
    public int getId() {
        return this.id;
    }
    public String getNimi() {
        return this.nimi;
    }
    public String getOhje() {
        return this.ohje;
    }
}
