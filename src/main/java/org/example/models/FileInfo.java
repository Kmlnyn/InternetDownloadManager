package org.example.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FileInfo {

    private SimpleIntegerProperty index = new SimpleIntegerProperty();

    private SimpleStringProperty fileName = new SimpleStringProperty();

    private SimpleStringProperty fileUrl = new SimpleStringProperty();

    private SimpleStringProperty status = new SimpleStringProperty();

    private SimpleStringProperty percentage = new SimpleStringProperty();
    private SimpleStringProperty action = new SimpleStringProperty();

    public FileInfo(int index, String fileName, String fileUrl, String status, String action, String per) {
        this.index.set(index);
        this.fileName.set(fileName);
        this.fileUrl.set(fileUrl);
        this.status.set(status);
        this.action.set(action);
        this.percentage.set(per);
    }

    public int getIndex() {
        return index.get();
    }

    public SimpleIntegerProperty indexProperty() {
        return index;
    }

    public void setIndex(int index) {
        this.index.set(index);
    }

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public String getFileUrl() {
        return fileUrl.get();
    }

    public SimpleStringProperty fileUrlProperty() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl.set(fileUrl);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getAction() {
        return action.get();
    }

    public SimpleStringProperty actionProperty() {
        return action;
    }

    public void setAction(String action) {
        this.action.set(action);
    }

    public String getPercentage() {
        return percentage.get();
    }

    public SimpleStringProperty percentageProperty() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage.set(percentage);
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "index=" + index +
                ", fileName=" + fileName +
                ", fileUrl=" + fileUrl +
                ", status=" + status +
                ", action=" + action +
                '}';
    }
}
