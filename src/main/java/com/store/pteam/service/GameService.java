package com.store.pteam.service;

import com.store.pteam.model.Game;
import com.store.pteam.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;



@Service
@Transactional
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public void addStaticGames() {
        List<Game> staticGames = createStaticGames();
        List<Game> newGames = new ArrayList<>();
        for (Game game : staticGames) {
            if (!gameRepository.existsByTitle(game.getTitle())) {
                newGames.add(game);
            }
        }
        gameRepository.saveAll(newGames);
    }

    public Game getGameById(Long gameId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        return gameOptional.orElse(null);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    private List<Game> createStaticGames() {
        List<Game> games = new ArrayList<>();

        Game game1 = new Game();
        game1.setTitle("Grand Theft Auto V");
        game1.setGenre("Action-Adventure");
        game1.setDescription("GTA 5 is an open-world action-adventure game featuring heists, crime, and exploration in the fictional city of Los Santos.");
        game1.setReleaseDate(Date.from(LocalDate.of(2013, 9, 17).atStartOfDay(ZoneId.systemDefault()).toInstant())); 
        game1.setDeveloper("Rockstar Studios");
        game1.setPublisher("Rockstar Games");
        game1.setPrice(BigDecimal.valueOf(29.99));
        games.add(game1);

        Game game2 = new Game();
        game2.setTitle("Red Dead Redemption 2");
        game2.setGenre("Action-Adventure");
        game2.setDescription("A Western-themed action-adventure game set in an open world environment.");
        game2.setReleaseDate(Date.from(LocalDate.of(2018, 10, 26).atStartOfDay(ZoneId.systemDefault()).toInstant())); 
        game2.setDeveloper("Rockstar Studios");
        game2.setPublisher("Rockstar Games");
        game2.setPrice(BigDecimal.valueOf(39.99));
        games.add(game2);

        Game game3 = new Game();
        game3.setTitle("The Last of Us Part II");
        game3.setGenre("Action-Adventure");
        game3.setDescription("An action-adventure game set in a post-apocalyptic world, focusing on the journey of Ellie.");
        game3.setReleaseDate(Date.from(LocalDate.of(2020, 6, 19).atStartOfDay(ZoneId.systemDefault()).toInstant())); 
        game3.setDeveloper("Naughty Dog");
        game3.setPublisher("Sony Interactive Entertainment");
        game3.setPrice(BigDecimal.valueOf(39.99));
        games.add(game3);

        Game game4 = new Game();
        game4.setTitle("God of War (2018)");
        game4.setGenre("Action-Adventure");
        game4.setDescription("An action-adventure game based on Norse mythology, featuring Kratos and his son Atreus.");
        game4.setReleaseDate(Date.from(LocalDate.of(2018, 4, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game4.setDeveloper("Santa Monica Studio");
        game4.setPublisher("Sony Interactive Entertainment");
        game4.setPrice(BigDecimal.valueOf(19.99));
        games.add(game4);

        Game game5 = new Game();
        game5.setTitle("Cyberpunk 2077");
        game5.setGenre("Action RPG");
        game5.setDescription("A dystopian action role-playing game set in the open-world Night City.");
        game5.setReleaseDate(Date.from(LocalDate.of(2020, 12, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game5.setDeveloper("CD Projekt Red");
        game5.setPublisher("CD Projekt");
        game5.setPrice(BigDecimal.valueOf(54.99));
        games.add(game5);

        Game game6 = new Game();
        game6.setTitle("5 noches con alfredo");
        game6.setGenre("Horror");
        game6.setDescription("Five Nights at Freddy's: sobrevive a los horrores animatrónicos durante cinco noches utilizando cámaras de vigilancia. Te esperan miedo, estrategia y sobresaltos.");
        game6.setReleaseDate(Date.from(LocalDate.of(2014, 8, 8).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game6.setDeveloper("Scott Cawthon");
        game6.setPublisher("Scottgames");
        game6.setPrice(BigDecimal.valueOf(9.99));
        games.add(game6);

        Game game7 = new Game();
        game7.setTitle("Assassin's Creed Valhalla");
        game7.setGenre("Action-Adventure");
        game7.setDescription("An action-adventure game set in the Viking Age, exploring the story of Eivor.");
        game7.setReleaseDate(Date.from(LocalDate.of(2020, 11, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game7.setDeveloper("Ubisoft Montreal");
        game7.setPublisher("Ubisoft");
        game7.setPrice(BigDecimal.valueOf(69.99));
        games.add(game7);

        Game game8 = new Game();
        game8.setTitle("Counter-strike 2");
        game8.setGenre("First-person Shooter");
        game8.setDescription("Counter-Strike 2 - Competitive FPS. Terrorists vs. Counter-Terrorists. Teamwork, tactics, and precision shooting in intense multiplayer battles.");
        game8.setReleaseDate(Date.from(LocalDate.of(2023, 9, 27).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game8.setDeveloper("Valve");
        game8.setPublisher("Valve");
        game8.setPrice(BigDecimal.valueOf(12.99));
        games.add(game8);

        Game game9 = new Game();
        game9.setTitle("Ghost of Tsushima");
        game9.setGenre("Action-Adventure");
        game9.setDescription("An action-adventure game set in feudal Japan, where players control a samurai named Jin Sakai.");
        game9.setReleaseDate(Date.from(LocalDate.of(2020, 7, 17).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game9.setDeveloper("Sucker Punch Productions");
        game9.setPublisher("Sony Interactive Entertainment");
        game9.setPrice(BigDecimal.valueOf(39.99));
        games.add(game9);

        Game game10 = new Game();
        game10.setTitle("Marvel's Spider-Man: Miles Morales");
        game10.setGenre("Action-Adventure");
        game10.setDescription("An action-adventure game featuring Miles Morales as he takes on the mantle of Spider-Man.");
        game10.setReleaseDate(Date.from(LocalDate.of(2020, 11, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game10.setDeveloper("Insomniac Games");
        game10.setPublisher("Sony Interactive Entertainment");
        game10.setPrice(BigDecimal.valueOf(29.99));
        games.add(game10);

        Game game11 = new Game();
        game11.setTitle("The Witcher 3: Wild Hunt");
        game11.setGenre("Action RPG");
        game11.setDescription("An open-world RPG where players control Geralt of Rivia, a monster hunter searching for his adopted daughter.");
        game11.setReleaseDate(Date.from(LocalDate.of(2015, 5, 19).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game11.setDeveloper("CD Projekt Red");
        game11.setPublisher("CD Projekt");
        game11.setPrice(BigDecimal.valueOf(39.99));
        games.add(game11);

        Game game12 = new Game();
        game12.setTitle("Dark Souls III");
        game12.setGenre("Action RPG");
        game12.setDescription("A challenging action RPG set in a dark fantasy world, known for its tough gameplay and intricate lore.");
        game12.setReleaseDate(Date.from(LocalDate.of(2016, 4, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game12.setDeveloper("FromSoftware");
        game12.setPublisher("Bandai Namco Entertainment");
        game12.setPrice(BigDecimal.valueOf(29.99));
        games.add(game12);

        Game game13 = new Game();
        game13.setTitle("Doom Eternal");
        game13.setGenre("First-person Shooter");
        game13.setDescription("Fast-paced and intense FPS where players battle demonic forces from Hell.");
        game13.setReleaseDate(Date.from(LocalDate.of(2020, 3, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game13.setDeveloper("id Software");
        game13.setPublisher("Bethesda Softworks");
        game13.setPrice(BigDecimal.valueOf(49.99));
        games.add(game13);

        Game game14 = new Game();
        game14.setTitle("Fallout 4");
        game14.setGenre("Action RPG");
        game14.setDescription("Set in a post-apocalyptic open world, players navigate a world destroyed by nuclear war.");
        game14.setReleaseDate(Date.from(LocalDate.of(2015, 11, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game14.setDeveloper("Bethesda Game Studios");
        game14.setPublisher("Bethesda Softworks");
        game14.setPrice(BigDecimal.valueOf(29.99));
        games.add(game14);

        Game game15 = new Game();
        game15.setTitle("Farming Simulator 22");
        game15.setGenre("Simulation");
        game15.setDescription("Manage your own farm, cultivate crops, raise livestock, and expand your farm in this detailed simulation game.");
        game15.setReleaseDate(Date.from(LocalDate.of(2021, 11, 22).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game15.setDeveloper("GIANTS Software");
        game15.setPublisher("GIANTS Software");
        game15.setPrice(BigDecimal.valueOf(29.99));
        games.add(game15);

        Game game16 = new Game();
        game16.setTitle("Borderlands 3");
        game16.setGenre("Action RPG");
        game16.setDescription("Loot-driven shooter with RPG elements, set in a vibrant and chaotic universe.");
        game16.setReleaseDate(Date.from(LocalDate.of(2019, 9, 13).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game16.setDeveloper("Gearbox Software");
        game16.setPublisher("2K Games");
        game16.setPrice(BigDecimal.valueOf(59.99));
        games.add(game16);

        Game game17 = new Game();
        game17.setTitle("Microsoft Flight Simulator");
        game17.setGenre("Flight Simulation");
        game17.setDescription("Experience the most realistic flight simulation with stunning visuals, accurate weather simulation, and detailed aircraft.");
        game17.setReleaseDate(Date.from(LocalDate.of(2020, 8, 18).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game17.setDeveloper("Asobo Studio");
        game17.setPublisher("Xbox Game Studios");
        game17.setPrice(BigDecimal.valueOf(59.99));
        games.add(game17);

        Game game18 = new Game();
        game18.setTitle("The Elder Scrolls V: Skyrim");
        game18.setGenre("Action RPG");
        game18.setDescription("Open-world RPG set in the fantasy world of Tamriel, known for its expansive world and modding community.");
        game18.setReleaseDate(Date.from(LocalDate.of(2011, 11, 11).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game18.setDeveloper("Bethesda Game Studios");
        game18.setPublisher("Bethesda Softworks");
        game18.setPrice(BigDecimal.valueOf(19.99));
        games.add(game18);

        Game game19 = new Game();
        game19.setTitle("Civilization VI");
        game19.setGenre("Strategy");
        game19.setDescription("Turn-based strategy game where players build an empire from ancient to modern times, competing against other civilizations.");
        game19.setReleaseDate(Date.from(LocalDate.of(2016, 10, 21).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game19.setDeveloper("Firaxis Games");
        game19.setPublisher("2K Games");
        game19.setPrice(BigDecimal.valueOf(59.99));
        games.add(game19);

        Game game20 = new Game();
        game20.setTitle("Sekiro: Shadows Die Twice");
        game20.setGenre("Action-Adventure");
        game20.setDescription("From the creators of Dark Souls, a challenging action game set in feudal Japan with intense sword combat.");
        game20.setReleaseDate(Date.from(LocalDate.of(2019, 3, 22).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game20.setDeveloper("FromSoftware");
        game20.setPublisher("Activision");
        game20.setPrice(BigDecimal.valueOf(59.99));
        games.add(game20);

        Game game21 = new Game();
        game21.setTitle("Far Cry 5");
        game21.setGenre("First-person Shooter");
        game21.setDescription("Open-world shooter set in rural America, where players must liberate a community from a dangerous cult.");
        game21.setReleaseDate(Date.from(LocalDate.of(2018, 3, 27).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game21.setDeveloper("Ubisoft Montreal");
        game21.setPublisher("Ubisoft");
        game21.setPrice(BigDecimal.valueOf(29.99));
        games.add(game21);

        Game game22 = new Game();
        game22.setTitle("Star Wars Jedi: Fallen Order");
        game22.setGenre("Action-Adventure");
        game22.setDescription("Play as a Jedi Padawan who survived Order 66, exploring the galaxy and fighting against the Empire.");
        game22.setReleaseDate(Date.from(LocalDate.of(2019, 11, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game22.setDeveloper("Respawn Entertainment");
        game22.setPublisher("Electronic Arts");
        game22.setPrice(BigDecimal.valueOf(39.99));
        games.add(game22);

        Game game23 = new Game();
        game23.setTitle("The Crew 2");
        game23.setGenre("Racing");
        game23.setDescription("An open-world racing game where players can explore the entire United States by land, sea, and air.");
        game23.setReleaseDate(Date.from(LocalDate.of(2018, 6, 29).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game23.setDeveloper("Ivory Tower");
        game23.setPublisher("Ubisoft");
        game23.setPrice(BigDecimal.valueOf(39.99));
        games.add(game23);

        Game game24 = new Game();
        game24.setTitle("Destiny 2");
        game24.setGenre("First-person Shooter");
        game24.setDescription("Sci-fi shooter with RPG elements, set in a post-apocalyptic world where players fight against alien threats.");
        game24.setReleaseDate(Date.from(LocalDate.of(2017, 9, 6).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game24.setDeveloper("Bungie");
        game24.setPublisher("Bungie");
        game24.setPrice(BigDecimal.valueOf(59.99));
        games.add(game24);

        Game game25 = new Game();
        game25.setTitle("Resident Evil Village");
        game25.setGenre("Horror");
        game25.setDescription("Horror game where players navigate a mysterious village and face off against supernatural threats.");
        game25.setReleaseDate(Date.from(LocalDate.of(2021, 5, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game25.setDeveloper("Capcom");
        game25.setPublisher("Capcom");
        game25.setPrice(BigDecimal.valueOf(59.99));
        games.add(game25);

        Game game26 = new Game();
        game26.setTitle("Forza Motorsport 5");
        game26.setGenre("Racing");
        game26.setDescription("Highly realistic racing simulation featuring a wide range of cars and tracks.");
        game26.setReleaseDate(Date.from(LocalDate.of(2022, 11, 8).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game26.setDeveloper("Turn 10 Studios");
        game26.setPublisher("Microsoft Studios");
        game26.setPrice(BigDecimal.valueOf(59.99));
        games.add(game26);

        Game game27 = new Game();
        game27.setTitle("Fallout 76");
        game27.setGenre("Action RPG");
        game27.setDescription("Multiplayer action RPG set in the Fallout universe, focusing on survival and exploration.");
        game27.setReleaseDate(Date.from(LocalDate.of(2018, 11, 14).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game27.setDeveloper("Bethesda Game Studios");
        game27.setPublisher("Bethesda Softworks");
        game27.setPrice(BigDecimal.valueOf(39.99));
        games.add(game27);

        Game game28 = new Game();
        game28.setTitle("Dead by Daylight");
        game28.setGenre("Survival Horror");
        game28.setDescription("Asymmetrical multiplayer horror game where one player is the killer and the rest are survivors.");
        game28.setReleaseDate(Date.from(LocalDate.of(2016, 6, 14).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game28.setDeveloper("Behaviour Interactive");
        game28.setPublisher("Behaviour Interactive");
        game28.setPrice(BigDecimal.valueOf(19.99));
        games.add(game28);

        Game game29 = new Game();
        game29.setTitle("Phasmophobia");
        game29.setGenre("Horror");
        game29.setDescription("Cooperative online multiplayer game where players investigate paranormal activities.");
        game29.setReleaseDate(Date.from(LocalDate.of(2020, 9, 18).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game29.setDeveloper("Kinetic Games");
        game29.setPublisher("Kinetic Games");
        game29.setPrice(BigDecimal.valueOf(14.99));
        games.add(game29);

        Game game30 = new Game();
        game30.setTitle("Call of Duty: Black Ops 6");
        game30.setGenre("First-person Shooter");
        game30.setDescription("Call of Duty: Black Ops 6 is signature Black Ops across a cinematic single-player Campaign, a best-in-class Multiplayer experience and with the epic return of Round-Based Zombies.");
        game30.setReleaseDate(Date.from(LocalDate.of(2024, 6, 13).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        game30.setDeveloper("Treyarch");
        game30.setPublisher("Activision");
        game30.setPrice(BigDecimal.valueOf(79.99));
        games.add(game30);

        return games;
    }
}
