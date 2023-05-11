package lr13;

// Напишите программу, которая создает 10 потоков и каждый поток выводит на экран свой номер.

import java.time.LocalTime;

public class Example4 {
    public static void main(String[] args) {
        int n = 10;
        Thread[] th = new Thread[n];

        for(int i=0; i<n; i++){
            th[i] = new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + ": " + LocalTime.now());
            });
        }

        for(int i=0; i<n; i++){
            th[i].setName("Thread #" + (i+1));
            th[i].start();
        }
    }
}
