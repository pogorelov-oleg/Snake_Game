import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    Random random = new Random();
    private final int SIZE = 960;
    private final int DOT_SIZE = 48; // размер ячейки (в пикселях )
    private final int ALL_DOTS = 1200; // всего всего ячеек (DOT_SIZE*20)
    private Image dot; // поле под игровую ячейку (import java.awt.*;)
    private Image apple; // поле под яблоко (import java.awt.*;)
    private int appleX; // x позиция яблока
    private int appleY; // y позиция яблока
    private int[] x = new int[ALL_DOTS]; // массив x-ов (все ячейки по оси x)
    private int[] y = new int[ALL_DOTS]; // массив y-ов (все ячейки по оси y)
    private int dots; // размер змейки
    private Timer timer; // таймер (import javax.swing.*;)
    private boolean left = false; // текущее направление движения змейки
    private boolean right = true; // текущее направление движения змейки
    private boolean up = false; // текущее направление движения змейки
    private boolean down = false; // текущее направление движения змейки
    private boolean inGame = true; // мы в игре

    public GameField() { // конструктор игрового поля
        setBackground(Color.lightGray); // цвет игрового поля
        loadImages(); // метод загрузки картинок
        initGame(); // начало игры
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("java.png");
        ImageIcon iid = new ImageIcon("canek.png");
        apple = iia.getImage();

        dot = iid.getImage();
    }

    public void initGame() {
        dots = 3; // инициализирую начальное колличество точек
        for (int i = 0; i < dots; i++) { // задаю начальные значения для X и Y позиций
            x[i] = 144 - i * DOT_SIZE; // отступ по 3 ячейки слева. 144 == DOT_SIZE * 3 (так как начальная длина
                                       // змейки 3 ячейки, на каждой итерации добавляю их справа налево)
            y[i] = 144; // отступ по 3 ячейки сверху. 144 == DOT_SIZE * 3
        }
        timer = new Timer(250, this); // создаю таймер (250 - это частота работы, this - говорит что именно этот
                                      // таймер будет отвечать за обработку каждые 250ms)
        timer.start(); // запускаю таймер
        createApple(); // создание яблока
    }

    private void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            timer.stop();
            setBackground(Color.black);
            String str = "try не работает";
            Sounds.playSound("cho_na_etot_raz.wav");
            Font f = new Font("arial", Font.BOLD, 48);
            g.setColor(Color.WHITE);
            g.setFont(f);
            g.drawString(str, 300, SIZE / 2);

        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            int change = random.nextInt(4);
            switch (change) {
                case (0):
                    Sounds.playSound("app_java.wav");
                    break;
                case (1):
                    Sounds.playSound("build_tools.wav");
                    break;
                case (2):
                    Sounds.playSound("create_java.wav");
                    break;
                case (3):
                    Sounds.playSound("yeahoo.wav");
                    break;
            }
            dots++;
            createApple();
        }
    }

    private void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }

            if (x[0] > SIZE) {
                inGame = false;
            }
            if (x[0] < 0) {
                inGame = false;
            }
            if (y[0] > SIZE) {
                inGame = false;
            }
            if (y[0] < 0) {
                inGame = false;
            }

        }
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple(); // проверка в игре ли я
            checkCollisions(); // проверка не встретил ли я сам себя
            move(); // двигаем змейку
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }
        }
    }
}
