import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainWindow extends JFrame{
    public MainWindow(){
        setTitle("Змейка");                                   //имя окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //закрытие окна 
        setSize(975, 975 + 25);                               //размер окна (+25 по оси y для панели сверху)
        setLocation(450, 20);                                   // положение окна
        add(new GameField());                                       // добавляю класс GameField на основное окно
        setVisible(true);                                         // сделать окно видимым
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}