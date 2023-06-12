package com.javafx;

import java.util.Collections;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabelBahan extends TableView<BahanKimia> {
    FileTeks file = new FileTeks(
            "D:\\Kuliah\\Semester 2\\Algoritma dan Struktur Data\\P09_TableView_72220533\\P09_VSC_TableView\\src\\main\\resources\\com\\example\\daftarBahan.csv");
    private int recordPointer;

    public TabelBahan() {
        setColoumn();
        openFile();
        this.setOnMousePressed(e -> this.recordPointer = this.getSelectionModel().getSelectedIndex());
    }

    public BahanKimia getBahanKimia() {
        return this.getItems().get(recordPointer);
    }

    void setColoumn() {
        // kolom kode
        TableColumn<BahanKimia, String> kolIdBahanKimia = new TableColumn<BahanKimia, String>("ID");
        kolIdBahanKimia.setCellValueFactory(new PropertyValueFactory<>("id_bahan_kimia"));
        kolIdBahanKimia.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        kolIdBahanKimia.setStyle("-fx-alignment:center");
        // kolom nama bahan
        TableColumn<BahanKimia, String> kolNamaBahan = new TableColumn<BahanKimia, String>("Nama Bahan");
        kolNamaBahan.setCellValueFactory(new PropertyValueFactory<BahanKimia, String>("nama"));
        kolNamaBahan.setMaxWidth(1f * Integer.MAX_VALUE * 35);
        // kolom Volume
        TableColumn<BahanKimia, String> kolVolume = new TableColumn<BahanKimia, String>("Volume");
        kolVolume.setCellValueFactory(new PropertyValueFactory<BahanKimia, String>("volume"));
        kolVolume.setMaxWidth(1f * Integer.MAX_VALUE * 10);
        // kolom bentuk
        TableColumn<BahanKimia, String> kolBentuk = new TableColumn<BahanKimia, String>("Bentuk");
        kolBentuk.setCellValueFactory(new PropertyValueFactory<BahanKimia, String>("bentuk"));
        kolBentuk.setMaxWidth(1f * Integer.MAX_VALUE * 10);
        kolBentuk.setStyle("-fx-alignment:center");
        // kolom tingkat bahaya
        TableColumn<BahanKimia, String> kolSifatZat = new TableColumn<BahanKimia, String>("Sifat Zat");
        kolSifatZat.setCellValueFactory(new PropertyValueFactory<BahanKimia, String>("sifat_zat"));
        kolSifatZat.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        // kolom tanggal terima
        TableColumn<BahanKimia, String> kolTanggalTerima = new TableColumn<BahanKimia, String>("Tanggal Terima");
        kolTanggalTerima.setCellValueFactory(new PropertyValueFactory<BahanKimia, String>("tanggal_terima"));
        kolTanggalTerima.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        kolTanggalTerima.setStyle("-fx-alignment:center");
        // add ke tabel
        this.getColumns().addAll(kolIdBahanKimia, kolNamaBahan, kolVolume, kolBentuk, kolSifatZat, kolTanggalTerima);
    }

    private void openFile() {
        try {
            String[] array_csv = this.file.read();
            String[] row;
            String id, nama, volume, bentuk, sifat, tgl_terima;
            if (!array_csv[0].equalsIgnoreCase("")) {
                for (int i = 0; i < array_csv.length; i++) {
                    row = array_csv[i].split(",");
                    id = row[0];
                    nama = row[1];
                    volume = row[2];
                    bentuk = row[3];
                    sifat = row[4];
                    tgl_terima = row[5];
                    this.getItems().add(new BahanKimia(id, nama, volume, bentuk, sifat, tgl_terima));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void addBahan(BahanKimia bahan) {
        ObservableList<BahanKimia> daftarBahan = this.getItems();
        int idx, cacah = daftarBahan.size();
        for (idx = 0; idx < cacah; idx++) {
            if (Integer.parseInt(daftarBahan.get(idx).getId_bahan_kimia()) > Integer.parseInt(bahan.getId_bahan_kimia())) {
                this.getItems().add(idx, bahan);
                break;
            }
            if (Integer.parseInt(daftarBahan.get(idx).getId_bahan_kimia()) < Integer.parseInt(bahan.getId_bahan_kimia())) {
                this.getItems().add(bahan);
                break;
            }
        }
    }

    public void updateBahan(BahanKimia bahan) {
        this.getItems().set(recordPointer, bahan);
    }

    public void deleteBahan() {
        ObservableList<BahanKimia> pilih, semua;
        semua = this.getItems();
        pilih = this.getSelectionModel().getSelectedItems();
        pilih.forEach(semua::remove);
    }

    private void quickSort(int kiri, int kanan, String orderby) {
        int i, j, pivot;
        ObservableList<BahanKimia> tableQuick = this.getItems();
        i = kiri;
        j = kanan;
        pivot = i;
        while (i < j) {
            if (orderby.equals("ID Bahan")) {
                while (tableQuick.get(j).getId_bahan_kimia().compareTo(tableQuick.get(pivot).getId_bahan_kimia()) >= 0 && j > pivot) {
                    j--;
                }
                pivot = j;
                Collections.swap(tableQuick, i, j);
                while (tableQuick.get(i).getId_bahan_kimia().compareTo(tableQuick.get(pivot).getId_bahan_kimia()) < 0 && i < pivot) {
                    i++;
                }
                pivot = i;
                Collections.swap(tableQuick, i, j);
            } else if (orderby.equals("Nama Bahan")) {
                while (tableQuick.get(j).getNama().compareTo(tableQuick.get(pivot).getNama()) >= 0 && j > pivot) {
                    j--;
                }
                pivot = j;
                Collections.swap(tableQuick, i, j);
                while (tableQuick.get(i).getNama().compareTo(tableQuick.get(pivot).getNama()) < 0 && i < pivot) {
                    i++;
                }
                pivot = i;
                Collections.swap(tableQuick, i, j);
            } else if (orderby.equals("Volume")) {
                while (tableQuick.get(j).getVolume().compareTo(tableQuick.get(pivot).getVolume()) >= 0 && j > pivot) {
                    j--;
                }
                pivot = j;
                Collections.swap(tableQuick, i, j);
                while (tableQuick.get(i).getVolume().compareTo(tableQuick.get(pivot).getVolume()) < 0 && i < pivot) {
                    i++;
                }
                pivot = i;
                Collections.swap(tableQuick, i, j);
            } else if (orderby.equals("Sifat Zat")) {
                while (tableQuick.get(j).getSifat_zat().compareTo(tableQuick.get(pivot).getSifat_zat()) >= 0 && j > pivot) {
                    j--;
                }
                pivot = j;
                Collections.swap(tableQuick, i, j);
                while (tableQuick.get(i).getSifat_zat().compareTo(tableQuick.get(pivot).getSifat_zat()) < 0 && i < pivot) {
                    i++;
                }
                pivot = i;
                Collections.swap(tableQuick, i, j);
            } else {
                while (tableQuick.get(j).getBentuk().compareTo(tableQuick.get(pivot).getBentuk()) >= 0 && j > pivot) {
                    j--;
                }
                pivot = j;
                Collections.swap(tableQuick, i, j);
                while (tableQuick.get(i).getBentuk().compareTo(tableQuick.get(pivot).getBentuk()) < 0 && i < pivot) {
                    i++;
                }
                pivot = i;
                Collections.swap(tableQuick, i, j);
            }
        }
        if (kiri < pivot - 1) {
            quickSort(kiri, pivot - 1, orderby);
        }
        if (pivot + 1 < kanan) {
            quickSort(pivot + 1, kanan,orderby);
        }
    }

    public void quikSortRekursif(String option) {
        int n = this.getItems().size();
        if (option.equals("ID Bahan")) {
            quickSort(0, n - 1, option);
        } else if (option.equals("Nama Bahan")) {
            quickSort(0, n - 1, option);
        } else if (option.equals("Volume")) {
            quickSort(0, n - 1, option);
        } else if (option.equals("Sifat Zat")) {
            quickSort(0, n - 1, option);
        } else if (option.equals("Bentuk")) {
            quickSort(0, n - 1, option);
        } 
    }
}
