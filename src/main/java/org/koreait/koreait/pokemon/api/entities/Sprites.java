package org.koreait.koreait.pokemon.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.koreait.pokemon.api.entities.SpritesOther;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprites {
    private SpritesOther other;
}
