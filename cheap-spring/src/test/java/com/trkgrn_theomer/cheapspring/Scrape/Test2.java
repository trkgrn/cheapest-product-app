package com.trkgrn_theomer.cheapspring.Scrape;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;
import com.trkgrn_theomer.cheapspring.api.model.concretes.Store;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test2 {
    public static void main(String[] args) {
//        List<TestDto> list = new ArrayList<>();
//        list.add(new TestDto(1,300,2));
//        list.add(new TestDto(1,400,4));
//        list.add(new TestDto(2,500,1));
//        list.add(new TestDto(2,400,1));
//        list.add(new TestDto(1,300,5));
//        list.add(new TestDto(3,400,1));
//        list.add(new TestDto(3,200,1));
//        list.add(new TestDto(1,500,5));
//        list.add(new TestDto(2,600,1));

        List<ProductWithStore> list = new ArrayList<>();
        Product p = new Product();
        p.setProductId(1L);
        Product p2 = new Product();
        p2.setProductId(2L);
        Store s = new Store();
        s.setStoreId(1L);
        ProductWithStore a = new ProductWithStore(0L,p,s,500.5,"1asdasd",4.4);
        ProductWithStore b = new ProductWithStore(0L,p,s,600.5,"2asdasd",5.4);
        ProductWithStore c = new ProductWithStore(0L,p,s,300.5,"3asdasd",1.4);
        ProductWithStore d = new ProductWithStore(0L,p,s,700.5,"4asdasd",2.4);
        ProductWithStore e = new ProductWithStore(0L,p2,s,600.5,"334asdasd",3.4);
        ProductWithStore f = new ProductWithStore(0L,p2,s,200.5,"324asdasd",4.4);
        ProductWithStore g = new ProductWithStore(0L,p2,s,700.5,"445asdasd",2.4);

        list.add(b);
        list.add(d);
        list.add(e);
        list.add(c);
        list.add(a);
        list.add(f);
        list.add(g);

        System.out.println(list.toString());

        List <ProductWithStore> tempList = list;

       list =  list.stream().map(item->{
           List<ProductWithStore> duplicates = new ArrayList<>();
           for (ProductWithStore iter:tempList) {
               if (item.getProduct().equals(iter.getProduct())){
                    duplicates.add(iter);
               }
           }
           duplicates = duplicates.stream()
                   .sorted(Comparator.comparing(ProductWithStore::getPrice))
                   .collect(Collectors.toList());
           System.out.println("DUPS: "+duplicates);

           return duplicates.get(0);
       }).collect(Collectors.toList());
        System.out.println("SIRALI "+list);
       list = list.stream().distinct().collect(Collectors.toList());
        for (ProductWithStore pr:list) {
            System.out.println( "PR ID: " +pr.getProduct().getProductId());
            System.out.println( "Price: " +pr.getPrice());
            System.out.println( "Score: " + pr.getScore());
            System.out.println("URL: " + pr.getProductUrlInStore());
        }
//        System.out.println(tempList.toString());
    }
}

class TestDto{
    int id;
    double price;
    double score;
   public TestDto(int id,double price,double score){
        this.id = id;
        this.price = price;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TestDto{" +
                "id=" + id +
                ", price=" + price +
                ", score=" + score +
                '}';
    }
}
