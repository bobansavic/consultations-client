/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsclient.window;

import com.bs.consultationsclient.model.UserDto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ChatFrame extends JFrame implements ActionListener {

    private Container rootPane = getContentPane();
    private JPanel panelChat;
    private JPanel panelSingleMessage;
    private JScrollPane scrollPaneChat;
    private JPanel panelClientMessage;
    private JLabel lblClientMessage;
    private JScrollPane scrollPaneClientMessage;
    private JTextArea taClientMessage;
    private JTextArea taSentMessage;
    private JButton btnSend;
    private JTextArea textHolder; //remove later
    private Dimension textHolderSize; //remove later
    private static int counter = 0;
    private static final int FONT_SIZE = 16;
    private final static Font font = new Font("Tahoma", Font.PLAIN, FONT_SIZE);
    private static final String TEXT_SUBMIT = "text-submit";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm a");

    private InputMap input = taClientMessage.getInputMap();
    private final KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
    private final KeyStroke shiftEnter = KeyStroke.getKeyStroke("shift ENTER");

    private UserDto selectedUser;

private static final String INSERT_BREAK = "insert-break";

    public ChatFrame() {
        initComponents();
    }

    private void initComponents() {

        rootPane.setLayout(new BorderLayout());

        textHolder = new JTextArea(); //remove later
        textHolderSize = new Dimension(300, 25); //remove later

        // Panel for sending client messages
        panelClientMessage = new JPanel();
        panelClientMessage.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        lblClientMessage = new JLabel("Your message:");
        lblClientMessage.setFont(font);

        taClientMessage = new JTextArea();
        taClientMessage.setMaximumSize(new Dimension(250, 2000));
        taClientMessage.setMinimumSize(new Dimension(250, 25));
        taClientMessage.setLineWrap(true);
        taClientMessage.setWrapStyleWord(true);
        taClientMessage.setFont(font);

        scrollPaneClientMessage = new JScrollPane(taClientMessage,
            JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneClientMessage.setMaximumSize(new Dimension(250, 5000));
        scrollPaneClientMessage.setPreferredSize(new Dimension(300, 16));

        btnSend = new JButton("Send");
        btnSend.setFont(font);
        btnSend.addActionListener(this);

        panelClientMessage.add(lblClientMessage);
        panelClientMessage.add(scrollPaneClientMessage);
        panelClientMessage.add(btnSend);

        // Panel for chatting
        rootPane.add(panelClientMessage, BorderLayout.SOUTH);

        panelChat = new JPanel();
        panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
//        panelChat.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        scrollPaneChat = new JScrollPane(panelChat,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        rootPane.add(scrollPaneChat, BorderLayout.CENTER);

        // Document listeners for client message text area
        taClientMessage.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                if (taClientMessage.getPreferredSize().height > 200) {
                    scrollPaneClientMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    return;
                } else {
                    scrollPaneClientMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }
                scrollPaneClientMessage.setPreferredSize(new Dimension(300, taClientMessage.getPreferredSize().height));
                scrollPaneClientMessage.setSize(new Dimension(300, taClientMessage.getPreferredSize().height));
                if (taClientMessage.getPreferredSize().height < (FONT_SIZE + 4) * 2) {
                    panelClientMessage
                        .setPreferredSize(new Dimension(getPreferredSize().width, scrollPaneClientMessage.getSize().height + 30 + (FONT_SIZE + 4)));
                } else {
                    panelClientMessage
                        .setPreferredSize(new Dimension(getPreferredSize().width, scrollPaneClientMessage.getSize().height + 30));
                }
                validate();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                textHolder.setText(taClientMessage.getText());
                textHolderSize = textHolder.getPreferredSize();
                String s = "REMOVE UPDATE!\n- taClientMessage prefSize: W" + taClientMessage.getPreferredSize().width + " H" + taClientMessage
                    .getPreferredSize().height + "\n- textHolderSize: W" + textHolderSize.width + " H" + textHolderSize.height;
                System.out.println(s);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                if (taClientMessage.getPreferredSize().height > 200) {
                    scrollPaneClientMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    return;
                } else {
                    scrollPaneClientMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }
                scrollPaneClientMessage.setPreferredSize(new Dimension(300, taClientMessage.getPreferredSize().height));
                scrollPaneClientMessage.setSize(new Dimension(300, taClientMessage.getPreferredSize().height));
                if (taClientMessage.getPreferredSize().height < (FONT_SIZE + 4) * 2) {
                    panelClientMessage
                        .setPreferredSize(new Dimension(getPreferredSize().width, scrollPaneClientMessage.getSize().height + 30 + (FONT_SIZE + 4)));
                } else {
                    panelClientMessage
                        .setPreferredSize(new Dimension(getPreferredSize().width, scrollPaneClientMessage.getSize().height + 30));
                }
                validate();
            }
        });

        // Input definition for the client message text area
        // (ENTER is send, SHIFT+ENTER is new line)
        input.put(shiftEnter, INSERT_BREAK);  // input.get(enter)) = "insert-break"
        input.put(enter, TEXT_SUBMIT);

        ActionMap actions = taClientMessage.getActionMap();
        actions.put(TEXT_SUBMIT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //                    rabbitMqService.sendMessage(taClientMessage.getText().getBytes());
                panelChat.add(createSentMessagePanel(""));
                taClientMessage.setText("");
                validate();
                scrollPaneChat
                    .getVerticalScrollBar().setValue(scrollPaneChat.getVerticalScrollBar().getMaximum());
            }
        });

