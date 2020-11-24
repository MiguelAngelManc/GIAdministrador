/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import Entidades.Producto;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Home
 */
public class ProductoListCellRenderer extends DefaultListCellRenderer{
    
    public Component getListCellRendererComponent(
                                   JList list,
                                   Object value,
                                   int index,
                                   boolean isSelected,
                                   boolean cellHasFocus) {
        if (value instanceof Producto) {
            Producto p = (Producto) value;
            if(p.getIdProducto() == -1)
                value = "Lista de productos.";
            else
                value = ""+p.getIdProducto()+"    |     "+p.getNombre();
        }
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }
    
}
