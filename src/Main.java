import java.util.List;
import java.util.Scanner;

/**
 * Punto de entrada de la aplicación. Muestra un menú por consola
 * que permite interactuar con la Agenda: agregar, buscar, listar,
 * eliminar y modificar contactos, además de ver el estado de espacio.
 */
public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        // Bucle principal: se repite hasta que el usuario elija la opción 0 (Salir)
        do {
            mostrarMenu();

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                mostrarError("Debes ingresar un número válido.");
                continue; // Vuelve a mostrar el menú sin intentar ejecutar ninguna opción
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
                // Captura cualquier excepción lanzada por la Agenda o Contacto
                // (por ejemplo, validaciones fallidas o agenda llena)
                mostrarError(e.getMessage());
            }

        } while (opcion != 0);

        scanner.close();
    }

    /**
     * Imprime el menú de opciones disponibles para el usuario.
     */
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

    /**
     * Pide nombre y teléfono al usuario y agrega un nuevo contacto a la agenda.
     * Las validaciones (formato, duplicados, capacidad) las maneja Contacto/Agenda
     * y se propagan como excepciones hacia el bloque try-catch del main.
     */
    private static void agregarContacto(Scanner scanner, Agenda agenda) {
        String nombre = leerDato(scanner, "Nombre: ");
        String telefono = leerDato(scanner, "Teléfono: ");
        agenda.agregarContacto(new Contacto(nombre, telefono));
        mostrarExito("Contacto añadido correctamente.");
    }

    /**
     * Pide un nombre al usuario y muestra el teléfono asociado, si existe.
     */
    private static void buscarContacto(Scanner scanner, Agenda agenda) {
        String nombre = leerDato(scanner, "Nombre: ");
        String telefono = agenda.buscaContacto(nombre);

        if (telefono != null) {
            mostrarExito("Teléfono de " + nombre + ": " + telefono);
        } else {
            mostrarError("No se encontró ningún contacto con ese nombre.");
        }
    }

    /**
     * Muestra todos los contactos guardados, ya ordenados alfabéticamente
     * (el orden lo aplica Agenda.listarContactos()).
     */
    private static void listarContactos(Agenda agenda) {
        List<Contacto> lista = agenda.listarContactos();

        if (lista.isEmpty()) {
            System.out.println("[INFO] La agenda está vacía.");
        } else {
            System.out.println("\n--- Contactos Guardados ---");
            lista.forEach(System.out::println);
        }
    }

    /**
     * Pide un nombre al usuario y elimina el contacto correspondiente.
     * Se crea un Contacto "temporal" con un teléfono cualquiera (válido)
     * solo para poder usar equals() y localizar el contacto real por nombre,
     * ya que la igualdad de Contacto se basa únicamente en el nombre.
     */
    private static void eliminarContacto(Scanner scanner, Agenda agenda) {
        String nombre = leerDato(scanner, "Nombre: ");

        if (agenda.eliminarContacto(new Contacto(nombre, "0000000"))) {
            mostrarExito("Contacto eliminado correctamente.");
        } else {
            mostrarError("No se encontró el contacto para eliminar.");
        }
    }

    /**
     * Pide un nombre y un nuevo teléfono, y actualiza el contacto correspondiente.
     */
    private static void modificarTelefono(Scanner scanner, Agenda agenda) {
        String nombre = leerDato(scanner, "Nombre: ");
        String nuevoTelefono = leerDato(scanner, "Nuevo teléfono: ");
        agenda.modificarTelefono(nombre, nuevoTelefono);
        mostrarExito("Teléfono actualizado correctamente.");
    }

    /**
     * Muestra cuántos espacios libres quedan y si la agenda está llena o no.
     */
    private static void mostrarEstadoAgenda(Agenda agenda) {
        System.out.println("\nEspacios libres: " + agenda.espacioLibres());
        if (agenda.agendaLlena()) {
            mostrarError("La agenda está llena. No hay espacio disponible.");
        } else {
            mostrarExito("Todavía puedes agregar nuevos contactos.");
        }
    }

    /**
     * Imprime un mensaje y lee la línea ingresada por el usuario.
     * Centraliza la lectura de datos para no repetir System.out.print + scanner.nextLine().
     */
    private static String leerDato(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    /**
     * Imprime un mensaje de error con un prefijo uniforme.
     */
    private static void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }

    /**
     * Imprime un mensaje de éxito con un prefijo uniforme.
     */
    private static void mostrarExito(String mensaje) {
        System.out.println("[OK] " + mensaje);
    }
}