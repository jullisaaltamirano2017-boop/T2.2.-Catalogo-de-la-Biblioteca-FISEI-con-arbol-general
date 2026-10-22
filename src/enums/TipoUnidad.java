package enums;

public enum TipoUnidad {
    BIBLIOTECA, AREA, ESTANTE;

    public boolean puedeTenerHijo(TipoUnidad hijo) {
        switch (this) {
            case BIBLIOTECA: return hijo == AREA;
            case AREA:       return hijo == ESTANTE;
            default:         return false; // un ESTANTE no puede tener hijos
        }
    }
}
