import java.util.concurrent.RecursiveTask;

public class ArrayTwoThreads extends RecursiveTask<Integer> {

    private int start;
    private int end;
    private int[] array;

    public ArrayTwoThreads(int start, int end, int[] array) {

        this.start = start;
        this.end = end;
        this.array  = array;
    }

    @Override
    protected Integer compute() {
        final int diff = end - start;
        switch (diff) {
            case 0: return 0;
            case 1: return array[start];
            case 2: return array[start] + array[start+1];
            default: return forkTasks();
        }
    }

    protected int forkTasks() {
        final int middle = (end - start)/2 + start;
        ArrayTwoThreads task1 = new ArrayTwoThreads(start, middle, array);
//        System.out.println("Task1 =" + task1);
        ArrayTwoThreads task2 = new ArrayTwoThreads(middle, end, array);
//        System.out.println("Task2 =" + task2);
        invokeAll(task1, task2);
        return (task1.join() + task2.join());
    }
}

