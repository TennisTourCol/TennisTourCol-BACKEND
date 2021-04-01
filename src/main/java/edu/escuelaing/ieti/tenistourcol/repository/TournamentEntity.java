package edu.escuelaing.ieti.tenistourcol.repository;

import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Date;

@NoArgsConstructor
@Document(collection = "tournament")
public class TournamentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    private String responsible;

    private String direction;

    private String city;

    private String clubSite;

    private String grade;

    private String category;

    private BigInteger price;

    private Date hour;

    private Date startDate;

    private Date finalDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClubSite() {
        return clubSite;
    }

    public void setClubSite(String clubSite) {
        this.clubSite = clubSite;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    @Override
    public String toString() {
        return "TournamentEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", responsible='" + responsible + '\'' +
                ", direction='" + direction + '\'' +
                ", city='" + city + '\'' +
                ", clubSite='" + clubSite + '\'' +
                ", grade='" + grade + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", hour=" + hour +
                ", startDate=" + startDate +
                ", finalDate=" + finalDate +
                '}';
    }
}
