package com.ecommerceproject1.ecommerce.Entity.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_sequence")
    @SequenceGenerator(name = "wallet_sequence", sequenceName = "wallet_sequence", allocationSize = 1)
    @Column(name = "walletId")
    private Integer walletId;

    @OneToOne
    private UserInfo user;

    @Column(name = "walletTotalAmount")
    private double walletTotalAmount;

    @OneToMany(mappedBy = "wallet", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<WalletHistory> walletHistory;
}

