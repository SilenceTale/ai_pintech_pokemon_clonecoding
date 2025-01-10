package org.koreait.koreait.pokemon.api.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.koreait.pokemon.api.entities.*;
import org.koreait.pokemon.api.entities.Ability;
import org.koreait.pokemon.api.entities.Genus;
import org.koreait.pokemon.api.entities.Names;
import org.koreait.pokemon.api.entities.Sprites;
import org.koreait.pokemon.api.entities.Types;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPokemon {
    private int id;
    private String name;
    private Sprites sprites;
    private int weight;
    private int height;
    private List<Types> types;
    private List<Ability> abilities;

    @JsonAlias("base_experience")
    private int baseExperience;

    // https://pokeapi.co/api/v2/pokemon-species/1
    private List<Names> names;

    @JsonAlias("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;

    private List<Genus> genera;
}

