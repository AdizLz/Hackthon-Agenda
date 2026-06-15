/**
 * Gestiona un arreglo de contactos con capacidad fija.
 * Proporciona operaciones para añadir, buscar, listar y eliminar contactos,
 * así como consultar el estado de la agenda (llena / espacios libres).
 */
public class Agenda {
    private Contacto[] contactos;
    private int tamanio;

    /**
     * Crea una agenda con capacidad predeterminada de 10 contactos.
     */
    public Agenda() {
        this.tamanio = 10;
        this.contactos = new Contacto[tamanio];
    }

    /**
     * Crea una agenda con una capacidad personalizada.
     *
     * @param tamanio Número máximo de contactos que puede contener la agenda.
     */
    public Agenda(int tamanio) {
        this.tamanio = tamanio;
        this.contactos = new Contacto[tamanio];
    }

    /**
     * Añade un contacto a la agenda si hay espacio disponible
     * y no existe ya un contacto con el mismo nombre.
     *
     * @param c Contacto a añadir.
     */
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

    /**
     * Verifica si un contacto ya existe en la agenda.
     *
     * @param c Contacto a buscar.
     * @return {@code true} si el contacto existe.
     */
    public boolean existeContacto(Contacto c) {
        for (Contacto contacto : contactos) {
            if (contacto != null && contacto.equals(c)) return true;
        }
        return false;
    }

    /**
     * Muestra en consola todos los contactos almacenados en la agenda.
     * Si la agenda está vacía, muestra un mensaje indicándolo.
     */
    public void listarContactos() {
        System.out.println("\n📋 --- Agenda ---");
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

    /**
     * Busca un contacto por su nombre y muestra su teléfono en consola.
     *
     * @param nombre Nombre del contacto a buscar (no distingue mayúsculas).
     */
    public void buscaContacto(String nombre) {
        for (Contacto c : contactos) {
            if (c != null && c.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Teléfono de " + nombre + ": " + c.getTelefono());
                return;
            }
        }
        System.out.println("No se encontró ningún contacto con ese nombre.");
    }

    /**
     * Elimina un contacto de la agenda buscándolo por nombre.
     *
     * @param c Contacto a eliminar (se compara por nombre).
     */
    public void eliminarContacto(Contacto c) {
        for (int i = 0; i < tamanio; i++) {
            if (contactos[i] != null && contactos[i].equals(c)) {
                contactos[i] = null;
                System.out.println("🗑️ Contacto eliminado.");
                return;
            }
        }
        System.out.println("No se encontró el contacto para eliminar.");
    }

    /**
     * Indica si la agenda está completamente llena.
     *
     * @return {@code true} si no quedan espacios libres.
     */
    public boolean agendaLlena() {
        return espaciosLibres() == 0;
    }

    /**
     * Calcula cuántos espacios libres quedan en la agenda.
     *
     * @return Número de posiciones vacías en el arreglo.
     */
    public int espaciosLibres() {
        int libres = 0;
        for (Contacto c : contactos) {
            if (c == null) libres++;
        }
        return libres;
    }
}