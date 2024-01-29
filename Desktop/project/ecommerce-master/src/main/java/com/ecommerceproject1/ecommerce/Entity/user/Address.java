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


    private String name;
    private String mobile;
    private String address;
    private String city;
    private String pin;
    private String state;


   private Boolean isDelete=false;

    @Transient
    public String getFullAddress() {
        return address + ", " + state+ ", " + city + " - " + pin;
    }
}
