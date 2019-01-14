package com.sky.parentalcontrol;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ParentalControlLevelTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void from_convertUToParentalControlLevel() {
        assertThat(ParentalControlLevel.from("U"), is(ParentalControlLevel.U));
    }

    @Test
    public void from_convertPGToParentalControlLevel() {
        assertThat(ParentalControlLevel.from("PG"), is(ParentalControlLevel.PG));
    }

    @Test
    public void from_convert12ToParentalControlLevel() {
        assertThat(ParentalControlLevel.from("12"), is(ParentalControlLevel.TWELVE));
    }

    @Test
    public void from_convert15ToParentalControlLevel() {
        assertThat(ParentalControlLevel.from("15"), is(ParentalControlLevel.FIFTEEN));
    }

    @Test
    public void from_convert18ToParentalControlLevel() {
        assertThat(ParentalControlLevel.from("18"), is(ParentalControlLevel.EIGHTEEN));
    }

    @Test
    public void throwsIllegalArgumentException_GivenInvalidLevel() {
        expectedException.expect(IllegalArgumentException.class);
        ParentalControlLevel.from("20");
    }

    @Test
    public void throwsIllegalArgumentException_GivenLevelIsNull() {
        expectedException.expect(IllegalArgumentException.class);
        ParentalControlLevel.from(null);
    }
}
