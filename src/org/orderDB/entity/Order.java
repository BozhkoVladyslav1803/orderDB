package org.orderDB.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "series")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "order_good",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "good_id"))
    private Set<Good> goods = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "sumOfGoodsPrice")
    private Double sumOfGoodsPrice;

    public Order() {

    }

    public void addGoods(Client client, Good good, int temp_amount) {
        goods.add(good);
        client.getOrders().add(this);
        this.setClient(client);
        good.getOrders().add(this);
        calculateSumOfGoodsPrice(temp_amount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Good> getGoods() {
        return goods;
    }

    public void setGoods(Set<Good> goods) {
        this.goods = goods;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getSumOfGoodsPrice() {
        return sumOfGoodsPrice;
    }

    private void setSumOfGoodsPrice(Double sumOfGoodsPrice) {
        this.sumOfGoodsPrice = sumOfGoodsPrice;
    }

    public void calculateSumOfGoodsPrice(int temp_amount) {
        double sum=0;
        for (Good good : goods) {
            sum += good.getPrice() * temp_amount;
        }
        setSumOfGoodsPrice(sum);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", goods=" + goods +
                ", client=" + client +
                ", sumOfGoodsPrice=" + sumOfGoodsPrice +
                '}';
    }
}
