package com.meetkiki.io.nio;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NioClient extends Thread {

    private Socket socket;

    private PrintWriter out;

    private InputStream input;

    public NioClient(int port) throws Exception {
        socket = new Socket();
        socket.connect(new InetSocketAddress(port));
        out = new PrintWriter(socket.getOutputStream());
        input = socket.getInputStream();
    }
 
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
                System.out.println("-------run---------");
                out.print("---send to nio server--");
                out.flush();
                int size = input.available();
                if (size > 0) {
                    byte[] bytes = new byte[size];
                    input.read(bytes);
                    System.out.println("reive = " + new String(bytes));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            NioClient client = new NioClient(9999);
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}