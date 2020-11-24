/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import Entidades.Producto;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;

/**
 *
 * @author Home
 */
public class ProductoComboEditor extends JTextField implements ComboBoxEditor{

    List<Object> productos;
    JTextField tf;
    
    public ProductoComboEditor(List<Object> productos) {
        tf = new JTextField("");
        this.productos = productos;
    }
    
    
    
    @Override
    public Component getEditorComponent() {
        return this;
    }

    @Override
    public void setItem(Object o) {
        Producto p = (Producto) o;
        tf.setText(""+p.getIdProducto()+"    -     "+p.getNombre());
    }

    @Override
    public Object getItem() {
        String id = tf.getText().split("|")[0].trim();
        try{
            int idd = Integer.parseInt(id);
            return productos.get(productos.indexOf(new Producto(idd)));
        }catch(Exception e){
            return "";
        }
    }

    @Override
    public void selectAll() {
        tf.selectAll();
    }

    @Override
    public void addActionListener(ActionListener al) {
        tf.addActionListener(al);
    }

    @Override
    public void removeActionListener(ActionListener al) {
        tf.removeActionListener(al);
    }
    
    
}
