import java.util.Arrays;
import java.util.Comparator;

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
            System.out.println("[ERROR] No se puede añadir: la agenda está llena.");
            return;
        }

        if (existeContacto(c)) {
            System.out.println("[ERROR] Ya existe un contacto con ese nombre y apellido.");
            return;
        }

        for (int i = 0; i < tamanio; i++) {
            if (contactos[i] == null) {
                contactos[i] = c;
                System.out.println("[OK] Contacto añadido correctamente.");
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

    public void listarContactos() {
        Contacto[] contactosOrdenados = Arrays.stream(contactos)
                .filter(contacto -> contacto != null)
                .sorted(Comparator
                        .comparing(Contacto::getNombre, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Contacto::getApellido, String.CASE_INSENSITIVE_ORDER))
                .toArray(Contacto[]::new);

        if (contactosOrdenados.length == 0) {
            System.out.println("[INFO] La agenda está vacía.");
            return;
        }

        for (Contacto contacto : contactosOrdenados) {
            System.out.println(contacto);
        }
    }

    public void buscaContacto(String nombre, String apellido) {
        for (Contacto c : contactos) {
            if (c != null
                    && c.getNombre().equalsIgnoreCase(nombre)
                    && c.getApellido().equalsIgnoreCase(apellido)) {
                System.out.println("[OK] Teléfono de " + c.getNombre() + " " + c.getApellido() + ": " + c.getTelefono());
                return;
            }
        }

        System.out.println("[INFO] No se encontró ningún contacto con ese nombre y apellido.");
    }

    public void eliminarContacto(Contacto contactoEliminar) {
        for (int i = 0; i < tamanio; i++) {
            if (contactos[i] != null && contactos[i].equals(contactoEliminar)) {
                contactos[i] = null;
                System.out.println("[OK] Contacto eliminado correctamente.");
                return;
            }
        }

        System.out.println("[INFO] No se encontró el contacto para eliminar.");
    }

    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        for (Contacto c : contactos) {
            if (c != null
                    && c.getNombre().equalsIgnoreCase(nombre)
                    && c.getApellido().equalsIgnoreCase(apellido)) {
                c.setTelefono(nuevoTelefono);
                System.out.println("[OK] Teléfono actualizado correctamente.");
                return;
            }
        }

        System.out.println("[INFO] No se encontró el contacto para modificar.");
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
