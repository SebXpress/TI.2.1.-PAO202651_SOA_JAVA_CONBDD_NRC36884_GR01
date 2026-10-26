package ec.edu.monster.modelo;

public class Cuenta {
    private String chr_cuencodigo;
    private String chr_monecodigo;
    private String chr_sucucodigo;
    private String chr_emplcreacuenta;
    private String chr_cliecodigo;
    private double dec_cuensaldo;
    private String dtt_cuenfechacreacion;
    private String vch_cuenestado;
    private int int_cuencontmov;
    private String chr_cuenclave;

    public Cuenta() {
    }

    public Cuenta(String chr_cuencodigo, String chr_monecodigo, String chr_sucucodigo, 
                  String chr_emplcreacuenta, String chr_cliecodigo, double dec_cuensaldo, 
                  String dtt_cuenfechacreacion, String vch_cuenestado, int int_cuencontmov, String chr_cuenclave) {
        this.chr_cuencodigo = chr_cuencodigo;
        this.chr_monecodigo = chr_monecodigo;
        this.chr_sucucodigo = chr_sucucodigo;
        this.chr_emplcreacuenta = chr_emplcreacuenta;
        this.chr_cliecodigo = chr_cliecodigo;
        this.dec_cuensaldo = dec_cuensaldo;
        this.dtt_cuenfechacreacion = dtt_cuenfechacreacion;
        this.vch_cuenestado = vch_cuenestado;
        this.int_cuencontmov = int_cuencontmov;
        this.chr_cuenclave = chr_cuenclave;
    }

    public String getChr_cuencodigo() { return chr_cuencodigo; }
    public void setChr_cuencodigo(String chr_cuencodigo) { this.chr_cuencodigo = chr_cuencodigo; }

    public String getChr_monecodigo() { return chr_monecodigo; }
    public void setChr_monecodigo(String chr_monecodigo) { this.chr_monecodigo = chr_monecodigo; }

    public String getChr_sucucodigo() { return chr_sucucodigo; }
    public void setChr_sucucodigo(String chr_sucucodigo) { this.chr_sucucodigo = chr_sucucodigo; }

    public String getChr_emplcreacuenta() { return chr_emplcreacuenta; }
    public void setChr_emplcreacuenta(String chr_emplcreacuenta) { this.chr_emplcreacuenta = chr_emplcreacuenta; }

    public String getChr_cliecodigo() { return chr_cliecodigo; }
    public void setChr_cliecodigo(String chr_cliecodigo) { this.chr_cliecodigo = chr_cliecodigo; }

    public double getDec_cuensaldo() { return dec_cuensaldo; }
    public void setDec_cuensaldo(double dec_cuensaldo) { this.dec_cuensaldo = dec_cuensaldo; }

    public String getDtt_cuenfechacreacion() { return dtt_cuenfechacreacion; }
    public void setDtt_cuenfechacreacion(String dtt_cuenfechacreacion) { this.dtt_cuenfechacreacion = dtt_cuenfechacreacion; }

    public String getVch_cuenestado() { return vch_cuenestado; }
    public void setVch_cuenestado(String vch_cuenestado) { this.vch_cuenestado = vch_cuenestado; }

    public int getInt_cuencontmov() { return int_cuencontmov; }
    public void setInt_cuencontmov(int int_cuencontmov) { this.int_cuencontmov = int_cuencontmov; }

    public String getChr_cuenclave() { return chr_cuenclave; }
    public void setChr_cuenclave(String chr_cuenclave) { this.chr_cuenclave = chr_cuenclave; }
}