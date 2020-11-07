package geekbrains.lesson2_5_hw;

public class Threads {
    private static final int size = 10000000;
    private static final int half_size = size / 2;

    public float[] calculate(float[] arr) {
        for (int i = 0; i < arr.length; i++)
            arr[i] = (float) (arr[i] * Math.sin(0.2f + arr[i] / 5) * Math.cos(0.2f + arr[i] / 5) * Math.cos(0.4f + arr[i] / 2));
        return arr;
    }

    public void doCalculationOne() {
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;
        long a = System.currentTimeMillis();
        calculate(arr);
        System.out.println("Время окончания первого метода: " + (System.currentTimeMillis() - a));
    }

    public void doCalculationTwo() {
        float[] arr = new float[size];
        float[] arr1 = new float[half_size];
        float[] arr2 = new float[half_size];
        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;

        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, half_size);
        System.arraycopy(arr, half_size, arr2, 0, half_size);

        new Thread() {
            public void run() {
                float[] ar1 = calculate(arr1);
                System.arraycopy(ar1, 0, arr1, 0, ar1.length);
            }
        }.start();

        new Thread() {
            public void run() {
                float[] ar2 = calculate(arr2);
                System.arraycopy(ar2, 0, arr2, 0, ar2.length);
            }
        }.start();

        System.arraycopy(arr1, 0, arr, 0, half_size);
        System.arraycopy(arr2, 0, arr, half_size, half_size);
        System.out.println("Время окончания второго метода: " + (System.currentTimeMillis() - a));
    }
}
