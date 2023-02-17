package com.ecommercebackend.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercebackend.dto.Rating;
import com.ecommercebackend.dto.RatingKey;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingKey> {
    public Optional<Rating> findByRatingId(RatingKey ratingId);

    public Page<Rating> findAllByIsShowTrueAndProductId(int productId, Pageable pageable);

    public List<Rating> findAllByIsShowTrueAndProductId(int productId);
}
