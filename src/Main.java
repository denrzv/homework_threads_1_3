import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        final int ARRAY_SIZE = 10_000_000;

        int[] numbers = getNumbers(ARRAY_SIZE);
        long start = System.currentTimeMillis();
        int sum = getArraySum(numbers);
        printResult("Однопоточный подсчёт", start, sum);


        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        ArraySumTask sumTask = new ArraySumTask(0, ARRAY_SIZE, numbers);
        start = System.currentTimeMillis();
        sum = pool.invoke(sumTask);
        printResult("Многопоточный подсчёт", start, sum);
    }

    private static void printResult(String message, long start, int sum) {
        System.out.printf("%s: %s мсек, результат: %s\n", message, System.currentTimeMillis() - start, sum);
    }

    public static int[] getNumbers(int size) {
        int[] numbers = new int[size];
        numbers = Arrays.stream(numbers)
                .parallel()
                .map(number -> number = new Random().nextInt(size))
                .toArray();
        return numbers;
    }

    public static int getArraySum(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
}