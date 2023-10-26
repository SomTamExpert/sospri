package ch.bbw.pr.sospri.message;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import ch.bbw.pr.sospri.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A chat message
 *
 * @author Marco Karpf
 * @version 13.04.2023
 */
@Entity
@Table(name = "message")
@Getter
@Setter
@ToString
public class Message {
    @Id
    @GeneratedValue(generator = "generatorMessage", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generatorMessage", initialValue = 10)
    private Long id;

    @NotEmpty(message = "content may not be empty")
    @Size(min = 2, max = 1012, message = "Die Länge der Message muss 2 bis 512 Zeichen sein.")
    private String content;

    //@NotEmpty (message = "author may not be empty" )
    @Size(min = 5, max = 50, message = "Die Länge des Autors 2 bis 20 Zeichen sein.")
    private String author;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date origin;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "channel_id")
    private Channel channel;
}
