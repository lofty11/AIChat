package com.springboot.back.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plug")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlugPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "description")
    private String description;

    @Column(name = "available")
    private Integer available;

    @Column(name = "open")
    private Integer open;

    @Column(name = "deleted")
    private Integer deleted;
}
