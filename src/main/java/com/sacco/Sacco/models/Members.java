package com.sacco.Sacco.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String idNo;
    private String kraPin;
    private String phoneOne;
    private String phoneTwo;
    private String email;
    private String userName;
    private String occupation;
    private String dob;
    private String county;
    private String constituency;
    private String ward;
    private String address;
    private String gender;
    private String position;
    private String kinOne;
    private String kinOnePhone;
    private String relationshipOne;
    private String kinTwo;
    private String kinTwoPhone;
    private String relationshipTwo;
    private String portImage;
    private String formImage;


}
