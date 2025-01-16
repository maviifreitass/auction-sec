package com.br.auction.sec.client;

import com.br.auction.sec.service.FrameClientService;

public class ClientRunner {
    public static void main(String[] args) throws InterruptedException, Exception {
        FrameClientService j = new FrameClientService();
        j.setVisible(true);
        j.setLocationRelativeTo(null);
        j.setResizable(false);
    }
}
