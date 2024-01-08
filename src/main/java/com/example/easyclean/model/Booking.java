package com.example.easyclean.model;

import com.example.easyclean.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"firstName","lastName", "email", "phone", "role", "address","createAndUpdateDetails"})
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "booked_by", referencedColumnName = "id")
    private Users bookedBy;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "cleaningService", referencedColumnName = "id")
    @JsonIgnoreProperties({"article", "videos", "createAndUpdateDetails", "module"})
    private CleaningService cleaningService;

    private CreateAndUpdateDetails bookingDate;
    private Status bookingStatus;
}
