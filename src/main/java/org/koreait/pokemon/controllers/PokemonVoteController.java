package org.koreait.pokemon.controllers;

import org.koreait.pokemon.entities.Pokemon;
import org.koreait.pokemon.repositories.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class PokemonVoteController {

    @Controller
    public class PokemonController {

        @Autowired
        private PokemonRepository pokemonRepository;

        @GetMapping("/pokemonList")
        public String getPokemonList(Model model) {
            List<Pokemon> pokemons = pokemonRepository.findAll();
            model.addAttribute("pokemons", pokemons);
            return "pokemonList"; // 템플릿 이름
        }
    }
}
