package cat.itacademy.s05.t02.n01.S05T02N01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    private String name;
    private String color;
    private int happiness;
    private int health;

}
