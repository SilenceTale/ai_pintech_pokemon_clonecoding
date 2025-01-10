package org.koreait.koreait.pokemon.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.koreait.pokemon.api.entities.UrlItem;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Names {
    private UrlItem language;
    private String name;
}
