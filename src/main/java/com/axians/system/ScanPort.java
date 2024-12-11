package com.axians.system;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ScanPort {

    public ScanPort() {

    }

    /***
     *
     * @param ip
     * @param startPort
     * @param endPort
     * @return List
     */
    public  List<String[]> scanPorts(String ip, int startPort, int endPort) {
        //Concurrently add to linked Queue
        ConcurrentLinkedQueue openPorts = new ConcurrentLinkedQueue<>();
        // 50 Threads will open
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        AtomicInteger port = new AtomicInteger(startPort);
        while (port.get() <= endPort) {
            final int currentPort = port.getAndIncrement();
            executorService.submit(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, currentPort), 200);
                    socket.close();
                    openPorts.add(new String[]{String.valueOf(currentPort), "OPEN"});

                } catch (IOException e) {
                   // openPorts.add(new String[]{String.valueOf(currentPort), "CLOSED"});
                }

            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List openPortList = new ArrayList();
        // System.out.println("openPortsQueue: " + openPorts.size());
        while (!openPorts.isEmpty()) {
            openPortList.add(openPorts.poll());
        }
        return openPortList;
    }
}
