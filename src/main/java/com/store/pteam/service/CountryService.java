package com.store.pteam.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.store.pteam.model.Country;
import com.store.pteam.repository.CountryRepository;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public void addCountriesIfNeeded() {
        long existingCountryCount = countryRepository.count();
        System.out.println("Existing country count: " + existingCountryCount);
        if (existingCountryCount == 0) {
            addCountries();
        }
    }

    public void addCountries() {
        Map<String, String> countries = new TreeMap<>();
        countries.put("Albania", "+355");
        countries.put("Andorra", "+376");
        countries.put("Austria", "+43");
        countries.put("Belarus", "+375");
        countries.put("Belgium", "+32");
        countries.put("Bulgaria", "+359");
        countries.put("Croatia", "+385");
        countries.put("Cyprus", "+357");
        countries.put("Czech Republic", "+420");
        countries.put("Denmark", "+45");
        countries.put("Estonia", "+372");
        countries.put("Finland", "+358");
        countries.put("France", "+33");
        countries.put("Germany", "+49");
        countries.put("Greece", "+30");
        countries.put("Hungary", "+36");
        countries.put("Iceland", "+354");
        countries.put("Ireland", "+353");
        countries.put("Italy", "+39");
        countries.put("Latvia", "+371");
        countries.put("Liechtenstein", "+423");
        countries.put("Lithuania", "+370");
        countries.put("Luxembourg", "+352");
        countries.put("Malta", "+356");
        countries.put("Moldova", "+373");
        countries.put("Monaco", "+377");
        countries.put("Montenegro", "+382");
        countries.put("Netherlands", "+31");
        countries.put("Norway", "+47");
        countries.put("Poland", "+48");
        countries.put("Portugal", "+351");
        countries.put("Romania", "+40");
        countries.put("Russia", "+7");
        countries.put("San Marino", "+378");
        countries.put("Serbia", "+381");
        countries.put("Slovakia", "+421");
        countries.put("Slovenia", "+386");
        countries.put("Spain", "+34");
        countries.put("Sweden", "+46");
        countries.put("Switzerland", "+41");
        countries.put("Ukraine", "+380");
        countries.put("United Kingdom", "+44");
        countries.put("Vatican City", "+39");

        for (Map.Entry<String, String> entry : countries.entrySet()) {
            Country country = new Country(entry.getKey(), entry.getValue());
            countryRepository.save(country);
        }
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }
}
