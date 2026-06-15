public class Agenda {
    private Contacto[] contactos;
    private int tamanio;

    // Constructor con tamaño por defecto
    public Agenda() {
        this.tamanio = 10;
        this.contactos = new Contacto[tamanio];
    }

    // Constructor con tamaño personalizado
    public Agenda(int tamanio) {
        this.tamanio = tamanio;
        this.contactos = new Contacto[tamanio];
    }

    // Añade un contacto si hay espacio y no existe ya
    public void añadirContacto(Contacto c) {
        if (agendaLlena()) {
            System.out.println("No se puede añadir: la agenda está llena.");
            return;
        }
        if (existeContacto(c)) {
            System.out.println("Ya existe un contacto con ese nombre.");
            return;
        }
        for (int i = 0; i < tamanio; i++) {
            if (contactos[i] == null) {
                contactos[i] = c;
                System.out.println("Contacto añadido.");
                return;
            }
        }
    }

    // Indica si el contacto existe
    public boolean existeContacto(Contacto c) {
        for (Contacto contacto : contactos) {
            if (contacto != null && contacto.equals(c)) return true;
        }
        return false;
    }

    // Lista todos los contactos
    public void listarContactos() {
        System.out.println("\n--- Agenda ---");
        boolean hayContactos = false;
        for (Contacto c : contactos) {
            if (c != null) {
                System.out.println(c);
                hayContactos = true;
            }
        }
        if (!hayContactos) System.out.println("La agenda está vacía.");
        System.out.println("----------------\n");
    }

    // Busca por nombre y muestra teléfono
    public void buscaContacto(String nombre) {
        for (Contacto c : contactos) {
            if (c != null && c.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Teléfono de " + nombre + ": " + c.getTelefono());
                return;
            }
        }
        System.out.println("No se encontró ningún contacto con ese nombre.");
    }

    // Elimina un contacto
    public void eliminarContacto(String nombre) {
        for (int i = 0; i < tamanio; i++) {
            if (contactos[i] != null &&
                    contactos[i].getNombre().equalsIgnoreCase(nombre)) {
                contactos[i] = null;
                System.out.println("Contacto eliminado.");
                return;
            }
        }
        System.out.println("No se encontró el contacto para eliminar.");
    }

    // Indica si la agenda está llena
    public boolean agendaLlena() {
        return espaciosLibres() == 0;
    }

    // Indica cuántos espacios libres hay
    public int espaciosLibres() {
        int libres = 0;
        for (Contacto c : contactos) {
            if (c == null) libres++;
        }
        return libres;
    }
}