package org.koreait.koreait.pokemon.api.entities;

import lombok.Data;
import org.koreait.pokemon.api.entities.UrlItem;

import java.util.List;

@Data
public class ApiResponse {
    private int count;
    private String next;
    private String previous;
    private List<UrlItem> results;
}
