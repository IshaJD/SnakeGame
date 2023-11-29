import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class snake extends JFrame {
    JFrame frame;
    JLabel label;
    JPanel panel;

    snake() {
        super("SNAKE GAME ");

        add(new logic());
        setVisible(true);

        setLocationRelativeTo(null);
        setSize(300, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        snake s = new snake();

    }

}

class logic extends JPanel implements ActionListener {
    private int dot_size = 10;
    int size = 900;
    int x[] = new int[size];
    int y[] = new int[size];
    private int dots;
    Image dot, head, apple;
    private int apple_x;
    private int apple_y;
    private final int random_pos = 20;
    private Timer timer;
    private boolean upDirection = false, downdirection = false, leftDirection = false, rightdirection = true;
    private boolean ingame = true;

    logic() {
        addKeyListener(new TAdapter());

        setBackground(Color.black);
        setFocusable(true);
        loadImages();
        init();
    }

    public void loadImages() {
        // imageicons
        ImageIcon dotIcon = new ImageIcon("dot.png");
        dot = dotIcon.getImage();
        ImageIcon headIcon = new ImageIcon("head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("apple.png");
        apple = appleIcon.getImage();

    }

    public void init() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 50 - (i * dot_size);
            y[i] = 50;
        }
        locateApple();
        // move
        timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple() {
        int xpos = (int) (Math.random() * random_pos);
        apple_x = xpos * dot_size;
        xpos = (int) (Math.random() * random_pos);
        apple_y = xpos * dot_size;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (ingame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0)
                    g.drawImage(head, x[i], y[i], this);
                else
                    g.drawImage(dot, x[i], y[i], this);
            }
            Toolkit.getDefaultToolkit().sync();
        }
        // game over
        else {
            gameOver(g);

        }
    }

    public void gameOver(Graphics g) {
        JOptionPane.showMessageDialog(null, null, "GAME OVER !", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            // adjust dots acc to head
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        // left dir
        if (leftDirection) {
            x[0] = x[0] - dot_size;
        }
        if (upDirection) {
            y[0] = y[0] - dot_size;
        }
        if (rightdirection) {
            x[0] = x[0] + dot_size;
        }
        if (downdirection) {
            y[0] = y[0] + dot_size;
        }
        // adjust head

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ingame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();

    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            // if snake collide with itself
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                ingame = false;
            }
            // or with the boundary
            // right
            if (y[0] >= 300)
                ingame = false;
            // left
            if (x[0] < 0)
                ingame = false;
            // top
            if (x[0] >= 300)
                ingame = false;
            if (y[0] < 0)
                ingame = false;
            if (!ingame) {
                timer.stop();
            }
        }
    }

    public void checkApple() {
        if (x[0] == apple_x && y[0] == apple_y) {
            // it is a hit
            dots++;
            locateApple();
        }
    }

    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && (!rightdirection)) {
                upDirection = false;
                downdirection = false;
                leftDirection = true;

            }
            if (key == KeyEvent.VK_UP && (!downdirection)) {
                upDirection = true;
                rightdirection = false;
                leftDirection = false;

            }
            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
                upDirection = false;
                downdirection = false;

                rightdirection = true;

            }
            if (key == KeyEvent.VK_DOWN && (!upDirection)) {
                rightdirection = false;
                downdirection = true;
                leftDirection = false;

            }
        }
    }
}