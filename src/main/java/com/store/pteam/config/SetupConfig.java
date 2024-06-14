package com.store.pteam.config;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.store.pteam.service.CountryService;
import com.store.pteam.service.GameService;
import com.store.pteam.service.CardService;

@Configuration
public class SetupConfig {

    @Autowired
    private CountryService countryService;

    @Autowired
    private GameService gameService;

    @Autowired
    private CardService cardService;

    @PostConstruct
    public void setup() {
        countryService.addCountriesIfNeeded();
        gameService.addStaticGames();
        cardService.updateCardStatuses();
    }
}
