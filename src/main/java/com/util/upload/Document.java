package com.util.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;

public class Document {
    ContentFactory holder;
    FileHolder file;
    private String description;
    private String documentId;
    private String fileExt;
    private String fileName;
    private int fileSize = 0;
    private String fileType;
    private String storeDate;
    private String storeFileName;
    private String storeFilePath;
    private String userDir;
    private String currentDate;
    private String currentTime;
    private final int directorySize = 2000;

    private String defaultDirectory = "\\";
    private String rootDirectory;

    public Document(String virualPath) {
        init(virualPath);
        this.currentDate = currentDate();
        this.currentTime = currentTime();
    }

    private void init(String virualPath) {
        String configPath = virualPath;

        if (configPath == null)
            System.out.println("文档服务器配置有错误");
        else {
            this.rootDirectory = (configPath + this.defaultDirectory);
        }
        File f = new File(this.rootDirectory);
        if (!f.exists())
            f.mkdirs();
    }

    public Document(String documentId, String virualPath) throws Exception {
        this(virualPath);
        this.documentId = documentId;
    }

    public boolean uploadFile(String para, String description, HttpServletRequest request) throws Exception {
        try {
            this.holder = ContentFactory.getContentFactory(request, 104857600);
            this.file = this.holder.getFileParameter(para);
            if (this.file == null) {
                return false;
            }
            this.description = this.holder.getParameter(description);
            this.fileName = this.file.getFileName();
            this.fileExt = this.file.getFileExt();
            this.fileType = this.file.getContentType();
            this.fileSize = this.file.getSize();
            setDocumentId();
            setStoreFileName();
            this.storeFilePath = storeFilePath();

            return true;
        } catch (ContentFactoryException ce) {
            System.out.println("上传文档失败:" + ce.getMessage());
            return false;
        } catch (IOException io) {
            System.out.println("上传文档失败:" + io.getMessage());
        }
        return false;
    }

    public boolean uploadbs(String para, String description, HttpServletRequest request, String id) throws Exception {
        try {
            this.holder = ContentFactory.getContentFactory(request, 2097152);
            this.file = this.holder.getFileParameter(para);
            if (this.file == null) {
                return false;
            }
            this.description = this.holder.getParameter(description);
            this.fileName = id;
            this.fileExt = this.file.getFileExt();
            this.fileType = this.file.getContentType();
            this.fileSize = this.file.getSize();
            this.documentId = id;
            setStoreFileName();
            this.storeFilePath = "";

            return true;
        } catch (ContentFactoryException ce) {
            System.out.println("上传文档失败" + ce.getMessage());
            return false;
        } catch (IOException io) {
            System.out.println("上传文档失败" + io.getMessage());
        }
        return false;
    }

    public String getParameter(String name) {
        return this.holder.getParameter(name);
    }

