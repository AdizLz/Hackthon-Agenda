import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Agenda {
    private List<Contacto> contactos;
    private int capacidadMaxima;

    public Agenda() {
        this(10);
    }

    public Agenda(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.contactos = new ArrayList<>(capacidadMaxima);
    }

    public void agregarContacto(Contacto c) {
        if (agendaLlena()) {
            throw new IllegalStateException("No se puede añadir: la agenda está llena.");
        }
        if (existeContacto(c)) {
            throw new IllegalArgumentException("Ya existe un contacto con ese nombre.");
        }
        contactos.add(c);
    }

    public boolean existeContacto(Contacto c) {
        return contactos.contains(c);
    }

    public List<Contacto> listarContactos() {
        List<Contacto> copia = new ArrayList<>(contactos);
        copia.sort(Comparator.comparing(Contacto::getNombre, String.CASE_INSENSITIVE_ORDER));
        return copia;
    }

    public String buscaContacto(String nombre) {
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c.getTelefono();
            }
        }
        return null;
    }

    public boolean eliminarContacto(Contacto c) {
        return contactos.remove(c);
    }

    public void modificarTelefono(String nombre, String nuevoTelefono) {
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                c.setTelefono(nuevoTelefono);
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró el contacto para modificar.");
    }

    public boolean agendaLlena() {
        return contactos.size() >= capacidadMaxima;
    }

    public int espacioLibres() {
        return capacidadMaxima - contactos.size();
    }
}