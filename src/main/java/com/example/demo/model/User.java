package com.example.demo.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "User_details")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "cnp", length = 100, unique = true)
    private String cnp;

    @Column(name = "series_and_number", length = 100)
    private String seriesAndNumber;

    @Column(name = "address_line1", length = 100)
    private String addressLine1;

    @Column(name = "address_line2", length = 100)
    private String addressLine2;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Column(name = "postal_code", length = 100)
    private String postalCode;

    @Column(name = "locality_id")
    private Integer localityId;

    @Column(name = "county_id")
    private Integer countyId;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "link_CI_photo", length = 200)
    private String linkCIPhoto;

//    @Enumerated(EnumType.STRING)
//    private Role role;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
