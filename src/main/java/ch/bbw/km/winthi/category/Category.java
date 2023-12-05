package ch.bbw.km.winthi.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
@Entity
@Table(name = "category")
@Getter
@Setter
@ToString
public class Category {

    @Id
    @GeneratedValue(generator = "generatorMember", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generatorMember", initialValue = 20)
    private Long id;
    @NotEmpty(message = "content may not be empty")
    private String title;
    @NotEmpty(message = "content may not be empty")
    private String icon;
}
