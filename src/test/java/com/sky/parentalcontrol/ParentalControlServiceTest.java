package com.sky.parentalcontrol;

import com.sky.api.MovieService;
import com.sky.api.TechnicalFailureException;
import com.sky.api.TitleNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceTest {
    private static final String MOVIE_ID = "5677";

    @Mock
    private MovieService movieService;

    private  ParentalControlService parentalControlService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        parentalControlService = new ParentalControlService(movieService);
    }

    @Test
    public void usersCanWatch_GivenLowerParentalControlLevels() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_ID)).thenReturn("PG");

        boolean result = parentalControlService.canWatchMovie(MOVIE_ID, ParentalControlLevel.FIFTEEN);

        assertTrue(result);
    }

    @Test
    public void usersCanWatch_GivenEqualParentalControlLevels() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_ID)).thenReturn("15");

        boolean result = parentalControlService.canWatchMovie(MOVIE_ID, ParentalControlLevel.FIFTEEN);

        assertTrue(result);
    }

    @Test
    public void usersCannotWatch_GivenHigherParentalControlLevels() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_ID)).thenReturn("18");

        boolean result = parentalControlService.canWatchMovie(MOVIE_ID, ParentalControlLevel.PG);

        assertFalse(result);
    }

    @Test
    public void usersCannotWatch_GivenInvalidParentalControlLevel() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_ID)).thenReturn("23");

        boolean result = parentalControlService.canWatchMovie(MOVIE_ID, ParentalControlLevel.PG);

        assertFalse(result);
    }

    @Test
    public void throwDownStreamExceptionException_GivenMovieServiceThrowsTitleNotFoundException() throws Exception {
        expectedException.expect(DownStreamSystemException.class);

        when(movieService.getParentalControlLevel(MOVIE_ID)).thenThrow(new TitleNotFoundException());
        parentalControlService.canWatchMovie(MOVIE_ID, ParentalControlLevel.EIGHTEEN);
    }

    @Test
    public void usersCannotWatch_GivenMovieServiceThrowsTechnicalFailureException() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_ID)).thenThrow(new TechnicalFailureException());

        boolean result = parentalControlService.canWatchMovie(MOVIE_ID, ParentalControlLevel.PG);

        assertFalse(result);
    }

    @Test
    public void throwDownStreamExceptionException_GivenMovieServiceThrowsRuntimeException() throws Exception {
        expectedException.expect(DownStreamSystemException.class);

        when(movieService.getParentalControlLevel(MOVIE_ID)).thenThrow(new RuntimeException());
        parentalControlService.canWatchMovie(MOVIE_ID, ParentalControlLevel.EIGHTEEN);
    }

    @Test
    public void throwIllegalArgumentException_GivenMovieIdIsEmpty() throws Exception {
        expectedException.expect(IllegalArgumentException.class);

        parentalControlService.canWatchMovie("", ParentalControlLevel.PG);
    }

    @Test
    public void throwIllegalArgumentException_GivenMovieIdIsNull() throws Exception {
        expectedException.expect(IllegalArgumentException.class);

        parentalControlService.canWatchMovie(null, ParentalControlLevel.PG);
    }

    @Test
    public void throwIllegalArgumentException_GivenParentalControlIsNull() throws Exception {
        expectedException.expect(IllegalArgumentException.class);

        parentalControlService.canWatchMovie(MOVIE_ID, null);
    }
}
