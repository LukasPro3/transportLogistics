package fxControllers;

import hibernate.CargoHib;
import hibernate.TripHib;
import hibernate.TruckHib;
import hibernate.UserHib;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.awt.*;
import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    public ListView<Driver> driverList;
    public Tab driversTab;
    public ListView<Truck> freeTruckList;
    public ListView<Truck> assignedTruckList;
    public TableView cargoTable;
    public TableColumn<CargoTableParameters,String>  columnAmount;
    public TableColumn<CargoTableParameters,String> columnPrice;
    public TableColumn<CargoTableParameters,String> columnCollection;
    public TableColumn<CargoTableParameters,String> columnDelivery;
    public TableColumn<CargoTableParameters, String> columnStatus;
    public TableColumn<CargoTableParameters,String> columnId;
    public ListView<Trip> tripList;
    @FXML
    private AnchorPane ap;

    private ObservableList<CargoTableParameters> data = FXCollections.observableArrayList();

    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;
    private User user;
    private EntityManager entityManager;
    private TruckHib truckHib;
    private CargoHib cargoHib;
    private TripHib tripHib;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.userHib = new UserHib(entityManagerFactory);
        this.truckHib = new TruckHib(entityManagerFactory);
        this.cargoHib = new CargoHib(entityManagerFactory);
        this.tripHib = new TripHib(entityManagerFactory);
        this.user = user;
        this.entityManager= entityManagerFactory.createEntityManager();
        fillAllLists();
        disableData();
    }
    private void disableData(){
        if (user.getClass() == Driver.class){
            driversTab.setDisable(true);
        }
        else{

        }
    }

    private void fillAllLists() {
        List<Driver> allDrivers= userHib.getAllDrivers();
        allDrivers.forEach(d->driverList.getItems().add(d));
        List<Truck> freeTrucks = truckHib.getFreeTrucks();
        freeTrucks.forEach(f->freeTruckList.getItems().add(f));
        List<Cargo> allCargo = cargoHib.getAllCargo();
        for(Cargo c:allCargo) {
            CargoTableParameters cargoTableParameters = new CargoTableParameters();
            cargoTableParameters.setCargoId(String.valueOf(c.getCargoId()));
            cargoTableParameters.setCargoAmount(String.valueOf(c.getAmount()));
            cargoTableParameters.setCargoCollection(c.getCollectionPoint());
            cargoTableParameters.setCargoDelivery(c.getDeliveryPoint());
            cargoTableParameters.setCargoStatus(String.valueOf(c.getCurrentStatus()));
            cargoTableParameters.setCargoPrice(String.valueOf(c.getPrice()));
            data.add(cargoTableParameters);
        }
        cargoTable.setItems(data);
        List<Trip> allTrips = tripHib.getAllTrips();
        allTrips.forEach(t->tripList.getItems().add(t));

    }

    public void logOut() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/login-page.fxml"));
        Parent parent = fxmlLoader.load();
        LoginPage loginPage = fxmlLoader.getController();

        Scene scene = new Scene(parent);
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.show();
    }

    public void showDetails() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/driverDetails-page.fxml"));
        Parent parent = fxmlLoader.load();
        DriverDetailsPage driverDetailsPage = fxmlLoader.getController();
        driverDetailsPage.setData(entityManagerFactory, user,driverList.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(driverList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void updateDriver() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/register-page.fxml"));
        Parent parent = fxmlLoader.load();
        RegisterPage registerPage = fxmlLoader.getController();
        registerPage.setData(entityManagerFactory, user,driverList.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(driverList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void deleteDriver() {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirm to delete this driver?");
        a.show();
        User selectedDriver = driverList.getSelectionModel().getSelectedItem();
        UserHib.deleteUser(selectedDriver.getId());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargoTable.setEditable(true);
        columnId.setCellValueFactory(new PropertyValueFactory<>("cargoId"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("cargoAmount"));
        columnAmount.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAmount.setOnEditCommit(t-> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCargoAmount(t.getNewValue());
                    Cargo cargo = cargoHib.getCargoById(Integer.parseInt(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoId()));
            cargo.setAmount(Double.parseDouble(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoAmount()));
                    cargoHib.updateCargo(cargo);
                }
        );
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("cargoPrice"));
        columnPrice.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPrice.setOnEditCommit(t-> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCargoPrice(t.getNewValue());
                    Cargo cargo = cargoHib.getCargoById(Integer.parseInt(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoId()));
                    cargo.setPrice(Double.parseDouble(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoPrice()));
                    cargoHib.updateCargo(cargo);
                }
        );

        columnCollection.setCellValueFactory(new PropertyValueFactory<>("cargoCollection"));
        columnCollection.setCellFactory(TextFieldTableCell.forTableColumn());
        columnCollection.setOnEditCommit(t-> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCargoCollection(t.getNewValue());
                    Cargo cargo = cargoHib.getCargoById(Integer.parseInt(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoId()));
                    cargo.setCollectionPoint(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoCollection());
                    cargoHib.updateCargo(cargo);
                }
        );

        columnDelivery.setCellValueFactory(new PropertyValueFactory<>("cargoDelivery"));
        columnDelivery.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDelivery.setOnEditCommit(t-> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCargoDelivery(t.getNewValue());
                    Cargo cargo = cargoHib.getCargoById(Integer.parseInt(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoId()));
                    cargo.setDeliveryPoint(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoDelivery());
                    cargoHib.updateCargo(cargo);
                }
        );
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("cargoStatus"));
        columnStatus.setCellFactory(TextFieldTableCell.forTableColumn());
        columnStatus.setOnEditCommit(t-> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setCargoStatus(t.getNewValue());
                    Cargo cargo = cargoHib.getCargoById(Integer.parseInt(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoId()));
                    cargo.setCurrentStatus(CargoStatus.valueOf(t.getTableView().getItems().get(t.getTablePosition().getRow()).getCargoStatus()));
                    cargoHib.updateCargo(cargo);
                }
        );

    }

    public void assignDriverToTruck() {
    }


    public void viewTruckDetails() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/truck-page.fxml"));
        Parent parent = fxmlLoader.load();
        TruckPage truckPage = fxmlLoader.getController();
        truckPage.setData(entityManagerFactory,freeTruckList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(freeTruckList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void createTruck() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/truck-page.fxml"));
        Parent parent = fxmlLoader.load();
        TruckPage truckPage = fxmlLoader.getController();
        truckPage.setData(entityManagerFactory);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(freeTruckList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void deleteTruck() {
        Truck selectedTruck = freeTruckList.getSelectionModel().getSelectedItem();
        truckHib.deleteTruck(selectedTruck.getId());
    }

    public void editTruck() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/truck-page.fxml"));
        Parent parent = fxmlLoader.load();
        TruckPage truckPage = fxmlLoader.getController();
        truckPage.setData(entityManagerFactory,freeTruckList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(freeTruckList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();

    }

    public void createTrip() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPage.class.getResource("/view/trip-page.fxml"));
        Parent parent = fxmlLoader.load();
        TripPage tripPage = fxmlLoader.getController();
        tripPage.setData(entityManagerFactory);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(tripList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();

    }

    public void editTrip() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("/view/trip-page.fxml"));
        Parent parent = fxmlLoader.load();
        TripPage tripPage = fxmlLoader.getController();
        tripPage.setData(entityManagerFactory,tripList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(tripList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void deleteTrip() {
        Trip selectedTrip = tripList.getSelectionModel().getSelectedItem();
        tripHib.deleteTrip(selectedTrip.getId());
    }

    public void createCargo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPage.class.getResource("/view/cargo-page.fxml"));
        Parent parent = fxmlLoader.load();
        CargoPage cargoPage = fxmlLoader.getController();
        cargoPage.setData(entityManagerFactory);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(cargoTable.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TransportLogistics");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void deleteCargo() {
    }
}
