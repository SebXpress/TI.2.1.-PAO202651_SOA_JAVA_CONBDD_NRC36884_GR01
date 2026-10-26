package ec.edu.monster.modelo;

public class Movimiento {
    private String chr_cuencodigo;
    private int int_movinumero;
    private String dtt_movifecha;
    private String chr_emplcodigo;
    private String chr_tipocodigo;
    private double dec_moviimporte;
    private String chr_cuenreferencia;

    public Movimiento() {
    }

    public String getChr_cuencodigo() { return chr_cuencodigo; }
    public void setChr_cuencodigo(String chr_cuencodigo) { this.chr_cuencodigo = chr_cuencodigo; }

    public int getInt_movinumero() { return int_movinumero; }
    public void setInt_movinumero(int int_movinumero) { this.int_movinumero = int_movinumero; }

    public String getDtt_movifecha() { return dtt_movifecha; }
    public void setDtt_movifecha(String dtt_movifecha) { this.dtt_movifecha = dtt_movifecha; }

    public String getChr_emplcodigo() { return chr_emplcodigo; }
    public void setChr_emplcodigo(String chr_emplcodigo) { this.chr_emplcodigo = chr_emplcodigo; }

    public String getChr_tipocodigo() { return chr_tipocodigo; }
    public void setChr_tipocodigo(String chr_tipocodigo) { this.chr_tipocodigo = chr_tipocodigo; }

    public double getDec_moviimporte() { return dec_moviimporte; }
    public void setDec_moviimporte(double dec_moviimporte) { this.dec_moviimporte = dec_moviimporte; }

    public String getChr_cuenreferencia() { return chr_cuenreferencia; }
    public void setChr_cuenreferencia(String chr_cuenreferencia) { this.chr_cuenreferencia = chr_cuenreferencia; }
}