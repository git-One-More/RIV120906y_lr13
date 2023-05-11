package lr13;

// Напишите функцию, которая находит максимальный элемент в массиве целых чисел при помощи многопоточности.
// Количество потоков должно быть равно количеству ядер процессора.

import java.util.Arrays;
import java.util.Random;

public class Example5 {
    private static final Object lock = new Object();
    private static Random random = new Random();
    private static int size = random.nextInt(1000, 10000);
    private static int[] arr = new int[size];
    private static int pointer = 0;
    private static int max = Integer.MIN_VALUE;
    private static final int cores = Runtime.getRuntime().availableProcessors();


    public static void main(String[] args) {

        Thread[] th = new Thread[cores];

        for(int i=0;i<size;i++){
            arr[i] = random.nextInt(-1000, 1000);
        }

        System.out.println("Массив arr: ");
        System.out.println(Arrays.toString(arr));

        for(int i=0; i<cores; i++){
            int c = i;
            th[i] = new Thread(() -> {
                synchronized (lock) {
                    while (pointer<size) {
                        System.out.println(pointer + ") " + Thread.currentThread().getName() + " (" + c + ") : " + arr[pointer]);
                        if (arr[pointer] > max) max = arr[pointer];
                        pointer++;
                        try {
                            lock.wait(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        lock.notifyAll();
                    }
                }
            });
        }

        for(int j=0; j<cores; j++) {
            th[j].setName("Thread #" + (j + 1));
            th[j].start();
        }

        for(int j=0; j<cores; j++) {
            try{
                th[j].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("MAX = " + max);
    }
}
