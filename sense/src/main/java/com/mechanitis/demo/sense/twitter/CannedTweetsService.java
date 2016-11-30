package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.sense.infrastructure.BroadcastingServerEndpoint;
import com.mechanitis.demo.sense.infrastructure.DaemonThreadFactory;
import com.mechanitis.demo.sense.infrastructure.WebSocketServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResource;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.getLogger;

/**
 * Reads tweets from a file and sends them to the Twitter Service endpoint.
 */
public class CannedTweetsService implements Runnable {
    private static final Logger LOGGER = getLogger(CannedTweetsService.class.getName());

    private final ExecutorService executor = newSingleThreadExecutor(new DaemonThreadFactory());
    private final BroadcastingServerEndpoint<String> tweetsEndpoint = new
            BroadcastingServerEndpoint<>();
    private final WebSocketServer server
            = new WebSocketServer("/tweets/", 8081, tweetsEndpoint);
    private final Path filePath;

    private CountDownLatch stopped = new CountDownLatch(1);
    private boolean running = true;

    public CannedTweetsService(Path filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) throws URISyntaxException {
        new CannedTweetsService(get(getSystemResource("./tweetdata60-mins.txt").toURI())).run();
    }

    @Override
    public void run() {
        executor.submit(server);
        try (Stream<String> lines = lines(filePath)) {
            lines.dropWhile(s -> running)
                 .filter(message -> !message.equals("OK"))
                 .peek(s -> addArtificialDelay())
                 .forEach(tweetsEndpoint::onMessage);
            stopped.countDown();
        } catch (IOException e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
        }
    }

    private void addArtificialDelay() {
        try {
            //reading the file is FAST, add an artificial delay
            MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
            LOGGER.log(WARNING, e.getMessage(), e);
        }
    }

    public void stop() throws Exception {
        running = false;
        stopped.await();
        server.stop();
        executor.shutdownNow();
    }

    private void monitoring() throws IOException {
        ProcessBuilder ls = new ProcessBuilder()
                .command("ls")
                .directory(Paths.get("/")
                                .toFile());
        ProcessBuilder grepPdf = new ProcessBuilder()
                .command("grep", "pdf")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT);
        List<Process> lsThenGrep = ProcessBuilder
                .startPipeline(asList(ls, grepPdf));

        CompletableFuture[] lsThenGrepFutures = lsThenGrep.stream()
                                                          // onExit returns a
                                                          // CompletableFuture<Process>
                                                          .map(Process::onExit)
                                                          .map(processFuture -> processFuture
                                                                  .thenAccept(
                                                                          process -> System.out
                                                                                  .println(
                                                                                  "PID: " +
                                                                                  process.getPid
                                                                                          ())))
                                                          .toArray(CompletableFuture[]::new);
        // wait until all processes are finished
        CompletableFuture
                .allOf(lsThenGrepFutures)
                .join();

        long pid = ProcessHandle.current()
                                .getPid();
    }
}
