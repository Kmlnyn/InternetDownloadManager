package org.example;

import org.example.models.FileInfo;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DownloadThread extends Thread{

    private FileInfo fileInfo;

    private DownloadController downloadController;

    public DownloadThread(FileInfo fileInfo, DownloadController downloadController){
        this.fileInfo = fileInfo;
        this.downloadController = downloadController;
    }

    @Override
    public void run() {
        String status = AppConfig.Status.Dowloading.toString();
        this.fileInfo.setStatus(status);
        this.downloadController.updateUI(fileInfo);
        double countSize = 0.0;
        double percentage = 0.0;
        try {
            //logic for downloading files
            //Files.copy(new URL(fileInfo.getFileUrl()).openStream(), Paths.get(AppConfig.DOWNLOAD_PATH+"/"+fileInfo.getFileName()), StandardCopyOption.REPLACE_EXISTING);

           URL url = new URL(fileInfo.getFileUrl());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            long fileSize = httpURLConnection.getContentLengthLong();



            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fos = new FileOutputStream(AppConfig.DOWNLOAD_PATH+"/"+fileInfo.getFileName());
            byte[] read = new byte[1024];
            int count =0;
            while((count = bis.read(read, 0, 1024))!=-1){
                fos.write(read, 0, count);
                countSize = countSize+count;
                if(fileSize>0) {
                    percentage = (countSize / fileSize * 100);
                    System.out.println(percentage);
                    fileInfo.setPercentage(String.valueOf(percentage));
                    this.downloadController.updateUI(fileInfo);
                }
            }
            percentage=100.0;
            bis.close();
            fos.close();
            status = AppConfig.Status.Downloaded.toString();
        }catch (IOException e){
            status = AppConfig.Status.Failed.toString();
            percentage = 0.0;
            e.printStackTrace();
        }

        this.fileInfo.setStatus(status);
        this.fileInfo.setPercentage(String.valueOf(percentage));
        this.downloadController.updateUI(fileInfo);
    }
}
