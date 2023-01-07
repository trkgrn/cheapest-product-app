package com.trkgrn_theomer.cheapspring.Thread;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {


       long start =  System.currentTimeMillis();
        List<Product> products = IntStream.range(1, 10000).parallel().mapToObj(count -> {
            try {
                System.out.println(count);
                return new ArrayList<Product>();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }).flatMap(List::stream).collect(Collectors.toList());
        long end =  System.currentTimeMillis();

        System.out.println("Time: "+(end-start));

//        test();
    }

   static void test(){
        System.out.println("Main thread is starting");

        MyThread myThread = new MyThread("child1");
        MyThread myThread2 = new MyThread("child2");
        MyThread myThread3 = new MyThread("child3");

        myThread.start();
        myThread2.start();
        myThread3.start();

        do {
            System.out.print(".");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Main thread is interrupted");
            }
        } while (myThread.isAlive() || myThread2.isAlive() || myThread3.isAlive());

        System.out.println("Main thread is terminating");
    }
}