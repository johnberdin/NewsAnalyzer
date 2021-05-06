package newsapi;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader {


    @Override
    public int process(List<String> urls) {

        long startTime = System.nanoTime();
        int result = 0;

        ExecutorService exService = Executors.newSingleThreadExecutor();

        Callable<Integer> downloadTask = () -> {
            int count = 0;
            for (String url : urls) {
                try {
                    String fileName = saveUrl2File(url);
                    if (fileName != null)
                        count++;
                }catch (Exception e) {
                    System.out.println("FYI: An exception has been found during the downloading phase.");
                }
            }
            return count;
        };

        Future<Integer> future = exService.submit(downloadTask);

        if (future.isDone()) {
            try {
                result = future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (future.isCancelled())
            System.out.println("Callable got cancelled!");

        System.out.println("[PARALLEL] Die Laufzeit betrug in ms: "+(System.nanoTime()-startTime)/1000000);
        return result;

    }




}
