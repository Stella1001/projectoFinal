package controlador;

import javax.swing.*;

public class RegistroException extends RuntimeException{

    public RegistroException(String msg){

        JOptionPane.showMessageDialog(null,msg);
    }
}
