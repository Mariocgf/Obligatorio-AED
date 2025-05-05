package dominio;

public class Cliente implements Comparable<Cliente>{
    private String cedula;
    private String Nombre;

    public Cliente(String cedula, String Nombre) {
        this.cedula = cedula;
        this.Nombre = Nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public String toString() {
        return cedula + "-" + Nombre;
    }
    @Override
    public int compareTo(Cliente o) {
        return this.cedula.compareToIgnoreCase(o.cedula);
    }
    
}
