package com.ecommerceproject1.ecommerce.Entity.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WalletHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "walletHistory_sequence")
    @SequenceGenerator(name = "walletHistory_sequence", sequenceName = "walletHistory_sequence", allocationSize = 1)
    @Column(name = "walletHisId")
    private Integer walletHisId;

    @Column(name = "addeddAmount")
    private double addedAmount;

    @Column(name = "amountAddedTime")
    private String amountAddedTime;

    @Column(name = "depositOrWithdraw")
    private String depositOrWithdraw;

    @Column(name = "withdrawAmount")
    private double withdrawAmount;

    @Column(name = "amountWithderalTime")
    private String amountWithdrawTime;

    @ManyToOne
    private Wallet wallet;
}
