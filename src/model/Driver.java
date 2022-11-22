package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Driver extends User implements Serializable {
    private String medicalSertificate;
    private String driversLicense;

    @OneToOne
    private Truck assignedTruckId;

    @OneToMany(mappedBy = "driversId")
    private List<Trip> trips;

    @OneToMany(mappedBy = "assignedTo")
    private List<Cargo> cargoList;

    public Driver(String firstName, String lastName, String phoneNumber, String email, LocalDate birthDate, String login, String password, UserRole driver, boolean isAdmin) {
        super(firstName, lastName, phoneNumber, email, birthDate, login, password,UserRole.DRIVER, isAdmin);
    }

    public Driver(String medicalSertificate, String driversLicense, Truck assignedTruckId, List<Trip> trips, List<Cargo> cargoList) {
        this.medicalSertificate = medicalSertificate;
        this.driversLicense = driversLicense;
        this.assignedTruckId = assignedTruckId;
        this.trips = trips;
        this.cargoList = cargoList;
    }

    public Driver() {
    }

}
