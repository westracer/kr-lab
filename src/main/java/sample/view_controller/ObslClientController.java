package sample.view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.FlowPane;
import javafx.util.StringConverter;

import sample.entity.AdresEntity;
import sample.entity.RaionEntity;
import sample.util.DbHelper;

import java.util.List;

@SuppressWarnings("unchecked")
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

        ObservableList cols = table.getColumns();
        cols.removeAll();
        cols.addAll(column1, column2, column3);

        ObservableList ol = table.getItems();
        ol.addAll(allAddresses);
    }
}
