package dominio;

public class ReporteCompra implements Comparable<ReporteCompra> {
    private int dia;
    private int cant;
    
    public ReporteCompra(int dia, int cant){
        this.dia = dia;
        this.cant = cant;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
    
    @Override
    public String toString() {
        return dia + "-" + cant;
    }

    @Override
    public int compareTo(ReporteCompra o) {
        return Integer.compare(this.dia, o.getDia());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReporteCompra other = (ReporteCompra) obj;
        return this.dia == other.dia;
    }
    
}