//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                    null, "Are you sure you want to close Consultations?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    System.exit(0);
                }
            }
        };
        this.addWindowListener(exitListener);
        pack();
        setLocationRelativeTo(null); // this centers the window

        taClientMessage.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent ce) {
                if (taClientMessage.getPreferredSize().height > 200) {
                    scrollPaneClientMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    return;
                } else {
                    scrollPaneClientMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }
                scrollPaneClientMessage.setPreferredSize(new Dimension(300, taClientMessage.getPreferredSize().height));
                scrollPaneClientMessage.setSize(new Dimension(300, taClientMessage.getPreferredSize().height));
                if (taClientMessage.getPreferredSize().height < (FONT_SIZE + 4) * 2) {
                    panelClientMessage
                        .setPreferredSize(new Dimension(getPreferredSize().width, scrollPaneClientMessage.getSize().height + 30 + (FONT_SIZE + 4)));
                } else {
                    panelClientMessage
                        .setPreferredSize(new Dimension(getPreferredSize().width, scrollPaneClientMessage.getSize().height + 30));
                }
                validate();
                String s = "taClientMessage RESIZED!\n- taClientMessage prefSize: W" + taClientMessage.getPreferredSize().width + " H" + taClientMessage
                    .getPreferredSize().height + "\n- scrollPaneClientMessage size: W" + scrollPaneClientMessage.getSize().width + " H" + scrollPaneClientMessage.getSize().height;
                System.out.println(s);
            }

            @Override
            public void componentMoved(ComponentEvent ce) {
            }

            @Override
            public void componentShown(ComponentEvent ce) {
            }

            @Override
            public void componentHidden(ComponentEvent ce) {
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnSend) {
            if (counter == 0) {
                panelChat.add(createSentMessagePanel("sent message"));
            } else {
                panelChat.add(createRecievedMessagePanel("received message"));
            }
            panelChat.revalidate();
        }
        validate();
        scrollPaneChat.getVerticalScrollBar().setValue(scrollPaneChat.getVerticalScrollBar().getMaximum());
    }

    private JPanel createSentMessagePanel(String text) {
        panelSingleMessage = new SentMessageField();
        panelSingleMessage.setLayout(new BoxLayout(panelSingleMessage, BoxLayout.Y_AXIS));
        panelSingleMessage.setMinimumSize(new Dimension(panelChat.getWidth(), 0));

        JPanel sentMessagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        taSentMessage = new JTextArea();
        taSentMessage.setFont(font);
        taSentMessage.setMaximumSize(new Dimension(500, 5000));
        
        taSentMessage.setBorder(BorderFactory
                .createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE, 2),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//        sentMessageTextArea.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2, true));
        taSentMessage.setText(text);
        sentMessagePanel.add(taSentMessage);

        JLabel timeLabel = new JLabel("8:40 PM");
//        timeLabel.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        labelPanel.add(timeLabel);
        panelSingleMessage.add(sentMessagePanel, BorderLayout.CENTER);
        panelSingleMessage.add(labelPanel, BorderLayout.SOUTH);
        counter++;

        return panelSingleMessage;
    }

    private JPanel createRecievedMessagePanel(String text) {
        panelSingleMessage = new SentMessageField();
        panelSingleMessage.setLayout(new BoxLayout(panelSingleMessage, BoxLayout.Y_AXIS));
        panelSingleMessage.setMinimumSize(new Dimension(panelChat.getWidth(), 0));

        JPanel sentMessagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taSentMessage = new JTextArea();
        taSentMessage.setMaximumSize(new Dimension(500, 5000));
        taSentMessage.setText(text);
        taSentMessage.setFont(font);
        
        taSentMessage.setBorder(BorderFactory
                .createCompoundBorder(BorderFactory.createLineBorder(Color.GREEN, 2),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        sentMessagePanel.add(taSentMessage);

        JLabel timeLabel = new JLabel("8:40 PM");
//        timeLabel.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.add(timeLabel);
        panelSingleMessage.add(sentMessagePanel, BorderLayout.CENTER);
        panelSingleMessage.add(labelPanel, BorderLayout.SOUTH);
        counter--;

        return panelSingleMessage;
    }

    class SentMessageField extends JPanel {

        SentMessageField() {
            super();
        }

        @Override
        public Dimension getMaximumSize() {
            Dimension size = super.getMaximumSize();
            size.height = getPreferredSize().height;
            return size;
        }
    }

    // Used for proper chat message text area positioning and sizing
    // relative to the screen and frame
    @Override
    public Dimension getPreferredSize() {
        // See my exchange with MadProgrammer in the comments for
        // a discussion of whether Toolkit#getScreenSize() is an
        // appropriate way to get the screen dimensions for sizing
        // a window.
//        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        // This is the correct way, as suggested in the documentation
        // for java.awt.GraphicsEnvironment#getMaximumWindowBounds():
        GraphicsConfiguration config = getGraphicsConfiguration();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
        Dimension size = config.getBounds().getSize();
        size.width -= insets.left + insets.right;
        size.height -= insets.top + insets.bottom;
        // Now we have the actual available space of the screen
        // so we can compute a relative size for the JFrame.
        size.width = size.width / 3;
        size.height = size.height * 2 / 3;
        return size;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatFrame().setVisible(true);
            }
        });
    }
}
