import java.util.Arrays;
import java.util.Comparator;

// [SOLUCIÓN - Legibilidad]: Falta Javadoc/comentarios de bloque explicando el propósito general y responsabilidades de la clase.
public class Agenda {

    // [SOLUCIÓN - OOP (Colecciones) / Complejidad]: Es una mala idea usar un arreglo estático (Contacto[]) aquí si se pedían colecciones.
    // Esto te obliga a gestionar los valores null manualmente y rompe la eficiencia.
    // Solución: Reemplazar con 'private List<Contacto> contactos = new ArrayList<>();' o un HashMap si buscas por nombre.
    private Contacto[] contactos;
    private int tamanio;

    // [SOLUCIÓN - Complejidad]: Falta un atributo 'cantidadActual' para rastrear los elementos.
    // Contarlos iterando en cada operación es ineficiente.

    public Agenda() {
        this.tamanio = 10;
        this.contactos = new Contacto[tamanio];
    }

    public Agenda(int tamanio) {
        this.tamanio = tamanio;
        this.contactos = new Contacto[tamanio];
    }

    // [SOLUCIÓN - Camelcase / Buenas Prácticas]: Usar la 'ñ' en nombres de métodos puede causar problemas de codificación.
    // Renombrar a 'agregarContacto'.
    public void añadirContacto(Contacto c) { // [SOLUCIÓN - Legibilidad]: Variable 'c' no es descriptiva, usar 'nuevoContacto'.

        // [SOLUCIÓN - Bucles innecesarios / Complejidad]: Esta lógica es pésima para el rendimiento.
        // Llamar a agendaLlena() itera todo el arreglo O(N).
        // Llamar a existeContacto() vuelve a iterar todo O(N).
        // El bucle final vuelve a iterar O(N) para buscar un espacio. Son 3 recorridos para una sola inserción.
        // Solución: Mantener una variable del índice actual o, mejor aún, usar Colecciones (ArrayList.add() maneja esto nativamente sin bucles manuales).
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
        // [SOLUCIÓN - OOP (Colecciones)]: Con una colección (ej. ArrayList o HashSet) se reduce a 'return contactos.contains(c);'.
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

    // [SOLUCIÓN - Legibilidad / Complejidad]: Los métodos 'buscaContacto', 'eliminarContacto' y 'modificarTelefono'
    // duplican exactamente la misma lógica de iteración y validación de nulls.
    // Solución: Crear un método privado reutilizable: 'private Contacto encontrarContacto(String nombre, String apellido)' (Principio DRY).
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
                // [SOLUCIÓN - Complejidad]: Asignar 'null' deja huecos fragmentados en el arreglo, lo que hace que tu lógica
                // dependa perpetuamente de 'if (c != null)'. Usar Colecciones (ArrayList.remove) soluciona esto desplazando los elementos automáticamente.
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
        // [SOLUCIÓN - Bucles innecesarios]: Totalmente redundante y costoso iterar todo para contar.
        // Solución: Si mantienes una variable 'elementosActuales' que incrementas al añadir y decrementas al eliminar,
        // este método se reduce a 'return tamanio - elementosActuales;', una operación O(1) sin ningún bucle.
        int libres = 0;

        for (Contacto c : contactos) {
            if (c == null) {
                libres++;
            }
        }

        return libres;
    }

    public Contacto[] getContactos() {
        // [SOLUCIÓN - OOP (Encapsulamiento)]: Exponer la estructura de datos interna rompe el encapsulamiento.
        // Cualquier clase externa podría hacer agenda.getContactos()[0] = null y corromper el estado interno.
        // Solución: Retornar una copia inmutable (ej. contactos.clone() o Collections.unmodifiableList si usaras List).
        return contactos;
    }
}