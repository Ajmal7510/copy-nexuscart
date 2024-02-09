package com.ecommerceproject1.ecommerce.model.user;

import lombok.Data;

@Data
public class AddressDTO {
    private String mobile;
    private String address;
    private String state;
    private  String name;
    private String city;
    private String pin;
}
