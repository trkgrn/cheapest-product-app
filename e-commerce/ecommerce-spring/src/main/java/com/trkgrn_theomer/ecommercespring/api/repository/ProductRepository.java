package com.trkgrn_theomer.ecommercespring.api.repository;

import com.trkgrn_theomer.ecommercespring.api.model.concretes.Product;
import com.trkgrn_theomer.ecommercespring.api.model.dtos.FilterRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT p.productCode  FROM Product p")
    public List<String> getProductCodes();

    @Query(value = "SELECT distinct p.productCode  FROM Product p WHERE p.productBrand=:#{#brandName.toUpperCase()}")
    public List<String> getProductCodesByBrand(String brandName);

    @Query(value = "SELECT p.productId  FROM Product p WHERE p.productCode=:#{#productCode}")
    public Long getProductIdByProductCode(String productCode);

    @Query(value = "SELECT distinct p.productBrand  FROM Product p")
    public List<String> getAllBrandName();

    @Query(value = "SELECT distinct p.usageType  FROM Product p")
    public List<String> getAllUsageType();

    @Query(value = "SELECT distinct p.color  FROM Product p")
    public List<String> getAllColor();

    @Query(value = "SELECT distinct p.operatingSystem  FROM Product p")
    public List<String> getAllOperatingSystem();

    @Query(value = "SELECT distinct p.GPU  FROM Product p")
    public List<String> getAllGPU();

    @Query(value = "SELECT distinct p.HDD  FROM Product p")
    public List<String> getAllHDD();

    @Query(value = "SELECT distinct p.RAM  FROM Product p")
    public List<String> getAllRAM();

    @Query(value = "SELECT distinct p.screenSize  FROM Product p")
    public List<String> getAllScreenSize();

    @Query(value = "SELECT distinct p.CPU  FROM Product p")
    public List<String> getAllCPU();

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    " FROM product p\n" +
                    " WHERE ( :#{#filter.brandName.size()} = 0  OR  p.product_brand IN (:#{#filter.brandName}) )" +
                    " AND ( :#{#filter.cpu.size()} = 0  OR  p.product_cpu IN (:#{#filter.cpu}) )" +
                    " AND ( :#{#filter.hdd.size()} = 0  OR  p.product_hdd IN (:#{#filter.hdd}) )" +
                    " AND ( :#{#filter.ram.size()} = 0  OR  p.product_ram IN (:#{#filter.ram}) )" +
                    " AND ( :#{#filter.color.size()} = 0  OR  p.product_color IN (:#{#filter.color}) )" +
                    " AND ( :#{#filter.operatingSystem.size()} = 0  OR  p.product_operating_system IN (:#{#filter.operatingSystem}) )" +
                    " AND ( :#{#filter.gpu.size()} = 0  OR  p.product_gpu IN (:#{#filter.gpu}) )" +
                    " AND ( :#{#filter.screenSize.size()} = 0  OR  p.product_screen_size IN (:#{#filter.screenSize}) )" )
    public List<Product> getProductsByFilter(FilterRequestDto filter, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT count(p.product_id)" +
                    " FROM product p\n" +
                    " WHERE ( :#{#filter.brandName.size()} = 0  OR  p.product_brand IN (:#{#filter.brandName}) )" +
                    " AND ( :#{#filter.cpu.size()} = 0  OR  p.product_cpu IN (:#{#filter.cpu}) )" +
                    " AND ( :#{#filter.hdd.size()} = 0  OR  p.product_hdd IN (:#{#filter.hdd}) )" +
                    " AND ( :#{#filter.ram.size()} = 0  OR  p.product_ram IN (:#{#filter.ram}) )" +
                    " AND ( :#{#filter.color.size()} = 0  OR  p.product_color IN (:#{#filter.color}) )" +
                    " AND ( :#{#filter.operatingSystem.size()} = 0  OR  p.product_operating_system IN (:#{#filter.operatingSystem}) )" +
                    " AND ( :#{#filter.gpu.size()} = 0  OR  p.product_gpu IN (:#{#filter.gpu}) )" +
                    " AND ( :#{#filter.screenSize.size()} = 0  OR  p.product_screen_size IN (:#{#filter.screenSize}) )" )
    public Long countProductsByFilter(FilterRequestDto filter);
}
