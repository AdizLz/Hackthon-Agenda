public class Contacto {
    private String nombre;
    private String apellido;
    private String telefono;

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

        if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s-]+")) {
            throw new IllegalArgumentException(campo + " solo debe contener letras.");
        }
    }

    private void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío.");
        }

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