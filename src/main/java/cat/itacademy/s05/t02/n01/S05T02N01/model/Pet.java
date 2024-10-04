package cat.itacademy.s05.t02.n01.S05T02N01.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Pet {

    @Id
    private String id;
    private String name;
    private String color;
    private String breed;
    private int happiness;
    private int health;
    private String ownerId;
}
