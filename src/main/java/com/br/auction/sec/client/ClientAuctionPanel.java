/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.br.auction.sec.client;

import com.br.auction.sec.entity.Items;
import com.br.auction.sec.entity.User;
import com.br.auction.sec.entity.dao.Multicast;
import com.br.auction.sec.server.ServerStatic;
import com.br.auction.sec.service.AuctionMonitoring;
import com.br.auction.sec.service.MulticastService;
import com.br.auction.sec.util.CryptoUtils;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author maria
 */
public class ClientAuctionPanel extends javax.swing.JPanel {

    /**
     * Creates new form ClientPan
     */
    private AuctionMonitoring monitoring;

    private MulticastService multicastService;

    private User user;

    public ClientAuctionPanel(User user, Multicast multicast) throws Exception {
        initComponents();

        multicastService = new MulticastService(multicast.getGroup(), multicast.getPort(), this);
        new Thread(multicastService).start();
        monitoring = new AuctionMonitoring(multicastService);

        if (monitoring != null) {
            displayItems(monitoring.returnItem(true, new Items()));
        }

        this.user = user;
        if (user != null) {
            labelCurrentUser.setText("Seu ID é: " + user.getIdAuction());
        }
    }

    public ClientAuctionPanel() throws Exception {
        initComponents();

        multicastService = new MulticastService("230.0.0.0", 5000, this);
        new Thread(multicastService).start();
        monitoring = new AuctionMonitoring(multicastService);

        if (monitoring != null) {
            displayItems(monitoring.returnItem(true, new Items()));
        }

    }

    public void displayItems(String encrypted) throws Exception {
        SecretKey secret = user != null ? user.getSimetricKey() : ServerStatic.getSecretKey();
        byte[] iniVetor = user != null ? user.getIniVetor() : ServerStatic.getIniVetor();

        JSONObject json = new JSONObject((CryptoUtils.decryptSim(encrypted, secret, iniVetor)));
        itemName.setText(json.get("itemName").toString());
        itemValue.setText(json.get("itemValue").toString());
        String image = (json.get("itemImage").toString());
        itemImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + image)));
    }

    public void displayMessage(String encrypted) throws Exception {
        SecretKey secret = user != null ? user.getSimetricKey() : ServerStatic.getSecretKey();
        byte[] iniVetor = user != null ? user.getIniVetor() : ServerStatic.getIniVetor();

        System.out.println("SECRET KEY: " + secret.hashCode());
        System.out.println(Arrays.toString(iniVetor));

        JSONObject json = new JSONObject((CryptoUtils.decryptSim(encrypted, secret, iniVetor)));
        System.out.println("JSON DECRIPTOGRAFADO: " + json);
        if (json.has("itemValue")) {
            labelUser.setText("Nenhum usuário realizou lance");
            itemName.setText(json.get("itemName").toString());
            itemValue.setText(json.get("itemValue").toString());
            String image = (json.get("itemImage").toString());
            itemImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/" + image)));
        } else if (json.has("time")) {
            timeLabel.setText("Tempo restante: " + CryptoUtils.decryptSim(json.get("time").toString(), secret, iniVetor) + " segundos");
        } else if (json.has("currentBid")) {
            Double value = Double.valueOf(itemValue.getText());
            itemValue.setText(String.valueOf(value + 1000));
            labelUser.setText(json.get("currentUser").toString());
        } else if (json.has("shutdown") && user != null) {
            JOptionPane.showMessageDialog(null, "Vencedor deste item: " + labelUser.getText(), "Item finalizado", JOptionPane.INFORMATION_MESSAGE);
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

        jPasswordField1 = new javax.swing.JPasswordField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        itemName = new javax.swing.JLabel();
        itemImage = new javax.swing.JLabel();
        itemValue = new javax.swing.JLabel();
        giveBid = new javax.swing.JButton();
        labelCurrentUser = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        labelUser = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setEnabled(false);
        setMaximumSize(new java.awt.Dimension(560, 531));
        setMinimumSize(new java.awt.Dimension(560, 531));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(560, 531));

        jPasswordField1.setText("jPasswordField1");

        jButton1.setText("Entrar");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/auctionT.png"))); // NOI18N

        itemName.setFont(new java.awt.Font("Montserrat SemiBold", 0, 24)); // NOI18N
        itemName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemName.setText("[Nome Item]");

        itemImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemImage.setText(" ");
        itemImage.setPreferredSize(new java.awt.Dimension(200, 200));

        itemValue.setFont(new java.awt.Font("Montserrat SemiBold", 0, 24)); // NOI18N
        itemValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemValue.setText("[Valor do Item]");

        giveBid.setFont(new java.awt.Font("Montserrat SemiBold", 0, 18)); // NOI18N
        giveBid.setText("Dar Lance");
        giveBid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                giveBidMouseClicked(evt);
            }
        });

        labelCurrentUser.setFont(new java.awt.Font("Montserrat SemiBold", 0, 18)); // NOI18N
        labelCurrentUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelCurrentUser.setText("[Id do Usuário]");

        timeLabel.setFont(new java.awt.Font("Montserrat SemiBold", 0, 12)); // NOI18N
        timeLabel.setText("Tempo Restante:");

        jLabel1.setFont(new java.awt.Font("Montserrat SemiBold", 0, 14)); // NOI18N
        jLabel1.setText("Valor minimo entre lances: R$1000");

        labelUser.setFont(new java.awt.Font("Montserrat SemiBold", 0, 18)); // NOI18N
        labelUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelUser.setText("Nenhum usuário realizou lance");

        jLabel4.setFont(new java.awt.Font("Montserrat SemiBold", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Lance atual feito por:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(773, 773, 773)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 2944, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(98, 98, 98)
                        .addComponent(timeLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(itemName, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(itemValue, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(itemImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(giveBid))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(labelCurrentUser, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(labelUser, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(jLabel4)))
                .addContainerGap(118, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeLabel))
                .addGap(18, 18, 18)
                .addComponent(itemName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(itemImage, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(itemValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(2, 2, 2)
                .addComponent(labelUser)
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(giveBid)
                .addGap(18, 18, 18)
                .addComponent(labelCurrentUser)
                .addGap(202, 202, 202)
                .addComponent(jLabel2)
                .addGap(35, 35, 35)
                .addComponent(jFormattedTextField1)
                .addGap(18, 18, 18)
                .addComponent(jPasswordField1)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void giveBidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_giveBidMouseClicked
        JSONObject json = new JSONObject();
        try {
            json.put("currentBid", ("true"));
            json.put("currentUser", user.getIdAuction());
            System.out.println("JSON DAR LANCE: " + json.toString());
        } catch (Exception ex) {
            Logger.getLogger(ClientAuctionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // enviar nome do usuario tb
            multicastService.sendMessage(CryptoUtils.encryptSim(json.toString(), user));
        } catch (Exception ex) {
            Logger.getLogger(ClientAuctionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_giveBidMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton giveBid;
    private javax.swing.JLabel itemImage;
    private javax.swing.JLabel itemName;
    private javax.swing.JLabel itemValue;
    private javax.swing.JButton jButton1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JLabel labelCurrentUser;
    private javax.swing.JLabel labelUser;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
