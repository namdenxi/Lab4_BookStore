package com.example.demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Không được bỏ trống")
    private int id;

    @Column(name = "title")
    @Size(min = 1, max = 12, message = "Title từ 1 đến 12 kí tự")
    private String title;

    @Column(name = "cover")
    private String cover;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
