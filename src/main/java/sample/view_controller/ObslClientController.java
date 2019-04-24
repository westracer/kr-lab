package sample.view_controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import sample.Main;
import sample.entity.*;
import sample.util.DateEditingCell;
import sample.util.DbHelper;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"unchecked", "Duplicates"})
public class ObslClientController {
    private Class currentEntity;

    @FXML
    public TableView table;

    @FXML
    public void initialize() {
        initAdresTable();
    }

    private <T> void _addDeleteColumn() {
        final double BUTTON_COL_WIDTH = 80;

        TableColumn<T, Void> colBtn = new TableColumn("");
        colBtn.setPrefWidth(BUTTON_COL_WIDTH);
        colBtn.setMaxWidth(BUTTON_COL_WIDTH);
        colBtn.setMinWidth(BUTTON_COL_WIDTH);

        Callback<TableColumn<T, Void>, TableCell<T, Void>> cellFactory
                = new Callback<TableColumn<T, Void>, TableCell<T, Void>>() {
            @Override
            public TableCell<T, Void> call(final TableColumn<T, Void> param) {
                return new TableCell<T, Void>() {
                    private final Button btn = new Button("Удалить");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            T item = getTableView().getItems().get(getIndex());
                            DbHelper.remove(item);
                            table.getItems().remove(item);
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
            }
        };

        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);
    }

    public void initAdresTable() {
        final double KLADR_COL_WIDTH = 200;

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
        column2.setMinWidth(KLADR_COL_WIDTH);
        column2.setMaxWidth(KLADR_COL_WIDTH);
        column2.setPrefWidth(KLADR_COL_WIDTH);
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
                return r != null ? r.toString() : "Выберите район";
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
        _addDeleteColumn();

        table.getItems().setAll(allAddresses);
        currentEntity = AdresEntity.class;
    }

    public void initSchetchikTable() {
        final double NOMER_COL_WIDTH = 100;
        final double DATE_COL_WIDTH = 250;

        List allTip = DbHelper.getAllEntitiesFromTable(TipSchetchikaEntity.class);
        List allTipEl = DbHelper.getAllEntitiesFromTable(TipElektrEntity.class);
        List allSchetchik = DbHelper.getAllEntitiesFromTable(SchetchikEntity.class);

        TableColumn<SchetchikEntity, String> column1 = new TableColumn<>("Номер");
        column1.setMinWidth(NOMER_COL_WIDTH);
        column1.setMaxWidth(NOMER_COL_WIDTH);
        column1.setPrefWidth(NOMER_COL_WIDTH);
        column1.setCellValueFactory(new PropertyValueFactory<>("nomer"));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column1.setOnEditCommit((val) -> {
            SchetchikEntity entity = val.getRowValue();
            entity.setNomer(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        TableColumn<SchetchikEntity, Date> column2 = new TableColumn<>("Дата поверки");
        column2.setCellValueFactory(cellData -> {
            Date sqlDate = cellData.getValue().getProverkaDate();
            return sqlDate != null ? new SimpleObjectProperty(new Date(sqlDate.getTime())) : null;
        });
        column2.setMinWidth(DATE_COL_WIDTH);
        column2.setMaxWidth(DATE_COL_WIDTH);
        column2.setPrefWidth(DATE_COL_WIDTH);
        column2.setCellFactory(param -> new DateEditingCell<>());
        column2.setOnEditCommit((val) -> {
            SchetchikEntity entity = val.getRowValue();
            Date date = val.getNewValue();
            entity.setProverkaDate(new java.sql.Date(date.getTime()));
            DbHelper.saveOrUpdate(entity);
        });

        TableColumn<SchetchikEntity, TipElektrEntity> column3 = new TableColumn<>("Тип электричества (заяв./прис./макс.)");
        column3.setCellValueFactory(new PropertyValueFactory<>("tipEl"));
        column3.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(allTipEl)));
        column3.setOnEditCommit((val) -> {
            SchetchikEntity entity = val.getRowValue();
            entity.setTipEl(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        TableColumn<SchetchikEntity, TipSchetchikaEntity> column4 = new TableColumn<>("Тип счетчика");
        column4.setCellValueFactory(new PropertyValueFactory<>("tip"));
        column4.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(allTip)));
        column4.setOnEditCommit((val) -> {
            SchetchikEntity entity = val.getRowValue();
            entity.setTip(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        table.getColumns().setAll(column1, column2, column3, column4);
        _addDeleteColumn();

        table.getItems().setAll(allSchetchik);
        currentEntity = SchetchikEntity.class;
    }

    public void initObjectTable() {
        final double PLOSHAD_COL_WIDTH = 100;

        List allDogovors = DbHelper.getAllEntitiesFromTable(DogovorEntity.class);
        List allAddresses = DbHelper.getAllEntitiesFromTable(AdresEntity.class);
        List allObjects = DbHelper.getAllEntitiesFromTable(ObjectEntity.class);

        TableColumn<ObjectEntity, DogovorEntity> column0 = new TableColumn<>("Договор");
        column0.setCellValueFactory(new PropertyValueFactory<>("dogovor"));
        column0.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<DogovorEntity>() {
            @Override
            public String toString(DogovorEntity d) {
                return d != null ? d.toString() : "Выберите договор";
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
        column1.setMinWidth(PLOSHAD_COL_WIDTH);
        column1.setMaxWidth(PLOSHAD_COL_WIDTH);
        column1.setPrefWidth(PLOSHAD_COL_WIDTH);
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
        column2.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(allAddresses)));
        column2.setOnEditCommit((val) -> {
            ObjectEntity entity = val.getRowValue();
            entity.setAdres(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        table.getColumns().setAll(column0, column1, column2);
        _addDeleteColumn();

        table.getItems().setAll(allObjects);
        currentEntity = ObjectEntity.class;
    }

    public void addRow() {
        Object obj;

        try {
            obj = currentEntity.newInstance();
            DbHelper.saveOrUpdate(obj);
            table.getItems().add(obj);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Невозможно создать строку");
            alert.showAndWait();
        }
    }

    public void exportDogovor(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Main.class.getResource("../dogovor_select.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Выберите договор");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(table.getScene().getWindow());
        stage.show();
    }

    public void exit() {
        System.exit(0);
    }
}
