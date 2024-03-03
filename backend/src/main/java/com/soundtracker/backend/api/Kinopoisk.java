package com.soundtracker.backend.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@SuppressWarnings("SpellCheckingInspection")
@Component
public class Kinopoisk {

    @Value(value = "${kinopoisk.apiKey}")
    String apiKeyl;


}
