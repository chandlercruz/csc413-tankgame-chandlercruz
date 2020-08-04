package Spectankular.menus;

import Spectankular.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HelpMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton back;
    private Launcher lf;

    public HelpMenuPanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("UserInterface/help.png"));
        } catch (IOException e) {
            System.out.println("Error can't read menuBackground");
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        back = new JButton("Back");
        back.setFont(new Font("Courier New", Font.BOLD, 24));
        back.setBounds(150, 450, 150, 50);
        back.addActionListener((actionEvent -> {
            this.lf.setFrame("start");
        }));

        this.add(back);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}
