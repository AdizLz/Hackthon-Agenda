import java.util.Scanner;

// [SOLUCIÓN - OOP / Legibilidad]: Falta documentación Javadoc de la clase. Además, usar exclusivamente
// métodos estáticos en el Main convierte tu aplicación en programación estructurada (procedural), no orientada a objetos.
// Solución: Crear una clase 'InterfazConsola' o 'ControladorAgenda' para manejar la vista y mantener Main solo como el punto de entrada.
public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            mostrarMenu();

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                mostrarError("Debes ingresar un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarContacto(scanner, agenda);
                    break;
                case 2:
                    buscarContacto(scanner, agenda);
                    break;
                case 3:
                    listarContactos(agenda);
                    break;
                case 4:
                    eliminarContacto(scanner, agenda);
                    break;
                case 5:
                    modificarTelefono(scanner, agenda);
                    break;
                case 6:
                    mostrarEstadoAgenda(agenda);
                    break;
                case 0:
                    mostrarExito("Gracias por usar la agenda. ¡Hasta luego!");
                    break;
                default:
                    mostrarError("Opción no válida. Intenta nuevamente.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println();
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║       AGENDA DE CONTACTOS      ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║ 1. Añadir contacto             ║");
        System.out.println("║ 2. Buscar contacto             ║");
        System.out.println("║ 3. Listar contactos            ║");
        System.out.println("║ 4. Eliminar contacto           ║");
        System.out.println("║ 5. Modificar teléfono          ║");
        System.out.println("║ 6. Ver estado de la agenda     ║");
        System.out.println("║ 0. Salir                       ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Elige una opción: ");
    }

    public static void agregarContacto(Scanner scanner, Agenda agenda) {
        mostrarTitulo("Añadir contacto");

        String nombre = leerDato(scanner, "Nombre: ");
        String apellido = leerDato(scanner, "Apellido: ");
        String telefono = leerDato(scanner, "Teléfono: ");

        try {
            // [SOLUCIÓN - Camelcase]: Recuerda cambiar 'añadirContacto' a 'agregarContacto' en la clase Agenda como se indicó antes.
            agenda.añadirContacto(new Contacto(nombre, apellido, telefono));
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    public static void buscarContacto(Scanner scanner, Agenda agenda) {
        mostrarTitulo("Buscar contacto");

        String nombre = leerDato(scanner, "Nombre: ");
        String apellido = leerDato(scanner, "Apellido: ");

        agenda.buscaContacto(nombre, apellido);
    }

    public static void listarContactos(Agenda agenda) {
        mostrarTitulo("Contactos guardados");
        agenda.listarContactos();
    }

    public static void eliminarContacto(Scanner scanner, Agenda agenda) {
        mostrarTitulo("Eliminar contacto");

        String nombre = leerDato(scanner, "Nombre: ");
        String apellido = leerDato(scanner, "Apellido: ");

        try {
            // [CRÍTICO - Buenas prácticas de OOP / Lógica débil]: Crear una instancia "dummy" pasando un teléfono falso ("0") es un hack pésimo (anti-patrón).
            // Caso límite ignorado: Si más adelante actualizas la validación de Contacto para exigir un número de 10 dígitos,
            // esta línea lanzará una excepción al instanciar el dummy y JAMÁS podrás eliminar un contacto.
            // Solución: Cambiar la firma en Agenda a 'agenda.eliminarContacto(String nombre, String apellido)' para evitar
            // crear objetos falsos solo para buscar y destruir.
            agenda.eliminarContacto(new Contacto(nombre, apellido, "0"));
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    public static void modificarTelefono(Scanner scanner, Agenda agenda) {
        mostrarTitulo("Modificar teléfono");

        String nombre = leerDato(scanner, "Nombre: ");
        String apellido = leerDato(scanner, "Apellido: ");
        String nuevoTelefono = leerDato(scanner, "Nuevo teléfono: ");

        try {
            agenda.modificarTelefono(nombre, apellido, nuevoTelefono);
        } catch (IllegalArgumentException e) {
            // [SOLUCIÓN - Casos límite ignorados]: La validación del nuevo teléfono ocurre DESPUÉS de buscar dentro de 'Agenda'.
            // Si el usuario ingresa letras aquí, iterará inútilmente sobre toda la agenda buscando a la persona, la encontrará,
            // intentará llamar al setter, fallará lanzando la excepción y cancelará todo.
            // Solución: Validar el formato de 'nuevoTelefono' ANTES de llamar a 'agenda.modificarTelefono'.
            mostrarError(e.getMessage());
        }
    }

    public static void mostrarEstadoAgenda(Agenda agenda) {
        mostrarTitulo("Estado de la agenda");

        System.out.println("Espacios libres: " + agenda.espaciosLibres());

        if (agenda.agendaLlena()) {
            mostrarError("La agenda está llena. No hay espacio disponible para nuevos contactos.");
        } else {
            mostrarExito("Todavía puedes agregar nuevos contactos.");
        }
    }

    public static String leerDato(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public static void mostrarTitulo(String titulo) {
        System.out.println();
        System.out.println("────────────────────────────────");
        System.out.println(" " + titulo);
        System.out.println("────────────────────────────────");
    }

    public static void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }

    public static void mostrarExito(String mensaje) {
        System.out.println("[OK] " + mensaje);
    }
}