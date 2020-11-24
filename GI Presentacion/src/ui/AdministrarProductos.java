/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Entidades.Clasificacion;
import Entidades.Producto;
import interfaz.CNegociosGIFactory;
import interfaz.INegociosGI;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
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
public class AdministrarProductos extends javax.swing.JFrame {

    INegociosGI negocio = null;
    
    /**
     * Creates new form AdministrarProductos
     * @param titulo
     */
    public AdministrarProductos(String titulo) throws Exception{
        initComponents();
        
        setAlwaysOnTop(true);
        
        ImageIcon GIIcon = new ImageIcon("src/img/GI.png");
        
        negocio = CNegociosGIFactory.buildDatos();
        
        this.setSize(1600, 900);
        this.setTitle("Administrar Productos");
        this.setResizable(false);
        this.setIconImage(GIIcon.getImage());
        centraVentana();
        
        this.lblTituloAdminP.setText(titulo);
        DefaultComboBoxModel modelClasificaciones = new DefaultComboBoxModel(Clasificacion.values());
        this.cbClasificaciones.setModel(modelClasificaciones);
        
        if(titulo.equals("Agregar producto"))
            tblProductos.setEnabled(false);
        
        if(titulo.equals("Consultar producto") || titulo.equals("Eliminar producto")){
            // Desactiva campos de edicion
            tfNombreProducto.setEditable(false);
            tfPrecio.setEditable(false);
            cbClasificaciones.setEnabled(false);
            btnALimpiar.setEnabled(false);
            tfContenido.setEditable(false);
            if(titulo.equals("Consultar producto")){
                btnAAceptar.setVisible(false);
                btnACancelar.setText("CERRAR");
            }
       }
        
        // Agrega listener a la tabla
        ListSelectionModel model = tblProductos.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try{
                    // Espera hasta que se deja de mover
                    if(!e.getValueIsAdjusting()){
                        // Obtiene producto seleccionado
                        Producto aux = (Producto) tblProductos.getModel().getValueAt(tblProductos.getSelectedRow(), 0);
                        // Establece valores
                        tfIdProducto.setText(""+aux.getIdProducto());
                        tfNombreProducto.setText(aux.getNombre());
                        tfPrecio.setText(""+aux.getPrecio());
                        cbClasificaciones.setSelectedItem(aux.getClasificacion());
                        tfContenido.setText(aux.getContenido());
                    }
                }catch(IndexOutOfBoundsException ex){
                    // Ignora indexoutofbounds
                    
                }
            }
        });
        
        tfBuscarProducto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
                String ingresado = tfBuscarProducto.getText();
                List<Producto> lista = negocio.soliciarListaProductos();
                DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();
                // Vacia
                model.setRowCount(0);
                // Llena
                // Itera
                for(Producto pro : lista){
                    if(pro.toString().toLowerCase().contains(ingresado.toLowerCase()))
                        model.addRow(new Object[]{pro});
                }
                // Establece
                tblProductos.setModel(model);
            }
            
        });
        
        tfBuscarProductoContenido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
                String ingresado = tfBuscarProductoContenido.getText();
                List<Producto> lista = negocio.soliciarListaProductos();
                DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();
                // Vacia
                model.setRowCount(0);
                // Llena
                // Itera
                for(Producto pro : lista){
                    if(pro.getContenido().toLowerCase().contains(ingresado.toLowerCase()))
                        model.addRow(new Object[]{pro});
                }
                // Establece
                tblProductos.setModel(model);
            }
            
        });
        
        llenarProductosLista();
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
        this.tfNombreProducto.setText("");
        this.cbClasificaciones.setSelectedIndex(0);
        this.tfPrecio.setText("");
        tfContenido.setText("");
    }
    
    private void llenarProductosLista(){
        List<Producto> lista = negocio.soliciarListaProductos();
        DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();
        // Vacia
        model.setRowCount(0);
        // Llena
        // Itera
        for(Producto pro : lista){
            model.addRow(new Object[]{pro});
        }
        // Establece
        tblProductos.setModel(model);
        tfIdProducto.setText("-");
    }
    
    private String validarCampos(){
        if(tfIdProducto.getText().isEmpty()||tfNombreProducto.getText().isEmpty()||tfPrecio.getText().isEmpty())
            return "Campos vacios, por favor revisar que se hayan escrito todos.";
        
        if(!lblTituloAdminP.getText().equals("Agregar producto"))
            try{
                Integer.parseInt(tfIdProducto.getText());
            }catch(NumberFormatException e){
                return "ID invalido.";
            }
        
        try{
            float f = Float.parseFloat(tfPrecio.getText());
            if(f<=0)
                return "Precio invalido, no puede ser negativo o gratis.";
        }catch(NumberFormatException e){
            return "Precio invalido.";
        }
        
        return null;
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
        tblProductos = new javax.swing.JTable();
        tfBuscarProducto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfBuscarProductoContenido = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pnlContainer = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfNombreProducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbClasificaciones = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        tfPrecio = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnAAceptar = new javax.swing.JButton();
        btnALimpiar = new javax.swing.JButton();
        btnACancelar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        tfIdProducto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tfContenido = new javax.swing.JTextArea();
        lblTituloAdminP = new javax.swing.JLabel();
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

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Producto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductos.setColumnSelectionAllowed(true);
        tblProductos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblProductos);
        tblProductos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tfBuscarProducto.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 2, 18)); // NOI18N
        jLabel1.setText("Buscar producto:");

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 2, 18)); // NOI18N
        jLabel2.setText("Lista de productos:");

        tfBuscarProductoContenido.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 2, 18)); // NOI18N
        jLabel7.setText("Buscar por contenido:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                    .addComponent(tfBuscarProducto)
                    .addComponent(tfBuscarProductoContenido)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfBuscarProductoContenido, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlContainer.setBackground(new java.awt.Color(29, 59, 91));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel3.setText("Nombre:");

        tfNombreProducto.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel4.setText("Clasificación:");

        cbClasificaciones.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        cbClasificaciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel5.setText("Precio:");

        tfPrecio.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel12.setText("ID:");

        tfIdProducto.setEditable(false);
        tfIdProducto.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel6.setText("Contenido:");

        tfContenido.setColumns(20);
        tfContenido.setRows(5);
        jScrollPane2.setViewportView(tfContenido);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(tfIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfNombreProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                            .addComponent(cbClasificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2))))
                .addGap(270, 270, 270))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbClasificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblTituloAdminP.setFont(new java.awt.Font("Bahnschrift", 2, 24)); // NOI18N
        lblTituloAdminP.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloAdminP.setText("Administrar Productos");

        javax.swing.GroupLayout pnlContainerLayout = new javax.swing.GroupLayout(pnlContainer);
        pnlContainer.setLayout(pnlContainerLayout);
        pnlContainerLayout.setHorizontalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTituloAdminP, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlContainerLayout.setVerticalGroup(
            pnlContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloAdminP, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
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
                .addComponent(pnlContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    // Crea producto auxiliar
                    Producto aux = new Producto(
                            tfNombreProducto.getText(), Float.parseFloat(tfPrecio.getText()),
                            tfContenido.getText(),(Clasificacion)cbClasificaciones.getModel().getSelectedItem());
                    switch(lblTituloAdminP.getText()){
                        case "Agregar producto":
                            // Revisa si existe
                            if(negocio.validarProducto(aux)){
                                // Error
                                JOptionPane.showMessageDialog(this, "Producto con ID seleccionado ya existe en la base de datos.\nNo se puede agregar.",
                                        "Error en administracion", JOptionPane.ERROR_MESSAGE);
                            }else{
                                // Agregalo
                                negocio.administrarProducto(1, aux);
                            }
                            break;
                        case "Editar producto":
                            // Asigna el ID
                            aux.setIdProducto(Integer.parseInt(tfIdProducto.getText()));
                            // Revisa si existe
                            if(negocio.validarProducto(aux)){
                                // Editalo
                                negocio.administrarProducto(2, aux);
                            }else{
                                // Error
                                JOptionPane.showMessageDialog(this, "Producto con ID seleccionado no existe en la base de datos."
                                        + "\nNo se puede editar.",
                                        "Error en administracion", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case "Eliminar producto":
                            // Asigna el ID
                            aux.setIdProducto(Integer.parseInt(tfIdProducto.getText()));
                            // Revisa si existe
                            if(negocio.validarProducto(aux)){
                                // Eliminalo
                                negocio.administrarProducto(3, aux);
                            }else{
                                // Error
                                JOptionPane.showMessageDialog(this, "Producto con ID seleccionado no existe en la base de datos."
                                        + "\nNo se puede eliminar.",
                                        "Error en administracion", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                    // Actualiza lista
                    llenarProductosLista();
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
    private javax.swing.JComboBox<String> cbClasificaciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblTituloAdminP;
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
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField tfBuscarProducto;
    private javax.swing.JTextField tfBuscarProductoContenido;
    private javax.swing.JTextArea tfContenido;
    private javax.swing.JTextField tfIdProducto;
    private javax.swing.JTextField tfNombreProducto;
    private javax.swing.JTextField tfPrecio;
    // End of variables declaration//GEN-END:variables
}
