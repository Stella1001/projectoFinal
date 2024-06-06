package vista;

import modelos.modeloTablas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaMostrar extends JPanel {

    private JButton botonFormulario;
    private JButton botonGuardar;

    private modeloTablas modeloTabla;
    private JTable tablaUsuarios;

    private JButton cambiarArchivo;
    private JButton limpiarTabla;
    private JButton botonGenerarPDF;
    public VistaMostrar(){

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        inicializarTabla();
        JPanel panelBotones = new JPanel();

        botonFormulario = new JButton("Register");
        panelBotones.add(botonFormulario);
        botonGuardar = new JButton("Save Changes");
        panelBotones.add(botonGuardar);
        cambiarArchivo = new JButton("Change File");
        panelBotones.add(cambiarArchivo);
        limpiarTabla = new JButton("Clean Table");
        panelBotones.add(limpiarTabla);
        botonGenerarPDF = new JButton("Generate PDF");
        panelBotones.add(botonGenerarPDF);
        add(panelBotones, BorderLayout.SOUTH);
    }
    public void inicializarTabla(){
        modeloTabla = new modeloTablas();
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        add(scroll,BorderLayout.CENTER);
    }
    public void agregarListener(ActionListener listener) {
        botonGuardar.addActionListener(listener);
        botonFormulario.addActionListener(listener);
        cambiarArchivo.addActionListener(listener);
        limpiarTabla.addActionListener(listener);
        botonGenerarPDF.addActionListener(listener);
    }

    public modeloTablas getModeloTabla(){
        return modeloTabla;
    }
}
