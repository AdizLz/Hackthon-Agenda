// [SOLUCIÓN - Legibilidad]: Falta documentación Javadoc en la clase que especifique las reglas de negocio de un Contacto.
public class Contacto {
    private String nombre;
    private String apellido;
    private String telefono;

    // [SOLUCIÓN - Complejidad / Rendimiento]: Compilar expresiones regulares en cada llamada a matches() es ineficiente.
    // Deberían declararse como constantes estáticas al inicio de la clase:
    // private static final Pattern TEXTO_PATTERN = Pattern.compile("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s-]+");
    // private static final Pattern TEL_PATTERN = Pattern.compile("\\d+");

    public Contacto(String nombre, String apellido, String telefono) {
        validarTexto(nombre, "El nombre");
        validarTexto(apellido, "El apellido");
        validarTelefono(telefono);

        this.nombre = nombre.trim();
        this.apellido = apellido.trim();
        this.telefono = telefono.trim();
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }

        // [SOLUCIÓN - Casos límite]: La expresión regular permite cadenas compuestas únicamente por espacios o guiones (ej. "   " o "---").
        // Aunque haces .trim() después, la validación aceptará valores visualmente vacíos o inválidos.
        // Ajustar el patrón para exigir caracteres alfabéticos obligatorios.
        if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s-]+")) {
            throw new IllegalArgumentException(campo + " solo debe contener letras.");
        }
    }

    private void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }

        // [SOLUCIÓN - Casos límite]: Restringir el teléfono a solo dígitos (\\d+) ignora formatos internacionales legítimos (ej. +52, prefijos, espacios o guiones).
        // Hará que falle ante entradas del mundo real.
        if (!telefono.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono solo debe contener números.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        validarTelefono(telefono);
        this.telefono = telefono.trim();
    }

    // [CRÍTICO - OOP / Colecciones]: Has sobreescrito 'equals' pero NO has sobreescrito 'hashCode()'.
    // Esto es un fallo grave en Java. Si esta clase se introduce en colecciones basadas en hash (como HashSet o HashMap,
    // sugeridos para optimizar la clase Agenda), la colección no detectará duplicados de contactos con el mismo nombre y apellido.
    // Solución: Sobreescribir hashCode() utilizando los mismos atributos que equals:
    // @Override
    // public int hashCode() { return Objects.hash(nombre.toLowerCase(), apellido.toLowerCase()); }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contacto)) return false;

        Contacto otro = (Contacto) obj;

        return this.nombre.equalsIgnoreCase(otro.nombre)
                && this.apellido.equalsIgnoreCase(otro.apellido);
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + telefono;
    }
}