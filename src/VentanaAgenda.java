import javax.swing.*;
import java.awt.*;

// [SOLUCIÓN - OOP / Arquitectura]: Rompes el Principio de Responsabilidad Única (SRP).
// Tu ventana hace de Vista, de Controlador, e inicializa el Modelo todo en un solo bloque. Deberías separar esto (MVC)
// o al menos extraer la lógica de creación de la UI a métodos privados para no tener un constructor monolítico de casi 150 líneas.
public class VentanaAgenda extends JFrame {

    private JTextField txtNombre;
    // [CRÍTICO - Estructura]: Sigues arrastrando el campo 'apellido' a la interfaz, perpetuando el error de alterar los requerimientos originales del proyecto.
    private JTextField txtApellido;
    private JTextField txtTelefono;

    private JTextArea areaResultados;
    private Agenda agenda;

    public VentanaAgenda() {

        // [SOLUCIÓN - OOP (Acoplamiento)]: Instanciar la agenda directamente aquí genera un acoplamiento duro.
        // Si mañana tu modelo de datos cambia, tendrás que modificar la interfaz gráfica.
        // Lo correcto en ingeniería de software es inyectar la dependencia: public VentanaAgenda(Agenda agenda)
        agenda = new Agenda();

        setTitle("Agenda de Contactos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("AGENDA DE CONTACTOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));

        formulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        formulario.add(txtNombre);

        formulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        formulario.add(txtApellido);

        formulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        formulario.add(txtTelefono);

        JPanel centro = new JPanel(new BorderLayout());

        centro.add(formulario, BorderLayout.NORTH);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaResultados);

        centro.add(scroll, BorderLayout.CENTER);

        panelPrincipal.add(centro, BorderLayout.CENTER);

        JButton btnAgregar = new JButton("Añadir");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnListar = new JButton("Listar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEstado = new JButton("Estado");
        JButton btnSalir = new JButton("Salir");

        btnAgregar.addActionListener(e -> {
            try {
                Contacto contacto = new Contacto(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtTelefono.getText()
                );

                // [CRÍTICO - Lógica ciega / Puntos ciegos]: Tu método añadirContacto() en Agenda retorna 'void' y hace un System.out.println() si falla (ej. agenda llena o contacto duplicado).
                // Como ese método no lanza excepciones ni devuelve un booleano, esta línea asume erróneamente que siempre tiene éxito.
                // Tu GUI le mentirá al usuario imprimiendo "Contacto agregado correctamente" aunque la clase Agenda lo haya rechazado.
                agenda.añadirContacto(contacto);

                areaResultados.append(
                        "Contacto agregado: "
                                + contacto.getNombre()
                                + " "
                                + contacto.getApellido()
                                + "\n"
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Contacto agregado correctamente"
                );

                txtNombre.setText("");
                txtApellido.setText("");
                txtTelefono.setText("");

                // [SOLUCIÓN - Buenas prácticas]: Capturar 'Exception' genérica oculta errores de sistema imprevistos (NullPointer, etc).
                // Debes capturar específicamente 'IllegalArgumentException', que es la única que lanza intencionalmente tu validación en la clase Contacto.
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        btnBuscar.addActionListener(e -> {
            areaResultados.append(
                    "Buscando: "
                            + txtNombre.getText()
                            + " "
                            + txtApellido.getText()
                            + "\n"
            );

            // [FALLO ESTRUCTURAL FATAL - Integración]: buscaContacto() en tu clase Agenda imprime el resultado en la CONSOLA usando System.out.println.
            // El usuario de la interfaz gráfica JAMÁS verá el teléfono de la persona en el 'areaResultados'.
            // Una clase de lógica de negocios jamás debe interactuar con la consola si espera ser consumida por una GUI.
            // Solución: Agenda debe retornar un String o un objeto Contacto.
            agenda.buscaContacto(
                    txtNombre.getText(),
                    txtApellido.getText()
            );
        });

        btnListar.addActionListener(e -> {
            areaResultados.append("Listando contactos...\n");

            // [FALLO ESTRUCTURAL FATAL - Integración]: Exactamente el mismo problema. listarContactos() imprime en la consola.
            // El JTextArea de la ventana se quedará vacío. Tu botón no hace nada visible para el usuario.
            agenda.listarContactos();
        });

        btnEliminar.addActionListener(e -> {
            try {
                // [CRÍTICO - Anti-patrón]: Arrastras el parche de inyectar un objeto falso con el teléfono "0" para poder borrar.
                // Además, eliminarContacto también retorna void e imprime a consola. La GUI asume ciegamente que se logró la acción.
                agenda.eliminarContacto(
                        new Contacto(
                                txtNombre.getText(),
                                txtApellido.getText(),
                                "0"
                        )
                );

                areaResultados.append(
                        "Intentando eliminar: "
                                + txtNombre.getText()
                                + " "
                                + txtApellido.getText()
                                + "\n"
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        btnModificar.addActionListener(e -> {
            try {
                // [FALLO ESTRUCTURAL FATAL - Integración]: Mismo problema de diseño. modificarTelefono() no avisa a la GUI si tuvo éxito o si el contacto no existía. Imprime a consola.
                agenda.modificarTelefono(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtTelefono.getText()
                );

                areaResultados.append(
                        "Intentando modificar teléfono de: "
                                + txtNombre.getText()
                                + " "
                                + txtApellido.getText()
                                + "\n"
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        btnEstado.addActionListener(e -> {
            // [INFO]: Este es el ÚNICO bloque que funciona correctamente a nivel de integración entre backend y frontend,
            // precisamente porque espaciosLibres() y agendaLlena() sí retornan valores reales (int y boolean) en lugar de imprimir a consola.
            String mensaje =
                    "Espacios libres: " + agenda.espaciosLibres();

            if (agenda.agendaLlena()) {
                mensaje += "\nLa agenda está llena.";
            } else {
                mensaje += "\nLa agenda tiene espacio disponible.";
            }

            areaResultados.append(mensaje + "\n");

            JOptionPane.showMessageDialog(this, mensaje);
        });

        btnSalir.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new GridLayout(3, 3, 10, 10));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnListar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEstado);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        setVisible(true);
    }
}