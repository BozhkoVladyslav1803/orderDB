package org.orderDB.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "good")
public class Good {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "amount")
    private Integer amount;

    @ManyToMany(mappedBy = "goods", cascade = CascadeType.ALL)
    private List<Order> orders=new ArrayList<>();;

    public Good() {
    }

    public Good(String name,
                Double price,
                Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void manageOfAmount(Integer amount){
        if(this.amount!=0 || this.amount>=amount)
            setAmount(this.amount-amount);
        else
            System.out.println("There are no such good");
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", orders=" + orders +
                '}';
    }
}
