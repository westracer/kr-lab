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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"unchecked", "Duplicates"})
// TODO: refactor controllers
public class ProdajController {
    private Class currentEntity;

    @FXML public TableView table;
    @FXML public Label tableLabel;

    @FXML
    public void initialize() {
        initPokazTable();
    }

    private void _setDoubleComparator(TableColumn<?, String> column) {
        column.setComparator((o1, o2) -> {
            String oa1 = o1 != null && !o1.isEmpty() ? o1 : "0";
            String oa2 = o2 != null && !o2.isEmpty() ? o2 : "0";

            return Double.compare(Double.parseDouble(oa1), Double.parseDouble(oa2));
        });
    }

    private void _setCurrentEntity(Class c) {
        currentEntity = c;

        String labelStr = "";
        if (c == PokazaniyaEntity.class) {
            labelStr = "Показания";
        } else if (c == SchetchikEntity.class) {
            labelStr = "Счетчики";
        } else if (c == ObjectEntity.class) {
            labelStr = "Объекты";
        }

        tableLabel.setText(labelStr);
    }

    private void _setDateComparator(TableColumn<?, Date> column) {
        column.setComparator((o1, o2) -> o1 != null ? o2 != null ? o1.compareTo(o2) : 1 : -1);
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

    public void initPokazTable() {
        final double VALUE_COL_WIDTH = 200;
        final double DATE_COL_WIDTH = 150;

        List allPokaz = DbHelper.getAllEntitiesFromTable(PokazaniyaEntity.class);
        List allSchetchik = DbHelper.getAllEntitiesFromTable(SchetchikEntity.class);

        TableColumn<PokazaniyaEntity, String> column1 = new TableColumn<>("Значение");
        column1.setMinWidth(VALUE_COL_WIDTH);
        column1.setMaxWidth(VALUE_COL_WIDTH);
        column1.setPrefWidth(VALUE_COL_WIDTH);
        _setDoubleComparator(column1);
        column1.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getValue())));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column1.setOnEditCommit((val) -> {
            try {
                PokazaniyaEntity entity = val.getRowValue();
                entity.setValue(Double.parseDouble(val.getNewValue()));
                System.out.println(entity.getId());
                DbHelper.saveOrUpdate(entity);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Введено некорректное значение");
                alert.showAndWait();

                table.refresh();
            }
        });

        TableColumn<PokazaniyaEntity, Date> column2 = new TableColumn<>("Дата снятия");
        _setDateComparator(column2);
        column2.setCellValueFactory(cellData -> {
            Date sqlDate = cellData.getValue().getDate();
            return sqlDate != null ? new SimpleObjectProperty(new Date(sqlDate.getTime())) : null;
        });
        column2.setMinWidth(DATE_COL_WIDTH);
        column2.setMaxWidth(DATE_COL_WIDTH);
        column2.setPrefWidth(DATE_COL_WIDTH);
        column2.setCellFactory(param -> new DateEditingCell<>());
        column2.setOnEditCommit((val) -> {
            PokazaniyaEntity entity = val.getRowValue();
            Date date = val.getNewValue();
            entity.setDate(new java.sql.Date(date.getTime()));
            DbHelper.saveOrUpdate(entity);
        });


        TableColumn<PokazaniyaEntity, SchetchikEntity> column3 = new TableColumn<>("Счетчик");
        column3.setCellValueFactory(new PropertyValueFactory<>("schetchik"));
        column3.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<SchetchikEntity>() {
            @Override
            public String toString(SchetchikEntity r) {
                return r != null ? r.toString() : "Выберите счетчик";
            }

            @Override
            public SchetchikEntity fromString(String string) {
                return null;
            }
        }, FXCollections.observableArrayList(allSchetchik)));
        column3.setOnEditCommit((val) -> {
            PokazaniyaEntity entity = val.getRowValue();
            entity.setSchetchik(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        table.getColumns().setAll(column1, column2, column3);
        _addDeleteColumn();

        table.getItems().setAll(allPokaz);
        _setCurrentEntity(PokazaniyaEntity.class);
    }

    public void initSchetchikTable() {
        final double NOMER_COL_WIDTH = 100;
        final double POTR_COL_WIDTH = 200;
        final double DATE_COL_WIDTH = 150;

        List allTip = DbHelper.getAllEntitiesFromTable(TipSchetchikaEntity.class);
        List allTipEl = DbHelper.getAllEntitiesFromTable(TipElektrEntity.class);
        List allObjects = DbHelper.getAllEntitiesFromTable(ObjectEntity.class);
        List allSchetchik = DbHelper.getAllEntitiesFromTable(SchetchikEntity.class);
        List<PokazaniyaEntity> allPokaz = DbHelper.getAllEntitiesFromTable(PokazaniyaEntity.class);

        TableColumn<SchetchikEntity, String> column1 = new TableColumn<>("Номер");
        column1.setMinWidth(NOMER_COL_WIDTH);
        column1.setMaxWidth(NOMER_COL_WIDTH);
        column1.setPrefWidth(NOMER_COL_WIDTH);
        _setDoubleComparator(column1);
        column1.setCellValueFactory(new PropertyValueFactory<>("nomer"));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column1.setOnEditCommit((val) -> {
            SchetchikEntity entity = val.getRowValue();
            entity.setNomer(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        TableColumn<SchetchikEntity, ObjectEntity> column5 = new TableColumn<>("Объект");
        column5.setCellValueFactory(new PropertyValueFactory<>("object"));
        column5.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(allObjects)));
        column5.setOnEditCommit((val) -> {
            SchetchikEntity entity = val.getRowValue();
            entity.setObject(val.getNewValue());
            DbHelper.saveOrUpdate(entity);
        });

        TableColumn<SchetchikEntity, Date> column2 = new TableColumn<>("Дата поверки");
        _setDateComparator(column2);
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

        TableColumn<SchetchikEntity, String> column6 = new TableColumn<>("Потребление за пред. месяц");
        column6.setMinWidth(POTR_COL_WIDTH);
        column6.setMaxWidth(POTR_COL_WIDTH);
        column6.setPrefWidth(POTR_COL_WIDTH);
        _setDoubleComparator(column6);
        column6.setCellValueFactory(cellData -> {
            final SchetchikEntity sch = cellData.getValue();
            ArrayList<PokazaniyaEntity> schPokaz = new ArrayList<>();

            // filter pokazaniya
            for (PokazaniyaEntity pok : allPokaz) {
                if (pok.getSchetchik().equals(sch)) {
                    schPokaz.add(pok);
                }
            }

            // descending sort
            schPokaz.sort((o1, o2) ->
                    o1.getDate() != null ? o2.getDate() != null ? -o1.getDate().compareTo(o2.getDate()) : -1 : 1);

            String res = "";
            if (schPokaz.size() > 1) {
                PokazaniyaEntity pokCurr = schPokaz.get(0);
                PokazaniyaEntity pokPrev = schPokaz.get(1);

                res = Double.toString(pokCurr.getValue() - pokPrev.getValue());
            }


            return new SimpleStringProperty(res);
        });

        table.getColumns().setAll(column1, column5, column2, column3, column4, column6);
        _addDeleteColumn();

        table.getItems().setAll(allSchetchik);
        _setCurrentEntity(SchetchikEntity.class);
    }

    public void addRow() {
        Object obj;

        try {
            obj = currentEntity.newInstance();
            DbHelper.saveOrUpdate(obj);
            table.getItems().add(obj);

            System.out.println(((PokazaniyaEntity) obj).getId());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Невозможно создать строку");
            alert.showAndWait();
        }
    }

    public void exit() {
        System.exit(0);
    }
}
