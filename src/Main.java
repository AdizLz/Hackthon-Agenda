import java.util.List;
import java.util.Scanner;

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

            try {
                switch (opcion) {
                    case 1: agregarContacto(scanner, agenda); break;
                    case 2: buscarContacto(scanner, agenda); break;
                    case 3: listarContactos(agenda); break;
                    case 4: eliminarContacto(scanner, agenda); break;
                    case 5: modificarTelefono(scanner, agenda); break;
                    case 6: mostrarEstadoAgenda(agenda); break;
                    case 0: mostrarExito("¡Hasta luego!"); break;
                    default: mostrarError("Opción no válida.");
                }
            } catch (Exception e) {
                mostrarError(e.getMessage());
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════╗");
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

    private static void agregarContacto(Scanner scanner, Agenda agenda) {
        String nombre = leerDato(scanner, "Nombre: ");
        String telefono = leerDato(scanner, "Teléfono: ");
        agenda.agregarContacto(new Contacto(nombre, telefono));
        mostrarExito("Contacto añadido correctamente.");
    }

    private static void buscarContacto(Scanner scanner, Agenda agenda) {
        String nombre = leerDato(scanner, "Nombre: ");
        String telefono = agenda.buscaContacto(nombre);

        if (telefono != null) {
            mostrarExito("Teléfono de " + nombre + ": " + telefono);
        } else {
            mostrarError("No se encontró ningún contacto con ese nombre.");
        }
    }

    private static void listarContactos(Agenda agenda) {
        List<Contacto> lista = agenda.listarContactos();

        if (lista.isEmpty()) {
            System.out.println("[INFO] La agenda está vacía.");
        } else {
            System.out.println("\n--- Contactos Guardados ---");
            lista.forEach(System.out::println);
        }
    }

    private static void eliminarContacto(Scanner scanner, Agenda agenda) {
        String nombre = leerDato(scanner, "Nombre: ");

        if (agenda.eliminarContacto(new Contacto(nombre, "0000000"))) {
            mostrarExito("Contacto eliminado correctamente.");
        } else {
            mostrarError("No se encontró el contacto para eliminar.");
        }
    }

    private static void modificarTelefono(Scanner scanner, Agenda agenda) {
        String nombre = leerDato(scanner, "Nombre: ");
        String nuevoTelefono = leerDato(scanner, "Nuevo teléfono: ");
        agenda.modificarTelefono(nombre, nuevoTelefono);
        mostrarExito("Teléfono actualizado correctamente.");
    }

    private static void mostrarEstadoAgenda(Agenda agenda) {
        System.out.println("\nEspacios libres: " + agenda.espacioLibres());
        if (agenda.agendaLlena()) {
            mostrarError("La agenda está llena. No hay espacio disponible.");
        } else {
            mostrarExito("Todavía puedes agregar nuevos contactos.");
        }
    }

    private static String leerDato(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }

    private static void mostrarExito(String mensaje) {
        System.out.println("[OK] " + mensaje);
    }
}