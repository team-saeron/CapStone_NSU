package togethers.togethers.test;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Album extends Item{
    private String artist;
}
