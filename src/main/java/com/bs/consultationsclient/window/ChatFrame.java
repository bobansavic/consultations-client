/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bs.consultationsclient.window;

import com.bs.consultationsclient.service.RabbitMqService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
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
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class ChatFrame extends JFrame implements ActionListener {
    
//    @Autowired
//    private RabbitMqService rabbitMqService;

    private JPanel panel;
    private JPanel singleMessagePanel;
    private JScrollPane scroll;
    private JPanel typingPanel;
    private JLabel typeMessageLabel;
    private JTextArea textArea;
    private JTextArea sentMessageTextArea;
    private JButton btnSend;
    private JTextArea textHolder;
    private Dimension textHolderSize;
    private static int counter = 0;
    private static final int FONT_SIZE = 16;
    private static Font font;
    private static final String TEXT_SUBMIT = "text-submit";
private static final String INSERT_BREAK = "insert-break";

    public ChatFrame() {
        font = new Font("Tahoma", Font.PLAIN, FONT_SIZE);
        getContentPane().setLayout(new BorderLayout());
        textHolder = new JTextArea();
        textHolderSize = new Dimension(300, 25);

        typingPanel = new JPanel();
        typingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        typeMessageLabel = new JLabel("Your message:");
        typeMessageLabel.setFont(font);
        typingPanel.add(typeMessageLabel);

        textArea = new JTextArea();
        textArea.setMaximumSize(new Dimension(250, 2000));
        textArea.setMinimumSize(new Dimension(250, 25));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(font);
        JScrollPane scrollPane = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(new Dimension(250, 5000));
        scrollPane.setPreferredSize(new Dimension(300, 16));
        typingPanel.add(scrollPane);

        btnSend = new JButton("Send");
        btnSend.setFont(font);
        btnSend.addActionListener(this);
        typingPanel.add(btnSend);
        getContentPane().add(typingPanel, BorderLayout.SOUTH);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        scroll = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        getContentPane().add(scroll, BorderLayout.CENTER);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                if (textArea.getPreferredSize().height > 200) {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    return;
                } else {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }
                scrollPane.setPreferredSize(new Dimension(300, textArea.getPreferredSize().height));
                scrollPane.setSize(new Dimension(300, textArea.getPreferredSize().height));
                if (textArea.getPreferredSize().height < (FONT_SIZE + 4) * 2) {
                    typingPanel.setPreferredSize(new Dimension(getPreferredSize().width, scrollPane.getSize().height + 30 + (FONT_SIZE + 4)));
                } else {
                    typingPanel.setPreferredSize(new Dimension(getPreferredSize().width, scrollPane.getSize().height + 30));
                }
                validate();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                textHolder.setText(textArea.getText());
                textHolderSize = textHolder.getPreferredSize();
                String s = "REMOVE UPDATE!\n- textArea prefSize: W" + textArea.getPreferredSize().width + " H" + textArea.getPreferredSize().height + "\n- textHolderSize: W" + textHolderSize.width + " H" + textHolderSize.height;
                System.out.println(s);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                if (textArea.getPreferredSize().height > 200) {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    return;
                } else {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }
                scrollPane.setPreferredSize(new Dimension(300, textArea.getPreferredSize().height));
                scrollPane.setSize(new Dimension(300, textArea.getPreferredSize().height));
                if (textArea.getPreferredSize().height < (FONT_SIZE + 4) * 2) {
                    typingPanel.setPreferredSize(new Dimension(getPreferredSize().width, scrollPane.getSize().height + 30 + (FONT_SIZE + 4)));
                } else {
                    typingPanel.setPreferredSize(new Dimension(getPreferredSize().width, scrollPane.getSize().height + 30));
                }
                validate();
            }
        });
        
        InputMap input = textArea.getInputMap();
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        KeyStroke shiftEnter = KeyStroke.getKeyStroke("shift ENTER");
        input.put(shiftEnter, INSERT_BREAK);  // input.get(enter)) = "insert-break"
        input.put(enter, TEXT_SUBMIT);

        ActionMap actions = textArea.getActionMap();
        actions.put(TEXT_SUBMIT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //                    rabbitMqService.sendMessage(textArea.getText().getBytes());
                panel.add(createSentMessagePanel());
                textArea.setText("");
                validate();
                scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // this centers the window

        textArea.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent ce) {
                if (textArea.getPreferredSize().height > 200) {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    return;
                } else {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }
                scrollPane.setPreferredSize(new Dimension(300, textArea.getPreferredSize().height));
                scrollPane.setSize(new Dimension(300, textArea.getPreferredSize().height));
                if (textArea.getPreferredSize().height < (FONT_SIZE + 4) * 2) {
                    typingPanel.setPreferredSize(new Dimension(getPreferredSize().width, scrollPane.getSize().height + 30 + (FONT_SIZE + 4)));
                } else {
                    typingPanel.setPreferredSize(new Dimension(getPreferredSize().width, scrollPane.getSize().height + 30));
                }
                validate();
                String s = "textArea RESIZED!\n- textArea prefSize: W" + textArea.getPreferredSize().width + " H" + textArea.getPreferredSize().height + "\n- scrollPane size: W" + scrollPane.getSize().width + " H" + scrollPane.getSize().height;
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
                panel.add(createSentMessagePanel());
            } else {
                panel.add(createRecievedMessagePanel());
            }
            panel.revalidate();
        }
        validate();
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }

    private JPanel createSentMessagePanel() {
        singleMessagePanel = new SentMessageField();
//        singleMessagePanel.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
        singleMessagePanel.setLayout(new BoxLayout(singleMessagePanel, BoxLayout.Y_AXIS));
        singleMessagePanel.setMinimumSize(new Dimension(panel.getWidth(), 0));

        JPanel sentMessagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sentMessageTextArea = new JTextArea();
        sentMessageTextArea.setFont(font);
        sentMessageTextArea.setMaximumSize(new Dimension(500, 5000));
        
        sentMessageTextArea.setBorder(BorderFactory
                .createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE, 2),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
//        sentMessageTextArea.setBorder(BorderFactory.createLineBorder(Color.blue, 2, true));
        sentMessageTextArea.setText(textArea.getText());
        sentMessagePanel.add(sentMessageTextArea);

        JLabel timeLabel = new JLabel("8:40 PM");
//        timeLabel.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        labelPanel.add(timeLabel);
        singleMessagePanel.add(sentMessagePanel, BorderLayout.CENTER);
        singleMessagePanel.add(labelPanel, BorderLayout.SOUTH);
        counter++;

        return singleMessagePanel;
    }

    private JPanel createRecievedMessagePanel() {
        singleMessagePanel = new SentMessageField();
        singleMessagePanel.setLayout(new BoxLayout(singleMessagePanel, BoxLayout.Y_AXIS));
        singleMessagePanel.setMinimumSize(new Dimension(panel.getWidth(), 0));

        JPanel sentMessagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sentMessageTextArea = new JTextArea();
        sentMessageTextArea.setMaximumSize(new Dimension(500, 5000));
        sentMessageTextArea.setText("testAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\nAAAAAAAAAAAAAAAAAA\nAAAAAAA");
        sentMessageTextArea.setFont(font);
        
        sentMessageTextArea.setBorder(BorderFactory
                .createCompoundBorder(BorderFactory.createLineBorder(Color.GREEN, 2),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        
        sentMessagePanel.add(sentMessageTextArea);

        JLabel timeLabel = new JLabel("8:40 PM");
//        timeLabel.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.add(timeLabel);
        singleMessagePanel.add(sentMessagePanel, BorderLayout.CENTER);
        singleMessagePanel.add(labelPanel, BorderLayout.SOUTH);
        counter--;

        return singleMessagePanel;
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

    class BoxyTextField extends JTextField {

        BoxyTextField(int width) {
            super(width);
        }

        @Override
        public Dimension getMaximumSize() {
            Dimension size = super.getMaximumSize();
            size.height = getPreferredSize().height;
            return size;
        }
    }

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
