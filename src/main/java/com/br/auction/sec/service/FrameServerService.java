
package com.br.auction.sec.service;

import com.br.auction.sec.server.ServerRegisterPanel;
import com.br.auction.sec.server.ServerMainPanel;
import java.awt.BorderLayout;
public class FrameServerService extends javax.swing.JFrame {
   public static ServerMainPanel TelaID;
   public static ServerRegisterPanel configPanel;
    
    public FrameServerService() {
        initComponents();
        TelaID = new ServerMainPanel();
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
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
