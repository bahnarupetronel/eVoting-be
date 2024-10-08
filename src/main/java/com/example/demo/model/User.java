package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "User_details")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "county", length = 100)
    private String county;

    @Column(name = "locality", length = 100)
    private String locality;

    @Column(name = "locality_id")
    private Long localityId;

    @JsonIgnore
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

    @Column(name = "is_email_confirmed", columnDefinition = "boolean default false")
    private Boolean isEmailConfirmed;

    @Column(name = "is_identity_verified", columnDefinition = "boolean default false")
    private Boolean isIdentityVerified;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return false;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Transient
    @JsonIgnore
    private ChangePasswordToken changePasswordToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Transient
    @JsonIgnore
    private ConfirmEmailToken confirmEmailToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Transient
    @JsonIgnore
    private StripeSession stripeSession;

}
