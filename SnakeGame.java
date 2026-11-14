import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    int foodX, foodY;
    char direction = 'R';
    boolean running = true;
    Timer timer;
    int box = 25;
    int width = 20;
    int height = 20;
    int snakeX[] = new int[400];
    int snakeY[] = new int[400];
    int length = 3;
    Random random = new Random();

    public SnakeGame() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        snakeX[0] = 5; snakeY[0] = 5;
        snakeX[1] = 4; snakeY[1] = 5;
        snakeX[2] = 3; snakeY[2] = 5;
        spawnFood();
        timer = new Timer(150, this);
        timer.start();
    }

    void spawnFood() {
        foodX = random.nextInt(width);
        foodY = random.nextInt(height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(foodX * box, foodY * box, box, box);
        for (int i = 0; i < length; i++) {
            if (i == 0) g.setColor(Color.GREEN);
            else g.setColor(new Color(0, 180, 0));
            g.fillRect(snakeX[i] * box, snakeY[i] * box, box, box);
        }
        if (!running) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("GAME OVER!", 150, 250);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {
            for (int i = length; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            if (direction == 'R') snakeX[0]++;
            if (direction == 'L') snakeX[0]--;
            if (direction == 'U') snakeY[0]--;
            if (direction == 'D') snakeY[0]++;

            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                length++;
                spawnFood();
            } else {
                // tail cut handled by length
            }

            if (snakeX[0] < 0 || snakeX[0] >= width || snakeY[0] < 0 || snakeY[0] >= height) {
                running = false;
                timer.stop();
            }
            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    running = false;
                    timer.stop();
                }
            }
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_RIGHT && direction != 'L') direction = 'R';
        if (k == KeyEvent.VK_LEFT && direction != 'R') direction = 'L';
        if (k == KeyEvent.VK_UP && direction != 'D') direction = 'U';
        if (k == KeyEvent.VK_DOWN && direction != 'U') direction = 'D';
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame f = new JFrame("Snake Game");
        f.add(new SnakeGame());
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}