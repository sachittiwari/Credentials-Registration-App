package com.sachit.credentials.registration.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORGANIZATION_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VAT_NUMBER")
    private String vatNumber;

    @Column(name = "SAP_ID")
    private String sapId;




}
