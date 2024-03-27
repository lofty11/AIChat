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
@Table(name = "function")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FunctionPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "e_name")
    private String e_name;

    @Column(name = "type")
    private String type;

    @Column(name = "api")
    private String api;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted")
    private Integer deleted;
}
