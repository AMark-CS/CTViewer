package src.communicatetest;

/**
 * @program: CTViwer
 * @description: receive picture from another computer
 * @Author: Mark Zhang
 * @Date: 6/4/2024
 **/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ImageReceiverUI extends JFrame {

    private JLabel imageLabel;

    public ImageReceiverUI() {
        super("Image Receiver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        imageLabel = new JLabel();
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        receiveImage();
    }

    private void receiveImage() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(7777)) { // 确保端口号与发送端匹配
                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         InputStream inputStream = clientSocket.getInputStream()) {
                        BufferedImage image = ImageIO.read(inputStream);
                        if (image != null) {
                            ImageIcon imageIcon = new ImageIcon(image);
                            SwingUtilities.invokeLater(() -> imageLabel.setIcon(imageIcon));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageReceiverUI frame = new ImageReceiverUI();
            frame.setVisible(true);
        });
    }
}
