package com.ecommerceproject1.ecommerce.Entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wishlist_sequence")
    @SequenceGenerator(name = "wishlist_sequence", sequenceName = "wishlist_sequence", allocationSize = 1)
    @Column(name = "wishlistId")
    private Long id;
    @OneToOne
    @JoinColumn(name = "wishlist_user_id" )
    private UserInfo user;
    @OneToMany(mappedBy = "wishlist", cascade = {CascadeType.ALL},orphanRemoval = true)
    private List<WishlistItems> wishlistItems;
}
