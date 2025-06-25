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
        picoIdTxt = new javax.swing.JTextField();
        rutaElegidaLabel = new javax.swing.JLabel();
        altitudTxt = new javax.swing.JTextField();
        accidenteIdTxt = new javax.swing.JTextField();
        cuposTxt = new javax.swing.JTextField();
        mortalidadTxt = new javax.swing.JTextField();
        fechaTxt = new javax.swing.JTextField();
        resultadoTxt = new javax.swing.JTextField();
        rutaTxt = new javax.swing.JTextField();
        motivoTxt = new javax.swing.JTextField();
        Miembros = new javax.swing.JButton();
        BorrarBtn1 = new javax.swing.JButton();
        picos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nuevaExpedicionBtn.setText("Nueva Expedicion");
        nuevaExpedicionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaExpedicionBtnActionPerformed(evt);
            }
        });

        volverBtn.setText("Volver");
        volverBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverBtnActionPerformed(evt);
            }
        });

        cambiarResultadoBtn.setText("Cambiar Expedicion");
        cambiarResultadoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarResultadoBtnActionPerformed(evt);
            }
        });

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

        expedicionesProcesoLabel.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        expedicionesProcesoLabel.setText("Expediciones en proceso");

        picoIdLabel.setText("Pico ID:");

        accidenteIdLabel.setText("Accidente ID:");

        altitudLabel.setText("Altitud:");

        cuposLabel.setText("Cupos:");

        mortalidadLabel.setText("Mortalidad");

        fechaLabel.setText("Fecha: ");

        resultadoLabel.setText("Resultado:");

        motivoLabel.setText("Motivo:");

        picoIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                picoIdTxtActionPerformed(evt);
            }
        });

        rutaElegidaLabel.setText("Ruta Elegida: ");

        altitudTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altitudTxtActionPerformed(evt);
            }
        });

        accidenteIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accidenteIdTxtActionPerformed(evt);
            }
        });

        Miembros.setText("Miembros");
        Miembros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MiembrosActionPerformed(evt);
            }
        });

        BorrarBtn1.setText("Borrar");
        BorrarBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarBtn1ActionPerformed(evt);
            }
        });

        picos.setText("Picos");
        picos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                picosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(expedicionesProcesoLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(nuevaExpedicionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cambiarResultadoBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BorrarBtn1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(volverBtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(picos, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(Miembros)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rutaElegidaLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(rutaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(accidenteIdLabel)
                                    .addComponent(picoIdLabel)
                                    .addComponent(altitudLabel)
                                    .addComponent(cuposLabel)
                                    .addComponent(mortalidadLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(picoIdTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(accidenteIdTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(altitudTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuposTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mortalidadTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(resultadoLabel)
                                    .addComponent(motivoLabel)
                                    .addComponent(fechaLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(resultadoTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(motivoTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fechaTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(expedicionesProcesoLabel)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(picoIdLabel)
                            .addComponent(picoIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(accidenteIdLabel)
                            .addComponent(accidenteIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(altitudTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cuposTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuposLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mortalidadTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mortalidadLabel)))
                            .addComponent(altitudLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resultadoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(resultadoLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(motivoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(motivoLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rutaElegidaLabel)
                            .addComponent(rutaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevaExpedicionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(volverBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Miembros, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(picos, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cambiarResultadoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BorrarBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 740, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    private void accidenteIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accidenteIdTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accidenteIdTxtActionPerformed

    private void altitudTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altitudTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_altitudTxtActionPerformed

    private void picoIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_picoIdTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_picoIdTxtActionPerformed

    private void cambiarResultadoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarResultadoBtnActionPerformed
        // TODO add your handling code here:
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
 List<Pico> picos = picosdao.getAllPeaks();

    if (picos.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay picos cargados en la base de datos.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    String[] columnas = {"ID", "Localización", "Nombre", "Abierto", "Altura", "Cambio Trekking", "Sin Aprobación", "Estado", "Host", "Restricciones"};
    Object[][] datos = new Object[picos.size()][columnas.length];

    for (int i = 0; i < picos.size(); i++) {
        Pico pico = picos.get(i);
        datos[i][0] = pico.getId();
        datos[i][1] = pico.getLocalizacion();
        datos[i][2] = pico.getNombrePico();
        datos[i][3] =( pico.getAbierto()==1)? "si" : "no";
        datos[i][4] = pico.getAltura();
        datos[i][5] = (pico.getCambio_trekking()==1)? "si" : "no";
        datos[i][6] = (pico.getSin_aprobacion()==1)? "si" : "no";
        datos[i][7] = pico.getEstado();
        datos[i][8] = (pico.getHost()==1)? "Nepal" : "Otro";
        datos[i][9] = pico.getRestricciones();
    }

    JTable tabla = new JTable(datos, columnas);
    JScrollPane scrollPane = new JScrollPane(tabla);
    scrollPane.setPreferredSize(new Dimension(800, 300));

    JOptionPane.showMessageDialog(this, scrollPane, "Todos los Picos Registrados", JOptionPane.INFORMATION_MESSAGE);


    }//GEN-LAST:event_picosActionPerformed

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
private void MiembrosActionPerformed(java.awt.event.ActionEvent evt) {
    String input = JOptionPane.showInputDialog(this, "Ingrese el ID de la expedición:", "Buscar Miembros", JOptionPane.QUESTION_MESSAGE);

    if (input != null && !input.trim().isEmpty()) {
        try {
            int idExpedicion = Integer.parseInt(input.trim());

            List<Object[]> miembros = miembrosDao.obtenerMiembrosPorExpedicion(idExpedicion);

            if (miembros.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron miembros para la expedición ID " + idExpedicion, "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String[] columnas = {"ID", "Nombre", "Apellido", "Nacionalidad", "Sexo", "Líder", "Staff", "Nacimiento", "Fallecido"};
            Object[][] datos = new Object[miembros.size()][columnas.length];

            for (int i = 0; i < miembros.size(); i++) {
                datos[i] = miembros.get(i);
            }

            JTable tabla = new JTable(datos, columnas);
            JScrollPane scrollPane = new JScrollPane(tabla);
            scrollPane.setPreferredSize(new Dimension(700, 300));

            JOptionPane.showMessageDialog(this, scrollPane, "Miembros de la Expedición " + idExpedicion, JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
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
    private javax.swing.JButton Miembros;
    private javax.swing.JLabel accidenteIdLabel;
    private javax.swing.JTextField accidenteIdTxt;
    private javax.swing.JLabel altitudLabel;
    private javax.swing.JTextField altitudTxt;
    private javax.swing.JButton cambiarResultadoBtn;
    private javax.swing.JLabel cuposLabel;
    private javax.swing.JTextField cuposTxt;
    private javax.swing.JLabel expedicionesProcesoLabel;
    private javax.swing.JTable expedicionesTable;
    private javax.swing.JLabel fechaLabel;
    private javax.swing.JTextField fechaTxt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
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
