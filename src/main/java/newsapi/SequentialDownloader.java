package newsapi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class SequentialDownloader extends Downloader {

    @Override
    public int process(List<String> urls) throws IOException {
        long startTime = System.nanoTime();

        int count = 0;

        for (String url : urls) {
            try {
                String fileName = saveUrl2File(url);
                if (fileName != null)
                    count++;
            } catch (Exception e) {
                System.out.println("FYI: An exception has been found during the downloading phase.");
            }
        }

        System.out.println("[SEQUENTIAL] Die Laufzeit betrug in ms: "+(System.nanoTime()-startTime)/1000000);
        return count;
    }


}
