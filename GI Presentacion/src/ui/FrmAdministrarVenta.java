/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Entidades.Clasificacion;
import Entidades.Cliente;
import Entidades.Producto;
import Entidades.Usuario;
import components.ClienteListCellRenderer;
import components.FilterComboBox;
import components.ProductoListCellRenderer;
import interfaz.CNegociosGIFactory;
import interfaz.INegociosGI;
import java.awt.Dimension;
import java.util.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ITSS
 */
public class FrmAdministrarVenta extends javax.swing.JFrame {

    INegociosGI negocio = null;
    
    /**
     * Creates new form InicioDelDia
     */
    public FrmAdministrarVenta() throws Exception{
        initComponents();
        negocio = CNegociosGIFactory.buildDatos();
        usuario = new Usuario();
        usuario.setIdUsuario(1);
        
        setAlwaysOnTop(true);
        
        ImageIcon GIIcon = new ImageIcon("src/img/GI.png");
        
        this.setSize(1600, 900);
        this.setTitle("Administrar Ventas");
        this.setResizable(false);
        this.setIconImage(GIIcon.getImage());
        centraVentana();
        
        
        // Fecha
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date date = new Date();
        this.lblFecha.setText(dateFormat.format(date));
        
        // Caja de cantidad
        SpinnerNumberModel model = (SpinnerNumberModel) sCantidad.getModel();
        model.setMinimum(1);
        sCantidad.setModel(model);
        
        // Cajas de clientes y productos
        fcbProducto = convierteComboBox(cbProducto, conseguirProductos());
        fcbCliente = convierteComboBox(cbCliente, conseguirClientes());
        fcbProducto.setRenderer(new ProductoListCellRenderer());
        fcbCliente.setRenderer(new ClienteListCellRenderer());
        
        // Listener de tabla
        tblVentas.putClientProperty("terminateEditOnFocusLost", true);
        tblVentas.getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent ce) {
                actualizarValores();
            }

