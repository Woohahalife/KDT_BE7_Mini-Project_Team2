package com.core.miniproject.src.image.repository;

import com.core.miniproject.src.image.domain.entity.AccommodationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AccommodationImageRepository extends JpaRepository<AccommodationImage, Long> {

    @Override
    <S extends AccommodationImage> List<S> saveAll(Iterable<S> entities);

    @Query("""
    select ai
    from AccommodationImage  ai
    where ai.accommodation.id=?1
    """)
    List<AccommodationImage> findAllById(Long accommodationId);
}
