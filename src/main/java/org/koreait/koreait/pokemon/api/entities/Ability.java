package org.koreait.koreait.pokemon.api.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.koreait.pokemon.api.entities.UrlItem;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ability {
    private UrlItem ability;
    @JsonAlias("is_hidden")
    private boolean isHidden;
    private int slot;
}
