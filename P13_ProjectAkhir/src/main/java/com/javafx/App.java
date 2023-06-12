package com.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {
    TabelBahan tabel = new TabelBahan();
    FileTeks file = new FileTeks(
            "D:\\Kuliah\\Semester 2\\Algoritma dan Struktur Data\\P09_TableView_72220533\\P09_VSC_TableView\\src\\main\\resources\\com\\example\\daftarBahan.csv");
    FilteredList<BahanKimia> filteredData;
    TextField txtIdBahan = new TextField();
    TextField txtNama = new TextField();
    TextField txtVolume = new TextField();
    TextField txtBentuk = new TextField();
    TextField txtSifatZat = new TextField();
    TextField txtTanggalTerima = new TextField();
    Label lblIdBahan = new Label("ID Bahan");
    Label lblNama = new Label("Nama");
    Label lblVolume = new Label("Volume");
    Label lblBentuk = new Label("Bentuk");
    Label lblSifatZat = new Label("Sifat Zat");
    Label lblTanggalTerima = new Label("Tanggal Terima");
    Label lblOrder = new Label("Order by");
    VBox vb = new VBox(5);
    ComboBox<String> comboBentuk = new ComboBox<>();
    ComboBox<String> comboQuickSort = new ComboBox<>();
    Button btnNewSave = new Button("_New");
    Button btnEditUndo = new Button("_Update");
    Button btnDelete = new Button("_Delete");
    Button btnClose = new Button("_Close");
    Button btnFind = new Button("_Find");
    Button btnFilter = new Button("_Filter");
    Button btnQuickSort = new Button("_Quick Sort");
    Pane reg = new Pane();
    GridPane grid = new GridPane();
    HBox buttonBox = new HBox();
    HBox orderBox = new HBox();
    Boolean baru = false;

    void editUndo() {
        if (btnEditUndo.getText().equals("_Edit")) {
            baru = false;
            open();
        } else {
            show();
            close();
        }
    }

    void close() {
        txtIdBahan.setEditable(false);
        txtNama.setEditable(false);
        txtSifatZat.setEditable(false);
        txtVolume.setEditable(false);
        txtBentuk.setDisable(false);
        txtTanggalTerima.setDisable(false);
        btnNewSave.setText("_New");
        btnEditUndo.setText("_Edit");
        btnDelete.setDisable(false);
        btnClose.setDisable(false);
    }

    void open() {
        txtIdBahan.setEditable(true);
        txtNama.setEditable(true);
        txtSifatZat.setEditable(true);
        txtVolume.setEditable(true);
        txtBentuk.setDisable(true);
        txtTanggalTerima.setEditable(true);
        btnNewSave.setText("_Save");
        btnEditUndo.setText("_Undo");
        btnDelete.setDisable(true);
        btnClose.setDisable(true);
        txtIdBahan.requestFocus();
    }

    void show() {
        BahanKimia bhn = tabel.getBahanKimia();
        txtIdBahan.setText(bhn.getId_bahan_kimia());
        txtNama.setText(bhn.getNama());
        txtVolume.setText(bhn.getVolume());
        txtSifatZat.setText(bhn.getSifat_zat());
        txtBentuk.setText(bhn.getBentuk());
        txtTanggalTerima.setText(bhn.getTanggal_terima());
    }

    void newSave() throws IOException {
        String id_bahan, nama, volume, bentuk, sifat_zat, tanggal_terima;
        id_bahan = txtIdBahan.getText();
        nama = txtNama.getText();
        volume = txtVolume.getText();
        bentuk = comboBentuk.getValue();
        sifat_zat = txtSifatZat.getText();
        tanggal_terima = txtTanggalTerima.getText();
        BahanKimia bahanKimia = new BahanKimia(id_bahan, nama, volume, bentuk, sifat_zat, tanggal_terima);

        if (btnNewSave.getText().equals("_New")) {
            baru = true;
            clearField();
            open();
        } else {
            if (baru) {
                if (id_bahan.equals("") || volume.equals("") || bentuk.equals("") || sifat_zat.equals("")
                        || tanggal_terima.equals("")) {
                    Utilities.warningAlert("WARNING", "Data anda masih ada yang kosong!", "Lengkapi data lebih dulu");
                } else {
                    tabel.addBahan(bahanKimia);
                }
            } else {
                tabel.updateBahan(bahanKimia);
            }
            close();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        tabel.setOnMouseClicked(e -> show());
        inisialisasiKontrol();
        close();
        filteredData = new FilteredList<>(tabel.getItems(), b -> true);
        tabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.show();
        btnNewSave.setOnAction(e -> {
            try {
                newSave();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        btnDelete.setOnAction(e->{
            try {
                delete();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        btnEditUndo.setOnAction(e->editUndo());
        btnClose.setOnAction(e -> {
            try {
                save();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.close();
        });
        btnFilter.setOnAction(e -> dialogBoxFilter());
        btnFind.setOnAction(e -> cari());
        btnQuickSort.setOnAction(e -> tabel.quikSortRekursif(comboQuickSort.getValue()));
        stage.setScene(new Scene(vb, 600, 600));
        stage.setTitle("Inventarisasi Bahan Kimia");
        stage.show();
    }

    public void clearField() {
        txtIdBahan.clear();
        txtNama.clear();
        txtVolume.clear();
        txtBentuk.clear();
        txtSifatZat.clear();
        txtTanggalTerima.clear();
    }

    private void inisialisasiKontrol() {
        txtIdBahan.setMinWidth(40);
        txtIdBahan.setMaxWidth(60);
        txtIdBahan.setPromptText("ID");
        txtNama.setMinWidth(245);
        txtNama.setMaxWidth(600);
        txtNama.setPromptText("Nama Bahan Kimia");
        txtVolume.setMinWidth(30);
        txtVolume.setMaxWidth(400);
        txtVolume.setPromptText("Volume");
        comboBentuk.getItems().addAll("Liquid", "Gas", "Solid");
        comboBentuk.setMinWidth(80);
        comboBentuk.setMaxWidth(120);
        txtSifatZat.setMinWidth(150);
        txtSifatZat.setMaxWidth(600);
        txtSifatZat.setPromptText("Sifat Zat");
        txtTanggalTerima.setMinWidth(130);
        txtTanggalTerima.setMaxWidth(600);
        txtTanggalTerima.setPromptText("Tanggal Terima");
        comboBentuk.setValue("Liquid");
        btnNewSave.setMinWidth(60);
        btnEditUndo.setMinWidth(60);
        btnDelete.setMinWidth(60);
        btnFilter.setMinWidth(60);
        btnFind.setMinWidth(60);
        btnClose.setMinWidth(60);
        comboQuickSort.getItems().addAll("ID Bahan", "Nama Bahan", "Volume", "Sifat Zat", "Bentuk");
        comboQuickSort.setMinWidth(60);
        comboQuickSort.setValue("ID Bahan");
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(15);
        grid.setVgap(5);
        grid.add(lblIdBahan, 0, 0);
        grid.add(txtIdBahan, 1, 0, 3, 1);
        grid.add(lblNama, 0, 1);
        grid.add(txtNama, 1, 1, 3, 1);
        grid.add(lblVolume, 0, 2);
        grid.add(txtVolume, 1, 2);
        grid.add(lblBentuk, 2, 2);
        grid.add(comboBentuk, 3, 2);
        buttonBox.getChildren().addAll(btnNewSave, btnEditUndo, btnDelete, btnFind, btnFilter, reg, btnClose);
        grid.add(lblSifatZat, 0, 3);
        grid.add(txtSifatZat, 1, 3, 3, 1);
        grid.add(lblTanggalTerima, 0, 4);
        grid.add(txtTanggalTerima, 1, 4, 3, 1);
        orderBox.getChildren().addAll(comboQuickSort, btnQuickSort);
        orderBox.setSpacing(5);
        grid.add(lblOrder, 0, 5);
        grid.add(orderBox, 1, 5, 2, 1);
        grid.add(buttonBox, 0, 6, 4, 1);
        HBox.setHgrow(btnNewSave, Priority.ALWAYS);
        HBox.setHgrow(btnEditUndo, Priority.ALWAYS);
        HBox.setHgrow(btnDelete, Priority.ALWAYS);
        HBox.setHgrow(btnFind, Priority.ALWAYS);
        HBox.setHgrow(btnClose, Priority.ALWAYS);
        HBox.setHgrow(reg, Priority.ALWAYS);
        buttonBox.setSpacing(7);
        GridPane.setHgrow(reg, Priority.ALWAYS);
        GridPane.setHgrow(txtIdBahan, Priority.ALWAYS);
        GridPane.setHgrow(txtNama, Priority.ALWAYS);
        GridPane.setHgrow(txtVolume, Priority.ALWAYS);
        GridPane.setHgrow(comboBentuk, Priority.ALWAYS);
        GridPane.setHgrow(txtSifatZat, Priority.ALWAYS);
        GridPane.setHgrow(txtTanggalTerima, Priority.ALWAYS);
        vb.getChildren().addAll(grid, tabel);
        vb.setPadding(new Insets(5));
    }

    private void delete() throws IOException {
        tabel.deleteBahan();
        show();
    }

    String dialogBoxFind() {
        Label lblId = new Label("Kunci Pencarian");
        ComboBox<String> comboAtribut = new ComboBox<>();
        comboAtribut.getItems().addAll("ID Bahan", "Nama Bahan", "Sifat Zat", "Tanggal Terima");
        comboAtribut.setValue("ID Bahan");
        TextField txtCari = new TextField();
        txtCari.setMaxWidth(200);
        txtCari.setPromptText("Masukkan kata kunci");
        Button btnCari = new Button("_Find");
        HBox hb = new HBox(10, lblId, comboAtribut, txtCari, btnCari);
        hb.setPadding(new Insets(15, 10, 10, 10));
        Stage window = new Stage();
        window.setTitle("Find");
        window.setScene(new Scene(hb));
        window.initModality(Modality.APPLICATION_MODAL);
        btnCari.setOnAction(e -> window.close());
        window.showAndWait();
        String keyfind = comboAtribut.getValue() + "," + txtCari.getText();
        return keyfind;
    }

    void save() throws IOException {
        try {
            if (readAllElement().equals("")) {
                file.write("");
            }
            file.write(readAllElement());
        } catch (Exception r) {
            System.err.println(r.getStackTrace());
        }
        Utilities.informationAlert("SAVE", "Simpan Berhasil!", "Data anda telah berhasil disimpan.");
    }

    private String readAllElement() {
        ObservableList<BahanKimia> semua = tabel.getItems();
        String id, nama, volume, bentuk, sifat, tgl_terima, readString = "";
        for (BahanKimia bahan : semua) {
            id = bahan.getId_bahan_kimia();
            nama = bahan.getNama();
            volume = bahan.getVolume();
            bentuk = bahan.getBentuk();
            sifat = bahan.getSifat_zat();
            tgl_terima = bahan.getTanggal_terima();
            readString += id + "," + nama + "," + volume + "," + bentuk + "," + sifat + "," + tgl_terima + "\n";
        }
        return readString;
    }

    void scrollTo(int idx, int cacah, String keyword) {
        if (idx < cacah) {
            tabel.getSelectionModel().select(idx);
            tabel.scrollTo(idx);
            tabel.requestFocus();
            Utilities.informationAlert("PENCARIAN", "Data Ditemukan!", "");
        } else {
            Utilities.warningAlert("PENCARIAN", keyword + "Tidak Ditemukan!", "");
        }
    }

    void cari() {
        String[] keyFind = dialogBoxFind().split(",");
        if (keyFind.length==2) {
            String category = keyFind[0], keyword = keyFind[1];
            ObservableList<BahanKimia> daftarBahan = tabel.getItems();
            int idx, cacah = daftarBahan.size();
            switch (category) {
                case "ID Bahan":
                    for (idx = 0; idx < cacah; idx++) {
                        if (daftarBahan.get(idx).getId_bahan_kimia().equalsIgnoreCase(keyword)) {
                            break;
                        }
                    }
                    scrollTo(idx, cacah, keyword);
                    break;
                case "Nama Bahan":
                    for (idx = 0; idx < cacah; idx++) {
                        if (daftarBahan.get(idx).getNama().equalsIgnoreCase(keyword)) {
                            break;
                        }
                    }
                    scrollTo(idx, cacah, keyword);
                    break;
                case "Sifat Zat":
                    for (idx = 0; idx < cacah; idx++) {
                        if (daftarBahan.get(idx).getSifat_zat().equalsIgnoreCase(keyword)) {
                            break;
                        }
                    }
                    scrollTo(idx, cacah, keyword);
                    break;
                case "Tanggal Terima":
                    for (idx = 0; idx < cacah; idx++) {
                        if (daftarBahan.get(idx).getTanggal_terima().equalsIgnoreCase(keyword)) {
                            break;
                        }
                    }
                    scrollTo(idx, cacah, keyword);
                    break;
                case "Bentuk":
                    for (idx = 0; idx < cacah; idx++) {
                        if (daftarBahan.get(idx).getBentuk().equalsIgnoreCase(keyword)) {
                            break;
                        }
                    }
                    scrollTo(idx, cacah, keyword);
                    break;
                default:
                    break;
            }
        }else{
            Utilities.warningAlert("Pencarian", "Data Kosong", "Kata kunci pencarian anda masih kosong!");
        }
    }

    void dialogBoxFilter() {
        TextField txtFilter = new TextField();
        TabelBahan filteredView = new TabelBahan();
        txtFilter.setMinWidth(275);
        txtFilter.setPromptText("Keywords");
        Button btnClose = new Button("Close");
        HBox hb = new HBox(5, txtFilter, btnClose);
        VBox vb = new VBox(5, hb, filteredView);
        hb.setPadding(new Insets(15, 10, 10, 10));
        filteredView.setOnMouseClicked(e -> show());
        Stage window = new Stage();
        window.setScene(new Scene(vb));
        window.initModality(Modality.APPLICATION_MODAL);
        btnClose.setOnAction(e -> window.close());
        FilteredList<BahanKimia> dataTersaring = new FilteredList<>(tabel.getItems(), b -> true);
        txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            dataTersaring.setPredicate(bahanKimia -> {
                String id = bahanKimia.getId_bahan_kimia().toLowerCase();
                String nama = bahanKimia.getNama().toLowerCase();
                String bentuk = bahanKimia.getBentuk().toLowerCase();
                String sifat = bahanKimia.getSifat_zat().toLowerCase();
                String volume = bahanKimia.getVolume().toLowerCase();
                String tanggal = bahanKimia.getTanggal_terima().toLowerCase();
                if (id.contains(newValue.toLowerCase())||nama.contains(newValue.toLowerCase())||bentuk.contains(newValue.toLowerCase())
                    ||sifat.contains(newValue.toLowerCase())||volume.contains(newValue.toLowerCase())||tanggal.contains(newValue.toLowerCase())) {
                    return true;
                } else {
                    return false;
                }
            });
            filteredView.setColoumn();
            filteredView.setItems(dataTersaring);
        });

        filteredView.setOnKeyPressed((event)->{
            if(event.getCode().equals(KeyCode.ENTER)){
                String id = filteredView.getItems().get(filteredView.getSelectionModel().getSelectedIndex()).getId_bahan_kimia();
                int idx;
                for(idx=0;idx<tabel.getItems().size();idx++){
                    if(tabel.getItems().get(idx).getId_bahan_kimia().equals(id)){
                        break;
                    }
                }
                tabel.scrollTo(idx);
                tabel.getSelectionModel().select(idx);
                window.close();
                tabel.requestFocus();
            }
        });
        window.setTitle("Tulis ID/Nama/Volume/Bentuk/Sifat Zat/Tanggal Terima");
        window.setX(500);
        window.setY(100);
        window.showAndWait();
        dataTersaring.setPredicate(t -> true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
