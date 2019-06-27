package com.fyp.agent.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory {

    private static final Logger LOGGER = Logger.getLogger( ZipDirectory.class.getName() );

    private String folderPath;
    private String zipName;
    private String outputPath;

    public ZipDirectory(String folderPath, String zipName, String outputPath) {
        this.folderPath = folderPath;
        this.zipName = zipName;
        this.outputPath = outputPath;
    }

    public void zip(){
        File directoryToZip = new File(folderPath);
        List<File> fileList = new ArrayList<File>();
        LOGGER.info("Loading agent dependencies");
        getAllFiles(directoryToZip, fileList);
        LOGGER.info("Compressing agent");
        writeZipFile(directoryToZip, fileList);
        LOGGER.info("Agent zip created");
    }

    public void getAllFiles(File dir, List<File> fileList) {
        File[] files = dir.listFiles();
        for (File file : files) {
            fileList.add(file);
            if (file.isDirectory()) {
                getAllFiles(file, fileList);
            } else {
            }
        }
    }

    public void writeZipFile(File directoryToZip, List<File> fileList) {

        try {
            FileOutputStream fos = new FileOutputStream(outputPath+"/"+zipName + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (File file : fileList) {

                if (!file.isDirectory()) { // we only zip files, not directories
                    addToZip(directoryToZip, file, zos);
                }
            }

            zos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException,
            IOException {

        FileInputStream fis = new FileInputStream(file);

        // we want the zipEntry's path to be a relative path that is relative
        // to the directory being zipped, so chop off the rest of the path
        String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
                file.getCanonicalPath().length());
        LOGGER.info("Copying '" + zipFilePath );
        ZipEntry zipEntry = new ZipEntry(zipFilePath);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

}
