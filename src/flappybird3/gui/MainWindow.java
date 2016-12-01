package flappybird3.gui;

import Service.CsvGameBoardLoader;
import Service.GameBoardLoader;
import flappybird3.game.GameBoard;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MainWindow extends JFrame {

    BoardPanel pnl = new BoardPanel();
    GameBoard gameBoard;
    long x = 0;

    class BoardPanel extends JPanel {

        public void paint(Graphics g) {
            super.paint(g); //vykresli prazdny panel
            gameBoard.draw(g);  //vykresli hraci plochu 
        }
    }

    public MainWindow() {
        try (InputStream is = new FileInputStream("level.csv")) {
            GameBoardLoader loader = new CsvGameBoardLoader(is);
            gameBoard = loader.loadLevel();

        } catch (FileNotFoundException el) {
            el.printStackTrace();
        } catch (IOException el) {
            el.printStackTrace();
        }

        //gameBoard = new GameBoard();
        add(pnl, BorderLayout.CENTER);
        pnl.setPreferredSize(new Dimension(200, gameBoard.getWidthPix()));
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                gameBoard.kickTheBird();

                //System.out.println("lol");
            }
        });

        Timer t = new Timer(20, e -> {
            gameBoard.tick(x++);
            pnl.repaint();
        });
        t.start();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SwingUtilities.invokeLater(() -> {
            MainWindow w = new MainWindow();
            w.setVisible(true);
        });

    }

}
