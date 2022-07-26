import java.util.concurrent.RecursiveTask;

public class ArraySumTask extends RecursiveTask<Integer> {
    private final int start;
    private final int end;
    private final int[] numbers;

    public ArraySumTask(int start, int end, int[] numbers) {
        this.start = start;
        this.end = end;
        this.numbers = numbers;
    }

    @Override
    protected Integer compute() {
        final int diff = end - start;
        return switch (diff) {
            case 0 -> 0;
            case 1 -> numbers[start];
            case 2 -> numbers[start] + numbers[start + 1];
            default -> forkTasksAndGetResult();
        };
    }

    private int forkTasksAndGetResult() {
        final int middle = (end - start) / 2 + start;

        ArraySumTask task1 = new ArraySumTask(start, middle, numbers);
        ArraySumTask task2 = new ArraySumTask(middle, end, numbers);
        invokeAll(task1, task2);
        return task1.join() + task2.join();
    }
}
