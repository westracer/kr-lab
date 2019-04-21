package sample.view_controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.FlowPane;
import javafx.util.StringConverter;

import sample.entity.AdresEntity;
import sample.entity.DogovorEntity;
import sample.entity.ObjectEntity;
import sample.entity.RaionEntity;
import sample.util.DbHelper;

import java.util.List;

@SuppressWarnings({"unchecked", "Duplicates"})
public class ObslClientController {
    @FXML
    public TableView table;

    @FXML
    public FlowPane flowPane;

    public void initAdresTable() {
        List allAddresses = DbHelper.getAllEntitiesFromTable(AdresEntity.class);
        List allRaions = DbHelper.getAllEntitiesFromTable(RaionEntity.class);

        TableColumn<AdresEntity, String> column1 = new TableColumn<>("Адрес");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column1.setOnEditCommit((val) -> {
            AdresEntity entity = val.getRowValue();
            entity.setName(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        TableColumn<AdresEntity, String> column2 = new TableColumn<>("Код по КЛАДР");
        column2.setCellValueFactory(new PropertyValueFactory<>("kodPoKladr"));
        column2.setCellFactory(TextFieldTableCell.forTableColumn());
        column2.setOnEditCommit((val) -> {
            AdresEntity entity = val.getRowValue();
            entity.setKodPoKladr(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        TableColumn<AdresEntity, RaionEntity> column3 = new TableColumn<>("Район");
        column3.setCellValueFactory(new PropertyValueFactory<>("raion"));
        column3.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<RaionEntity>() {
            @Override
            public String toString(RaionEntity r) {
                return r.toString();
            }

            @Override
            public RaionEntity fromString(String string) {
                return null;
            }
        }, FXCollections.observableArrayList(allRaions)));
        column3.setOnEditCommit((val) -> {
            AdresEntity entity = val.getRowValue();
            entity.setRaion(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        table.getColumns().setAll(column1, column2, column3);
        table.getItems().setAll(allAddresses);
    }

    public void initObjectTable() {
        List allDogovors = DbHelper.getAllEntitiesFromTable(DogovorEntity.class);
        List allAddresses = DbHelper.getAllEntitiesFromTable(AdresEntity.class);
        List allObjects = DbHelper.getAllEntitiesFromTable(ObjectEntity.class);

        TableColumn<ObjectEntity, DogovorEntity> column0 = new TableColumn<>("Договор");
        column0.setCellValueFactory(new PropertyValueFactory<>("dogovor"));
        column0.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<DogovorEntity>() {
            @Override
            public String toString(DogovorEntity d) {
                return d.toString();
            }

            @Override
            public DogovorEntity fromString(String string) {
                return null;
            }
        }, FXCollections.observableArrayList(allDogovors)));
        column0.setOnEditCommit((val) -> {
            ObjectEntity entity = val.getRowValue();
            entity.setDogovor(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        TableColumn<ObjectEntity, String> column1 = new TableColumn<>("Площадь");
        column1.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getPloshad())));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column1.setOnEditCommit((val) -> {
            try {
                ObjectEntity entity = val.getRowValue();
                entity.setPloshad(Double.parseDouble(val.getNewValue()));
                DbHelper.saveOrUpdate(entity);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Введено некорректное значение");
                alert.showAndWait();

                table.refresh();
            }
        });

        TableColumn<ObjectEntity, AdresEntity> column2 = new TableColumn<>("Адрес");
        column2.setCellValueFactory(new PropertyValueFactory<>("adres"));
        column2.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<AdresEntity>() {
            @Override
            public String toString(AdresEntity a) {
                return a.toString();
            }

            @Override
            public AdresEntity fromString(String string) {
                return null;
            }
        }, FXCollections.observableArrayList(allAddresses)));
        column2.setOnEditCommit((val) -> {
            ObjectEntity entity = val.getRowValue();
            entity.setAdres(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        table.getColumns().setAll(column0, column1, column2);
        table.getItems().setAll(allObjects);
    }
}
