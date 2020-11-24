/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Entidades.Clasificacion;
import Entidades.Cliente;
import Entidades.Producto;
import interfaz.CNegociosGIFactory;
import interfaz.INegociosGI;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aldeco
 */
public class AdministrarClientes extends javax.swing.JFrame {

    INegociosGI negocio = null;
    
    /**
     * Creates new form AdministrarClientes
     * @param titulo
     */
    public AdministrarClientes(String titulo) throws Exception{
        initComponents();
        
        setAlwaysOnTop(true);
        
        ImageIcon GIIcon = new ImageIcon("src/img/GI.png");
        
        negocio = CNegociosGIFactory.buildDatos();
        
        this.setSize(1600, 900);
        this.setTitle("Administrar Clientes");
        this.setResizable(false);
        this.setIconImage(GIIcon.getImage());
        centraVentana();
        
        this.lblTituloAdminC.setText(titulo);
        
        if(titulo.equals("Agregar cliente"))
            tblClientes.setEnabled(false);
        
        if(titulo.equals("Consultar cliente") || titulo.equals("Eliminar cliente")){
            // Desactiva campos de edicion
            tfNombreCliente.setEditable(false);
            tfCorreoElectronico.setEditable(false);
            tfTelefono.setEditable(false);
            tfDirCalle.setEditable(false);
            tfDirExt.setEditable(false);
            tfDirInt.setEditable(false);
            tfDirColonia.setEditable(false);
            tfDirEntre.setEditable(false);
            tfDirCP.setEditable(false);
            btnALimpiar.setEnabled(false);
            if(titulo.equals("Consultar cliente")){
                btnAAceptar.setVisible(false);
                btnACancelar.setText("CERRAR");
            }
       }
        
        // Agrega listener a la tabla
        ListSelectionModel model = tblClientes.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try{
                    // Espera hasta que se deja de mover
                    if(!e.getValueIsAdjusting()){
                        // Obtiene producto seleccionado
                        Cliente aux = (Cliente) tblClientes.getModel().getValueAt(tblClientes.getSelectedRow(), 0);
                        // Establece valores
                        tfIdCliente.setText(""+aux.getIdCliente());
                        tfNombreCliente.setText(aux.getNombre());
                        tfCorreoElectronico.setText(aux.getCorreo());
                        tfTelefono.setText(aux.getTelefono());
                        tfDirCalle.setText(aux.getDirCalle());
                        tfDirExt.setText(""+aux.getDirExt());
                        tfDirInt.setText(""+aux.getDirInt());
                        tfDirColonia.setText(aux.getDirColonia());
                        tfDirEntre.setText(aux.getDirEntre());
                        tfDirCP.setText(""+aux.getDirCP());
                    }
                }catch(IndexOutOfBoundsException ex){
                    // Ignora indexoutofbounds
                    
                }
            }
        });
        
        tfBuscarCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
                String ingresado = tfBuscarCliente.getText();
                List<Cliente> lista = negocio.soliciarListaClientes();
                DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
                // Vacia
                model.setRowCount(0);
                // Llena
                // Itera
                for(Cliente cli: lista){
                    if(cli.toString().toLowerCase().contains(ingresado.toLowerCase()))
                        model.addRow(new Object[]{cli});
                }
                // Establece
                tblClientes.setModel(model);
            }
            
        });
        
        llenarClientesLista();
        
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
    
    private void limpiarCampos() {
        tfNombreCliente.setText("");
        tfCorreoElectronico.setText("");
        tfTelefono.setText("");
        tfDirCalle.setText("");
        tfDirExt.setText("");
        tfDirInt.setText("");
        tfDirColonia.setText("");
        tfDirEntre.setText("");
        tfDirCP.setText("");
    }
    
    private String validarCampos(){
        if(tfIdCliente.getText().isEmpty()||tfNombreCliente.getText().isEmpty()||tfCorreoElectronico.getText().isEmpty()||
                tfTelefono.getText().isEmpty()||tfDirCalle.getText().isEmpty()||tfDirExt.getText().isEmpty()||
                tfDirInt.getText().isEmpty()||tfDirColonia.getText().isEmpty()||tfDirEntre.getText().isEmpty()||
                tfDirCP.getText().isEmpty())
            return "Campos vacios, por favor revisar que se hayan escrito todos.";
        
        if(!lblTituloAdminC.getText().equals("Agregar cliente"))
            try{
                Integer.parseInt(tfIdCliente.getText());
            }catch(NumberFormatException e){
                return "ID invalido.";
            }
        
        // Otros enteros
        try{
                Integer.parseInt(tfDirCP.getText());
                Integer.parseInt(tfDirInt.getText());
                Integer.parseInt(tfDirExt.getText());
            }catch(NumberFormatException e){
                return "Valores invalidos en campos numericos.";
            }
        
        
        return null;
    }
    
    private void llenarClientesLista(){
        List<Cliente> lista = negocio.soliciarListaClientes();
        DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
        // Vacia
        model.setRowCount(0);
        // Llena
        // Itera
        for(Cliente cli : lista){
            model.addRow(new Object[]{cli});
        }
        // Establece
        tblClientes.setModel(model);
        tfIdCliente.setText("-");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        tfBuscarCliente = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlContainer = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        tfNombreCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnAAceptar = new javax.swing.JButton();
        btnALimpiar = new javax.swing.JButton();
        btnACancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        tfCorreoElectronico = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tfTelefono = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfDirCalle = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfDirColonia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfDirEntre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tfDirExt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tfDirInt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfDirCP = new javax.swing.JTextField();
        tfIdCliente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lblTituloAdminC = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        menuAdministrarProductos = new javax.swing.JMenu();
        optAgregarProducto = new javax.swing.JMenuItem();
        optEditarProducto = new javax.swing.JMenuItem();
        optEliminarProducto = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        optConsultarProductos = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuAdministrarClientes = new javax.swing.JMenu();
        optAgergarCliente = new javax.swing.JMenuItem();
        optEditarCliente = new javax.swing.JMenuItem();
        optEliminarCliente = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        optConsultarClientes = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        optVenta = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(208, 219, 230));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tblClientes);
        tblClientes.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tfBuscarCliente.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 2, 18)); // NOI18N
        jLabel1.setText("Buscar cliente:");

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 2, 18)); // NOI18N
        jLabel2.setText("Lista de clientes:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                    .addComponent(tfBuscarCliente)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlContainer.setBackground(new java.awt.Color(29, 59, 91));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tfNombreCliente.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel5.setText("Nombre:");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAAceptar.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        btnAAceptar.setText("ACEPTAR");
        btnAAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAAceptarActionPerformed(evt);
            }
        });

        btnALimpiar.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        btnALimpiar.setText("LIMPIAR");
        btnALimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnALimpiarActionPerformed(evt);
            }
        });

        btnACancelar.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        btnACancelar.setText("CANCELAR");
        btnACancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnACancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(btnAAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(317, 317, 317)
                .addComponent(btnALimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addComponent(btnACancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnALimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnACancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel6.setText("Correo Electrónico:");

        tfCorreoElectronico.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel7.setText("Teléfono:");

        tfTelefono.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Dirección", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bahnschrift", 2, 14))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel3.setText("Calle:");

        tfDirCalle.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel4.setText("Colonia:");

        tfDirColonia.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel8.setText("Entre:");

        tfDirEntre.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel9.setText("Ext:");

        tfDirExt.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        tfDirExt.setPreferredSize(new java.awt.Dimension(6, 40));

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel10.setText("Int:");

        tfDirInt.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel11.setText("C.P:");

        tfDirCP.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        tfDirCP.setPreferredSize(new java.awt.Dimension(6, 40));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfDirCalle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfDirExt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfDirInt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfDirColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfDirEntre))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfDirCP, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfDirCalle)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfDirInt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfDirExt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDirColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDirEntre, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfDirCP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tfIdCliente.setEditable(false);
        tfIdCliente.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel12.setText("ID:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(tfIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(258, 258, 258))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(41, 41, 41)
                            .addComponent(tfNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(273, 273, 273))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblTituloAdminC.setFont(new java.awt.Font("Bahnschrift", 2, 24)); // NOI18N
        lblTituloAdminC.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloAdminC.setText("Administrar Clientes");

        javax.swing.GroupLayout pnlContainerLayout = new javax.swing.GroupLayout(pnlContainer);
        pnlContainer.setLayout(pnlContainerLayout);
        pnlContainerLayout.setHorizontalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTituloAdminC, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlContainerLayout.setVerticalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloAdminC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu3.setText("Menu");
        jMenuBar2.add(jMenu3);

        jMenu1.setText("Productos");

        menuAdministrarProductos.setText("Administrar Productos");

        optAgregarProducto.setText("Agregar producto");
        optAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optAgregarProductoActionPerformed(evt);
            }
        });
        menuAdministrarProductos.add(optAgregarProducto);

        optEditarProducto.setText("Editar producto");
        optEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optEditarProductoActionPerformed(evt);
            }
        });
        menuAdministrarProductos.add(optEditarProducto);

        optEliminarProducto.setText("Eliminar producto");
        optEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optEliminarProductoActionPerformed(evt);
            }
        });
        menuAdministrarProductos.add(optEliminarProducto);
        menuAdministrarProductos.add(jSeparator1);

        optConsultarProductos.setText("Consultar productos");
        optConsultarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optConsultarProductosActionPerformed(evt);
            }
        });
        menuAdministrarProductos.add(optConsultarProductos);

        jMenu1.add(menuAdministrarProductos);

        jMenuBar2.add(jMenu1);

        jMenu2.setText("Clientes");

        menuAdministrarClientes.setText("Administrar Clientes");

        optAgergarCliente.setText("Agregar cliente");
        optAgergarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optAgergarClienteActionPerformed(evt);
            }
        });
        menuAdministrarClientes.add(optAgergarCliente);

        optEditarCliente.setText("Editar cliente");
        optEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optEditarClienteActionPerformed(evt);
            }
        });
        menuAdministrarClientes.add(optEditarCliente);

        optEliminarCliente.setText("Eliminar cliente");
        optEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optEliminarClienteActionPerformed(evt);
            }
        });
        menuAdministrarClientes.add(optEliminarCliente);
        menuAdministrarClientes.add(jSeparator2);

        optConsultarClientes.setText("Consultar clientes");
        optConsultarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optConsultarClientesActionPerformed(evt);
            }
        });
        menuAdministrarClientes.add(optConsultarClientes);

        jMenu2.add(menuAdministrarClientes);

        jMenuBar2.add(jMenu2);

        jMenu4.setText("Ventas");

        optVenta.setText("Administrar Venta");
        optVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optVentaActionPerformed(evt);
            }
        });
        jMenu4.add(optVenta);

        jMenuBar2.add(jMenu4);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(61, 61, 61))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAAceptarActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(this, "Estas seguro?", "Confirmacion", JOptionPane.OK_CANCEL_OPTION);
        if(confirmacion==0){
            try{
                String msg = validarCampos();
                if(msg==null){
                    // Crea cliente auxiliar
                    Cliente aux = new Cliente(tfNombreCliente.getText(), tfCorreoElectronico.getText(), tfTelefono.getText(),
                            tfDirCalle.getText(), Integer.parseInt(tfDirInt.getText()), Integer.parseInt(tfDirExt.getText()),
                            tfDirColonia.getText(), tfDirEntre.getText(), Integer.parseInt(tfDirCP.getText()));
                    switch(lblTituloAdminC.getText()){
                        case "Agregar cliente":
                            // Revisa si existe
                            if(negocio.validarCliente(aux)){
                                // Error
                                JOptionPane.showMessageDialog(this, "Cliente con ID seleccionado ya existe en la base de datos.\nNo se puede agregar.",
                                        "Error en administracion", JOptionPane.ERROR_MESSAGE);
                            }else{
                                // Agregalo
                                negocio.administrarCliente(1, aux);
                            }
                            break;
                        case "Editar cliente":
                            // Asigna el ID
                            aux.setIdCliente(Integer.parseInt(tfIdCliente.getText()));
                            // Revisa si existe
                            if(negocio.validarCliente(aux)){
                                // Editalo
                                negocio.administrarCliente(2, aux);
                            }else{
                                // Error
                                JOptionPane.showMessageDialog(this, "Cliente con ID seleccionado no existe en la base de datos."
                                        + "\nNo se puede editar.",
                                        "Error en administracion", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case "Eliminar cliente":
                            // Asigna el ID
                            aux.setIdCliente(Integer.parseInt(tfIdCliente.getText()));
                            // Revisa si existe
                            if(negocio.validarCliente(aux)){
                                // Eliminalo
                                negocio.administrarCliente(3, aux);
                            }else{
                                // Error
                                JOptionPane.showMessageDialog(this, "Cliente con ID seleccionado no existe en la base de datos."
                                        + "\nNo se puede eliminar.",
                                        "Error en administracion", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                    // Actualiza lista
                    llenarClientesLista();
                    // Limpia todo
                    limpiarCampos();
                }else{
                    // alerta
                    JOptionPane.showMessageDialog(this, msg, "Error en validacion", JOptionPane.ERROR_MESSAGE);
                }
            }catch(Exception e){
                // Error base de datos
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnAAceptarActionPerformed

    private void btnALimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnALimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnALimpiarActionPerformed

    private void btnACancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnACancelarActionPerformed
        // Confirmacion
        int confirmacion = JOptionPane.showConfirmDialog(this, "Estas seguro?", "Confirmacion", JOptionPane.OK_CANCEL_OPTION);
        if(confirmacion==0){
            FrmMenuPrincipal nuevaVentana = null;
            try{
                nuevaVentana = new FrmMenuPrincipal();
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos. \n No se puede trabajar sin conexion.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
            }
            if(nuevaVentana!=null){
                dispose();
                nuevaVentana.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnACancelarActionPerformed

    private void optAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optAgregarProductoActionPerformed
        AdministrarProductos nuevaVentana = null;
        try{
            nuevaVentana = new AdministrarProductos("Agregar producto");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos. \n No se puede trabajar sin conexion.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        if(nuevaVentana!=null){
            dispose();
            nuevaVentana.setVisible(true);
        }
    }//GEN-LAST:event_optAgregarProductoActionPerformed

    private void optEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optEditarProductoActionPerformed
        AdministrarProductos nuevaVentana = null;
        try{
            nuevaVentana = new AdministrarProductos("Editar producto");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos. \n No se puede trabajar sin conexion.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        if(nuevaVentana!=null){
            dispose();
            nuevaVentana.setVisible(true);
        }
    }//GEN-LAST:event_optEditarProductoActionPerformed

    private void optEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optEliminarProductoActionPerformed
        AdministrarProductos nuevaVentana = null;
        try{
            nuevaVentana = new AdministrarProductos("Eliminar producto");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos. \n No se puede trabajar sin conexion.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        if(nuevaVentana!=null){
            dispose();
            nuevaVentana.setVisible(true);
        }
    }//GEN-LAST:event_optEliminarProductoActionPerformed

    private void optConsultarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optConsultarProductosActionPerformed
        AdministrarProductos nuevaVentana = null;
        try{
            nuevaVentana = new AdministrarProductos("Consultar producto");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos. \n No se puede trabajar sin conexion.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        if(nuevaVentana!=null){
            dispose();
            nuevaVentana.setVisible(true);
        }
    }//GEN-LAST:event_optConsultarProductosActionPerformed

    private void optAgergarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optAgergarClienteActionPerformed
        AdministrarClientes nuevaVentana = null;
        try{
            nuevaVentana = new AdministrarClientes("Agregar cliente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos. \n No se puede trabajar sin conexion.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        if(nuevaVentana!=null){
            dispose();
            nuevaVentana.setVisible(true);
        }
    }//GEN-LAST:event_optAgergarClienteActionPerformed

    private void optEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optEditarClienteActionPerformed
        AdministrarClientes nuevaVentana = null;
        try{
            nuevaVentana = new AdministrarClientes("Editar cliente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos. \n No se puede trabajar sin conexion.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        if(nuevaVentana!=null){
            dispose();
            nuevaVentana.setVisible(true);
        }
    }//GEN-LAST:event_optEditarClienteActionPerformed

    private void optEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optEliminarClienteActionPerformed
        AdministrarClientes nuevaVentana = null;
        try{
            nuevaVentana = new AdministrarClientes("Eliminar cliente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos. \n No se puede trabajar sin conexion.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        if(nuevaVentana!=null){
            dispose();
            nuevaVentana.setVisible(true);
        }
    }//GEN-LAST:event_optEliminarClienteActionPerformed

    private void optConsultarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optConsultarClientesActionPerformed
        AdministrarClientes nuevaVentana = null;
        try{
            nuevaVentana = new AdministrarClientes("Consultar cliente");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos. \n No se puede trabajar sin conexion.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        if(nuevaVentana!=null){
            dispose();
            nuevaVentana.setVisible(true);
        }
    }//GEN-LAST:event_optConsultarClientesActionPerformed

    private void optVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optVentaActionPerformed
        FrmAdministrarVenta nuevaVentana = null;
        try{
            nuevaVentana = new FrmAdministrarVenta();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos. \n No se puede trabajar sin conexion.", "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        if(nuevaVentana!=null){
            dispose();
            nuevaVentana.setVisible(true);
        }
    }//GEN-LAST:event_optVentaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAAceptar;
    private javax.swing.JButton btnACancelar;
    private javax.swing.JButton btnALimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblTituloAdminC;
    private javax.swing.JMenu menuAdministrarClientes;
    private javax.swing.JMenu menuAdministrarProductos;
    private javax.swing.JMenuItem optAgergarCliente;
    private javax.swing.JMenuItem optAgregarProducto;
    private javax.swing.JMenuItem optConsultarClientes;
    private javax.swing.JMenuItem optConsultarProductos;
    private javax.swing.JMenuItem optEditarCliente;
    private javax.swing.JMenuItem optEditarProducto;
    private javax.swing.JMenuItem optEliminarCliente;
    private javax.swing.JMenuItem optEliminarProducto;
    private javax.swing.JMenuItem optVenta;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField tfBuscarCliente;
    private javax.swing.JTextField tfCorreoElectronico;
    private javax.swing.JTextField tfDirCP;
    private javax.swing.JTextField tfDirCalle;
    private javax.swing.JTextField tfDirColonia;
    private javax.swing.JTextField tfDirEntre;
    private javax.swing.JTextField tfDirExt;
    private javax.swing.JTextField tfDirInt;
    private javax.swing.JTextField tfIdCliente;
    private javax.swing.JTextField tfNombreCliente;
    private javax.swing.JTextField tfTelefono;
    // End of variables declaration//GEN-END:variables
}
