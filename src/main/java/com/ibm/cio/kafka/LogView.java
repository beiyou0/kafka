package com.ibm.cio.kafka;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by qianqian on 30/11/2017.
 */
public class LogView {
    private long lastTimeFileSize = 0;

    public void realtimeShowLog(File logFile) throws IOException {
        final RandomAccessFile randomFile = new RandomAccessFile(logFile,"rw");
        ScheduledExecutorService exec =
                Executors.newScheduledThreadPool(1);
        exec.scheduleWithFixedDelay(new Runnable(){
            public void run() {
                try {
                    randomFile.seek(lastTimeFileSize);
                    String tmp;
                    while( (tmp = randomFile.readLine())!= null) {
                        System.out.println(tmp);
                    }
                    lastTimeFileSize = randomFile.length();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws Exception {
        LogView view = new LogView();
        final File tmpLogFile = new File("src/main/resources/aep_so_chl1.log.20180130");
        view.realtimeShowLog(tmpLogFile);
    }
}
