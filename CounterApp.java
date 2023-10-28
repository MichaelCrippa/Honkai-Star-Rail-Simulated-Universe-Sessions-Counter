import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class CounterApp {
    private int count = 0;
    private Clip backgroundMusic;

    public CounterApp() {
        JFrame frame = new JFrame("Clearence Counter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 230);
        frame.setLayout(null);

        // Definisci un cursore personalizzato
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = toolkit.getImage("Herta_Sticker_01.png");
        Cursor customCursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "CustomCursor");
        frame.setCursor(customCursor); // Imposta il cursore personalizzato

        ImageIcon backgroundIcon = new ImageIcon("F0kNPDZacAA5uzr.png");
        Image image = backgroundIcon.getImage();
        Image scaledImage = image.getScaledInstance(320, 520, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, 320, 520);
        frame.add(backgroundLabel);

        JButton increaseButton = new JButton("Add");
        JButton resetButton = new JButton("Reset");
        increaseButton.setBounds(10, 10, 100, 30);
        resetButton.setBounds(120, 10, 100, 30);

        increaseButton.setBackground(Color.BLACK);
        resetButton.setBackground(Color.BLACK);

        increaseButton.setForeground(Color.WHITE);
        resetButton.setForeground(Color.WHITE);

        increaseButton.setBorder(new LineBorder(Color.WHITE, 1));
        resetButton.setBorder(new LineBorder(Color.WHITE, 1));

        Font buttonFont = new Font("Times New Roman", Font.BOLD, 16);
        increaseButton.setFont(buttonFont);
        resetButton.setFont(buttonFont);

        // Aggiungi effetto cambio colore al passaggio del mouse
        increaseButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Color myCustomColor = new Color(84, 63, 139);
                increaseButton.setBackground(myCustomColor);
            }

            public void mouseExited(MouseEvent e) {
                increaseButton.setBackground(Color.BLACK);
            }
        });

        resetButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Color myCustomColor = new Color(84, 63, 139);
                resetButton.setBackground(myCustomColor);
            }

            public void mouseExited(MouseEvent e) {
                resetButton.setBackground(Color.BLACK);
            }
        });

        frame.add(increaseButton);
        frame.add(resetButton);

        JLabel label = new JLabel("Clearence: " + count);
        label.setBounds(10, 50, 210, 30);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);

        Font labelFont = new Font("Times New Roman", Font.BOLD, 16);
        label.setFont(labelFont);

        backgroundLabel.add(label);

        increaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (count < 4) {
                    count++;
                    label.setText("Clearence: " + count);
                    playSound("Talent 1.wav");
                } else {
                    playSound("Turn 1.wav");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (count == 0)
                    playSound("Weakness Break.wav");
                else {
                    count = 0;
                    label.setText("Clearence: " + count);
                    playSound("Talent 2.wav");
                }
            }
        });

        frame.setVisible(true);

        // Avvia la canzone di sottofondo
        playBackgroundMusic("Uma Musume.wav");
    }

    public void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playBackgroundMusic(String backgroundMusicFilePath) {
        try {
            File soundFile = new File(backgroundMusicFilePath);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(AudioSystem.getAudioInputStream(soundFile));

            // Imposta il volume della canzone di sottofondo (valori da -80.0 a 6.0206)
            FloatControl gainControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-5.0f); // Esempio di impostazione del volume a -20 decibel

            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CounterApp();
            }
        });
    }
}
