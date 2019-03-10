package com.irunninglog.spring.challenges;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
final class ChallengeDefinitions {

    private static final String DRIVING_DISTANCE = "Driving distance";
    private static final String EQUATORIAL_DISTANCE = "Distance at the equator";

    List<ChallengeDefinition> definitions() {
        List<ChallengeDefinition> definitions = new ArrayList<>();

        definitions.add(new ChallengeDefinition()
                .setName("New York to Boston")
                .setDesctiption(DRIVING_DISTANCE)
                .setDistance(BigDecimal.valueOf(346170)));

        definitions.add(new ChallengeDefinition()
                .setName("London to Rome")
                .setDesctiption(DRIVING_DISTANCE)
                .setDistance(BigDecimal.valueOf(1880196.6F)));

        definitions.add(new ChallengeDefinition()
                .setName("Appalachain Trail")
                .setDesctiption("Official website distance")
                .setDistance(BigDecimal.valueOf(3524464)));

        definitions.add(new ChallengeDefinition()
                .setName("New York to Los Angeles")
                .setDesctiption(DRIVING_DISTANCE)
                .setDistance(BigDecimal.valueOf(4468830)));

        definitions.add(new ChallengeDefinition()
                .setName("Miami to Prudhoe Bay")
                .setDesctiption(DRIVING_DISTANCE)
                .setDistance(BigDecimal.valueOf(8596000)));

        definitions.add(new ChallengeDefinition()
                .setName("Around the Moon")
                .setDesctiption(EQUATORIAL_DISTANCE)
                .setDistance(BigDecimal.valueOf(10921009)));

        definitions.add(new ChallengeDefinition()
                .setName("London to Cape Town")
                .setDesctiption(DRIVING_DISTANCE)
                .setDistance(BigDecimal.valueOf(13271778F)));

        definitions.add(new ChallengeDefinition()
                .setName("Lisbon to Magadan")
                .setDesctiption(DRIVING_DISTANCE)
                .setDistance(BigDecimal.valueOf(15145060)));

        definitions.add(new ChallengeDefinition()
                .setName("Around Mars")
                .setDesctiption(EQUATORIAL_DISTANCE)
                .setDistance(BigDecimal.valueOf(21344730)));

        definitions.add(new ChallengeDefinition()
                .setName("Prudhoe Bay to Ushuaia")
                .setDesctiption(DRIVING_DISTANCE)
                .setDistance(BigDecimal.valueOf(24222238)));

        definitions.add(new ChallengeDefinition()
                .setName("Around the World")
                .setDesctiption(EQUATORIAL_DISTANCE)
                .setDistance(BigDecimal.valueOf(40030020)));

        definitions.add(new ChallengeDefinition()
                .setName("From the Earth to the Moon")
                .setDesctiption("Average distance")
                .setDistance(BigDecimal.valueOf(384400000)));

        return definitions;
    }

}
