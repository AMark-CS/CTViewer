package src.communicatetest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Communicator extends JFrame {
    private JButton openButton;
    private JTextArea textArea;
    private ServerSocket serverSocket;

    public Communicator() {
        // 设置UI组件
        openButton = new JButton("Open Port");
        textArea = new JTextArea(20, 30);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        // 布局
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(openButton);
        this.add(panel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        // 设置窗口基本属性
        this.setTitle("Simple Communicator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        // 添加按钮事件监听
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPort();
            }
        });
    }

    private void openPort() {
        try {
            // 在7777端口创建ServerSocket
            serverSocket = new ServerSocket(7777);
            textArea.append("Port 7777 opened.\n");
            acceptConnections();
        } catch (Exception e) {
            textArea.append("Could not open port: " + e.getMessage() + "\n");
        }
    }

    private void acceptConnections() {
        new Thread(() -> {
            try {
                // 接受连接
                Socket clientSocket = serverSocket.accept();
                textArea.append("Client connected.\n");

                // 创建BufferedReader来读取客户端发送的信息
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String receivedMessage;
                while ((receivedMessage = input.readLine()) != null) {
                    textArea.append("Received: " + receivedMessage + "\n");
                }
            } catch (Exception e) {
                textArea.append("Error: " + e.getMessage() + "\n");
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Communicator());
    }
}