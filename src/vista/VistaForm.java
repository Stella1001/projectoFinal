package vista;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class VistaForm extends JFrame {

        static JTextField text1, text2, text3,text4;
        static JTextArea area;
        static JComboBox<String> generos;
        static ButtonGroup cuenta;
        static JRadioButton boton1, boton2;
        static JCheckBox cb;
        static JButton limpiar;
        static JButton signIn;



        public VistaForm(){

                setLayout(null);

                cuenta = new ButtonGroup();
                boton1 = new JRadioButton("Personal");
                boton1.setBounds(200, 100, 100, 30);
                cuenta.add(boton1);
                add(boton1);

                boton2 = new JRadioButton("Company");
                boton2.setBounds(300, 100, 100, 30);
                cuenta.add(boton2);
                add(boton2);

                JLabel titulo = new JLabel("Sign In");
                titulo.setFont(new Font("Arial", Font.PLAIN, 30));
                titulo.setBounds(250,0,150,50);
                add(titulo);

                JLabel usuario = new JLabel("Username: ");
                usuario.setFont(new Font("Arial", Font.PLAIN, 20));
                usuario.setBounds(30,150,120,30);
                add(usuario);

                JLabel contrasena = new JLabel("Password: ");
                contrasena.setFont(new Font("Arial", Font.PLAIN, 20));
                contrasena.setBounds(30,200,100,30);
                add(contrasena);

                JLabel contrasena2 = new JLabel("Re-enter Password:");
                contrasena2.setFont(new Font("Arial", Font.PLAIN, 20));
                contrasena2.setBounds(30,250,200,30);
                add(contrasena2);

                JLabel correo = new JLabel("Email address: ");
                correo.setFont(new Font("Arial", Font.PLAIN, 20));
                correo.setBounds(30,300,150,30);
                add(correo);

                text1 = new JTextField();
                text1.setFont(new Font("Arial", Font.PLAIN, 20));
                text1.setText("");
                text1.setBounds(220,150,200,30);
                add(text1);

                text2 = new JPasswordField();
                text2.setFont(new Font("Arial", Font.PLAIN, 20));
                text2.setText("");
                text2.setBounds(220,200,200,30);
                add(text2);

                text3 = new JPasswordField();
                text3.setFont(new Font("Arial", Font.PLAIN, 20));
                text3.setText("");
                text3.setBounds(220,250,200,30);
                add(text3);

                text4 = new JTextField();
                text4.setFont(new Font("Arial", Font.PLAIN, 20));
                text4.setText("");
                text4.setBounds(220,300,200,30);
                add(text4);

                JLabel genero = new JLabel("Gender:");
                genero.setFont(new Font("Arial", Font.PLAIN, 20));
                genero.setBounds(30,350,100,30);
                add(genero);

                String[] opcionesGenero = {"Male", "Female", "Other", "Prefer not say"};
                generos = new JComboBox<String>(opcionesGenero);
                generos.setSelectedIndex(-1);
                generos.setBounds(220,350,150,30);
                add(generos);

                JLabel otro = new JLabel("If other:");
                otro.setFont(new Font("Arial", Font.PLAIN, 20));
                otro.setBounds(100,400,100,30);
                add(otro);

                area = new JTextArea();
                area.setFont(new Font("Arial", Font.PLAIN, 20));
                area.setText("");
                area.setBounds(220,400,200,30);

                add(area);

                signIn = new JButton("Sign Up");
                signIn.setBackground(Color.BLACK);
                signIn.setForeground(Color.white);
                signIn.setBounds(100,500,200,30);
                signIn.setActionCommand("signUp");
                add(signIn);

                limpiar = new JButton("Clean");
                limpiar.setBackground(Color.WHITE);
                limpiar.setForeground(Color.BLACK);
                limpiar.setBounds(350,500,150,30);
                limpiar.setActionCommand("clean");
                add(limpiar);



                JLabel text5 = new JLabel("Or Sign Up Using");
                text5.setFont(new Font("Arial", Font.PLAIN, 14));
                text5.setBounds(250,580,150,30);
                add(text5);

                cb = new JCheckBox("By checking this box you agree to share your data with third party services");
                cb.setBounds(50,650,500, 30);
                add(cb);


        }
        public void agregarListeners(ActionListener listener){

                signIn.addActionListener(listener);
                limpiar.addActionListener(listener);
        }


        public ArrayList<JComponent> getObjects(){

                ArrayList<JComponent> elementos = new ArrayList<>();

                elementos.add(boton1);
                elementos.add(boton2);
                elementos.add(text1);
                elementos.add(text2);
                elementos.add(text3);
                elementos.add(text4);
                elementos.add(area);
                elementos.add(generos);
                elementos.add(cb);
                return elementos;
        }

        public void clearElements(){

                text1.setText("");
                text2.setText("");
                text3.setText("");
                text4.setText("");
                area.setText("");
                boton1.setSelected(false);
                boton2.setSelected(false);
                cb.setSelected(false);
                generos.setSelectedIndex(-1);
        }

}
