/**
 * Representa un contacto dentro de la agenda.
 * Contiene nombre y teléfono, y define la igualdad entre contactos
 * basándose únicamente en el nombre (sin distinguir mayúsculas/minúsculas).
 */
public class Contacto {
    private String nombre;
    private String telefono;

    /**
     * Crea un nuevo contacto con nombre y teléfono.
     *
     * @param nombre   Nombre del contacto.
     * @param telefono Número de teléfono del contacto.
     */
    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    /**
     * Devuelve el nombre del contacto.
     *
     * @return Nombre del contacto.
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve el teléfono del contacto.
     *
     * @return Número de teléfono del contacto.
     */
    public String getTelefono() { return telefono; }

    /**
     * Compara este contacto con otro objeto. Dos contactos se consideran
     * iguales si tienen el mismo nombre (ignorando mayúsculas/minúsculas).
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si los contactos tienen el mismo nombre.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contacto)) return false;
        Contacto otro = (Contacto) obj;
        return this.nombre.equalsIgnoreCase(otro.nombre);
    }

    /**
     * Devuelve una representación en cadena del contacto.
     *
     * @return Cadena con nombre y teléfono del contacto.
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Tel: " + telefono;
    }
}