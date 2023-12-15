package com.ecommerceproject1.ecommerce.Entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullName")
    private String name;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "House_No.")
    private String address;

    @Column(name = "locality")
    private String locality;

    @Column(name = "city")
    private String city;

    @Column(name = "pin")
    private String pin;

    public String getFullAddress() {
        return address + ", " + locality + ", " + city + " - " + pin;
    }
}
