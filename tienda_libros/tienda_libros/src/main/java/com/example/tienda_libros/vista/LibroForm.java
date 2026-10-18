package com.example.tienda_libros.vista;
import com.example.tienda_libros.modelo.Libro;
import com.example.tienda_libros.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class LibroForm extends JFrame {
    LibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private JTextField idTexto;
    private JTextField libroTexto;
    private JTextField autorTexto;
    private JTextField precioTexto;
    private JTextField existenciasTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroForm(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
        iniciarForma();
        agregarButton.addActionListener(e -> {
            agregarLibro();
        });
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSeleccionado();
            }
        });

        modificarButton.addActionListener(e -> {
            modificarLibro();
        });
        eliminarButton.addActionListener(e -> {
            eliminarLibro();
        });
    }

    private void iniciarForma() {
        panel = new JPanel();
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setVisible(true);
        setSize(900, 700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = toolkit.getScreenSize();
        int x = (tamanioPantalla.width - getWidth())/ 2;
        int y = (tamanioPantalla.height - getHeight())/ 2;
        setLocation(x, y);
        setVisible(true);
    }

    private void agregarLibro(){
        //Leer valores del formulario
        if (libroTexto.getText().isEmpty() || autorTexto.getText().isEmpty() || precioTexto.getText().isEmpty() || existenciasTexto.getText().isEmpty()){
            mostrarMensaje("Complete todos los campos");
            libroTexto.requestFocusInWindow();
            return;
        }

//        var nombreLibro = libroTexto.getText();
//        var autor = autorTexto.getText();
//        var precio = Double.parseDouble(precioTexto.getText());
//        var existencias = Integer.parseInt(existenciasTexto.getText());

        //Crear el objeto libro
        //var libro = new Libro(null, nombreLibro, autor, precio, existencias);
//        libro.setnombrelibro(nombrelibro);
//        libro.setautor(autor);
//        libro.setprecio(precio);
//        libro.setexistencias(existencias);
        var libro = getText();
        Integer tra = libro.getIdLibro();
        if (tra != null){
            mostrarMensaje("EL libro ya ha sido agregado");
            limpiarFormulario();
            listarLibros();
            return;
        }
        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Se agrego el libro");
        limpiarFormulario();
        listarLibros();
    }

    private Libro getText(){
        Integer id = null;
        var getId = idTexto.getText().trim();
        if (!getId.isEmpty()){
            id = Integer.parseInt(getId);
        }

        var nombreLibro = libroTexto.getText();
        var autor = autorTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var existencias = Integer.parseInt(existenciasTexto.getText());

        return new Libro(id, nombreLibro, autor, precio, existencias);
    }

    private void cargarLibroSeleccionado(){
        //Indices de columnas inician en 0
        var fila = tablaLibros.getSelectedRow();
        if (fila != -1){ //Regresa -1 si no se selecciono ningun registro

            String idLibro = tablaLibros.getModel().getValueAt(fila, 0).toString();
            idTexto.setText(idLibro);

            String libro = tablaLibros.getModel().getValueAt(fila, 1).toString();
            libroTexto.setText(libro);

            String autor = tablaLibros.getModel().getValueAt(fila, 2).toString();
            autorTexto.setText(autor);

            String precio = tablaLibros.getModel().getValueAt(fila, 3).toString();
            precioTexto.setText(precio);

            String existencias = tablaLibros.getModel().getValueAt(fila, 4).toString();
            existenciasTexto.setText(existencias);
        }
    }

    private void modificarLibro() {
        if (libroTexto.getText().isEmpty() || autorTexto.getText().isEmpty() || precioTexto.getText().isEmpty() || existenciasTexto.getText().isEmpty()){
            mostrarMensaje("Seleccione un registro...");
            libroTexto.requestFocusInWindow();
            return;
        }
//        var id = integer.parseint(idtexto.gettext());
//        var nombrelibro = librotexto.gettext();
//        var autor = autortexto.gettext();
//        var precio = double.parsedouble(preciotexto.gettext());
//        var existencias = integer.parseint(existenciastexto.gettext());
//
//        //crear el objeto libro
//        var libro = new libro(id, nombrelibro, autor, precio, existencias);
        var libro = getText();


        this.libroServicio.guardarLibro(libro);
        mostrarMensaje("Se modifico el libro...");
        limpiarFormulario();
        listarLibros();

    }

    private void eliminarLibro() {
        if (idTexto.getText().isEmpty()){
            mostrarMensaje("Seleccione un registro...");
            return;
        }

        Integer idLibro = Integer.parseInt(idTexto.getText());
        Libro lib = this.libroServicio.buscarLibroPorId(idLibro);

        if (lib == null){
            mostrarMensaje("ERROR VUELVA A INTENTAR...");
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this, "Desea eliminar el registro?", "Advertencia", JOptionPane.YES_NO_CANCEL_OPTION);
        if (resp == JOptionPane.YES_OPTION){
            this.libroServicio.eliminarLibro(lib);
            mostrarMensaje("Libro eliminado...");
            limpiarFormulario();
            listarLibros();
        }
    }

    private void limpiarFormulario(){
        idTexto.setText("");
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        existenciasTexto.setText("");
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE );
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //Elemento idTexto oculto
        idTexto = new JTextField("");
        idTexto.setVisible(false);

        this.tablaModeloLibros = new DefaultTableModel(0,5){
            @Override
            public boolean isCellEditable(int row, int column){return false;}
        };
        String[] cabeceros = {"Id", "Libro", "Autor", "Precio", "Existencias"};
        this.tablaModeloLibros.setColumnIdentifiers(cabeceros);
        this.tablaLibros = new JTable(tablaModeloLibros);
        //Evitar seleccion multiple
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarLibros();


    }

    private void listarLibros() {
        //Limpiar la tabla
        tablaModeloLibros.setRowCount(0);
        //Obtener los libros
        var libros = libroServicio.listarLibros();
        libros.forEach(libro -> {
            Object[] filaLibro = {
                    libro.getIdLibro(),
                    libro.getNombreLibro(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getExistencias()
            };
            this.tablaModeloLibros.addRow(filaLibro);
        });
    }
}


















