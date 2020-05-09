package org.tmdrk.toturial.collection.falseSharing;

/**
 * @ClassName FalseSharing
 * @Description
 * http://ifeve.com/from-javaeye-false-sharing/
 * @Author zhoujie
 * @Date 2020/3/30 14:37
 * @Version 1.0
 **/
public class FalseSharing implements Runnable{
    public static int NUM_THREADS = 4; // change

    public final static long ITERATIONS = 100L * 1000L * 1000L;

    private final int arrayIndex;

    private static VolatileLong[] longs;



    public FalseSharing(final int arrayIndex) {

        this.arrayIndex = arrayIndex;

    }



    public static void main(final String[] args) throws Exception {

        Thread.sleep(1000);

        System.out.println("starting....");

        if (args.length == 1) {

            NUM_THREADS = Integer.parseInt(args[0]);

        }



        longs = new VolatileLong[NUM_THREADS];

        for (int i = 0; i < longs.length; i++) {

            longs[i] = new VolatileLong();

        }

        final long start = System.nanoTime();

        runTest();

        System.out.println("duration = " + (System.nanoTime() - start));

    }



    private static void runTest() throws InterruptedException {

        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++) {

            threads[i] = new Thread(new FalseSharing(i));

        }

        for (Thread t : threads) {

            t.start();

        }

        for (Thread t : threads) {

            t.join();

        }

    }



    public void run() {

        long i = ITERATIONS + 1;

        while (0 != --i) {

            longs[arrayIndex].value = i;

        }

    }



    public final static class VolatileLong {

        public volatile long value = 0L;

//        public long p1, p2, p3, p4, p5, p6; // 注释

    }
}
