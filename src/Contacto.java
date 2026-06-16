import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Representa un contacto con nombre y teléfono.
 * Valida el formato de ambos datos al crearse o modificarse,
 * y define igualdad/hash basados únicamente en el nombre
 * (sin distinguir mayúsculas/minúsculas), para evitar duplicados en la Agenda.
 */
public class Contacto {

    private String nombre;
    private String telefono;

    // El nombre debe contener al menos una letra (incluye letras acentuadas y la ñ)
    private static final Pattern NOMBRE_PATTERN = Pattern.compile(".*[a-zA-ZáéíóúÁÉÍÓÚñÑ].*");

    // El teléfono permite un "+" opcional al inicio, dígitos, espacios y guiones,
    // con una longitud total de entre 7 y 15 caracteres
    private static final Pattern TEL_PATTERN = Pattern.compile("^\\+?[0-9\\s-]{7,15}$");

    /**
     * Crea un nuevo contacto, validando nombre y teléfono antes de asignarlos.
     *
     * @param nombre   nombre del contacto
     * @param telefono número de teléfono del contacto
     * @throws IllegalArgumentException si el nombre o el teléfono no son válidos
     */
    public Contacto(String nombre, String telefono) {
        validarNombre(nombre);
        validarTelefono(telefono);
        // Se guardan ya "limpios" (sin espacios sobrantes al inicio/fin)
        this.nombre = nombre.trim();
        this.telefono = telefono.trim();
    }

    /**
     * Valida que el nombre no sea nulo, vacío, ni esté compuesto
     * solo por caracteres no alfabéticos.
     *
     * @param nombre nombre a validar
     * @throws IllegalArgumentException si el nombre es inválido
     */
    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty() || !NOMBRE_PATTERN.matcher(nombre).matches()) {
            throw new IllegalArgumentException("El nombre es inválido o está vacío.");
        }
    }

    /**
     * Valida que el teléfono no sea nulo, vacío, y que cumpla el formato
     * esperado (dígitos, espacios, guiones y un "+" opcional al inicio).
     *
     * @param telefono teléfono a validar
     * @throws IllegalArgumentException si el teléfono es inválido
     */
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

    /**
     * Actualiza el teléfono del contacto, validándolo nuevamente
     * antes de asignarlo.
     *
     * @param telefono nuevo teléfono a asignar
     * @throws IllegalArgumentException si el teléfono no es válido
     */
    public void setTelefono(String telefono) {
        validarTelefono(telefono);
        this.telefono = telefono.trim();
    }

    /**
     * Dos contactos se consideran iguales si tienen el mismo nombre,
     * sin importar mayúsculas/minúsculas. El teléfono no se considera
     * para la igualdad, ya que el nombre identifica al contacto.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contacto)) return false;
        Contacto otro = (Contacto) obj;
        return this.nombre.equalsIgnoreCase(otro.nombre);
    }

    /**
     * El hashCode se calcula a partir del nombre en minúsculas,
     * para mantener coherencia con equals() (mismo criterio de comparación).
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre.toLowerCase());
    }

    /**
     * Representación legible del contacto, útil para depuración
     * o para mostrarlo directamente en listas.
     */
    @Override
    public String toString() {
        return nombre + " - " + telefono;
    }
}