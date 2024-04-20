package com.springboot.back.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dell
 */
@Entity
@Table(name = "userpara")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserParaPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plug_id")
    private Long plugId;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;
}
