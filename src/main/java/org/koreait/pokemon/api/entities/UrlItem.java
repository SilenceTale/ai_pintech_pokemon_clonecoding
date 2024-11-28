package org.koreait.pokemon.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlItem {
    private String name;
    private String url;
    private List<UrlItem> results;
}