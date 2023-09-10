package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;

import static org.example.ZodiacSignUtils.determineZodiacSign;

class ZodiacSignBotTest {

    @MethodSource("getZodiacSignTestData")
    @ParameterizedTest
    void should_determine_zodiac_sign(String signName, LocalDate beginDate, LocalDate endDate) {
        for (LocalDate date = beginDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Assertions.assertEquals(
                    signName,
                    determineZodiacSign(date.getMonthValue(), date.getDayOfMonth())
            );
        }
    }

    private static Object[][] getZodiacSignTestData() {
        return new Object[][]{
                {"Aries", LocalDate.parse("2023-03-21"), LocalDate.parse("2023-04-19")},
                {"Taurus", LocalDate.parse("2023-04-20"), LocalDate.parse("2023-05-20")},
                {"Gemini", LocalDate.parse("2023-05-21"), LocalDate.parse("2023-06-20")},
                {"Cancer", LocalDate.parse("2023-06-21"), LocalDate.parse("2023-07-22")},
                {"Leo", LocalDate.parse("2023-07-23"), LocalDate.parse("2023-08-22")},
                {"Virgo", LocalDate.parse("2023-08-23"), LocalDate.parse("2023-09-22")},
                {"Libra", LocalDate.parse("2023-09-23"), LocalDate.parse("2023-10-22")},
                {"Scorpio", LocalDate.parse("2023-10-23"), LocalDate.parse("2023-11-21")},
                {"Sagittarius", LocalDate.parse("2023-11-22"), LocalDate.parse("2023-12-21")},
                {"Capricorn", LocalDate.parse("2023-12-22"), LocalDate.parse("2024-01-19")},
                {"Aquarius", LocalDate.parse("2024-01-20"), LocalDate.parse("2024-02-18")},
                {"Pisces", LocalDate.parse("2024-02-19"), LocalDate.parse("2024-03-20")}
        };
    }
}
