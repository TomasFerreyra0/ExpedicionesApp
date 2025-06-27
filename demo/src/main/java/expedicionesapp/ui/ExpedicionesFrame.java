package expedicionesapp.ui;

import expedicionesapp.dao.ExpedicionesDao; 
import expedicionesapp.dao.MiembrosDao;
import expedicionesapp.dao.PicoDao;
import expedicionesapp.model.Pico;
import expedicionesapp.model.Expediciones; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import javax.swing.table.DefaultTableModel; 
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextField; 
import java.text.SimpleDateFormat; 
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JTable;


/**
 *
 * @author 54224
 */
public class ExpedicionesFrame extends javax.swing.JFrame {
    

    // Declara una instancia de ExpedicionesDao
    private ExpedicionesDao expedicionesDao;
    private ExpedicionesTableModel expedicionesTableModel;
    private MiembrosDao miembrosDao;
    private PicoDao picosdao;

    
    
    
    /**
     * Creates new form Expediciones
     */
    public ExpedicionesFrame() {
        initComponents();
        miembrosDao=new MiembrosDao();
        expedicionesDao = new ExpedicionesDao(); // Inicializa el DAO
        picosdao=new PicoDao();
        cargarTablaExpediciones(); // Llama al método para cargar la tabla al iniciar la ventana
        
        // 2. Inicializar y configurar el ExpedicionesTableModel para tu JTable
        // Es crucial que 'tablaExpediciones' ya haya sido inicializada por initComponents()
        expedicionesTableModel = new ExpedicionesTableModel(expedicionesDao.getAllExpeditions());
        expedicionesTable.setModel(expedicionesTableModel); // <-- Aquí asocias el modelo a tu JTable
        
        expedicionesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = expedicionesTable.getSelectedRow();
                    if (selectedRow != -1) { 
                        seleccionarExpedicion(expedicionesTable, picoIdTxt, accidenteIdTxt,
                                              altitudTxt, cuposTxt, mortalidadTxt, fechaTxt,
                                              resultadoTxt, motivoTxt, rutaTxt);
                    } else {
                        // Opcional: Limpiar campos si no hay selección
                        // limpiarCampos(); // Si tienes un método para limpiar
                    }
                }
            }
        });
        
    }
    
        private void crearNuevaExpedicion() {
            try {
                // --- Validación de campos obligatorios (NOT NULL en BD) ---
                // Validar Pico ID
                if (picoIdTxt.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo 'Pico ID' no puede estar vacío.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                    return; // Sale del método si falla la validación
                }
                int idPico;
                try {
                    idPico = Integer.parseInt(picoIdTxt.getText().trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El campo 'Pico ID' debe ser un número entero válido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validar Accidente ID (si es NOT NULL y 0 no es un valor válido, o si es FK y 0 no existe)
                int idAccidente;
                if (accidenteIdTxt.getText().trim().isEmpty()) {
                    idAccidente = 0; // O un valor sentinel para luego manejarlo en el DAO o aquí si se permite NULL en BD
                } else {
                    try {
                        idAccidente = Integer.parseInt(accidenteIdTxt.getText().trim());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "El campo 'Accidente ID' debe ser un número entero válido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                
                // Validar Motivo
                String motivo = motivoTxt.getText().trim();
                if (motivo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo 'Motivo' no puede estar vacío.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validar Ruta
                String ruta = rutaTxt.getText().trim();
                if (ruta.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo 'Ruta Elegida' no puede estar vacío.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // --- Manejo de campos opcionales (NULL en BD) ---
                float altitud;
                if (altitudTxt.getText().trim().isEmpty()) {
                    altitud = 0.0f; // Asignar 0.0f si el campo está vacío, ya que altitud permite NULL en BD
                } else {
                    try {
                        altitud = Float.parseFloat(altitudTxt.getText().trim());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "El campo 'Altitud' debe ser un número decimal válido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                int cupos;
                if (cuposTxt.getText().trim().isEmpty()) {
                    cupos = 0; // Asignar 0 si el campo está vacío, ya que cupos permite NULL en BD
                } else {
                    try {
                        cupos = Integer.parseInt(cuposTxt.getText().trim());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "El campo 'Cupos' debe ser un número entero válido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                int conteoMortalidad;
                if (mortalidadTxt.getText().trim().isEmpty()) {
                    conteoMortalidad = 0; // Asignar 0 si el campo está vacío, ya que conteo_mortalidad permite NULL en BD
                } else {
                    try {
                        conteoMortalidad = Integer.parseInt(mortalidadTxt.getText().trim());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "El campo 'Mortalidad' debe ser un número entero válido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                java.util.Date fechaUtil = null;
                if (!fechaTxt.getText().trim().isEmpty()) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        dateFormat.setLenient(false); // Importante para que no acepte fechas inválidas como 2023-02-30
                        fechaUtil = dateFormat.parse(fechaTxt.getText().trim());
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(this, "Error en el formato de la fecha. Por favor, use el formato AAAA-MM-DD (ej. 2025-06-23).", "Error de Formato de Fecha", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                java.sql.Date fechaSQL = (fechaUtil != null) ? new java.sql.Date(fechaUtil.getTime()) : null;


                String resultado = resultadoTxt.getText().trim(); // resultado permite NULL, así que puede ser ""

                //Crear un objeto Expediciones con los datos del formulario
                Expediciones nuevaExpedicion = new Expediciones();
                nuevaExpedicion.setIdPico(idPico);
                nuevaExpedicion.setIdAccidente(idAccidente); 
                nuevaExpedicion.setAltitud(altitud); 
                nuevaExpedicion.setCupos(cupos); 
                nuevaExpedicion.setConteoMortalidad(conteoMortalidad); 
                nuevaExpedicion.setFecha(fechaSQL); 
                nuevaExpedicion.setResultado(resultado.isEmpty() ? null : resultado); 
                nuevaExpedicion.setMotivo(motivo); 
                nuevaExpedicion.setRuta(ruta); 

                //Llamar al método insertExpedition de ExpedicionesDao para guardar en la base de datos
                boolean insertadoExitosamente = expedicionesDao.insertExpedition(nuevaExpedicion);

                if (insertadoExitosamente) {
                    //Actualizar la tabla si la inserción fue exitosa
                    expedicionesTableModel.setExpediciones(expedicionesDao.getAllExpeditions());
                    expedicionesTableModel.fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
                    JOptionPane.showMessageDialog(this, "Expedición creada y añadida a la tabla correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    //Limpiar el formulario para la siguiente entrada 
                    limpiarFormulario();
                } else {
                    // El mensaje de error ya se imprime en el DAO, aquí solo mostramos el general
                    JOptionPane.showMessageDialog(this, "No se pudo insertar la expedición en la base de datos. Verifique los datos ingresados.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) { // Captura cualquier otra excepción inesperada, como NumberFormatException
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al crear la expedición: " + ex.getMessage(), "Error Inesperado", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Para depuración, imprime la pila de llamadas en la consola de NetBeans
            }
        }
    
    private void limpiarFormulario() {
        picoIdTxt.setText("");
        accidenteIdTxt.setText("");
        altitudTxt.setText("");
        cuposTxt.setText("");
        mortalidadTxt.setText("");
        fechaTxt.setText("");
        resultadoTxt.setText("");
        motivoTxt.setText("");
        rutaTxt.setText("");
    }
    
    
    
    private void cargarTablaExpediciones() {
        // Define las columnas de la tabla
        String[] columnNames = {"ID", "Pico ID", "Accidente ID", "Altitud", "Cupos", 
                                "Mortalidad", "Fecha", "Resultado", "Motivo", "Ruta"};
        
        // Crea un DefaultTableModel. El segundo argumento es el número inicial de filas (0)
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); 
        
        // Obtiene la lista de expediciones del DAO
        List<Expediciones> listaExpediciones = expedicionesDao.getAllExpeditions();
        
        // Itera sobre la lista y añade cada expedición como una fila a la tabla
        for (Expediciones expedicion : listaExpediciones) {
            Object[] rowData = new Object[10]; // 10 columnas
            rowData[0] = expedicion.getId();
            rowData[1] = expedicion.getIdPico();
            rowData[2] = expedicion.getIdAccidente();
            rowData[3] = expedicion.getAltitud();
            rowData[4] = expedicion.getCupos();
            rowData[5] = expedicion.getConteoMortalidad();
            rowData[6] = expedicion.getFecha();
            rowData[7] = expedicion.getResultado();
            rowData[8] = expedicion.getMotivo();
            rowData[9] = expedicion.getRuta();
            
            model.addRow(rowData);
        }
        
        // Asigna el modelo al JTable
        expedicionesTable.setModel(model); 
    }

    private void seleccionarExpedicion(javax.swing.JTable paramTableExpediciones,
                                       javax.swing.JTextField paramPicoId,
                                       javax.swing.JTextField paramAccidenteId,
                                       javax.swing.JTextField paramAltitud,
                                       javax.swing.JTextField paramCupos,
                                       javax.swing.JTextField paramMortalidad,
                                       javax.swing.JTextField paramFecha,
                                       javax.swing.JTextField paramResultado,
                                       javax.swing.JTextField paramMotivo,
                                       javax.swing.JTextField paramRuta) {
        try {
            int fila = paramTableExpediciones.getSelectedRow();

            if (fila >= 0) {
                paramPicoId.setText(paramTableExpediciones.getValueAt(fila, 1).toString());      // Columna 1 (Pico ID)
                paramAccidenteId.setText(paramTableExpediciones.getValueAt(fila, 2).toString()); // Columna 2 (Accidente ID)
                paramAltitud.setText(paramTableExpediciones.getValueAt(fila, 3).toString());
                paramCupos.setText(paramTableExpediciones.getValueAt(fila, 4).toString());
                paramMortalidad.setText(paramTableExpediciones.getValueAt(fila, 5).toString());

                // La fecha requiere formateo si no es String en la tabla
                Object fechaObj = paramTableExpediciones.getValueAt(fila, 6);
                if (fechaObj instanceof java.util.Date || fechaObj instanceof java.sql.Date) {
                    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    paramFecha.setText(dateFormat.format((java.util.Date) fechaObj));
                } else {
                    paramFecha.setText(fechaObj != null ? fechaObj.toString() : "");
                }
                
                paramResultado.setText(paramTableExpediciones.getValueAt(fila, 7).toString());
                paramMotivo.setText(paramTableExpediciones.getValueAt(fila, 8).toString());
                paramRuta.setText(paramTableExpediciones.getValueAt(fila, 9).toString());
            } else {
                // Limpia todos los campos si no hay selección
                paramPicoId.setText("");
                paramAccidenteId.setText("");
                paramAltitud.setText("");
                paramCupos.setText("");
                paramMortalidad.setText("");
                paramFecha.setText("");
                paramResultado.setText("");
                paramMotivo.setText("");
                paramRuta.setText("");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al seleccionar expedición: " + e.getMessage());
        }
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
        nuevaExpedicionBtn = new javax.swing.JButton();
        volverBtn = new javax.swing.JButton();
        cambiarResultadoBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        expedicionesTable = new javax.swing.JTable();
        expedicionesProcesoLabel = new javax.swing.JLabel();
        picoIdLabel = new javax.swing.JLabel();
        accidenteIdLabel = new javax.swing.JLabel();
        altitudLabel = new javax.swing.JLabel();
        cuposLabel = new javax.swing.JLabel();
        mortalidadLabel = new javax.swing.JLabel();
        fechaLabel = new javax.swing.JLabel();
        resultadoLabel = new javax.swing.JLabel();
        motivoLabel = new javax.swing.JLabel();
        rutaElegidaLabel = new javax.swing.JLabel();
        altitudTxt = new javax.swing.JTextField();
        accidenteIdTxt = new javax.swing.JTextField();
        cuposTxt = new javax.swing.JTextField();
        mortalidadTxt = new javax.swing.JTextField();
        fechaTxt = new javax.swing.JTextField();
        resultadoTxt = new javax.swing.JTextField();
        rutaTxt = new javax.swing.JTextField();
        motivoTxt = new javax.swing.JTextField();
        BorrarBtn1 = new javax.swing.JButton();
        picos = new javax.swing.JButton();
        miembros = new javax.swing.JButton();
        picoIdTxt = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 500));
        setPreferredSize(new java.awt.Dimension(800, 500));
        setSize(new java.awt.Dimension(800, 500));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(106, 146, 218));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nuevaExpedicionBtn.setText("Nueva Expedicion");
        nuevaExpedicionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaExpedicionBtnActionPerformed(evt);
            }
        });
        jPanel1.add(nuevaExpedicionBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 130, 39));

        volverBtn.setText("Volver");
        volverBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverBtnActionPerformed(evt);
            }
        });
        jPanel1.add(volverBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, -1, 39));

        cambiarResultadoBtn.setText("Cambiar Expedicion");
        cambiarResultadoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarResultadoBtnActionPerformed(evt);
            }
        });
        jPanel1.add(cambiarResultadoBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 350, -1, 39));

        expedicionesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(expedicionesTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 18, 563, 300));

        expedicionesProcesoLabel.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        expedicionesProcesoLabel.setText("Expediciones en proceso");
        jPanel1.add(expedicionesProcesoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, -1));

        picoIdLabel.setText("Pico ID:");
        jPanel1.add(picoIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 20, -1, -1));

        accidenteIdLabel.setText("Accidente ID:");
        jPanel1.add(accidenteIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 48, -1, -1));

        altitudLabel.setText("Altitud:");
        jPanel1.add(altitudLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 78, -1, -1));

        cuposLabel.setText("Cupos:");
        jPanel1.add(cuposLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 112, -1, -1));

        mortalidadLabel.setText("Mortalidad");
        jPanel1.add(mortalidadLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 144, -1, -1));

        fechaLabel.setText("Fecha: ");
        jPanel1.add(fechaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 180, -1, -1));

        resultadoLabel.setText("Resultado:");
        jPanel1.add(resultadoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 220, -1, -1));

        motivoLabel.setText("Motivo:");
        jPanel1.add(motivoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 258, -1, -1));

        rutaElegidaLabel.setText("Ruta Elegida: ");
        jPanel1.add(rutaElegidaLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 298, -1, -1));

        altitudTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altitudTxtActionPerformed(evt);
            }
        });
        jPanel1.add(altitudTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 78, 70, 20));

        accidenteIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accidenteIdTxtActionPerformed(evt);
            }
        });
        jPanel1.add(accidenteIdTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 46, 70, 20));
        jPanel1.add(cuposTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 110, 70, 20));

        mortalidadTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mortalidadTxtActionPerformed(evt);
            }
        });
        jPanel1.add(mortalidadTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 142, 70, 20));
        jPanel1.add(fechaTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 180, 70, 20));
        jPanel1.add(resultadoTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 218, 70, 20));
        jPanel1.add(rutaTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 296, 70, 20));
        jPanel1.add(motivoTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 256, 70, 20));

        BorrarBtn1.setText("Borrar");
        BorrarBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarBtn1ActionPerformed(evt);
            }
        });
        jPanel1.add(BorrarBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, -1, 39));

        picos.setText("Picos");
        picos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                picosActionPerformed(evt);
            }
        });
        jPanel1.add(picos, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 350, 118, 39));

        miembros.setText("Miembros");
        miembros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miembrosActionPerformed(evt);
            }
        });
        jPanel1.add(miembros, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 350, 118, 39));

        picoIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                picoIdTxtActionPerformed(evt);
            }
        });
        jPanel1.add(picoIdTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(674, 18, 70, 20));

        background.setIcon(new javax.swing.ImageIcon("C:\\Users\\54224\\Desktop\\images\\background.png")); // NOI18N
        background.setText("jLabel1");
        jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    private void accidenteIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accidenteIdTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accidenteIdTxtActionPerformed

    private void altitudTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altitudTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_altitudTxtActionPerformed

    private void cambiarResultadoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarResultadoBtnActionPerformed
     String inputId = JOptionPane.showInputDialog(
        this,
        "Ingrese el ID de la expedición a modificar:",
        "Modificar Expedición",
        JOptionPane.QUESTION_MESSAGE
    );
    if (inputId == null || inputId.trim().isEmpty()) return;

    int idExpedicion;
    try {
        idExpedicion = Integer.parseInt(inputId.trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Expediciones expSel = expedicionesDao.getExpeditionById(idExpedicion);
    if (expSel == null) {
        JOptionPane.showMessageDialog(this,
            "No se encontró ninguna expedición con ese ID.",
            "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    String[] columnas = {
        "ID Pico", "ID Accidente", "Altitud", "Cupos",
        "Mortalidad", "Fecha (yyyy-MM-dd)", "Resultado", "Motivo", "Ruta Elegida"
    };

    Object[][] datos = {{
        expSel.getIdPico(),
        expSel.getIdAccidente(),
        expSel.getAltitud(),
        expSel.getCupos(),
        expSel.getConteoMortalidad(),
        expSel.getFecha() != null ? new SimpleDateFormat("yyyy-MM-dd").format(expSel.getFecha()) : "",
        expSel.getResultado() != null ? expSel.getResultado() : "",
        expSel.getMotivo(),
        expSel.getRuta()
    }};

    JTable tabla = new JTable(datos, columnas);
    JScrollPane scroll = new JScrollPane(tabla);
    scroll.setPreferredSize(new Dimension(750, 75));

    int opcion = JOptionPane.showConfirmDialog(
        this, scroll,
        "Editar Expedición ID " + idExpedicion,
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    );
    if (opcion != JOptionPane.OK_OPTION) return;

    try {
        Expediciones nueva = new Expediciones();
        nueva.setId(idExpedicion); // 🔒 El ID no se modifica

        nueva.setIdPico(Integer.parseInt(tabla.getValueAt(0, 0).toString().trim()));

        String acc = tabla.getValueAt(0, 1).toString().trim();
        nueva.setIdAccidente(acc.isEmpty() ? null : Integer.parseInt(acc));

        String alt = tabla.getValueAt(0, 2).toString().trim();
        nueva.setAltitud(alt.isEmpty() ? null : Float.parseFloat(alt));

        String cup = tabla.getValueAt(0, 3).toString().trim();
        nueva.setCupos(cup.isEmpty() ? null : Integer.parseInt(cup));

        String mort = tabla.getValueAt(0, 4).toString().trim();
        nueva.setConteoMortalidad(mort.isEmpty() ? null : Integer.parseInt(mort));

        String fechaTxt = tabla.getValueAt(0, 5).toString().trim();
        if (fechaTxt.isEmpty()) {
            nueva.setFecha(null);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date parsed = sdf.parse(fechaTxt);
            nueva.setFecha(parsed);
        }

        String resultado = tabla.getValueAt(0, 6).toString().trim();
        nueva.setResultado(resultado.isEmpty() ? null : resultado);

        String motivo = tabla.getValueAt(0, 7).toString().trim();
        if (motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Motivo' no puede estar vacío.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        nueva.setMotivo(motivo);

        String ruta = tabla.getValueAt(0, 8).toString().trim();
        nueva.setRuta(ruta);
        
        boolean ok = expedicionesDao.modifyExpedition(nueva);
        if (ok) {
            expedicionesTableModel.setExpediciones(expedicionesDao.getAllExpeditions());
            tabla.repaint();
            JOptionPane.showMessageDialog(this,
                "Expedición actualizada correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "No se pudo actualizar la expedición.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (NumberFormatException | ParseException ex) {
        JOptionPane.showMessageDialog(this,
            "Error en los datos ingresados: " + ex.getMessage(),
            "Error de Formato", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_cambiarResultadoBtnActionPerformed

    private void volverBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverBtnActionPerformed
        // Crea una instancia de ScreenFrame
        ScreenFrame screenFrame = new ScreenFrame();

        // Hace visible ScreenFrame
        screenFrame.setVisible(true);

        // Cierra la ventana actual
        this.dispose(); // 'this' se refiere al JFrame actual
    }//GEN-LAST:event_volverBtnActionPerformed

    private void nuevaExpedicionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaExpedicionBtnActionPerformed
        // TODO add your handling code here:
        crearNuevaExpedicion();
    }//GEN-LAST:event_nuevaExpedicionBtnActionPerformed

    private void picosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_picosActionPerformed
        PicoFrame picoFrame = new PicoFrame();
        picoFrame.setVisible(true);
        picoFrame.setLocationRelativeTo(this);
    }//GEN-LAST:event_picosActionPerformed

    private void miembrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miembrosActionPerformed
        MiembrosFrame screenFrame = new MiembrosFrame();
        screenFrame.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_miembrosActionPerformed

    private void mortalidadTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mortalidadTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mortalidadTxtActionPerformed

    private void picoIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_picoIdTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_picoIdTxtActionPerformed
     
private void BorrarBtn1ActionPerformed(java.awt.event.ActionEvent evt) {
    String input = JOptionPane.showInputDialog(this, "Ingrese el ID de la expedición a eliminar:");
    if (input != null && !input.trim().isEmpty()) {
        try {
            int idExpedicion = Integer.parseInt(input.trim());

            if (miembrosDao.tieneMiembrosAsociados(idExpedicion)) {
                JOptionPane.showMessageDialog(this,
                    "No se puede eliminar la expedición con ID " + idExpedicion +
                    " porque tiene miembros asociados.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                boolean eliminada = expedicionesDao.deleteExpedition(idExpedicion);
                if (eliminada) {
                    JOptionPane.showMessageDialog(this,
                        "Expedición eliminada correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "No se encontró la expedición con ese ID.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "El ID ingresado no es válido.",
                "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Expediciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Expediciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Expediciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Expediciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExpedicionesFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BorrarBtn1;
    private javax.swing.JLabel accidenteIdLabel;
    private javax.swing.JTextField accidenteIdTxt;
    private javax.swing.JLabel altitudLabel;
    private javax.swing.JTextField altitudTxt;
    private javax.swing.JLabel background;
    private javax.swing.JButton cambiarResultadoBtn;
    private javax.swing.JLabel cuposLabel;
    private javax.swing.JTextField cuposTxt;
    private javax.swing.JLabel expedicionesProcesoLabel;
    private javax.swing.JTable expedicionesTable;
    private javax.swing.JLabel fechaLabel;
    private javax.swing.JTextField fechaTxt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton miembros;
    private javax.swing.JLabel mortalidadLabel;
    private javax.swing.JTextField mortalidadTxt;
    private javax.swing.JLabel motivoLabel;
    private javax.swing.JTextField motivoTxt;
    private javax.swing.JButton nuevaExpedicionBtn;
    private javax.swing.JLabel picoIdLabel;
    private javax.swing.JTextField picoIdTxt;
    private javax.swing.JButton picos;
    private javax.swing.JLabel resultadoLabel;
    private javax.swing.JTextField resultadoTxt;
    private javax.swing.JLabel rutaElegidaLabel;
    private javax.swing.JTextField rutaTxt;
    private javax.swing.JButton volverBtn;
    // End of variables declaration//GEN-END:variables
}
