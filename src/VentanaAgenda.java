import javax.swing.*;
import java.awt.*;

public class VentanaAgenda extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;

    private JTextArea areaResultados;
    private Agenda agenda;

    public VentanaAgenda() {

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
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnSalir = new JButton("Salir");

        btnAgregar.addActionListener(e -> {
            try {
                Contacto contacto = new Contacto(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtTelefono.getText()
                );

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
            String resultado =
                    agenda.buscaContacto(
                            txtNombre.getText().trim(),
                            txtApellido.getText().trim()
                    );
            areaResultados.append(resultado + "\n");
        });


        btnListar.addActionListener(e -> {
            areaResultados.setText("");
            areaResultados.append(
                    agenda.listarContactos()
            );
        });

        btnEliminar.addActionListener(e -> {
            try {
                String resultado =
                        agenda.eliminarContacto(
                                new Contacto(
                                        txtNombre.getText().trim(),
                                        txtApellido.getText().trim(),
                                        "0"
                                )
                        );

                areaResultados.append(resultado + "\n");
                limpiarResultados();
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
                String resultado =
                        agenda.modificarTelefono(
                                txtNombre.getText().trim(),
                                txtApellido.getText().trim(),
                                txtTelefono.getText().trim()
                        );
                areaResultados.append(resultado + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });


        btnLimpiar.addActionListener(
                e -> areaResultados.setText("")
        );

        btnEstado.addActionListener(e -> {

            String mensaje =
                    "Espacios libres: " + agenda.espaciosLibres();

            if (agenda.agendaLlena()) {
                mensaje += "\nLa agenda está llena.";
            } else {
                mensaje += "\nLa agenda tiene espacio disponible.";
            }

            areaResultados.append(mensaje + "\n");
            limpiarResultados();

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
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);

        setVisible(true);
    }

    private void limpiarResultados(){
        areaResultados.setText("");
        txtNombre.requestFocus();
    }
}

