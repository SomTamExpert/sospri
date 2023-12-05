package ch.bbw.km.winthi.room;

import ch.bbw.km.winthi.category.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * A chat message
 *
 * @author Marco Karpf
 * @version 13.04.2023
 */
@Entity
@Table(name = "room")
@Getter
@Setter
@ToString
public class Room {
    @Id
    @GeneratedValue(generator = "generatorMessage", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generatorMessage", initialValue = 10)
    private Long id;

    @NotEmpty(message = "content may not be empty")
    @Size(min = 2, max = 1012, message = "Die Länge des Titels  muss 2 bis 512 Zeichen sein.")
    private String title;

    @NotEmpty(message = "content may not be empty")
    @Size(min = 2, max = 1012, message = "Die Länge des Inhalts  muss 2 bis 512 Zeichen sein.")
    private String content;

    //@NotEmpty (message = "author may not be empty" )
    @Size(min = 5, max = 50, message = "Die Länge des Autors 2 bis 20 Zeichen sein.")
    private String price;

    private String rating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    private Boolean booked;

    private String img;


}
