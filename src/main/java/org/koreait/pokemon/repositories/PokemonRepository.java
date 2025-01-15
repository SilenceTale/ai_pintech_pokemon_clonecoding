package org.koreait.pokemon.repositories;

import org.koreait.pokemon.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Pokemon p SET p.recommendationCount = p.recommendationCount + 1 WHERE p.seq = :seq")
    void incrementRecommendationCount(Long seq);
}
