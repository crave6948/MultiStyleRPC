package multistylerpc.tray.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import multistylerpc.discord.value.Value;
import multistylerpc.discord.value.values.FloatValue;
import multistylerpc.discord.value.values.IntegerValue;
import multistylerpc.discord.value.values.LongValue;
import multistylerpc.discord.value.values.TextValue;

public class ValueChangeGui extends JFrame{
    private void setValue(Value<?> value, String text) {
        if (text.isEmpty()) return;
        if (value instanceof TextValue) {
            TextValue textValue = (TextValue) value;
            textValue.set(text);
            System.out.println(String.format("%s value has been setted to -> || %s", textValue.name, textValue.value));
        }else if (value instanceof IntegerValue) {
            IntegerValue integerValue = (IntegerValue) value;
            try {
                integerValue.set(Integer.parseInt(text));
                System.out.println(String.format("%s value has been setted to -> || %s", integerValue.name, integerValue.value));
            }catch (NumberFormatException e) {
                return;
            }
        }else if (value instanceof LongValue) {
            LongValue longValue = (LongValue) value;
            try {
                longValue.set(Long.parseLong(text));
                System.out.println(String.format("%s value has been setted to -> || %s", longValue.name, longValue.value));
            }catch (NumberFormatException e) {
                return;
            }
        }else if (value instanceof FloatValue) {
            FloatValue floatValue = (FloatValue) value;
            try {
                floatValue.set(Float.parseFloat(text));
                System.out.println(String.format("%s value has been setted to -> || %s", floatValue.name, floatValue.value));
            }catch (NumberFormatException e) {
                return;
            }
        }
    }
    private char[] numbercharacters = "1234567890".toCharArray();
    private char[] specialcharacters = ".,#@".toCharArray();
    private boolean isContains(char character,boolean number, boolean special, char[] extras) {
        if (number) {
            for (char c : numbercharacters) if (character == c) return true;
        }
        if (special) {
            for (char c : specialcharacters) if (character == c) return true;
        }
        if (extras != null) {
            for (char c : extras) if (character == c) return true;
        }
        return false;
    }
    private boolean onKeyTyped(KeyEvent e, Value<?> value,String text) {
        char typechar = e.getKeyChar();
        if (value instanceof IntegerValue || value instanceof LongValue) {
            boolean isAllowed = isContains(typechar, true, false, null);
            return !isAllowed;
        }
        if (value instanceof FloatValue) {
            if (text.indexOf(".") != -1 && typechar == '.') return true;
            boolean isAllowed = isContains(typechar, true, false, ".".toCharArray());
            return !isAllowed;
        }
        return false;
    }
    public void intialize(Value<?> value) {
        JTextField textField = new JTextField(value.get().toString());
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (onKeyTyped(e,value,textField.getText())) e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                    setValue(value, textField.getText());
                }
                if (keyCode ==KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER) dispose();
            }
        });
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 179, 198, 255));
        mainPanel.add(textField,BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
        setTitle("Change value of " + value.name);
        setSize(200, 70);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - getHeight()) / 2);
        setLocation(centerX, centerY);
        setMinimumSize(new Dimension(100,70));
        setMaximumSize(new Dimension(100,70));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        new Thread() {
            @Override
            public void run() {
                try {
                    boolean haveFocused = false;
                    while (true) {
                        if (haveFocused && !isFocused()) {
                            dispose();
                            break;
                        }
                        haveFocused = isFocused();
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
