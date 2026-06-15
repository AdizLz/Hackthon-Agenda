public class Agenda {
    private Contacto[] contactos;
    private int tamanio;

    public Agenda() {
        this.tamanio = 10;
        this.contactos = new Contacto[tamanio];
    }

    public Agenda(int tamanio) {
        this.tamanio = tamanio;
        this.contactos = new Contacto[tamanio];
    }
    public void añadirContacto(Contacto c) {
        if (agendaLlena()) {
            throw new IllegalArgumentException(
                    "La agenda está llena."
            );
        }
        if (existeContacto(c)) {
            throw new IllegalArgumentException(
                    "Ya existe un contacto con ese nombre y apellido."
            );
        }
        for (int i = 0; i < tamanio; i++) {
            if (contactos[i] == null) {
                contactos[i] = c;
                return;
            }
        }
    }
    public boolean existeContacto(Contacto c) {
        for (Contacto contacto : contactos) {
            if (contacto != null && contacto.equals(c)) {
                return true;
            }
        }
        return false;
    }


    public String listarContactos() {
        StringBuilder lista = new StringBuilder();
        for (Contacto c : contactos) {
            if (c != null) {
                lista.append(c).append("\n");
            }
        }
        if (lista.isEmpty()) {
            return "No hay contactos registrados.";
        }
        return lista.toString();
    }

    public String buscaContacto(String nombre, String apellido) {
        for (Contacto c : contactos) {
            if (c != null &&
                    c.getNombre().equalsIgnoreCase(nombre) &&
                    c.getApellido().equalsIgnoreCase(apellido)) {

                return "Teléfono: " + c.getTelefono();
            }
        }
        return "Contacto no encontrado.";
    }

    public String eliminarContacto(Contacto c) {
        for (int i = 0; i < tamanio; i++) {
            if (contactos[i] != null &&
                    contactos[i].equals(c)) {
                contactos[i] = null;
                return "Contacto eliminado correctamente.";
            }
        }
        return "Contacto no encontrado.";
    }

    public String modificarTelefono(
            String nombre,
            String apellido,
            String nuevoTelefono) {

        for (Contacto c : contactos) {

            if (c != null &&
                    c.getNombre().equalsIgnoreCase(nombre) &&
                    c.getApellido().equalsIgnoreCase(apellido)) {

                c.setTelefono(nuevoTelefono);

                return "Teléfono actualizado correctamente.";
            }
        }

        return "Contacto no encontrado.";
    }

    public boolean agendaLlena() {
        return espaciosLibres() == 0;
    }

    public int espaciosLibres() {
        int libres = 0;

        for (Contacto c : contactos) {
            if (c == null) {
                libres++;
            }
        }

        return libres;
    }

    public Contacto[] getContactos() {
        return contactos;
    }
}
