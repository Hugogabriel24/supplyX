package pagesOpcoes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.swing.JOptionPane;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.Waypoint;
import MapViewFrete.RoutingData;
import static pagesOpcoes.TelaFrete.calcular;
import MapViewFrete.EventWaypoint;
import MapViewFrete.MyWaypoint;
import MapViewFrete.WaypointRender;
import br.com.tanamao.dal.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Iterator;
import notification.Notification;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

public class TelaFrete extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public static JXMapViewer mapViewer;
    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String API_KEY = "AIzaSyAZbN8L3qEPm7KMiW3drBUIbdCJhZ5Fz5c";
    public static Point startPoint;
    public static final Set<MyWaypoint> waypointsXs = new HashSet<>();
    public static List<RoutingData> routingData = new ArrayList<>();
    public static EventWaypoint event;
    public static Point mousePosition;

    public TelaFrete() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        ListarEntregador();

    }

    public void ListarEntregador() {
        String sql = "select nome from entregadores order by nome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cbEntregador.addItem(nome);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public static GeoPosition geocodeAddress(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String requestUrl = GEOCODING_API_URL + "?address=" + encodedAddress + "&key=" + API_KEY;

            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response using Gson library
                Gson gson = new Gson();
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray resultsArray = jsonObject.getAsJsonArray("results");
                JsonObject firstResult = resultsArray.get(0).getAsJsonObject();
                JsonObject location = firstResult.getAsJsonObject("geometry").getAsJsonObject("location");
                double latitude = location.get("lat").getAsDouble();
                double longitude = location.get("lng").getAsDouble();

                return new GeoPosition(latitude, longitude);
            } else {
                System.out.println("Geocoding request failed. Response Code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void showMap(String enderecoInicial) {
        GeoPosition origem = geocodeAddress(enderecoInicial);
        mapViewer = new JXMapViewer();
        mapViewer.setZoom(100);
        TileFactory tileFactory = new DefaultTileFactory(new OSMTileFactoryInfo());
        mapViewer.setTileFactory(tileFactory);
        mapViewer.setAddressLocation(origem);

        JFrame frame = new JFrame("Visualização no mapa");
        frame.setLayout(new BorderLayout());
        frame.add(mapViewer);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mapViewer.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                // Armazene a posição inicial do mouse
                startPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Limpe a posição inicial do mouse
                startPoint = null;
            }
        }
        );

        mapViewer.addMouseWheelListener(e -> {
            int rotation = e.getWheelRotation();
            int zoom = mapViewer.getZoom();

            // Ajuste o nível de zoom com base na direção da rolagem do mouse
            if (rotation < 0) {
                // Zoom in
                if (zoom > mapViewer.getTileFactory().getInfo().getMinimumZoomLevel()) {
                    mapViewer.setZoom(zoom - 1);
                }
            } else {
                // Zoom out
                if (zoom < mapViewer.getTileFactory().getInfo().getMaximumZoomLevel()) {
                    mapViewer.setZoom(zoom + 1);
                }
            }
        });

        mapViewer.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (startPoint != null) {
                    // Calcule o deslocamento do mouse
                    int dx = e.getX() - startPoint.x;
                    int dy = e.getY() - startPoint.y;

                    // Obtenha a posição atual do mapa
                    GeoPosition currentPosition = mapViewer.getAddressLocation();

                    // Converta a posição atual do mapa em coordenadas de pixel
                    Point2D currentPositionPixel = mapViewer.getTileFactory().geoToPixel(currentPosition, mapViewer.getZoom());

                    // Calcule a nova posição do mapa com base no deslocamento do mouse
                    double newPixelX = currentPositionPixel.getX() - dx;
                    double newPixelY = currentPositionPixel.getY() - dy;

                    // Converta as novas coordenadas de pixel na nova posição do mapa
                    GeoPosition newPosition = mapViewer.getTileFactory().pixelToGeo(new Point2D.Double(newPixelX, newPixelY), mapViewer.getZoom());

                    // Defina a nova posição do mapa
                    mapViewer.setAddressLocation(newPosition);

                    // Atualize a posição inicial do mouse
                    startPoint = e.getPoint();
                }
            }
        });

    }

    public static void addMapPoints(String enderecoOrigem, String enderecoDestino) {

        GeoPosition origem = geocodeAddress(enderecoOrigem);
        GeoPosition destino = geocodeAddress(enderecoDestino);

        waypointsXs.add(new MapViewFrete.MyWaypoint("teste", geocodeAddress(enderecoOrigem)));
        waypointsXs.add(new MapViewFrete.MyWaypoint("teste", geocodeAddress(enderecoDestino)));
        initWaypoint();

    }

    public static List<Waypoint> createWaypoints(List<GeoPosition> positions) {
        List<Waypoint> waypoints = new ArrayList<>();
        for (GeoPosition position : positions) {
            waypoints.add(new DefaultWaypoint(position));
        }
        return waypoints;
    }

//    public static void addWaypoint(MyWaypoint waypoint) {
//        for (MyWaypoint d : waypointsXs) {
//            mapViewer.remove(d.getButton());
//        }
//        Iterator<MyWaypoint> iter = waypointsXs.iterator();
//        while (iter.hasNext()) {
//            if (iter.next().getPointType() == waypoint.getPointType()) {
//                iter.remove();
//            }
//        }
//        waypointsXs.add(waypoint);
//        initWaypoint();
//    }
    public static void initWaypoint() {
        WaypointPainter<MyWaypoint> wp = new WaypointRender();
        wp.setWaypoints(waypointsXs);
        mapViewer.setOverlayPainter(wp);
        for (MyWaypoint d : waypointsXs) {
            mapViewer.add(d.getButton());
        }

    }

    public static void clearWaypoint() {
        for (MyWaypoint d : waypointsXs) {
            mapViewer.remove(d.getButton());
        }
        routingData.clear();
        waypointsXs.clear();
        initWaypoint();
    }

