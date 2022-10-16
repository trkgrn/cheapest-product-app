package com.trkgrn_theomer.ecommercespring;

public class Test {
    public static void main(String[] args) {
        String x  = "INTEL Core i5";
        String y = "Intel Core i5";
       x = x.replace("®","");
        System.out.println(x.toUpperCase());
        System.out.println(y.toUpperCase());
    }

}
