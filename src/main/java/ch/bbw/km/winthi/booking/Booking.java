package ch.bbw.km.winthi.booking;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "booking")
@Getter
@Setter
@ToString
public class Booking {

    @Id
    @GeneratedValue(generator = "generatorMember", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generatorMember", initialValue = 20)
    private Long id;
    //    @NotEmpty(message = "content may not be empty")
    private Long memberId;

//    @NotEmpty(message = "content may not be empty")
    private Long roomId;

    @NotEmpty(message = "content may not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startDate;

    @NotEmpty(message = "content may not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endDate;

    @NotEmpty(message = "content may not be empty")
    private String price;

}
