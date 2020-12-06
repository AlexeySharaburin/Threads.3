import java.util.Random;

public class Main {
    static Random random = new Random();
    static int size = 10_000_000;
    static int[] array = new int[size];

    public static void main(String[] args) {

        getArray();
        System.out.printf("Количество элементов в массиве = %d\n", size);

        long timeDirect = calculationsDirect();
        System.out.println("Время выполнения арифметических операций: " + timeDirect + " мс.\n");

        long timeForkJoinPool = calculationsWithForkJoinPool();
        System.out.println("Время выполнения арифметических операций: " + timeForkJoinPool + " мс.\n");

        compareMethods(timeDirect, timeForkJoinPool);
    }

    public static void getArray() {
        for (int i = 0; i < (size - 1); i++) {
            array[i] = random.nextInt(1_000_000_000);
        }
    }

    public static long calculationsDirect() {
        long tStart = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += array[i];
        }
        double average = sum * 1.0 / array.length;
        long tFinish = System.currentTimeMillis();
        System.out.printf("Однопоточное решение:" +
                "\nСумма элементов массива = %d." +
                "\nСреднее арифметическое элементов массива = %.4f\n", sum, average);
        return (tFinish - tStart);
    }

    public static long calculationsWithForkJoinPool() {
        long tStart = System.currentTimeMillis();
        int sum = new ArrayTwoThreads(0, size, array).compute();
        double average = sum * 1.0 / array.length;
        long tFinish = System.currentTimeMillis();
        System.out.printf("ForkJoinPool решение:" +
                "\nСумма элементов массива = %d." +
                "\nСреднее арифметическое элементов массива = %.4f\n", sum, average);
        return (tFinish - tStart);
    }

    public static void compareMethods(long timeDirect, long timeForkJoinPool) {
        long diff = timeDirect - timeForkJoinPool;
        if (diff < 0) {
            System.out.printf("Метод с ForkJoinPool работает медленнее на %d мс.\n", Math.abs(diff));
        } else if (diff > 0) {
            System.out.printf("Метод с ForkJoinPool работает быстрее на %d мс.\n", Math.abs(diff));
        } else {
            System.out.println("Методы работают с одинаковой скоростью");
        }
    }
}
