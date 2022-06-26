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

        List<Callable<String>> callables = new ArrayList<>();
        for(String url : urls){
            Callable<String> task = () -> {     //   "->" DAS ist die lambda expression
                try{
                    return saveUrl2File(url);
                }
                catch (Exception couldntDownload){
                    return "couldnt download URL: " + couldntDownload.getMessage();
                }
            };
            callables.add(task);  // Callable represents an asynchronous task which can be executed by a separate thread
        }

        int count = 0;
        try {
            List<Future<String>> allFutures = pool.invokeAll(callables);//A Future represents the result of an asynchronous computation.
            for(Future<String> all : allFutures){
                if (all.get() != null) {
                    count++;
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        pool.shutdown();

        return count;
    }
}
