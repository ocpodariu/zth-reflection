package ro.teamnet.zth.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

/**
 * Author: Ovidiu
 * Date:   5/5/2015
 */
public class LogFileWriter {
    public static final String TOMCAT_PATH = System.getenv("CATALINA_HOME").replace(";","");

    /**
     * Logs a header to %CATALINA_HOME%\logs\header.log
     * @param headerName - Header name
     * @param headerValue - Header value
     */
    public static void logHeader(String headerName, String headerValue) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(TOMCAT_PATH + File.separator +
                    "logs" + File.separator + "header.log", "rw");

            StringBuilder sb = new StringBuilder();
            String ln;
            while((ln = randomAccessFile.readLine()) != null)
                sb.append(ln).append("\r\n");
            sb.append(new Date()).append(":").append(headerName).append(":").append(headerValue).append("\r\n");

            randomAccessFile.writeBytes(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        logHeader("test_header", "some random value 14");
    }


}
