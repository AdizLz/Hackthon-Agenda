public class Contacto {
    private String nombre;
    private String telefono;

    public Contacto(String nombre, String telefono) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s-]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras.");
        }

        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }

        if (!telefono.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono solo debe contener números.");
        }

        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }

    // Dos contactos son iguales si tienen el mismo nombre
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contacto)) return false;
        Contacto otro = (Contacto) obj;
        return this.nombre.equalsIgnoreCase(otro.nombre);
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Tel: " + telefono;
    }
}