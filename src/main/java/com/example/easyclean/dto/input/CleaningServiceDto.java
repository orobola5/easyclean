package com.example.easyclean.dto.input;
import java.util.Objects;

public class CleaningServiceDto {


    private String serviceName;

    private String description;

    private double price;

    private int duration;

    private String availability;

    public CleaningServiceDto(String serviceName, String description, double price, int duration, String availability) {
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.availability = availability;
    }



    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CleaningServiceDto)) return false;
        CleaningServiceDto that = (CleaningServiceDto) o;
        return Double.compare(that.price, price) == 0 && duration == that.duration && Objects.equals(serviceName, that.serviceName) && Objects.equals(description, that.description) && Objects.equals(availability, that.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, description, price, duration, availability);
    }

    @Override
    public String toString() {
        return "CleaningServiceDto{" +
                "serviceName='" + serviceName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", availability='" + availability + '\'' +
                '}';
    }
}
