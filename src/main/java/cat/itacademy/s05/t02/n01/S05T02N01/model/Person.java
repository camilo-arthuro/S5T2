package cat.itacademy.s05.t02.n01.S05T02N01.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document
public class Person {

    @Id
    private String id;
    private String userName;
    private String userPassword;
    private ArrayList<Pet> petList;
    private String userRole;
}
