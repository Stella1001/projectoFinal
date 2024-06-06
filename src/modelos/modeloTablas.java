package modelos;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class modeloTablas extends AbstractTableModel {

    private Vector<Usuario> datos = new Vector<>();
    private String nombreColumnas[] = {"Account Type", "Name", "Password", "Email", "Gender", "Share Data"};

    @Override
    public String getColumnName(int col){

        return nombreColumnas[col];
    }
    public void addRow(Usuario usuario){

        datos.add(usuario);
        fireTableRowsInserted(datos.size() - 1, datos.size() - 1);

    }
    @Override
    public int getRowCount() {

        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }

    @Override
    public Object getValueAt(int fila, int col) {
        Usuario usuario = datos.elementAt(fila);

        switch (col){

            case 0:
                return usuario.getTipoCuenta();

            case 1:
                return usuario.getNombre();

            case 2:
                return usuario.getContrasena();

            case 3:
                return usuario.getEmail();

            case 4:
                return usuario.getGenero();

            case 5:
                return usuario.isComparteDatos();
        }
        return null;
    }

    public void setValueAt(Object valor, int fila, int col){

        Usuario usuario = datos.elementAt(fila);

        try{
            switch (col){

                case 0:

                    usuario.setTipoCuenta((String)valor);
                    break;

                case 1:

                    usuario.setNombre((String)valor);
                    break;

                case 2:

                    usuario.setContrasena((String)valor);
                    break;

                case 3:

                    usuario.setEmail((String)valor);
                    break;

                case 4:

                    usuario.setGenero((String)valor);
                    break;

                case 5:

            }
            fireTableCellUpdated(fila,col);
        }catch (IllegalArgumentException ex){
            JOptionPane.showMessageDialog(null,"Error" + ex.getMessage());
        }
    }

    @Override
    public boolean isCellEditable(int fila, int col){

        //if(col == 4){

        //}
        return false;
    }

    @Override
    public Class<?> getColumnClass(int col){


        if(col != 5){
            return String.class;
        }

        else{
            return Boolean.class;
        }

    }

    public void clearTable() {
        datos.clear();
        fireTableDataChanged();
    }
}
