/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Home
 */
public class FilterComboBox extends JComboBox {
    private List<? extends Object> array;

    public FilterComboBox(List<? extends Object> array) {
        super(array.toArray());
        this.array = array;
        this.setEditable(true);
        final JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
        textfield.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        comboFilter(textfield.getText(), ke);
                    }
                });
            }
        });

    }

public void comboFilter(String enteredText, KeyEvent ke) {
    if(ke.getKeyCode()==KeyEvent.VK_ENTER){
        return;
    }
    
    if (!this.isPopupVisible()) {
        this.showPopup();
    }
    
    for (int i = 0; i < array.size(); i++) {
        if (array.get(i).toString().toLowerCase().equals(enteredText.toLowerCase())) {
            return;
        }
    }
    
    List<Object> filterArray= new ArrayList<>();
    for (int i = 0; i < array.size(); i++) {
        if (array.get(i).toString().toLowerCase().contains(enteredText.toLowerCase())) {
            filterArray.add(array.get(i));
        }
    }
    if (filterArray.size() > 0) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) this.getModel();
        model.removeAllElements();
        for (Object s: filterArray)
            model.addElement(s);
        JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
        textfield.setText(enteredText);
    }
}
}