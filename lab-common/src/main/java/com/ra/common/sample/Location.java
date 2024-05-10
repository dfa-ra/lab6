package com.ra.common.sample;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Класс локации с координатами x, y, z и названием локации
 * @author Захарчекно Роман
 */

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Serializable {
    /**
     * Координата x, не может быть null.
     */
    @NotNull(message = "Error! Location x can't be null!")
    private double x; //Поле не может быть null
    /**
     * Координата y, не может быть null.
     */
    @NotNull(message = "Error! Location y can't be null!")
    private Long y; //Поле не может быть null
    /**
     * Координата z, не может быть null.
     */
    @NotNull(message = "Error! Location z can't be null!")
    private float z; //Поле не может быть null
    /**
     * Название локации, может быть null.
     */
    private String name; //Поле может быть null
    @JsonCreator
    public Location(@JacksonXmlProperty(localName = "x") String x,
                    @JacksonXmlProperty(localName = "y") String y,
                    @JacksonXmlProperty(localName = "z") String z,
                    @JacksonXmlProperty(localName = "name") String name) {
        try{
            this.x = Double.parseDouble(x);
            this.y = Long.parseLong(y);
            this.z = Float.parseFloat(z);
            this.name = name;
        }catch (IllegalArgumentException e) {
            System.out.println("There are incorrect types in the database! Check the database! This ticket was not cancelled! (Location)");
        }catch (NullPointerException e){
            System.out.println("There are incorrect types in the database! Check the database! This ticket was not cancelled! (Location)");
        }
    }

    /**
     * Переопределённый метод toString.
     * @return возвращает читаемую и информативную строку с координатами (x, y, z) и названием локации
     */
    @Override
    public String toString() {
        return "{" +
                "\n\t\t\tx=" + x +
                "\n\t\t\ty=" + y +
                "\n\t\t\tz=" + z +
                "\n\t\t\tname='" + name + '\'' +
                "\n\t\t}";
    }
}

