package controlador;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import modelos.Usuario;
import modelos.modeloTablas;
import vista.VistaForm;
import vista.VistaMostrar;

public class ControladorForm implements ActionListener  {

    private Usuario modelo;
    private VistaForm formulario;
    private VistaMostrar vista;
    static JTextField text1, text2, text3,text4;
    static JTextArea area;
    static JComboBox<String> generos;
    static ButtonGroup cuenta;
    static JRadioButton boton1, boton2;
    static JCheckBox cb;
    static JButton limpiar;
    static JButton signIn;

    private JFileChooser fileChooser;
    private File archivo;
    private modeloTablas modeloTabla;


    static ArrayList<Usuario> usuarios;

    public ControladorForm(VistaMostrar vista){

        this.vista = vista;
        this.vista.agregarListener(this);

        this.modeloTabla = vista.getModeloTabla();
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Documentos de texto", "txt"));
        archivo = new File("src/archivos/texto.json");
        cargarUsuarios();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton boton = (JButton) e.getSource();
        String texto = boton.getText();

        if(e.getActionCommand().equals("clean")){

            formulario.clearElements();
        }
        else if(e.getActionCommand().equals("Register")){

            formulario = new VistaForm();
            formulario.agregarListeners(this);
            formulario.setVisible(true);
            formulario.setSize(600,800);
            formulario.setLocationRelativeTo(vista);

        }
        else if(e.getActionCommand().equals("Save Changes")){

            if(modelo != null){
                guardarUsuario();
            }
            else{
                JOptionPane.showMessageDialog(null,"There are not new changes");
            }

        }
        else if(e.getActionCommand().equals("Change File")){

            cambiarArchivo();
        }
        else if(e.getActionCommand().equals("signUp")){

            try{
                registrar();


            }catch (RegistroException ex){
                System.out.println(ex.getMessage());
            }

        }
        else if(e.getActionCommand().equals("Clean Table")){

            cleanTable();
        }
        else if(e.getActionCommand().equals("Generate PDF")){
            generarPdf();
        }
    }

    private void generarPdf() {
        UIManager.put("FileChooser.cancelButtonText", "Cancelar");

        JFileChooser fileChooser = new JFileChooser("C:\\Users\\Uni\\Desktop");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter pdfs = new FileNameExtensionFilter("PDF Documents","pdf");
        fileChooser.addChoosableFileFilter(pdfs);
        fileChooser.setFileFilter(pdfs);


        int respuesta = fileChooser.showDialog(null, "Generate PDF");

        if(respuesta == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "Exporting process was cancelled");
            return;
        }

