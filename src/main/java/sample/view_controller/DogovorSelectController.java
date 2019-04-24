package sample.view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import sample.Main;
import sample.entity.DogovorEntity;
import sample.entity.UrLicoEntity;
import sample.util.DbHelper;
import sample.util.DogovorEditor;

import java.util.List;

@SuppressWarnings({"unchecked", "Duplicates"})
public class DogovorSelectController {
    @FXML public ListView list;
    @FXML public TextField textField;

    private FilteredList<DogovorEntity> filteredList;

    @FXML
    public void initialize() {
        List<DogovorEntity> allEntities = DbHelper.getAllEntitiesFromTable(DogovorEntity.class);
        ObservableList<DogovorEntity> baseList = FXCollections.observableArrayList(allEntities);
        filteredList = new FilteredList<>(baseList, t -> true);

        list.setItems(filteredList);
        list.setCellFactory(new Callback<ListView<DogovorEntity>, ListCell<DogovorEntity>>() {
            @Override
            public ListCell<DogovorEntity> call(ListView<DogovorEntity> list) {
                return new ListCell<DogovorEntity>() {
                    @Override
                    public void updateItem(DogovorEntity item, boolean empty) {
                        super.updateItem(item, empty);

                        Label l = new Label();
                        if (item != null) l.setText(item.toStringExtended());
                        setGraphic(l);
                    }
                };
            }
        });

        textField.textProperty().addListener((obj, oldVal, newVal) -> filteredList.setPredicate(item -> {
            String filter = newVal.toLowerCase().trim();
            if (filter.isEmpty()) return true;

            UrLicoEntity urLico = item.getUrLico();
            boolean tooShort = filter.length() < 4;

            // TODO: array of string fields
            boolean foundInClientName = urLico != null && urLico.getName().toLowerCase().contains(filter);
            boolean foundInInn = urLico != null && !tooShort && urLico.getInn().toLowerCase().contains(filter);
            boolean foundInKpp = urLico != null && !tooShort && urLico.getKpp().toLowerCase().contains(filter);
            boolean foundInNumber = item.getNomer().toLowerCase().equals(filter);

            return foundInClientName || foundInInn || foundInKpp || foundInNumber;
        }));
    }

    public void save() {
        DogovorEditor de = DogovorEditor.getInstance(Main.class.getResource("../template/dogovor.docx"));

        // TODO: throw exception
        if (de == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ошибка при экспорте");
            alert.showAndWait();
            return;
        }

        de.replace();
    }
}
