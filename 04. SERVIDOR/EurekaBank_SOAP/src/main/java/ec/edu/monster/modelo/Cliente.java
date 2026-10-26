package ec.edu.monster.modelo;

/** Modelo de la tabla cliente de EurekaBank. */
public class Cliente {
    private String chr_cliecodigo;
    private String vch_cliepaterno;
    private String vch_cliematerno;
    private String vch_clienombre;
    private String chr_cliedni;
    private String vch_clieciudad;
    private String vch_cliedireccion;
    private String vch_clietelefono;
    private String vch_clieemail;

    public Cliente() {
    }

    public Cliente(String chr_cliecodigo, String vch_cliepaterno, String vch_cliematerno,
                   String vch_clienombre, String chr_cliedni, String vch_clieciudad,
                   String vch_cliedireccion, String vch_clietelefono, String vch_clieemail) {
        this.chr_cliecodigo = chr_cliecodigo;
        this.vch_cliepaterno = vch_cliepaterno;
        this.vch_cliematerno = vch_cliematerno;
        this.vch_clienombre = vch_clienombre;
        this.chr_cliedni = chr_cliedni;
        this.vch_clieciudad = vch_clieciudad;
        this.vch_cliedireccion = vch_cliedireccion;
        this.vch_clietelefono = vch_clietelefono;
        this.vch_clieemail = vch_clieemail;
    }

    public String getChr_cliecodigo() { return chr_cliecodigo; }
    public void setChr_cliecodigo(String chr_cliecodigo) { this.chr_cliecodigo = chr_cliecodigo; }

    public String getVch_cliepaterno() { return vch_cliepaterno; }
    public void setVch_cliepaterno(String vch_cliepaterno) { this.vch_cliepaterno = vch_cliepaterno; }

    public String getVch_cliematerno() { return vch_cliematerno; }
    public void setVch_cliematerno(String vch_cliematerno) { this.vch_cliematerno = vch_cliematerno; }

    public String getVch_clienombre() { return vch_clienombre; }
    public void setVch_clienombre(String vch_clienombre) { this.vch_clienombre = vch_clienombre; }

    public String getChr_cliedni() { return chr_cliedni; }
    public void setChr_cliedni(String chr_cliedni) { this.chr_cliedni = chr_cliedni; }

    public String getVch_clieciudad() { return vch_clieciudad; }
    public void setVch_clieciudad(String vch_clieciudad) { this.vch_clieciudad = vch_clieciudad; }

    public String getVch_cliedireccion() { return vch_cliedireccion; }
    public void setVch_cliedireccion(String vch_cliedireccion) { this.vch_cliedireccion = vch_cliedireccion; }

    public String getVch_clietelefono() { return vch_clietelefono; }
    public void setVch_clietelefono(String vch_clietelefono) { this.vch_clietelefono = vch_clietelefono; }

    public String getVch_clieemail() { return vch_clieemail; }
    public void setVch_clieemail(String vch_clieemail) { this.vch_clieemail = vch_clieemail; }

    @Override
    public String toString() {
        return chr_cliecodigo + " - " + vch_clienombre + " " + vch_cliepaterno + " " + vch_cliematerno
                + " (" + vch_clieciudad + ")";
    }
}
