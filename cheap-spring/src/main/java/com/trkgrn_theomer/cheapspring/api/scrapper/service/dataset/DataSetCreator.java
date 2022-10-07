package com.trkgrn_theomer.cheapspring.api.scrapper.service.dataset;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Product;
import com.trkgrn_theomer.cheapspring.api.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSetCreator {

    private final ProductService productService;

    public DataSetCreator(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> creator() throws IOException {
        String baseURL = "https://www.vatanbilgisayar.com";
        String url = "https://www.vatanbilgisayar.com/notebook/?page=";
        int count = 1;
        int totalProduct = 0;
        List<Product> productList = new ArrayList<Product>();

        while(count < 15){
            Document doc = Jsoup.connect(url+count).get();
            Elements body = doc.select("div.wrapper-product--list-page");
            for (Element e:body.select("div.product-list--list-page")) {
                String modelNo = e.select("div.product-list__content > a > div.product-list__product-code").text();
                String productTitle = e.select("div.product-list__content > a > div.product-list__product-name").text();
                String productImg = e.select("div.product-list__image-safe > a > div:nth-child(1) > img").attr("data-src");
                // document.querySelector("#productsLoad > div:nth-child(1) > div.product-list__content > a > div.product-list__product-name")
                totalProduct++;
                if(modelNo.contains("-Gaming")){
                    int index =  modelNo.indexOf("-Gaming");
                    modelNo = modelNo.substring(0,index);
                }

                String productUrl = baseURL + e.select("div.product-list__content > a").attr("href");

                System.out.println(totalProduct + " - Model No: " + modelNo);
                System.out.println(totalProduct + " - Product URL: " + productUrl);
                System.out.println();

                Product product = this.getProduct(productUrl);
                product.setProductTitle(productTitle);
                product.setProductCode(modelNo);
                product.setProductImage(productImg);
                productList.add(product);
            }
            count++;
        }

        List<Product> savedProducts = productService.saveAll(productList);
        System.out.println(savedProducts);
        return savedProducts;
    }

    public Product getProduct(String url) throws IOException {
        Product product = new Product();
        Document doc = Jsoup.connect(url).get();

        String productBrand = doc.select("ul.breadcrumb > li:nth-child(4) > a").text();
        product.setProductBrand(productBrand);

        Elements body = doc.select("div.property-tab");
        for (Element e:body.select("div.property-tab-item")){
            String title = e.select("h3").text();
          //  System.out.println(title);
            Elements tbody = e.select("div.product-feature > table.product-table > tbody");

            if(title.equals("İşlemci Özellikleri")){
                 product.setCPU(this.getCpuProperty(tbody));
            }
            else if (title.equals("Ram Özellikleri")){
                product.setRAM(this.getRamProperty(tbody));
            }
            else if (title.equals("Ekran kartı")){
                product.setGPU(this.getGpuProperty(tbody));
            }
            else if (title.equals("HDD Özellikleri")){
                product.setHDD(this.getHddProperty(tbody));
            }
            else if (title.equals("İşletim Sistemi")){
                product.setOperatingSystem(this.getOperatingSystemProperty(tbody));
            }
            else if (title.equals("Ekran Özellikleri")){
                product.setScreenSize(this.getScreenSizeProperty(tbody));
            }
            else if (title.equals("Genel Özellikler")){
                product.setColor(this.getColorProperty(tbody));
            }
            else if (title.equals("Ağırlık & boyutlar")){
                product.setWeight(this.getWeightProperty(tbody));
            }
            else if (title.equals("Diğer")){
                product.setUsageType(this.getUsageTypeProperty(tbody));
            }
        }
        return product;
    }


    public String getCpuProperty(Elements tbody){
            String cpuBrand="";
            String cpuVersion="";
            for (Element c:tbody.select("tr")) {
                if(c.select("td").first().text().equals("İşlemci Markası")){
                    cpuBrand = c.select("td > p").first().text();
                }
                if(c.select("td").first().text().equals("İşlemci Teknolojisi")){
                    cpuVersion = c.select("td > p").first().text();
                }
            }
            return cpuBrand+" "+cpuVersion;
    }

    public String getRamProperty(Elements tbody){
        String ram="";
        for (Element c:tbody.select("tr")) {
            if(c.select("td").first().text().equals("Ram (Sistem Belleği)")){
                ram = c.select("td > p").first().text();
            }
        }
        return ram;
    }

    public String getGpuProperty(Elements tbody){
        String gpu="";
        for (Element c:tbody.select("tr")) {
            if(c.select("td").first().text().equals("Ekran Kartı Chipseti")){
                gpu = c.select("td > p").first().text();
            }
        }
        return gpu;
    }

    public String getHddProperty(Elements tbody){
        String hdd="";
        for (Element c:tbody.select("tr")) {
            if(c.select("td").first().text().equals("Disk Kapasitesi")){
                hdd = c.select("td > p").first().text();
            }
        }
        return hdd;
    }

    public String getUsageTypeProperty(Elements tbody){
        String type="";
        for (Element c:tbody.select("tr")) {
            if(c.select("td").first().text().equals("Kullanım Amacı")){
                type = c.select("td > p").first().text();
            }
        }
        return type;
    }

    public String getOperatingSystemProperty(Elements tbody){
        String system="";
        for (Element c:tbody.select("tr")) {
            if(c.select("td").first().text().equals("İşletim Sistemi")){
                system = c.select("td > p").first().text();
            }
        }
        return system;
    }

    public String getWeightProperty(Elements tbody){
        String weight="";
        for (Element c:tbody.select("tr")) {
            if(c.select("td").first().text().equals("Cihaz Ağırlığı")){
                weight = c.select("td > p").first().text();
            }
        }
        return weight;
    }

    public String getScreenSizeProperty(Elements tbody){
        String size="";
        for (Element c:tbody.select("tr")) {
            if(c.select("td").first().text().equals("Ekran Boyutu")){
                size = c.select("td > p").first().text();
            }
        }
        return size;
    }

    public String getColorProperty(Elements tbody){
        String color="";
        for (Element c:tbody.select("tr")) {
            if(c.select("td").first().text().equals("Renk")){
                color = c.select("td > p").first().text();
                if(color.equals("SIYAH"))
                    color="Siyah";
                else if (color.equals("0"))
                    color="Siyah";
                else if (color.equals("GRI"))
                    color = "Gri";
            }
        }
        return color;
    }

}
