
package com.br.auction.sec.service;

import com.br.auction.sec.client.ClientAuctionPanel;
import com.br.auction.sec.client.ClientMainPanel;
import com.br.auction.sec.client.ClientRegisterPanel;
import java.awt.BorderLayout;
public class FrameClientService extends javax.swing.JFrame {
    public static ClientMainPanel TelaID;
    public static ClientAuctionPanel auctionPanel;
    public static ClientRegisterPanel registerPanel;
    
    public FrameClientService() {
        initComponents();
        TelaID = new ClientMainPanel();
        this.setLayout(new BorderLayout());
        this.add(TelaID, BorderLayout.CENTER);
        this.pack();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 608, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