    public FileInputStream downFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.rootDirectory + this.storeFilePath + "\\"
                    + this.storeFileName);

            return fileInputStream;
        } catch (Exception e) {
            System.out.println("文件流有错" + e.getMessage());
        }
        return null;
    }

    private void setDocumentId() {
        Calendar c = Calendar.getInstance();
        int year = c.get(1);
        int month = c.get(2) + 1;
        int day = c.get(5);
        int hour = c.get(11);
        int minute = c.get(12);
        int second = c.get(13);
        double rndDouble = Math.random();
        int rndInt = (int) (rndDouble * 999.0D);
        this.documentId = (String.valueOf(year) + String.valueOf(day) + String.valueOf(month) + String.valueOf(minute)
                + String.valueOf(hour) + String.valueOf(second) + String.valueOf(rndInt));

        this.documentId = this.documentId;
    }

    private void setStoreFileName() {
        this.storeFileName = (this.documentId + "." + this.fileExt);
    }

    private String storeFilePath() {
        if (this.userDir != null) {
            File userDir = new File(this.rootDirectory + this.userDir);
            if (!userDir.exists()) {
                userDir.mkdir();

                File userDirChild = new File(this.rootDirectory + this.userDir + "//" + this.currentDate);
                userDirChild.mkdir();
                this.storeFilePath = (this.userDir + "\\" + this.currentDate);
            } else {
                getClass();
                if (directorySize(this.rootDirectory + this.userDir + "//"
                        + directoryName(new StringBuffer().append(this.rootDirectory).append(this.userDir).toString())) > 2000 - 1) {
                    File userDirChild = new File(this.rootDirectory + this.userDir + "//" + this.currentDate);

                    userDirChild.mkdir();
                    this.storeFilePath = (this.userDir + "\\" + this.currentDate);
                } else {
                    this.storeFilePath = (this.userDir + "\\" + directoryName(new StringBuffer()
                            .append(this.rootDirectory).append(this.userDir).toString()));
                }
            }
        } else if (directoryName(this.rootDirectory, "20") == null) {
            File sysDirChild = new File(this.rootDirectory + "//" + this.currentDate);
            sysDirChild.mkdir();
            this.storeFilePath = this.currentDate;
        } else {
            getClass();
            if (directorySize(this.rootDirectory + "//" + directoryName(this.rootDirectory, "20")) > 2000 - 1) {
                File sysDirChild = new File(this.rootDirectory + "//" + this.currentDate);
                sysDirChild.mkdir();
                this.storeFilePath = this.currentDate;
            } else {
                this.storeFilePath = directoryName(this.rootDirectory, "20");
            }
        }
        return this.storeFilePath;
    }

    private int directorySize(String dir) {
        File f = new File(dir);
        String[] fileNames = f.list();
        return fileNames != null ? fileNames.length : 0;
    }

    private String directoryName(String dir) {
        File f = new File(dir);
        File[] files = f.listFiles();
        if (files != null) {
            if (files.length == 0) {
                return null;
            }
            int max = 0;
            for (int i = 0; i < files.length; i++) {
                if (files[i].lastModified() > files[max].lastModified()) {
                    max = i;
                }
            }
            return files[max].getName();
        }
        return null;
    }

    private String directoryName(String dir, String filterChar) {
        File f = new File(dir);
        File[] files = f.listFiles();
        if (files != null) {
            if (files.length == 0) {
                return null;
            }
            int max = 0;
            boolean isSys = false;
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().startsWith(filterChar)) {
                    isSys = true;
                    if (files[i].lastModified() > files[max].lastModified()) {
                        max = i;
                    }
                }

            }

            if (isSys) {
                return files[max].getName();
            }
            return null;
        }

        return null;
    }

    public void clean() {
    }

    private String currentDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(1);
        int month = c.get(2) + 1;
        int day = c.get(5);
        return String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
    }

    private String currentTime() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(11);
        int minute = c.get(12);
        int second = c.get(13);
        return String.valueOf(hour) + ":" + String.valueOf(minute) + ":" + String.valueOf(second);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String aDescription) {
        this.description = aDescription;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public String getFileExt() {
        return this.fileExt;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public String getFileType() {
        return this.fileType;
    }

    public String getStoreDate() {
        return this.currentDate + " " + this.currentTime;
    }

    public void setStoreFilePath(String aStoreFilePath) {
        this.userDir = aStoreFilePath;
    }

    public String getStoreFilePath() {
        return this.storeFilePath;
    }

    public String getStoreFileName() {
        return this.storeFileName;
    }

    public String getUserDir() {
        return this.userDir;
    }

    public void saveTo() {
        try {
            this.file.saveTo(this.rootDirectory + this.storeFilePath + "\\" + this.storeFileName);
        } catch (IOException io) {
            System.out.println("上传文档保存失败" + io.getMessage());
        }
    }

    public void saveAs(String filename) {
        try {
            this.file.saveTo(this.rootDirectory + this.storeFilePath + "\\" + filename);
        } catch (IOException io) {
            System.out.println("上传文档保存失败" + io.getMessage());
        }
    }

    public void saveTobs() {
        try {
            this.file.saveTo(this.rootDirectory + "\\" + this.storeFileName);
        } catch (IOException io) {
            System.out.println("上传文档保存失败" + io.getMessage());
        }
    }
}
