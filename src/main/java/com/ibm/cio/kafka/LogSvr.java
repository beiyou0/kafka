package com.ibm.cio.kafka;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by qianqian on 30/11/2017.
 */
public class LogSvr {
    private SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public void logMsg(File logFile, String mesInfo) throws IOException {
        if(logFile == null) {
            throw new IllegalStateException("logFile can not be null!");
        }
        Writer txtWriter = new FileWriter(logFile,true);
//        txtWriter.write(dateFormat.format(new Date()) +"\t"+mesInfo+"\n");
//        txtWriter.write(dateFormat.format(new Date()) + ",[INFO],<Stored Procedure processing complete - EventId: 111111  BOEventId: 111111  Order: 111111  SalesOrg: 111111\n");  //2017-11-30 00:59:04.775
        txtWriter.write(dateFormat.format(new Date()) + ",[INFO],>QQTEST EventId: 1  BOEventId: 2  Order: 3  SalesOrg: 4\n");  //2017-11-30 00:59:04.775
//        txtWriter.write(dateFormat.format(new Date()) + ",[INFO],Processing finished,Request Id,1235436346915045608\n");  //2017-11-30 00:59:04.775
        txtWriter.flush();
    }

    public static void main(String[] args) throws Exception{

        final LogSvr logSvr = new LogSvr();
//        final File tmpLogFile = new File("src/main/resources/test.aep/aep_so_chl1.log.20180130");
//        final File tmpLogFile = new File("src/main/resources/test.aep/aep_so_chl2.log.20180130");
        final File tmpLogFile = new File("src/main/resources/test/idm/idm_request.log.20180130");
        if(!tmpLogFile.exists()) {
            tmpLogFile.createNewFile();
        }

        ScheduledExecutorService exec =
                Executors.newScheduledThreadPool(1);
        exec.scheduleWithFixedDelay(new Runnable(){
            public void run() {
                try {
                    logSvr.logMsg(tmpLogFile, " test.aep log test !");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 15, TimeUnit.SECONDS);
    }
}
