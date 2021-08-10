package com.meetkiki.io.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    // 选择器
    private Selector selector;

    private ByteBuffer byteBuffer = ByteBuffer.allocate(2014);

    //nio服务端
    public NioServer(int port) throws Exception {
        selector = Selector.open(); // 打开一个选择器 选择器能够监听是否有感兴趣的事情发生
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false); // 非阻塞
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 注册感兴趣的事件
        System.out.println("--server --- start----");
    }

    // 启动nio服务的监听处理
    public void start() throws Exception {
        while (true) { //
            int selects = selector.select(); // 没有敢兴趣的事件 阻塞 --》有那么selects > 0
            if (selects <= 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys(); // 获取已经选择器集合
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();
                handle(selectionKey); //处理 ，路由操作
                it.remove();
            }
        }
    }

    private void handle(SelectionKey selectionKey) throws Exception {
        if (selectionKey.isAcceptable()) { // 已经准备了Accept
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept(); // accept这个socket
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ); // 注册read感兴趣事件
            System.out.println("---- register read-----");
        } else if (selectionKey.isReadable()) {
            System.out.println("----- is read----");
            readBuffer(selectionKey);
        } else if (selectionKey.isWritable()) {
            System.out.println("------ is write-----");
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            byteBuffer.clear();
            byteBuffer.put("hello ljq--".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            System.out.println("--write to client --");
        }
    }

    private void readBuffer(SelectionKey selectionKey) throws Exception {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        int size = socketChannel.read(byteBuffer);
        if (size > 0) {
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_WRITE);
            byteBuffer.clear();
        } else {
            socketChannel.close();
        }
    }

    public static void main(String[] args) {
        try {
            NioServer nioServer = new NioServer(9999);
            nioServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}