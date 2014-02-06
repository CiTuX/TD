package ch.citux.td.data.model;

public class StreamToken extends Base {

    private int p;
    private String nauth;
    private String nauthsig;

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getNauth() {
        return nauth;
    }

    public void setNauth(String nauth) {
        this.nauth = nauth;
    }

    public String getNauthsig() {
        return nauthsig;
    }

    public void setNauthsig(String nauthsig) {
        this.nauthsig = nauthsig;
    }

    @Override
    public String toString() {
        return "nauth: " + nauth + "nauthsig: " + nauthsig + "p: " + p;
    }
}
