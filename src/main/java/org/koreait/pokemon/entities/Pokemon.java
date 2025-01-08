package org.koreait.pokemon.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.koreait.global.entities.BaseEntity;

import java.util.List;
import java.util.Map;

@Data
@Entity
public class Pokemon extends BaseEntity {
    @Id
    private Long seq;

    @Column(length=50)
    private String name; // 포켓몬 한글 이름

    @Column(length=50)
    private String nameEn; // 포켓몬 영어 이름
    private int weight;
    private int height;
    private int baseExperience;

    private String frontImage;

    @Lob
    private String flavorText; // 설명
    private String types; // 타입1||타입2||타입3
    private String abilities; // 능력1||능력2||능력3

    @Column(length=100)
    private String genus; // 분류

    @Transient
    private List<String> _types;

    @Transient
    private List<String> _abilities;

    @Transient
    private Map<String, Object> prevItem;

    @Transient
    private Map<String, Object> nextItem;

    @Transient
    private boolean recommended; // 추천 여부를 나타냄

    /**
     * 추천 여부에 따라 이모지를 반환하는 메서드
     */
    public String getRecommendationEmoji() {
        return recommended ? "👍" : "👎";
    }
}
