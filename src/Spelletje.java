/* made on 13/03/2019 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Spelletje extends Applet {

    private TextField tekstvak;
    private Button button, buttonReset;
    private Image[] afbeeldingen = new Image[2];
    private int xPositie, yPositie, plaatjes, deComputer;
    private int[] winGetal = {21, 17, 13, 9, 5, 1};
    private int teller, afbeelding = 1;
    private boolean gameResult, waarschuwing;
    private String result;
    private String[] buttonName = {"START", "RESET"};


    public void init() {
        setSize(900, 700);
        URL resources;
        tekstvak = new TextField(15); //TEXTVAK
        add(tekstvak);

        TekstvakListener kl = new TekstvakListener();
        tekstvak.addActionListener(kl);

        button = new Button(buttonName[0]); //KNOP
        add(button);
        button.addActionListener(kl);

        buttonReset = new Button(buttonName[1]); //KNOP
        add(buttonReset);
        buttonReset.addActionListener(new ButtonListener());

        resources = Spelletje.class.getResource("/Resources/"); //AFBEELDINGEN
        afbeeldingen[0] = getImage(resources, "sharingan.png");
        afbeeldingen[1] = getImage(resources, "tenseigan.png");

        afbeelding = 0;
        xPositie = 0;
        yPositie = 0;
        plaatjes = 23;
        waarschuwing = false;
    }


    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            teller = 0;
            plaatjes = 23;
            deComputer = 0;
            gameResult = false;
            tekstvak.setEnabled(true);
            button.setEnabled(true);
            waarschuwing = false;
            afbeelding = 0;
            repaint();
        }
    }

    public void paint(Graphics g) {
        int x = 30;
        if (waarschuwing) {
            g.setColor(Color.red);
            g.drawString("Invoer is ongeldig!", x, 24);
        } else {
            if (gameResult) {
                g.drawString("Je hebt " + result, x, 24);
            } else {
                g.drawString("Hoeveel plaatjes neem je (één, twee of drie)?", x, 24);
                if (deComputer == 0) {
                    g.drawString("Aantal plaatjes: " + plaatjes, x, 48);
                } else {
                    g.drawString("De computer heeft " + deComputer + " plaatjes weggehaald. " + "Er zijn nog " + plaatjes + " plaatjes over, het is nu jouw beurt!", x, 48);
                }
            }
        }


        tekstvak.setLocation(310, 9);
        button.setLocation(450, 8);
        buttonReset.setLocation(510, 8);
        xPositie = 20;
        yPositie = 75;
        for (int i = 0; i < plaatjes; i++) {
            if (i == 5 || i == 10 || i == 15 || i == 20) {
                xPositie = 20;
                yPositie += 125;
            }
            g.drawImage(afbeeldingen[afbeelding], xPositie, yPositie, 100, 100, this);
            xPositie += 125;
        }
    }

    class TekstvakListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int invoer;
            try {
                invoer = Integer.parseInt(tekstvak.getText().trim());
//                System.out.println( "" + invoer);
            } catch (NumberFormatException junk) {
//                System.out.println("NumberFormatException: " + junk.getMessage());
                return;
            }

            if (invoer < 1 || invoer > 3) {
                waarschuwing = true;

            } else {
                waarschuwing = false;
                plaatjes -= invoer;

                if (plaatjes < winGetal[teller]) {
                    teller++;
                }
                int temporary = plaatjes - winGetal[teller];

                int randomGetal;
                if (temporary == 0 || temporary > 3) {
                    randomGetal = (int) (Math.random() * 3 + 1);
                    plaatjes -= randomGetal;
                    deComputer = randomGetal;
                } else {
                    plaatjes -= temporary;
                    deComputer = temporary;
                    afbeelding = 1;
                }
                teller++;
            }
            if (plaatjes == 1) {
                gameResult = true;
                result = "verloren. =(";
                tekstvak.setEnabled(false);
                button.setEnabled(false);
                tekstvak.setText("");
            }
            if (plaatjes <= 0) {
                gameResult = true;
                result = "gewonnen! :D";
                tekstvak.setEnabled(false);
                button.setEnabled(false);
                tekstvak.setText("");
            }
            repaint();
        }
    }
}