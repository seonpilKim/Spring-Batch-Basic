package study.spring.SpringBatchBasic.domain.player;

import java.time.Year;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerYears {

    private String id;
    private String lastName;
    private String firstName;
    private String position;
    private int birthYear;
    private int debutYear;
    private int yearsExperience;

    public static PlayerYears of(Player player) {
        return PlayerYears.builder()
                .id(player.getId())
                .lastName(player.getLastName())
                .firstName(player.getFirstName())
                .position(player.getPosition())
                .birthYear(player.getBirthYear())
                .debutYear(player.getDebutYear())
                .yearsExperience(Year.now().getValue() - player.getDebutYear())
                .build();
    }

}
