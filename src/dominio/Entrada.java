package dominio;

public class Entrada {
    private String cedula;
    private String codigoEvento;

    public Entrada(String cedula, String codigoEvento) {
        this.cedula = cedula;
        this.codigoEvento = codigoEvento;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(String codigoEvento) {
        this.codigoEvento = codigoEvento;
    }
    
}
