package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Truck implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate technicalInspectionUntil;
    private int euroStandart;
    private int mileage;
    private int fuelTankCapacity;
    private  String color;
    private int horsePower;
    private int kwPower;
    private String licensePlate;
    private String vin;
    private VehicleStatus currentStatus;
    private String assignedTo;

    public Truck() {

    }

    @Override
    public String toString() {
        return licensePlate;
    }

    public Truck(LocalDate technicalInspectionUntil, int euroStandart, int mileage, int fuelTankCapacity, String color, int horsePower, int kwPower, String licensePlate, String vin, VehicleStatus currentStatus) {
        this.technicalInspectionUntil = technicalInspectionUntil;
        this.euroStandart = euroStandart;
        this.mileage = mileage;
        this.fuelTankCapacity = fuelTankCapacity;
        this.color = color;
        this.horsePower = horsePower;
        this.kwPower = kwPower;
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.currentStatus = currentStatus;
    }

    public int getId() {
        return id;
    }

    public LocalDate getTechnicalInspectionUntil() {
        return technicalInspectionUntil;
    }

    public int getEuroStandard() {
        return euroStandart;
    }

    public int getMileage() {
        return mileage;
    }

    public int getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public String getColor() {
        return color;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public int getKwPower() {
        return kwPower;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public VehicleStatus getCurrentStatus() {
        return currentStatus;
    }

    public String getAssignedTo() {
        return assignedTo;

    }

    public void setTechnicalInspectionUntil(LocalDate technicalInspectionUntil) {
        this.technicalInspectionUntil = technicalInspectionUntil;
    }

    public void setEuroStandart(int euroStandart) {
        this.euroStandart = euroStandart;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setFuelTankCapacity(int fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public void setKwPower(int kwPower) {
        this.kwPower = kwPower;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setCurrentStatus(VehicleStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
