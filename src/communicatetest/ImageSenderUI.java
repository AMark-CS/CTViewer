package src.communicatetest;

/**
 * @program: CTViwer
 * @description: send picture to another computer
 * @Author: Mark Zhang
 * @Date: 6/4/2024
 **/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.net.Socket;

public class ImageSenderUI extends JFrame {

    public ImageSenderUI() {
        super("Image Sender");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null);

        JButton sendButton = new JButton("Send Image");
        sendButton.addActionListener(e -> sendImage());

        getContentPane().add(sendButton, BorderLayout.CENTER);
    }

    private void sendImage() {
        // 打开文件选择器让用户选择一张图片
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (Socket socket = new Socket("192.168.43.47", 7777); // 修改为接收端的正确地址和端口
                 OutputStream outputStream = socket.getOutputStream()) {

                // 读取图片并通过Socket发送
                BufferedImage image = ImageIO.read(selectedFile);
                ImageIO.write(image, "JPG", outputStream);
                JOptionPane.showMessageDialog(this, "Image sent successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to send the image: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageSenderUI frame = new ImageSenderUI();
            frame.setVisible(true);
        });
    }
}
