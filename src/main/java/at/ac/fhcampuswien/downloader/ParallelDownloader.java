package at.ac.fhcampuswien.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;

// Class is needed for exercise 4 - ignore for exercise 3 solution
public class ParallelDownloader extends Downloader{

    // returns number of downloaded article urls
    @Override
    public int process(List<String> urls) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(availableProcessors);

        List<Callable<String>> futures = new ArrayList<>();
        for(String url : urls){
            Callable<String> task = () -> {
                try{
                    return saveUrl2File(url);
                }
                catch (Exception couldntDownload){
                    return "couldnt download URL: " + couldntDownload.getMessage();
                }
            };
            futures.add(task);
        }

        int count = 0;
        try {
            List<Future<String>> allFutures = pool.invokeALL(futures);
            for(Future<String> f : allFutures){
                if (f.get() != null) {
                    count++;
                }
            }
        }

        pool.shutdown();

        return count;
    }
}
