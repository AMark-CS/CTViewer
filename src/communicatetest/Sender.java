package src.communicatetest;

/**
 * @program: CTViwer
 * @description: 像同一个局域网下的电脑发送数据
 * @Author: Mark Zhang
 * @Date: 6/4/2024
 **/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender extends JFrame {
    private JTextField messageField;
    private JButton sendButton;
    private JTextField ipField;
    private Socket socket;
    private PrintWriter out;

    public Sender() {
        super("Message Sender");

        // 设置界面组件
        messageField = new JTextField(20);
        sendButton = new JButton("Send");
        ipField = new JTextField("127.0.0.1", 15); // 默认IP地址为本机

        // 布局
        JPanel panel = new JPanel();
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Receiver IP:"));
        this.add(ipField);
        this.add(messageField);
        this.add(sendButton);

        // 设置窗口基本属性
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        // 添加发送按钮的事件监听器
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });
    }

    private void send() {
        try {
            // 如果socket未连接，则创建新的socket连接
            if (socket == null) {
                String ip = ipField.getText();
                socket = new Socket(ip, 7777); // 连接到接收端的IP和端口
                out = new PrintWriter(socket.getOutputStream(), true);
            }

            // 发送消息并清空文本框
            String message = messageField.getText();
            if (!message.isEmpty()) {
                out.println(message);
                messageField.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error sending the message: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Sender());
    }
}
