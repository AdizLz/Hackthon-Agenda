import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda(); // tamaño por defecto: 10
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("╔══════════════════════════╗");
            System.out.println("║     AGENDA CONTACTOS     ║");
            System.out.println("╠══════════════════════════╣");
            System.out.println("║ 1. Añadir contacto        ║");
            System.out.println("║ 2. Buscar contacto        ║");
            System.out.println("║ 3. Listar contactos       ║");
            System.out.println("║ 4. Eliminar contacto      ║");
            System.out.println("║ 5. Ver espacios libres    ║");
            System.out.println("║ 0. Salir                  ║");
            System.out.println("╚══════════════════════════╝");
            System.out.print("Elige una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String tel = scanner.nextLine();
                    agenda.añadirContacto(new Contacto(nombre, tel));
                    break;

                case 2:
                    System.out.print("Nombre a buscar: ");
                    String buscar = scanner.nextLine();
                    agenda.buscaContacto(buscar);
                    break;

                case 3:
                    agenda.listarContactos();
                    break;

                case 4:
                    System.out.print("Nombre a eliminar: ");
                    String eliminar = scanner.nextLine();
                    agenda.eliminarContacto(new Contacto(eliminar, ""));
                    break;

                case 5:
                    System.out.println("Espacios libres: " + agenda.espaciosLibres());
                    System.out.println("¿Agenda llena? " + (agenda.agendaLlena() ? "Sí" : "No"));
                    break;

                case 0:
                    System.out.println("¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}