package com.br.auction.sec.server;

import com.br.auction.sec.service.FrameServerService;

public class ServerRunner {
    public static void main(String[] args) throws InterruptedException, Exception {
        FrameServerService j = new FrameServerService();
        j.setVisible(true);
        j.setLocationRelativeTo(null);
        j.setResizable(false);
    }
}