//    private EventWaypoint getEvent() {
//        return new EventWaypoint() {
//            @Override
//            public void selected(MyWaypoint waypoint) {
//                //JOptionPane.showMessageDialog(Main.this, waypoint.getName());
//            }
//        };
//    }
    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public static String calcular(String origem, String destino) {
        URL url;
        try {
            url = new URL(
                    "https://maps.googleapis.com/maps/api/directions/xml?origin=" + origem + "&destination=" + destino + "&key=AIzaSyAOME7FCQDBd-yzqsZWAhWxh6-95llIJTA");

            Document document = getDocumento(url);

            return analisaXml(document);
        } catch (MalformedURLException | DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressWarnings("rawtypes")
    public static String analisaXml(Document document) {
        List list = document
                .selectNodes("//DirectionsResponse/route/leg/distance/text");

        Element element = (Element) list.get(list.size() - 1);

        return element.getText();
    }

    public static Document getDocumento(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    public static String calcMin(String origem, String destino) {
        URL url;
        try {
            url = new URL(
                    "https://maps.googleapis.com/maps/api/directions/xml?origin=" + origem + "&destination=" + destino + "&key=AIzaSyAOME7FCQDBd-yzqsZWAhWxh6-95llIJTA");

            Document document = getDocumento1(url);

            return analisaXml1(document);
        } catch (MalformedURLException | DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressWarnings("rawtypes")
    public static String analisaXml1(Document document) {
        List list = document
                .selectNodes("//DirectionsResponse/route/leg/duration/text");

        Element element = (Element) list.get(list.size() - 1);

        return element.getText();
    }

    public static Document getDocumento1(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    public void calcularKm() {
        if (txDestino.getText().equals("") || txOrigem.getText().equals("")) {
            Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Campo de origem ou destino vazia!!");
            noti.showNotification();
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String rua1 = txOrigem.getText();
            String rua2 = txDestino.getText();
            String calc = calcular(rua1, rua2);
            String calcM = calcMin(rua1, rua2);
            String Minutos = calcM.replaceAll("[\\D]", "");
            String kmConv = calc.replaceAll("[\\D]", "");
            int kmFrete = Integer.parseInt(kmConv);
            Double preco, total;
            preco = 0.13;
            total = kmFrete * preco + 5;
            String valorF = decimalFormat.format(total);
            txKM.setText(calc);
            txVLR.setText(valorF + " R$");
            txMin.setText(Minutos + " minutos");

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
        txOrigem = new loginAnimation.TextFIeld();
        cbEntregador = new javax.swing.JComboBox<>();
        txDestino = new loginAnimation.TextFIeld();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txKM = new javax.swing.JLabel();
        txVLR = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txMin = new javax.swing.JLabel();
        Btcopy1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculador de frete");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txOrigem.setBackground(new java.awt.Color(255, 255, 255));
        txOrigem.setForeground(new java.awt.Color(0, 0, 0));
        txOrigem.setCaretColor(new java.awt.Color(0, 0, 0));
        txOrigem.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txOrigem.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txOrigem.setLabelText("Endereço de origem");

        cbEntregador.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        txDestino.setBackground(new java.awt.Color(255, 255, 255));
        txDestino.setForeground(new java.awt.Color(0, 0, 0));
        txDestino.setCaretColor(new java.awt.Color(0, 0, 0));
        txDestino.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txDestino.setLabelText("Endereço de destino");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Selecione o entregador");

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/entregador.png"))); // NOI18N
        jButton1.setText("Calcular frete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(147, 167, 39));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("KM TOTAL:");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("PREÇO DO FRETE:");

        txKM.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txKM.setForeground(new java.awt.Color(255, 255, 255));

        txVLR.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txVLR.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TEMPO TOTAL:");

        txMin.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        txMin.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txVLR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txKM, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txMin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txKM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txMin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txVLR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        Btcopy1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/casa.png"))); // NOI18N
        Btcopy1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btcopy1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MapViewFrete/localizacao.png"))); // NOI18N
        jButton2.setText("  Abrir Mapa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/copy.png"))); // NOI18N
        jButton3.setText("Copiar como mensagem");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txDestino, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                            .addComponent(txOrigem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(Btcopy1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbEntregador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbEntregador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btcopy1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        calcularKm();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void Btcopy1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btcopy1ActionPerformed
        // TODO add your handling code here:
        txOrigem.setText("Rua Francisco Berenguer, 737, Recife-PE");
    }//GEN-LAST:event_Btcopy1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (txDestino.getText().equals("") || txOrigem.getText().equals("")) {
            Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Campo de origem ou destino vazia!!");
            noti.showNotification();

        } else {
            showMap(txOrigem.getText());
            addMapPoints(txOrigem.getText(), txDestino.getText());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaFrete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaFrete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaFrete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaFrete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaFrete().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btcopy1;
    private javax.swing.JComboBox<String> cbEntregador;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private loginAnimation.TextFIeld txDestino;
    private javax.swing.JLabel txKM;
    private javax.swing.JLabel txMin;
    private loginAnimation.TextFIeld txOrigem;
    private javax.swing.JLabel txVLR;
    // End of variables declaration//GEN-END:variables
}
