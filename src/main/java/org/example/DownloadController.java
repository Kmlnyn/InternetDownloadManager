package org.example;

import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.example.models.FileInfo;

import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;

public class DownloadController {

    private int index = 0;
    @FXML
    private TextField urlTextField;

    @FXML
    private TableView<FileInfo> fileInfoTable;

    @FXML
    void downloadButtonClicked(ActionEvent event) {
        String urlTextFieldText = urlTextField.getText();
        if (urlTextFieldText != null && urlTextFieldText.length() != 0) {
            String fileName = urlTextFieldText.substring(urlTextFieldText.lastIndexOf("/") + 1);
            String status = AppConfig.Status.Starting.toString();
            String action = "Open";
            FileInfo fileInfo = new FileInfo(++index, fileName, urlTextFieldText, status, action, "0");
            DownloadThread downloadThread = new DownloadThread(fileInfo, this);
            this.fileInfoTable.getItems().add(fileInfo);

            this.urlTextField.setText("");
            downloadThread.start();

        }
    }

    public void updateUI(FileInfo metaFileInfo) {
        FileInfo fileInfo = this.fileInfoTable.getItems().get(metaFileInfo.getIndex() - 1);
        fileInfo.setStatus(fileInfo.getStatus());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        fileInfo.setPercentage(decimalFormat.format(Double.parseDouble(metaFileInfo.getPercentage())));
        this.fileInfoTable.refresh();
    }

    @FXML
    public void initialize(){
        addButtonToTable();
        TableColumn<FileInfo, Number> sn = (TableColumn<FileInfo, Number>) fileInfoTable.getColumns().get(0);
        sn.setCellValueFactory(p->{
            return p.getValue().indexProperty();
        });
        TableColumn<FileInfo, String> fileName = (TableColumn<FileInfo, String>) fileInfoTable.getColumns().get(1);
        fileName.setCellValueFactory(p->{
            return p.getValue().fileNameProperty();
        });
        TableColumn<FileInfo, String> fileUrl = (TableColumn<FileInfo, String>) fileInfoTable.getColumns().get(2);
        fileUrl.setCellValueFactory(p->{
            return p.getValue().fileUrlProperty();
        });
        TableColumn<FileInfo, String> status = (TableColumn<FileInfo, String>) fileInfoTable.getColumns().get(3);
        status.setCellValueFactory(p->{
            return p.getValue().statusProperty();
        });
        TableColumn<FileInfo, String> percentage = (TableColumn<FileInfo, String>) fileInfoTable.getColumns().get(4);
        percentage.setCellValueFactory(p->{
            SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
            simpleStringProperty.set(p.getValue().getPercentage()+" %");
            return simpleStringProperty;
        });

    }

    private void addButtonToTable() {
        TableColumn<FileInfo, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<FileInfo, Void>, TableCell<FileInfo, Void>> cellFactory = new Callback<TableColumn<FileInfo, Void>, TableCell<FileInfo, Void>>() {
            @Override
            public TableCell<FileInfo, Void> call(final TableColumn<FileInfo, Void> param) {
                final TableCell<FileInfo, Void> cell = new TableCell<FileInfo, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            FileInfo fileInfo = fileInfoTable.getItems().get(index-1);
                            System.out.println(fileInfo);
                            File file
                                    = new File(AppConfig.DOWNLOAD_PATH+"/"+fileInfo.getFileName());

                            if (file.delete()) {
                                System.out.println("File deleted successfully");
                                index--;
                                fileInfoTable.getItems().remove(fileInfo);
                            }
                            else {
                                System.out.println("Failed to delete the file");
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        fileInfoTable.getColumns().add(colBtn);

    }
}
