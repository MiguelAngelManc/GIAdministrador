/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Entidades.Doctor;
import components.FilterComboBox;
import interfaz.CNegociosGIFactory;
import interfaz.INegociosGI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author Home
 */
public class DlgDoctor extends JDialog implements PropertyChangeListener{
    
    private JOptionPane optionPane;
    private FilterComboBox fcbDoctor;
    private String rfc;
    
    private String btnString1 = "Aceptar";
    private String btnString2 = "Cancelar";
    
    private JTextField txtRFC = new JTextField(20);
    private JTextField txtNombre = new JTextField(50);
    
    private JRadioButton rbExistente = new JRadioButton("Existente - Seleccione un doctor existente:",true);
    private JRadioButton rbNuevo = new JRadioButton("Nuevo - Inserte la informacion del doctor:");

    public DlgDoctor(Frame frame, List<? extends Object> doctores) {
        super(frame,true);
        fcbDoctor = new FilterComboBox(doctores);
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        
        setAlwaysOnTop(true);

        setTitle("Elegir un doctor que receto los productos.");
        
        Object[] options = {btnString1, btnString2};
        
        
        
        //Crea los paneles para las opciones
        JPanel existente = new JPanel();
        JPanel nuevo = new JPanel();
        
        existente.setBorder(BorderFactory.createLineBorder(Color.black));
        nuevo.setBorder(BorderFactory.createLineBorder(Color.black));
        
        
        ButtonGroup rgBotones = new ButtonGroup();
        
        
        rgBotones.add(rbExistente);
        rgBotones.add(rbNuevo);
        
        existente.add(rbExistente);
        existente.add(fcbDoctor);
        
        nuevo.add(rbNuevo);
        nuevo.add(new JLabel("RFC:"));
        nuevo.add(txtRFC);
        nuevo.add(new JLabel("Nombre:"));
        nuevo.add(txtNombre);
        
        txtNombre.setEnabled(false);
        txtRFC.setEnabled(false);
        
        
        Object[] array = {existente,nuevo};
        
         //Create the JOptionPane.
        optionPane = new JOptionPane(array,
                                    JOptionPane.INFORMATION_MESSAGE,
                                    JOptionPane.OK_CANCEL_OPTION,
                                    null,
                                    options,
                                    options[0]);

        //Make this dialog display it.
        setContentPane(optionPane);
        
        
        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(
                                        JOptionPane.CLOSED_OPTION));
            }
        });
        
        rbExistente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                txtNombre.setEnabled(false);
                txtRFC.setEnabled(false);
                fcbDoctor.setEnabled(true);
            }
        });
        
        rbNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                txtNombre.setEnabled(true);
                txtRFC.setEnabled(true);
                fcbDoctor.setEnabled(false);
            }
        });
        
        optionPane.addPropertyChangeListener(this);
        
        
        pack();
        centraVentana();
    }

    public String getRfc() {
        return rfc;
    }
    
    
    
    private void centraVentana() {
        // Obtiene el tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Obtiene el tamaño de la ventana de la aplicación
        Dimension frameSize = getSize();
        
        // Se asegura que el tamaño de la ventana de la aplicación
        // no exceda el tamaño de la pantalla
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.width;
        }
        
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        
        // Centra la ventana de la aplicación sobre la pantalla
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }
    
    /** This method reacts to state changes in the option pane. */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
         && (e.getSource() == optionPane)
         && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
             JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }

            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            // Si se dio click en aceptar
            if (btnString1.equals(value)) {
                // Existente
                Doctor doctor;
                if(rbExistente.isSelected()){
                    try{
                        doctor = (Doctor) fcbDoctor.getSelectedItem();
                   }catch(ClassCastException ex){
                       JOptionPane.showMessageDialog(this, "Doctor invalido. Favor de seleccionar otro.", "Error de doctor", JOptionPane.ERROR_MESSAGE);
                       doctor = null;
                   }
                    
                    if(doctor!=null){
                        rfc = doctor.getRfc();
                        dispose();
                    }
                }
                // Nuevo
                else{
                    Doctor aux;
                    try{
                        aux = new Doctor(txtRFC.getText(), txtNombre.getText());
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(this, "Doctor invalido. Favor de ingresar correctamente los datos.", "Error de doctor", JOptionPane.ERROR_MESSAGE);
                        aux = null;
                    }
                    
                    if(aux==null)
                        return;
                    
                    try{
                        INegociosGI negocio = CNegociosGIFactory.buildDatos();
                        negocio.guardarDoctor(aux);
                        rfc = aux.getRfc();
                        dispose();
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(this, "Error al guardar el doctor, favor de revisar bien los datos (Talvez esta repetido?)", "Error de doctor", JOptionPane.ERROR_MESSAGE);
                        aux = null;
                    }
                }
            } else { //user closed dialog or clicked cancel
                rfc = null;
                dispose();
            }
        }
    }
    
}
