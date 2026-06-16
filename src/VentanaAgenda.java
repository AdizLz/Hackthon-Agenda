import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ventana principal (Swing) de la aplicación. Muestra un formulario
 * con campos de nombre/teléfono, un área de resultados y botones
 * para ejecutar las distintas operaciones sobre la Agenda.
 */
public class VentanaAgenda extends JFrame {

    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextArea areaResultados;
    private Agenda agenda;

    public VentanaAgenda(Agenda agenda) {
        this.agenda = agenda;
        configurarVentana();
        inicializarComponentes();
    }

    /**
     * Define las propiedades básicas de la ventana: título, tamaño,
     * comportamiento al cerrar y posición inicial (centrada en pantalla).
     */
    private void configurarVentana() {
        setTitle("Agenda de Contactos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Construye y organiza todos los componentes visuales de la ventana:
     * título, formulario de entrada, área de resultados y panel de botones.
     */
    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("AGENDA DE CONTACTOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Formulario con los campos de nombre y teléfono
        JPanel formulario = new JPanel(new GridLayout(2, 2, 10, 10));
        formulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        formulario.add(txtNombre);

        formulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        formulario.add(txtTelefono);

        // Panel central: formulario arriba + área de resultados (con scroll) debajo
        JPanel centro = new JPanel(new BorderLayout(0, 15));
        centro.add(formulario, BorderLayout.NORTH);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false); // Solo de lectura: aquí solo se muestran mensajes/resultados
        JScrollPane scroll = new JScrollPane(areaResultados);
        centro.add(scroll, BorderLayout.CENTER);

        panelPrincipal.add(centro, BorderLayout.CENTER);
        panelPrincipal.add(crearPanelBotones(), BorderLayout.SOUTH);

        add(panelPrincipal);
        setVisible(true);
    }

    /**
     * Crea el panel inferior con los botones de acción y conecta
     * cada uno con su respectivo método mediante un ActionListener.
     *
     * @return panel ya armado, listo para agregarse a la ventana
     */
    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new GridLayout(2, 4, 10, 10));

        JButton btnAgregar = new JButton("Añadir");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnListar = new JButton("Listar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEstado = new JButton("Estado");
        JButton btnLimpiar = new JButton("Limpiar UI");
        JButton btnSalir = new JButton("Salir");

        btnAgregar.addActionListener(e -> accionAgregar());
        btnBuscar.addActionListener(e -> accionBuscar());
        btnListar.addActionListener(e -> accionListar());
        btnEliminar.addActionListener(e -> accionEliminar());
        btnModificar.addActionListener(e -> accionModificar());
        btnEstado.addActionListener(e -> accionEstado());
        btnLimpiar.addActionListener(e -> areaResultados.setText("")); // Solo limpia la UI, no toca la agenda
        btnSalir.addActionListener(e -> dispose());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnListar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEstado);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSalir);

        return panelBotones;
    }

    /**
     * Toma el nombre y teléfono ingresados, crea un Contacto y lo agrega
     * a la agenda. Si la validación o el agregado fallan (excepción),
     * se muestra el error en un diálogo en vez de romper la aplicación.
     */
    private void accionAgregar() {
        try {
            Contacto contacto = new Contacto(txtNombre.getText(), txtTelefono.getText());
            agenda.agregarContacto(contacto);

            areaResultados.append("Contacto agregado: " + contacto.getNombre() + "\n");
            txtNombre.setText("");
            txtTelefono.setText("");

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    /**
     * Busca el contacto cuyo nombre coincide con el campo de texto
     * y muestra su teléfono en el área de resultados.
     */
    private void accionBuscar() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            mostrarError("Ingresa un nombre para buscar.");
            return;
        }

        String telefono = agenda.buscaContacto(nombre);
        if (telefono != null) {
            areaResultados.append("Búsqueda exitosa -> " + nombre + ": " + telefono + "\n");
        } else {
            areaResultados.append("No se encontró el contacto: " + nombre + "\n");
        }
    }

    /**
     * Imprime en el área de resultados todos los contactos guardados
     * (ya vienen ordenados alfabéticamente desde Agenda.listarContactos()).
     */
    private void accionListar() {
        List<Contacto> lista = agenda.listarContactos();
        areaResultados.append("--- Lista de Contactos ---\n");
        if (lista.isEmpty()) {
            areaResultados.append("La agenda está vacía.\n");
        } else {
            for (Contacto c : lista) {
                areaResultados.append(c.toString() + "\n");
            }
        }
    }

    /**
     * Elimina el contacto cuyo nombre coincide con el campo de texto.
     * Se crea un Contacto "temporal" con un teléfono cualquiera (válido)
     * solo para poder usar equals() y ubicar el contacto real por nombre.
     */
    private void accionEliminar() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            mostrarError("Ingresa un nombre para eliminar.");
            return;
        }

        if (agenda.eliminarContacto(new Contacto(nombre, "0000000"))) {
            areaResultados.append("Contacto eliminado: " + nombre + "\n");
        } else {
            areaResultados.append("No se encontró el contacto para eliminar: " + nombre + "\n");
        }
    }

    /**
     * Actualiza el teléfono del contacto indicado en el campo de nombre,
     * usando el valor ingresado en el campo de teléfono.
     */
    private void accionModificar() {
        try {
            agenda.modificarTelefono(txtNombre.getText(), txtTelefono.getText());
            areaResultados.append("Teléfono actualizado para: " + txtNombre.getText() + "\n");
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    /**
     * Muestra en el área de resultados cuántos espacios libres quedan
     * y si la agenda está llena o no.
     */
    private void accionEstado() {
        String estado = "Espacios libres: " + agenda.espacioLibres() + "\n";
        estado += agenda.agendaLlena() ? "La agenda está LLENA.\n" : "Hay espacio disponible.\n";
        areaResultados.append(estado);
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo emergente.
     *
     * @param mensaje texto a mostrar en el diálogo
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}