import javax.swing.JOptionPane;

public class AgendaD {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        int opcion = -1; // nos aseguramos de que la agenda empiece en un estado neutral

        do {


            String[] opciones = {
                    "1. Añadir contacto", "2. Buscar contacto", "3. Listar contactos",
                    "4. Eliminar contacto", "5. Modificar teléfono", "6. Ver estado de la agenda", "0. Salir"
            };

            String seleccion = (String) JOptionPane.showInputDialog(
                    null, "Selecciona una opción:", "AGENDA",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            if (seleccion == null) {
                mostrarExito("Gracias por usar la agenda. ¡Hasta luego!");
                break;
            }
            opcion = Character.getNumericValue(seleccion.charAt(0));

            switch (opcion) {
                case 1:
                    agregarContacto(agenda);
                    break;
                case 2:
                    buscarContacto(agenda);
                    break;
                case 3:
                    listarContactos(agenda);
                    break;
                case 4:
                    eliminarContacto(agenda);
                    break;
                case 5:
                    modificarTelefono(agenda);
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

    }

    public static void agregarContacto(Agenda agenda) {


        String nombre = leerDato("Añadir contacto" , "Nombre: ");
        String apellido = leerDato("Añadir contacto" ,  "Apellido: ");
        String telefono = leerDato("Añadir contacto" , "Teléfono: ");

        try {
            agenda.añadirContacto(new Contacto(nombre, apellido, telefono));
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    public static void buscarContacto(Agenda agenda) {


        String nombre = leerDato("Buscar contacto", "Nombre: ");
        String apellido = leerDato("Buscar contacto", "Apellido: ");

        agenda.buscaContacto(nombre, apellido);
    }

    public static void listarContactos(Agenda agenda) {

        agenda.listarContactos();
    }

    public static void eliminarContacto(Agenda agenda) {


        String nombre = leerDato("Eliminar contacto" , "Nombre: ");
        String apellido = leerDato("Eliminar contacto" , "Apellido: ");
        // Se quita el scanner y se modifica con el titulo de la ventana que es Eliminar contacto
        try {
            agenda.eliminarContacto(new Contacto(nombre, apellido, "0"));
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    public static void modificarTelefono(Agenda agenda) {


        String nombre = leerDato("Modificar contacto" , "Nombre: ");
        String apellido = leerDato("Modificar contacto" , "Apellido: ");
        String nuevoTelefono = leerDato("Modificar contacto", "Nuevo teléfono: ");
        //Se quita el scannr y se pone el titulo de la ventana, en este caso Modificar contacto
        try {
            agenda.modificarTelefono(nombre, apellido, nuevoTelefono);
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    public static void mostrarEstadoAgenda(Agenda agenda) {


        System.out.println("Espacios libres: " + agenda.espaciosLibres());

        if (agenda.agendaLlena()) {
            mostrarError("La agenda está llena. No hay espacio disponible para nuevos contactos.");
        } else {
            mostrarExito("Todavía puedes agregar nuevos contactos.");
        }
    }

    // Cambia la consola por una cajita  de texto flotante
    public static String leerDato(String titulo, String mensaje) {
        return JOptionPane.showInputDialog(null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
    }

    // Cambia el texto ERROR por una ventanita  de alerta roja
    public static void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Cambia el texto OK por una ventana informativa azul
    public static void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
}

