/*
 * 
 */ 

package ui;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import java.beans.*; //property change stuff
import java.awt.*;
import java.awt.event.*;

class DlgCobro extends JDialog
                   implements ActionListener,
                              PropertyChangeListener {
    private String typedText = null;
    private JTextField txtMoneda;
    private JTextField txtTarjeta;
    
    private JOptionPane optionPane;

    private String btnString1 = "Aceptar";
    private String btnString2 = "Cancelar";
    
    private float pagar;
    
    private float moneda;
    private float tarjeta;

    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     */
    public String getValidatedText() {
        return typedText;
    }

    /** Creates the reusable dialog. */
    public DlgCobro(Frame aFrame, float pagar) {
        super(aFrame, true);
        
        setAlwaysOnTop(true);

        this.pagar = pagar;
        setTitle("Cobro total: "+pagar);

        txtMoneda  = new JTextField();
        txtTarjeta = new JTextField("0.0");
        
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        

        //Create an array of the text and components to be displayed.
        Object[] array = {"Tarjeta:", txtTarjeta, "Moneda:", txtMoneda};

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {btnString1, btnString2};

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

        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                txtTarjeta.requestFocusInWindow();
                txtTarjeta.selectAll();
            }
        });

        //Register an event handler that puts the text into the option pane.
        txtTarjeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    tarjeta = Float.parseFloat(txtTarjeta.getText());
                    if(tarjeta>pagar){
                        txtMoneda.setText("0.0");
                    }else{
                        txtMoneda.setText(""+(pagar-tarjeta));
                    }
                    txtMoneda.requestFocusInWindow();
                }catch(NumberFormatException e){
                    
                }
            }
        });

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
        
        centraVentana();
        pack();
    }

    public float getCobro() {
        return moneda + tarjeta;
    }

    public float getMoneda() {
        return moneda;
    }

    public float getTarjeta() {
        return tarjeta;
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
    
    /** This method handles events for the text field. */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnString1);
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
                
                // Trata de convertir los valores
                try{
                    moneda = Float.parseFloat(txtMoneda.getText());
                    tarjeta = Float.parseFloat(txtTarjeta.getText());
                    clearAndHide();
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(this, "Error al registrar el cobro, favor de solo insertar valores numericos.", "Error al cobrar", JOptionPane.ERROR_MESSAGE);
                    txtTarjeta.selectAll();
                }
            } else { //user closed dialog or clicked cancel
                moneda = 0f;
                tarjeta = 0f;
                clearAndHide();
            }
        }
    }

    /** This method clears the dialog and hides it. */
    public void clearAndHide() {
        dispose();
    }
}