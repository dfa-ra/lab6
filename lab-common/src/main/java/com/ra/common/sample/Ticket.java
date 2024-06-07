package com.ra.common.sample;

import com.ra.common.enum_.TicketType;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;


/**
 * Класс билетов и описания билетов (какой, чей и т.д)
 * @author Захарченко Роман
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket implements Comparable<Ticket>, Serializable {
    /**
     * Id каждого билета. Не может быть null, значение должно быть больше 0, должно быть уникальным, должно генерироваться автоматически
     */
    @NotNull(message = "Error! Id can't be null!")
    @Min(value = 0, message = "Error! The id value must be greater than 0!")
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /**
     * Имя человека которому принадлежит билет. Не может быть null, не может быть пустой
     */
    @NotNull(message = "Error! Name can't be null!")
    @NotEmpty(message = "Error! Name can't be empty!")
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * Координаты, не может быть null
     */
    @Valid
    @NotNull(message = "Error! Coordinates can't be null!")
    private Coordinates coordinates = null; //Поле не может быть null
    /**
     * Дата создания билета. Не может быть null, генерируется автоматически
     */
    @NotNull(message = "Error! Creation sate can't be null!")
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * Цена билета. Не может быть null, должно быть больше 0
     */
    @NotNull(message = "Error! Price can't be null!")
    @Min(value = 0, message = "The price value must be greater than 0")
    private Double price; //Поле не может быть null, Значение поля должно быть больше 0
    /**
     * Комментарий к биллету.
     */
    @NotEmpty(message = "Error! Comment can't be empty!")
    private String comment; //Строка не может быть пустой, Поле может быть null
    /**
     * Возвратность билет.
     */
    private Boolean refundable; //Поле может быть null
    /**
     * Тип билета.
     */
    @NotNull(message = "Error! Ticket type can't be null!")
    private TicketType type; //Поле не может быть null
    /**
     * Некоторая информация о человеке.
     */
    @Valid
    private Person person = null; //Поле может быть null

    public Ticket(String id,
                  String name,
                  Coordinates coordinates,
                  String creationDate,
                  String price,
                  String comment,
                  String refundable,
                  String type,
                  Person person) {
        try {
            this.id = Long.parseLong(id);
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate = ZonedDateTime.parse(creationDate);
            this.price = Double.parseDouble(price);
            this.comment = comment;
            this.refundable = Boolean.parseBoolean(refundable);
            this.type = TicketType.valueOf(type);
            this.person = person;
        }catch (IllegalArgumentException e) {
            System.out.println("There are incorrect types in the database! Check the database! Null is placed instead of the faulty type! (Ticket)");
        }catch (NullPointerException e) {
            System.out.println("There are incorrect types in the database! Check the database! Null is placed instead of the faulty type! (Ticket)");
        }
    }

    public String[] mytoString() {
        if (person == null){
            String[] res = {
                    name,
                    String.valueOf(coordinates.getX()),
                    String.valueOf(coordinates.getY()),
                    String.valueOf(creationDate),
                    String.valueOf(price),
                    comment,
                    String.valueOf(refundable),
                    String.valueOf(type),
                    "-",
                    "-",
                    "-",
                    "-",
                    "-",
                    "-",
            };
            return res;
        }else{
            String[] res = {
                    name,
                    String.valueOf(coordinates.getX()),
                    String.valueOf(coordinates.getY()),
                    String.valueOf(creationDate),
                    String.valueOf(price),
                    comment,
                    String.valueOf(refundable),
                    String.valueOf(type),
                    String.valueOf(person.getBirthday()),
                    String.valueOf(person.getHairColor()),
                    String.valueOf(person.getLocation().getX()),
                    String.valueOf(person.getLocation().getY()),
                    String.valueOf(person.getLocation().getZ()),
                    person.getLocation().getName()
            };
            return res;
        }

    }

    /**
     * Переопределённый компаратор. Создан для сортировки элементов коллекции по id.
     */
        @Override
        public int compareTo(Ticket o) {
            return (int) (this.getId() - o.getId());
        }
}
