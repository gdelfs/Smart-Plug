/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart_plug_server;
/**
 *
 * @author Delfino, Voltan, Abreu
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Base64;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;


public class ServerBoard extends JFrame{
    private JTextArea messagesArea;
    private JButton sendButton;
    private JTextField message;
    private JButton startServer;
    private TCPServer mServer;

    public ServerBoard() {

        super("Smart Plug - Server de Teste");

        JPanel panelFields = new JPanel();
        panelFields.setLayout(new BoxLayout(panelFields,BoxLayout.X_AXIS));

        JPanel panelFields2 = new JPanel();
        panelFields2.setLayout(new BoxLayout(panelFields2,BoxLayout.X_AXIS));

        //Tela das mensagens
        messagesArea = new JTextArea();
        messagesArea.setColumns(25);
        messagesArea.setRows(35);
        messagesArea.setEditable(false);

        startServer = new JButton("Iniciar serviço");
        messagesArea.append(" PFC 2018 - Delfino, Voltan, Abreu\n ");
        //messagesArea.append("Server: aguardando envio das imagens...\n ");
        startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Após iniciar, desabilita o botão (serviço terá sido iniciado)
                startServer.setEnabled(false);

                //cria o objeto OnMessageReceived (ligado ao construtor do TCPServer)
                mServer = new TCPServer(new TCPServer.OnMessageReceived() {
                    @Override
                    public void messageReceived(String message) {
                        messagesArea.append("\n "+ message);
                    }
                });
                mServer.start();
            }
        });

        //Campo de digitação
        /*
        message = new JTextField();
        message.setSize(200, 20);*/

        //adicionando os campos e botões
        panelFields.add(messagesArea);
        panelFields.add(startServer);

        /*panelFields2.add(message);
        panelFields2.add(sendButton);*/

        getContentPane().add(panelFields);
        getContentPane().add(panelFields2);

        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        setSize(300, 170);
        setVisible(true);
    }
}