        try (
                PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileChooser.getSelectedFile()));
                Document doc = new Document(pdfDoc, PageSize.LETTER.rotate());
        ){


            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            Cell cell = new Cell(1, 7)
                    .add(new Paragraph("Gamestore users"))
                    .setFont(font)
                    .setFontSize(12)
                    .setFontColor(DeviceGray.WHITE)
                    .setBackgroundColor(new DeviceRgb(255,182,193))
                    .setTextAlignment(TextAlignment.CENTER);

            float[] anchoColumnas = {1,1,1,1,1,1,1};
            Table tabla = new Table(UnitValue.createPercentArray(anchoColumnas)).useAllAvailableWidth();
            tabla.addHeaderCell(cell);


            Cell[] headerFooter = new Cell[] {
                    new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("User #")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Account type")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Name")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Password")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Email")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Gender")),
                    new Cell().setTextAlignment(TextAlignment.CENTER).setBorderTop(new SolidBorder(1f)).setBackgroundColor(new DeviceGray(0.80f)).add(new Paragraph("Share data"))

            };

            for (Cell celda : headerFooter) {

                tabla.addHeaderCell(celda);
            }


            int indice = 1;
            for(Usuario u : usuarios) {
                tabla.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(indice))));
                tabla.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(u.getTipoCuenta())));
                tabla.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(u.getNombre())));
                tabla.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(u.getContrasena())));
                tabla.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(u.getEmail())));
                tabla.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(u.getGenero())));

                if(u.isComparteDatos()){
                    tabla.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Yes")));
                }
                else {
                    tabla.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph("No")));
                }

                indice++;
            }

            doc.add(tabla);

            if(Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(fileChooser.getSelectedFile());
                }catch(IOException ex) {
                    JOptionPane.showMessageDialog(vista, "File could not be opened");
                }
            }

        }catch(IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "PDF file could not be exported");
        }


    }

    public void cleanTable(){


        try {

            FileOutputStream fileOutputStream = new FileOutputStream(archivo.getPath());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            fileOutputStream.getChannel().truncate(0);
            objectOutputStream.close();
            fileOutputStream.close();
            modeloTabla.clearTable();
            JOptionPane.showMessageDialog(null,"The data table has been cleared successfully");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void registrar(){

        ArrayList<JComponent> objetos = formulario.getObjects();
        boton1 = (JRadioButton) objetos.get(0);
        boton2 = (JRadioButton) objetos.get(1);
        text1 = (JTextField) objetos.get(2);
        text2 = (JTextField) objetos.get(3);
        text3 = (JTextField) objetos.get(4);
        text4 = (JTextField) objetos.get(5);
        area = (JTextArea) objetos.get(6);
        generos = (JComboBox<String>) objetos.get(7);
        cb = (JCheckBox) objetos.get(8);
        if(validadCampos()){
            String tipoCuenta;
            String username;
            String contrasena;
            String email;
            String genero;
            boolean comparteDatos = false;

            if(boton1.isSelected()){
                tipoCuenta = "Personal";
            }
            else{
                tipoCuenta = "Company";
            }
            username = text1.getText();
            contrasena = text2.getText();
            email = text3.getText();
            if(generos.getSelectedIndex() != 2){
                genero = (String) generos.getSelectedItem();
            }
            else{
                genero = area.getText();
            }
            if(cb.isSelected()){
                comparteDatos = true;
            }


            modelo = new Usuario(tipoCuenta, username, contrasena, email, genero, comparteDatos);
            usuarios.add(modelo);
            JOptionPane.showMessageDialog(null,"User was registered successfully");
            formulario.dispose();
            formulario.clearElements();
            modeloTabla.addRow(modelo);
        }

    }

    public void cambiarArchivo(){
        int respuesta = fileChooser.showOpenDialog(vista);

        if(respuesta == JFileChooser.APPROVE_OPTION) {
            archivo = fileChooser.getSelectedFile();
            fileChooser.setCurrentDirectory(archivo);
            cargarUsuarios();
        }


    }
    public void guardarUsuario() {

        if(modelo != null){

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    objectMapper.writeValue(archivo, usuarios);
                    JOptionPane.showMessageDialog(formulario, "User has been registered");
                }catch(IOException ex) {
                    JOptionPane.showMessageDialog(formulario, "File could not be modified");
                    ex.printStackTrace();
                }


            modelo = null;
        }
        else{
            JOptionPane.showMessageDialog(vista, "No user has been registered");
        }
    }
    public void cargarUsuarios() {

        usuarios = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            usuarios = objectMapper.readValue(archivo, new TypeReference<ArrayList<Usuario>>() {});
        }catch(IOException ex) {
            ex.printStackTrace();
        }

        mostrarUsuarios();
    }

    public void mostrarUsuarios() {

        for (Usuario v : usuarios) {
           modeloTabla.addRow(v);

        }
        /*
        try(Connection conexion = ConexionDB.crearConexion();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ventas");
        ){

            while(rs.next()) {
                int id = rs.getInt("id");
                String descripcion = rs.getString("descripcion");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");
                double total = rs.getDouble("total");

                Usuario venta = new Usuario(id, descripcion, cantidad, precio, total);
                System.out.println(venta.toString());
                System.out.println("---------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectarse u obtener los datos: " + e.getMessage());
        }*/
    }

    public static boolean validadCampos() throws RegistroException {

        if(!boton1.isSelected() && !boton2.isSelected()){
            throw new RegistroException("The field of type of account has not been selected");

        }
        else if(text1.getText().isEmpty()){

            throw new RegistroException("The field of username is empty");
        }
        else if(text2.getText().isEmpty()){
            throw new RegistroException("The field of password is empty");
        }
        else if(text3.getText().isEmpty()){
            throw new RegistroException("The field of re-enter password is empty");
        }
        else if(text4.getText().isEmpty()){
            throw new RegistroException("The field of email is empty");
        }
        else if(generos.getSelectedIndex() == 2){

            if(area.getText().isEmpty()){
                throw new RegistroException("The field of 'other' is empty");
            }
        }
        else{
            return true;
        }

        return false;
    }

    private TableModelListener getTableModelListener() {

        TableModelListener modelListener = new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {

                if(e.getType() == TableModelEvent.UPDATE) {
                    int fila = e.getFirstRow();
                    int columna = e.getColumn();

                    if(columna != TableModelEvent.ALL_COLUMNS) {
                        //guardarCambios();
                        System.out.println("Fila: " + fila);
                        System.out.println("Columna: " + columna);
                        System.out.println("Valor: " + modeloTabla.getValueAt(fila, columna));

                    }
                }

            }
        };

        return modelListener;

    }
}



