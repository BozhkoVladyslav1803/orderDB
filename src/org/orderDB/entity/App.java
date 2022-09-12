package org.orderDB.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {

    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("OrderDataBase");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add some client");
                    System.out.println("2: add some good");
                    System.out.println("3: create some order");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addClient(sc);
                            break;
                        case "2":
                            addGood(sc);
                            break;
                        case "3":
                            createOrder(sc);
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void addClient(Scanner sc) {
        System.out.print("Enter client name: ");
        String name = sc.nextLine();
        System.out.print("Enter numberOfCard: ");
        String numberOfCard = sc.nextLine();

        em.getTransaction().begin();
        try {
            Client client= new Client(name, numberOfCard);
            em.persist(client);
            em.getTransaction().commit();

            System.out.println(client.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void addGood(Scanner sc) {
        System.out.print("Enter name of good: ");
        String name = sc.nextLine();
        System.out.print("Enter good's price: ");
        String sPrice = sc.nextLine();
        double price = Double.parseDouble(sPrice);
        System.out.print("Enter amount of goods: ");
        String sAmount = sc.nextLine();
        int amount =Integer.parseInt(sAmount);
        em.getTransaction().begin();
        try {
            Good good= new Good(name, price,amount);
            em.persist(good);
            em.getTransaction().commit();

            System.out.println(good.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

//    private static void createOrder(Scanner sc) {
//
//        System.out.print("Enter client's id: ");
//        String sClientID = sc.nextLine();
//        Long clientID=Long.parseLong(sClientID);
//
//        System.out.print("Enter good's id: ");
//        String sGoodID = sc.nextLine();
//        Long goodID=Long.parseLong(sGoodID);
//
//        System.out.print("Enter amount of goods: ");
//        String sAmount = sc.nextLine();
//        int amount =Integer.parseInt(sAmount);
//
//        Client client=em.getReference(Client.class, clientID);
//        List<Good> goodList=new ArrayList<>();
//        Good good=em.getReference(Good.class,goodID);
//        good.setAmount(amount);
//        goodList.add(good);
//
//        while(true){
//            System.out.println("Enter new good's id if you wanna to go on creating orders, otherwise - '0'");
//            String sNewGoodID = sc.nextLine();
//            long newGoodID=Long.parseLong(sNewGoodID);
//            if(newGoodID==0){
//                break;
//            }
//
//            System.out.println("Enter amount of goods: ");
//            String sNewAmount = sc.nextLine();
//            amount =Integer.parseInt(sNewAmount);
//            Good anotherGood = em.getReference(Good.class, sNewGoodID);
//            anotherGood.setAmount(amount);
//            goodList.add(anotherGood);
//        }
//
//        em.getTransaction().begin();
//        try {
//
//            Order order= new Order(client, goodList);
//            em.persist(order);
//            em.getTransaction().commit();
//
//            System.out.println(order.getId());
//        } catch (Exception ex) {
//            em.getTransaction().rollback();
//        }
//    }

    public static void createOrder(Scanner sc) {
        System.out.println("Enter client id");
        String clientId = sc.nextLine();
        Long id = Long.parseLong(clientId);

        Client client = em.getReference(Client.class, id);
        if (client == null) {
            System.out.println("No client");
            return;
        }

        String s_good_id;
        Long good_id;
        int temp_amount=0;
        System.out.println("select products for the customer");
        List<Good> goods = new ArrayList<>();
        do {

            System.out.println("Enter product id or 0 to exit");
            s_good_id = sc.nextLine();
            good_id = Long.parseLong(s_good_id);

            System.out.print("Enter amount of the good: ");
            String s_Amount = sc.nextLine();
            int amount =Integer.parseInt(s_Amount);
            if (good_id != 0 || amount!=0) {
                temp_amount=amount;
                Good good = em.getReference(Good.class, good_id);
                good.manageOfAmount(amount);
                goods.add(good);
            }
        } while (good_id != 0);

        em.getTransaction().begin();
        Order order=new Order();
        for (Good good :goods) {
            order.addGoods(client,good,temp_amount);
        }
        try {
            em.persist(order);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }
}

