package com.trkgrn_theomer.cheapspring.Scrape;

import com.trkgrn_theomer.cheapspring.api.model.concretes.ProductWithStore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test2 {
    public static void main(String[] args) {
        List<TestDto> list = new ArrayList<>();
        list.add(new TestDto(1,300,2));
        list.add(new TestDto(1,400,4));
        list.add(new TestDto(2,500,1));
        list.add(new TestDto(2,400,1));
        list.add(new TestDto(1,300,5));
        list.add(new TestDto(3,400,1));
        list.add(new TestDto(3,200,1));
        list.add(new TestDto(1,500,5));
        list.add(new TestDto(2,600,1));

        System.out.println(list.toString());

        List <TestDto> tempList = list;

       list =  list.stream().map(item->{
           List<TestDto> duplicates = new ArrayList<>();
           for (TestDto iter:tempList) {
               if (item.id == iter.id){
                    duplicates.add(iter);
               }
           }
           duplicates = duplicates.stream()
                   .sorted(Comparator.comparing(TestDto::getPrice))
                   .collect(Collectors.toList());

           return duplicates.get(0);
       }).collect(Collectors.toList());
        System.out.println(list.stream().distinct().collect(Collectors.toList()).toString());
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
