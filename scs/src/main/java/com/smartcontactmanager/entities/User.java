package com.smartcontactmanager.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User implements UserDetails {
    @Id
    @Column(name="user_id")
    private String userId;
   
    @Column(name = "user_name")
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String password;
    
    @Column(length = 5000)
    private String about;
    
    @Column(length = 5000)
    private String profilePic;
    
    private String phoneNumber;
    
    @Builder.Default
    private boolean enabled = false;
    
    @Builder.Default
    private boolean emailVerified = false;

    private String emailToken;
    
    @Builder.Default
    private boolean phoneVerified = false;


    @Builder.Default
    @Enumerated (value = EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String providerUserID;
    
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contact = new ArrayList<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    public List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
        
    }

    @Override
    public String getUsername() {
        return this.email;  
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return this.enabled;
    }

    @Override
    public String getPassword(){
        return this.password;
    }
}
