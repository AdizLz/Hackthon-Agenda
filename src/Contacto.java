import java.util.Objects;
import java.util.regex.Pattern;

public class Contacto {
    private String nombre;
    private String telefono;

    private static final Pattern NOMBRE_PATTERN = Pattern.compile(".*[a-zA-ZáéíóúÁÉÍÓÚñÑ].*");
    private static final Pattern TEL_PATTERN = Pattern.compile("^\\+?[0-9\\s-]{7,15}$");

    public Contacto(String nombre, String telefono) {
        validarNombre(nombre);
        validarTelefono(telefono);

        this.nombre = nombre.trim();
        this.telefono = telefono.trim();
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty() || !NOMBRE_PATTERN.matcher(nombre).matches()) {
            throw new IllegalArgumentException("El nombre es inválido o está vacío.");
        }
    }

    private void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty() || !TEL_PATTERN.matcher(telefono).matches()) {
            throw new IllegalArgumentException("El teléfono es inválido.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        validarTelefono(telefono);
        this.telefono = telefono.trim();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contacto)) return false;

        Contacto otro = (Contacto) obj;
        return this.nombre.equalsIgnoreCase(otro.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase());
    }

    @Override
    public String toString() {
        return nombre + " - " + telefono;
    }
}