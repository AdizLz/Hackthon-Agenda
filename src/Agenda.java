import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Representa una agenda de contactos con una capacidad máxima configurable.
 * Permite agregar, buscar, listar, modificar y eliminar contactos,
 * evitando duplicados (por nombre) y respetando el límite de espacio.
 */
public class Agenda {

    // Lista interna que almacena los contactos registrados
    private List<Contacto> contactos;

    // Número máximo de contactos que la agenda puede contener
    private int capacidadMaxima;

    /**
     * Constructor por defecto.
     * Crea una agenda con capacidad máxima de 10 contactos.
     */
    public Agenda() {
        this(10);
    }

    /**
     * Constructor que permite definir una capacidad máxima personalizada.
     *
     * @param capacidadMaxima número máximo de contactos permitidos
     */
    public Agenda(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        // Se inicializa el ArrayList con la capacidad indicada para optimizar memoria
        this.contactos = new ArrayList<>(capacidadMaxima);
    }

    /**
     * Agrega un nuevo contacto a la agenda.
     *
     * @param c contacto a agregar
     * @throws IllegalStateException   si la agenda ya alcanzó su capacidad máxima
     * @throws IllegalArgumentException si ya existe un contacto igual (mismo nombre)
     */
    public void agregarContacto(Contacto c) {
        if (agendaLlena()) {
            throw new IllegalStateException("No se puede añadir: la agenda está llena.");
        }
        if (existeContacto(c)) {
            throw new IllegalArgumentException("Ya existe un contacto con ese nombre.");
        }
        contactos.add(c);
    }

    /**
     * Verifica si un contacto ya existe en la agenda.
     * Depende de que Contacto implemente correctamente equals().
     *
     * @param c contacto a verificar
     * @return true si el contacto ya está en la lista, false en caso contrario
     */
    public boolean existeContacto(Contacto c) {
        return contactos.contains(c);
    }

    /**
     * Devuelve una copia de la lista de contactos, ordenada alfabéticamente
     * por nombre (sin distinguir mayúsculas/minúsculas).
     *
     * Se retorna una copia para no exponer ni permitir modificar
     * la lista interna original.
     *
     * @return lista de contactos ordenada por nombre
     */
    public List<Contacto> listarContactos() {
        List<Contacto> copia = new ArrayList<>(contactos);
        copia.sort(Comparator.comparing(Contacto::getNombre, String.CASE_INSENSITIVE_ORDER));
        return copia;
    }

    /**
     * Busca un contacto por nombre (ignorando mayúsculas/minúsculas)
     * y devuelve su número de teléfono.
     *
     * @param nombre nombre del contacto a buscar
     * @return el teléfono del contacto, o null si no se encontró
     */
    public String buscaContacto(String nombre) {
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c.getTelefono();
            }
        }
        // No se encontró ningún contacto con ese nombre
        return null;
    }

    /**
     * Elimina un contacto de la agenda.
     *
     * @param c contacto a eliminar
     * @return true si el contacto existía y fue eliminado, false si no se encontró
     */
    public boolean eliminarContacto(Contacto c) {
        return contactos.remove(c);
    }

    /**
     * Modifica el número de teléfono de un contacto existente,
     * identificándolo por su nombre.
     *
     * @param nombre        nombre del contacto a modificar
     * @param nuevoTelefono nuevo número de teléfono a asignar
     * @throws IllegalArgumentException si no se encuentra ningún contacto con ese nombre
     */
    public void modificarTelefono(String nombre, String nuevoTelefono) {
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                c.setTelefono(nuevoTelefono);
                return; // Se sale en cuanto se encuentra y modifica el contacto
            }
        }
        throw new IllegalArgumentException("No se encontró el contacto para modificar.");
    }

    /**
     * Indica si la agenda alcanzó su capacidad máxima.
     *
     * @return true si está llena, false si todavía hay espacio disponible
     */
    public boolean agendaLlena() {
        return contactos.size() >= capacidadMaxima;
    }

    /**
     * Calcula cuántos espacios libres quedan en la agenda.
     *
     * @return número de contactos que aún se pueden agregar
     */
    public int espacioLibres() {
        return capacidadMaxima - contactos.size();
    }
}