            @Override
            public void editingCanceled(ChangeEvent ce) {
                ;
            }
        });
        
        // Listener de la ventana, para cobro rapido, seleccionar productos
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F3"), "Seleccionar Producto");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F8"), "Cobrar");
        getRootPane().getActionMap().put("Seleccionar Producto", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fcbProducto.requestFocus();
            }
        });
        getRootPane().getActionMap().put("Cobrar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                btnCobrarActionPerformed(ae);
            }
        });
        
        revalidate();
        repaint();
        pack();
    }
    
    private List<Cliente> conseguirClientes(){
        return negocio.soliciarListaClientes();
    }
    
    private List<Producto> conseguirProductos(){
        List<Producto> productos = negocio.soliciarListaProductos();
        productos.add(0, new Producto(-1, "Productos....", -1f, Clasificacion.PLACEBO));
        return productos;
    }
    
    private FilterComboBox convierteComboBox(JComboBox src, List<? extends Object> items){
        List<Object> array = new ArrayList();
        
        // Obtiene los elementos de la anterior
        for(int i=0;i<items.size();i++){
            array.add(items.get(i));
        }
        
        FilterComboBox newBox = new FilterComboBox(array);
        
        newBox.setSize(src.getSize());
        newBox.setLocation(src.getLocation());
        newBox.setBounds(src.getBounds());
        newBox.setFont(src.getFont());
        newBox.setBorder(src.getBorder());
        newBox.setBackground(src.getBackground());
        
        src.getParent().add(newBox);
        newBox.setVisible(true);
        newBox.setEnabled(true);
        
        
        src.setVisible(false);
        src.getParent().revalidate();
        src.getParent().repaint();
        
        return newBox;
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        sCantidad = new javax.swing.JSpinner();
        btnAgregar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblRecibido = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblAPagar = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblCambio = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        cbCliente = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        cbProducto = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVentas = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnLimpiarLista = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblImpuestos = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnCobrar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        btnTerminar = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        menuAdministrarProductos = new javax.swing.JMenu();
        optAgregarProducto = new javax.swing.JMenuItem();
        optEditarProducto = new javax.swing.JMenuItem();
        optEliminarProducto = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        optConsultarProductos = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        menuAdministrarClientes = new javax.swing.JMenu();
        optAgergarCliente = new javax.swing.JMenuItem();
        optEditarCliente = new javax.swing.JMenuItem();
        optEliminarCliente = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        optConsultarClientes = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        optVenta = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenuItem8.setText("jMenuItem8");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(208, 219, 230));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(10, 36, 62), 4));

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel1.setText("Seleccione un producto:");

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel2.setText("Seleccione un cliente (OPCIONAL):");

        sCantidad.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        sCantidad.setValue(1);
        sCantidad.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sCantidadStateChanged(evt);
            }
        });
        sCantidad.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                sCantidadPropertyChange(evt);
            }
        });

        btnAgregar.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        btnAgregar.setText("AGREGAR");
        btnAgregar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(10, 36, 62), 4));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(81, 109, 138));
        jLabel6.setFont(new java.awt.Font("Bahnschrift", 1, 48)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("A PAGAR:");
        jLabel6.setOpaque(true);

        lblRecibido.setBackground(new java.awt.Color(10, 36, 62));
        lblRecibido.setFont(new java.awt.Font("Tahoma", 1, 54)); // NOI18N
        lblRecibido.setForeground(new java.awt.Color(255, 255, 255));
        lblRecibido.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecibido.setText("0.00");
        lblRecibido.setOpaque(true);

        jLabel8.setBackground(new java.awt.Color(81, 109, 138));
        jLabel8.setFont(new java.awt.Font("Bahnschrift", 1, 48)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("RECIBIDO:");
        jLabel8.setOpaque(true);

        lblAPagar.setBackground(new java.awt.Color(10, 36, 62));
        lblAPagar.setFont(new java.awt.Font("Tahoma", 1, 54)); // NOI18N
        lblAPagar.setForeground(new java.awt.Color(255, 255, 255));
        lblAPagar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAPagar.setText("0.00");
        lblAPagar.setOpaque(true);

        jLabel9.setBackground(new java.awt.Color(208, 219, 230));
        jLabel9.setFont(new java.awt.Font("Bahnschrift", 1, 48)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("CAMBIO:");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(10, 36, 62), 4));
        jLabel9.setOpaque(true);

        lblCambio.setBackground(new java.awt.Color(10, 36, 62));
        lblCambio.setFont(new java.awt.Font("Tahoma", 1, 54)); // NOI18N
        lblCambio.setForeground(new java.awt.Color(255, 255, 255));
        lblCambio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCambio.setText("0.00");
        lblCambio.setOpaque(true);

        cbCliente.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        cbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbClienteActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel3.setText("Nombre:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        cbProducto.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel4.setText("Nombre:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(cbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(cbProducto))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(lblAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(sCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)))
                        .addGap(156, 156, 156))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRecibido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblCambio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(sCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(88, 88, 88))))
        );

        jPanel2.setBackground(new java.awt.Color(159, 190, 221));

        tblVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Clave", "Producto", "Cantidad", "P. Unitario", "Importe", "Descuento", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVentas.setColumnSelectionAllowed(true);
        tblVentas.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblVentas);
        tblVentas.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblVentas.getColumnModel().getColumnCount() > 0) {
            tblVentas.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel4.setBackground(new java.awt.Color(159, 190, 221));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(159, 190, 221));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/trash2.png"))); // NOI18N
        btnEliminar.setBorderPainted(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiarLista.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        btnLimpiarLista.setText("LIMPIAR LISTA");
        btnLimpiarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarListaActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(10, 36, 62));
        jLabel7.setFont(new java.awt.Font("Bahnschrift", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("SubTOTAL");
        jLabel7.setOpaque(true);

        lblSubtotal.setBackground(new java.awt.Color(208, 219, 230));
        lblSubtotal.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        lblSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSubtotal.setText("0.00");
        lblSubtotal.setOpaque(true);

        jLabel11.setBackground(new java.awt.Color(10, 36, 62));
        jLabel11.setFont(new java.awt.Font("Bahnschrift", 3, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Impuestos");
        jLabel11.setOpaque(true);

        lblImpuestos.setBackground(new java.awt.Color(208, 219, 230));
        lblImpuestos.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        lblImpuestos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblImpuestos.setText("0.00");
        lblImpuestos.setOpaque(true);

        jLabel13.setBackground(new java.awt.Color(10, 36, 62));
        jLabel13.setFont(new java.awt.Font("Bahnschrift", 3, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("TOTAL");
        jLabel13.setOpaque(true);

        lblTotal.setBackground(new java.awt.Color(208, 219, 230));
        lblTotal.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("0.00");
        lblTotal.setOpaque(true);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        btnCobrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cobrar.png"))); // NOI18N
        btnCobrar.setBorderPainted(false);
        btnCobrar.setContentAreaFilled(false);
        btnCobrar.setEnabled(false);
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCobrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCobrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(10, 36, 62));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ITSS-shikito_1.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 3, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Farmacia GI - ");

        lblFecha.setBackground(new java.awt.Color(50, 81, 113));
        lblFecha.setFont(new java.awt.Font("Bahnschrift", 3, 24)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFecha.setOpaque(true);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnTerminar.setText("Terminar compra");
        btnTerminar.setEnabled(false);
        btnTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(btnLimpiarLista, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(312, 312, 312)
                        .addComponent(btnTerminar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblImpuestos, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnLimpiarLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblSubtotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblImpuestos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTerminar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jMenu3.setText("Menu");
        jMenuBar2.add(jMenu3);

        jMenu7.setText("Productos");

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

        jMenu7.add(menuAdministrarProductos);

        jMenuBar2.add(jMenu7);

        jMenu8.setText("Clientes");

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

        jMenu8.add(menuAdministrarClientes);

        jMenuBar2.add(jMenu8);

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actualizarValores(){
        DefaultTableModel model = (DefaultTableModel) tblVentas.getModel();
        float subtotal = 0f;
        float total = 0f;
        
        // Itera sobre la tabla 
        for(int i = 0;i<model.getRowCount();i++){
            // Obtiene valores base
            int cantidad;
            try{
                cantidad = Integer.parseInt(model.getValueAt(i, 2).toString());
                if(cantidad<1){
                    throw new ArithmeticException("Cantidad invalida");
                }
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Favor de ingresar solo numeros enteros", "Error en cantidad", JOptionPane.ERROR_MESSAGE);
                return;
            }catch(ArithmeticException e){
                JOptionPane.showMessageDialog(this, "Favor de ingresar solo numeros positivos", "Error en cantidad", JOptionPane.ERROR_MESSAGE);
                model.setValueAt("1", i, 2);
                return;
            }
            
            float pUnitario = Float.parseFloat(model.getValueAt(i, 3).toString());
            
            float importe = cantidad * pUnitario;
            float desc;
            try{
                desc = 100 - Float.parseFloat(model.getValueAt(i, 5).toString());
                if (desc<0){
                    throw new ArithmeticException("Descuento mas grande del 100 porciento.");
                }
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Favor de ingresar un valor valido de descuento.", "Error en descuento", JOptionPane.ERROR_MESSAGE);
                return;
            }catch(ArithmeticException e){
                JOptionPane.showMessageDialog(this, "Favor de ingresar un descuento menor de 101.", "Error en descuento", JOptionPane.ERROR_MESSAGE);
                model.setValueAt("0", i, 5);
                return;
            }
            // Actualiza el campo de importe
            tblVentas.setValueAt(""+importe, i, 4);
            
            // Obtiene el precio despues de descuento
            float calc = importe * desc/100;
            
            // Anade a totales
            subtotal+= importe;
            total+=calc;
        }
        
        // Obtiene el monto recibido al momento
        float recibido = Float.parseFloat(lblRecibido.getText());
        
        // Caluclos para campos
        float faltante = total - recibido;
        
        // Actualiza campos
        lblSubtotal.setText(""+subtotal);
        lblTotal.setText(""+total);
        
        if(faltante>0){
            lblAPagar.setText(""+faltante);
            lblCambio.setText("0.00");
        }else{
            lblCambio.setText(""+Math.abs(faltante));
            lblAPagar.setText("0.00");
        }
        
        if(model.getRowCount()==0)
            btnCobrar.setEnabled(false);
        else
            btnCobrar.setEnabled(true);
        
    }
    
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
       String[] nuevo;
       DefaultTableModel model = (DefaultTableModel) tblVentas.getModel();
       Producto producto;
       Cliente cliente;
       int cantidad = 0;
       
       try{
            producto = (Producto) fcbProducto.getSelectedItem();
            if(producto.getIdProducto() == -1)
                throw new ClassCastException("Producto invalido");
       }catch(ClassCastException e){
           JOptionPane.showMessageDialog(this, "Producto invalido. Favor de seleccionar otro.", "Error de producto", JOptionPane.ERROR_MESSAGE);
           producto = null;
       }
       
       try{
            cliente = (Cliente) fcbCliente.getSelectedItem();
       }catch(ClassCastException e){
           JOptionPane.showMessageDialog(this, "Cliente invalido. Favor de seleccionar otro.", "Error de cliente", JOptionPane.ERROR_MESSAGE);
           cliente = null;
       }
       
       try{
           cantidad =  (int) sCantidad.getModel().getValue();
       }catch(ClassCastException e){
           JOptionPane.showMessageDialog(this, "Cantidad invalida. Favor de seleccionar otra.", "Error de cantidad", JOptionPane.ERROR_MESSAGE);
           cantidad = 0;
       }
       
       
        if(producto==null||cliente==null||cantidad==0)
            return;
        
        float importe = cantidad * producto.getPrecio();
        // Arma lo que se va a agregar a la tabla
        // ID, Nombre, Cantidad, PrecioUnitario, Importe, Descuento, %
        nuevo = new String[]{""+producto.getIdProducto(),producto.getNombre(),""+cantidad,""+producto.getPrecio(),""+importe,"0","%"};
        
       if(nuevo!=null)
        model.addRow(nuevo);
       
       sCantidad.setValue(1);
       actualizarValores();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void cbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbClienteActionPerformed

    private void sCantidadPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_sCantidadPropertyChange
        
    }//GEN-LAST:event_sCantidadPropertyChange

    private void sCantidadStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sCantidadStateChanged
        
    }//GEN-LAST:event_sCantidadStateChanged

    private void btnLimpiarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarListaActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblVentas.getModel();
        model.setRowCount(0);
        actualizarValores();
    }//GEN-LAST:event_btnLimpiarListaActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if(tblVentas.getSelectedRow()!=-1){
            DefaultTableModel model = (DefaultTableModel) tblVentas.getModel();
            model.removeRow(tblVentas.getSelectedRow());
            actualizarValores();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        DlgCobro dlgCobro = new DlgCobro(this, Float.parseFloat(lblTotal.getText()));
        dlgCobro.setVisible(true);
        
        
        //Valores negativos
        moneda = dlgCobro.getMoneda();
        tarjeta = dlgCobro.getTarjeta();
        
        if(moneda<0 || tarjeta<0){
            JOptionPane.showMessageDialog(this, "No se pudo realizar el cobro.\nFavor de ingresar valores validos (Mayores a cero)", "Error de cobro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        lblRecibido.setText(""+dlgCobro.getCobro());
        actualizarValores();
        
        importe = Float.parseFloat(lblTotal.getText());
        moneda = dlgCobro.getMoneda();
        tarjeta = dlgCobro.getTarjeta();
        
        try{
            resultado = (negocio.verificarVenta((DefaultTableModel)tblVentas.getModel(), importe, moneda, tarjeta));
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "No se pudo realizar el cobro.\nRevise la conexión a base de datos", "Error de cobro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        if(resultado>-1){
            btnTerminar.setEnabled(true);
            btnTerminar.requestFocus();
        }else{
            btnTerminar.setEnabled(false);
        }
    }//GEN-LAST:event_btnCobrarActionPerformed

    private void btnTerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminarActionPerformed
        if(resultado>-1){
            Cliente cliente;
            try{
                cliente = (Cliente) fcbCliente.getSelectedItem();
            }catch(ClassCastException e){
               JOptionPane.showMessageDialog(this, "Cliente invalido. Favor de seleccionar otro.", "Error de cliente", JOptionPane.ERROR_MESSAGE);
               cliente = null;
            }
            
            if(cliente==null)
                return;
            
            try{
                if(resultado==0)
                    negocio.guardarVenta((DefaultTableModel)tblVentas.getModel(), importe, moneda, tarjeta, cliente.getIdCliente(), usuario.getIdUsuario(), "null");
                else{
                    DlgDoctor dlgDoctor = new DlgDoctor(this, negocio.soliciarListaDoctores());
                    dlgDoctor.setVisible(true);
                    String rfc = dlgDoctor.getRfc();
                    
                    if(rfc!=null)
                        negocio.guardarVenta((DefaultTableModel)tblVentas.getModel(), importe, moneda, tarjeta, cliente.getIdCliente(), usuario.getIdUsuario(), rfc);
                    else{
                        JOptionPane.showMessageDialog(this, "No se puede terminar la venta sin un doctor valido", "Error de doctor", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "Error al terminar la venta\n Revise bien la conexión a base de datos.", "Error de venta", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            btnLimpiarListaActionPerformed(evt);
            lblRecibido.setText("0.00");
            actualizarValores();
            JOptionPane.showMessageDialog(this, "Venta realizada con exito.", "Venta realizada.", JOptionPane.INFORMATION_MESSAGE);
            btnTerminar.setEnabled(false);
            
        }
        resultado = -1;
    }//GEN-LAST:event_btnTerminarActionPerformed

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

    
    
    
    int resultado;
    Usuario usuario;
    float importe;
    float moneda;
    float tarjeta;
    FilterComboBox fcbProducto;
    FilterComboBox fcbCliente;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiarLista;
    private javax.swing.JButton btnTerminar;
    private javax.swing.JComboBox<String> cbCliente;
    private javax.swing.JComboBox<String> cbProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblAPagar;
    private javax.swing.JLabel lblCambio;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblImpuestos;
    private javax.swing.JLabel lblRecibido;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTotal;
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
    private javax.swing.JSpinner sCantidad;
    private javax.swing.JTable tblVentas;
    // End of variables declaration//GEN-END:variables
}
