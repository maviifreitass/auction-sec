/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.br.auction.sec.client;

import com.br.auction.sec.db.UserDB;
import com.br.auction.sec.entity.User;
import com.br.auction.sec.entity.dao.Multicast;
import com.br.auction.sec.service.FrameClientService;
import com.br.auction.sec.service.UnicastClient;
import com.br.auction.sec.util.CryptoUtils;
import java.awt.BorderLayout;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.json.JSONObject;

/**
 *
 * @author maria
 */
public class ClientMainPanel extends javax.swing.JPanel {

    /**
     * Creates new form ClientPan
     */
    public ClientMainPanel() {
        initComponents();
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
        jLabel1 = new javax.swing.JLabel();
        cadastrarBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cpfLabel = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        entrarBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setEnabled(false);
        setMaximumSize(new java.awt.Dimension(625, 625));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(350, 400));

        jPasswordField1.setText("jPasswordField1");

        jButton1.setText("Entrar");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Montserrat SemiBold", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ainda não possui cadastro?");

        cadastrarBtn.setFont(new java.awt.Font("Montserrat SemiBold", 0, 12)); // NOI18N
        cadastrarBtn.setText("Cadastrar-se");
        cadastrarBtn.setBorder(null);
        cadastrarBtn.setContentAreaFilled(false);
        cadastrarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cadastrarBtnMouseClicked(evt);
            }
        });
        cadastrarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastrarBtnActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/auctionT.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Montserrat SemiBold", 0, 12)); // NOI18N
        jLabel4.setText("Seja bem-vindo!");

        entrarBtn.setText("Entrar");
        entrarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                entrarBtnMouseClicked(evt);
            }
        });
        entrarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entrarBtnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Montserrat SemiBold", 0, 12)); // NOI18N
        jLabel5.setText("Digite seu CPF");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(773, 773, 773)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 2944, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)))
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(cadastrarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3)))
            .addGroup(layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(entrarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(cpfLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(8, 8, 8)
                .addComponent(cpfLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(entrarBtn)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cadastrarBtn)
                .addGap(366, 366, 366)
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

    private void cadastrarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastrarBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cadastrarBtnActionPerformed

    private void entrarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entrarBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_entrarBtnActionPerformed

    private void entrarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_entrarBtnMouseClicked
        try {
            UserDB userDB = new UserDB();
            User user = new User();
            user = userDB.findByCpf(cpfLabel.getText(), Boolean.FALSE);

            String encryptedKey = UnicastClient.sendRequest(user);

            JSONObject receivedMsg = new JSONObject(encryptedKey);

            System.out.println(receivedMsg.toString());

            byte[] decodedKey = Base64.getDecoder().decode(receivedMsg.get("Key").toString());
            SecretKey secretKey = new SecretKeySpec(decodedKey, "AES");
            user.setSimetricKey(secretKey);
            System.out.println("SECRET KEY: " + secretKey.hashCode());

            String decryptedPort = receivedMsg.get("Port").toString();
            String decryptedGroup = receivedMsg.get("Group").toString();
            byte[] iniVetor = Base64.getDecoder().decode(receivedMsg.get("IV").toString());
             System.out.println( Arrays.toString(iniVetor));
            user.setIniVetor(iniVetor);

            int port = Integer.parseInt(decryptedPort);
            String group = decryptedGroup;

            Multicast multicast = new Multicast(port, group);

            CryptoUtils crypto = new CryptoUtils();
            user.setIdAuction(crypto.generateRandomId());

            FrameClientService.auctionPanel = new ClientAuctionPanel(user, multicast);
            JFrame janela = (JFrame) SwingUtilities.getWindowAncestor(this);
            janela.getContentPane().remove(FrameClientService.TelaID);
            janela.add(FrameClientService.auctionPanel, BorderLayout.CENTER);
            janela.setSize(800, 600);
            janela.pack();
        } catch (Exception ex) {
            Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_entrarBtnMouseClicked

    public String decryptAssim(String encryptedMessage, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));

            return new String(decryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException
                | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException ex) {
            Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    private void cadastrarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cadastrarBtnMouseClicked
        FrameClientService.registerPanel = new ClientRegisterPanel();
        JFrame janela = (JFrame) SwingUtilities.getWindowAncestor(this);
        janela.getContentPane().remove(FrameClientService.TelaID);
        janela.add(FrameClientService.registerPanel, BorderLayout.CENTER);
        janela.pack();
    }//GEN-LAST:event_cadastrarBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cadastrarBtn;
    private javax.swing.JFormattedTextField cpfLabel;
    private javax.swing.JButton entrarBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField jPasswordField1;
    // End of variables declaration//GEN-END:variables
}
