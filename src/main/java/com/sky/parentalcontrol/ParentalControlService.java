package com.sky.parentalcontrol;

import com.sky.api.MovieService;
import com.sky.api.TechnicalFailureException;
import com.sky.api.TitleNotFoundException;

import java.util.Objects;

import static com.sky.parentalcontrol.ParentalControlLevel.from;

public class ParentalControlService {
    private final MovieService movieService;

    public ParentalControlService(MovieService movieService) {
        this.movieService = movieService;
    }

    public boolean canWatchMovie(String movieId, ParentalControlLevel userParentalControlLevel) throws DownStreamSystemException {
        validateInput(movieId, userParentalControlLevel);
        try {
            String parentalControlLevel = movieService.getParentalControlLevel(movieId);
            return userParentalControlLevel.compareTo(from(parentalControlLevel)) >= 0;
        } catch (IllegalArgumentException | TechnicalFailureException ex) {
            return false;
        } catch (TitleNotFoundException | RuntimeException ex) {
            throw new DownStreamSystemException("Exception while calling Movie Service", ex);
        }
    }

    private void validateInput(String movieId, ParentalControlLevel userParentalControlLevel) {
        if (Objects.isNull(movieId) || movieId.equals("")) {
            throw new IllegalArgumentException("movie id is null or empty");
        }
        if (Objects.isNull(userParentalControlLevel)) {
            throw new IllegalArgumentException("parent control level is null");
        }
    }
}
