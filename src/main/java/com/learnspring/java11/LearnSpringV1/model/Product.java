package com.learnspring.java11.LearnSpringV1.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="tbProduct")
public class Product {

    @Id
    //@GeneratedValue use create id auto increment
    // use "sequence" de thiet lap qui tac
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1 // tang 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private long id;
    @Column(nullable = false, unique = true, length = 300)
    private String name;
    @Column(length = 300)
    private String description;
    private String url;

    public Product(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public Product() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id
                && Objects.equals(name, product.name)
                && Objects.equals(description, product.description)
                && Objects.equals(url, product.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, url);
    }
}
