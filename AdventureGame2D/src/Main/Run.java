package Main;
/**
 * Второй способ игрового цикла (через sleep)
 */
// public class Run {
//     @Override
//     public void run() {

//         double drawInterval = 1000000000 / fPS; // 1 секунда в наносекундах / FPS = 0,01666666.. секунды
//         double nextDrawTime = System.nanoTime() + drawInterval;

//         while (gameThread != null) {

//             // 1 UPDATE
//             update();
//             // 2 DRAW
//             repaint(); // так вызываем метод paintComponent (не знаю почему)

//             try {
//                 double remainingTime = nextDrawTime - System.nanoTime();
//                 remainingTime /= 1000000; // переводим в миллисекунды, так как метод .sleep работает с мс.

//                 if (remainingTime < 0) { // на всякий случай :)
//                     remainingTime = 0;
//                 }

//                 Thread.sleep((long) remainingTime);
//                 nextDrawTime += drawInterval;

//             } catch (InterruptedException e) {
//                 // TODO Auto-generated catch block
//                 e.printStackTrace();
//             }
//         }
//     }
// }
