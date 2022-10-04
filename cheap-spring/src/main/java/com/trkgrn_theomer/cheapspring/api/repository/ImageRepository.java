package com.trkgrn_theomer.cheapspring.api.repository;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findByName(String name);
    Image findByImageId(Long id);
}
