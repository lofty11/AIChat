package com.springboot.back.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "functions")
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
    @Column(name = "ename")
    private String ename;
    @Column(name = "type")
    private String type;
    @Column(name = "api")
    private String api;
    @Column(name = "description")
    private String description;
    @Column(name = "deleted")
    private Integer deleted;
}
