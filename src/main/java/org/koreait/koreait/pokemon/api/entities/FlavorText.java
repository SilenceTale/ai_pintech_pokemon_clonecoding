package org.koreait.koreait.pokemon.api.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.koreait.pokemon.api.entities.UrlItem;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlavorText {
    @JsonAlias("flavor_text")
    private String flavorText;
    private UrlItem language;
}
