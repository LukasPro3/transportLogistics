package fxControllers;

import hibernate.TripHib;
import hibernate.TruckHib;
import hibernate.UserHib;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Driver;
import model.Trip;
import model.Truck;
import model.User;

import javax.persistence.EntityManagerFactory;

import java.sql.Time;
import java.time.LocalTime;

import static model.VehicleStatus.FREE;

public class TripPage {


    public TextField driversIdField;
    public TextField stopLocationField;
    public TextField stopTimeField;
    public TextField destinationField;
    public TextField startField;
    public TextField cargoIdField;
    public Button createButton;

    private EntityManagerFactory entityManagerFactory;
    private Trip selectedTrip;
    private TripHib tripHib;

    public void setData(EntityManagerFactory entityManagerFactory, Trip selectedTrip) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedTrip = selectedTrip;
        this.tripHib = new TripHib(entityManagerFactory);
        fillFields();
    }
    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.tripHib = new TripHib(entityManagerFactory);
    }

    private void fillFields() {
        Trip trip = tripHib.getTripById(selectedTrip.getId());
        //driversIdField.setText(String.valueOf(trip.getDriversId().getId()));
        try {
            driversIdField.setText(String.valueOf(trip.getDriversId().getId()));
        } catch (NullPointerException e) {
        e.printStackTrace();
        }
        startField.setText(trip.getStartPoint());
        stopTimeField.setText(String.valueOf(trip.getStopTime()));
        stopLocationField.setText(trip.getStopLocation());
        destinationField.setText(trip.getDestination());
        cargoIdField.setText(String.valueOf(trip.getCargoId()));
        createButton.setOnAction(actionEvent -> {
            updateTrip(trip);
        });
        createButton.setText("Update");
    }

    private void updateTrip(Trip trip) {
        trip.setDriversId(UserHib.getUserById(Integer.parseInt(driversIdField.getText())));
        trip.setStartPoint(startField.getText());
        trip.setStopLocation(stopLocationField.getText());
        trip.setStopTime(LocalTime.parse(stopTimeField.getText()));
        trip.setDestination(destinationField.getText());
        trip.setCargoId(Integer.parseInt(cargoIdField.getText()));
        tripHib.updateTrip(trip);

    }

    public void createTrip() {
        Trip trip = null;
        trip = new Trip(Integer.parseInt(cargoIdField.getText()), startField.getText(), destinationField.getText(), LocalTime.parse(stopTimeField.getText()), stopLocationField.getText(), UserHib.getUserById(Integer.parseInt(driversIdField.getText())));
        tripHib.createTrip(trip);
    }
}
