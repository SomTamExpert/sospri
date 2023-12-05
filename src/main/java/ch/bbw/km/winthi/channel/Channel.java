package ch.bbw.km.winthi.channel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "channel")
@Getter
@Setter
@ToString
    public class Channel {
    @Id
    @GeneratedValue(generator = "generatorMember", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generatorMember", initialValue = 20)
    private Long id;
    @NotEmpty(message = "content may not be empty")
    private String topic;
    @NotEmpty(message = "content may not be empty")
    private String description;

}
