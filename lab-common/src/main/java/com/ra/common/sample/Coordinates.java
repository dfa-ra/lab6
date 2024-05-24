package com.ra.common.sample;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Класс координат (x, y)
 * @author Захарченко Роман
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates implements Serializable {
    /**
     * Координата x, значение должно быть больше -785.
     */
    @Min(value = -785, message = "Error! The coordinates x value must be greater than -785")
    private float x; //Значение поля должно быть больше -785
    /**
     * Координата y, не может быть null.
     */
    @NotNull(message = "Error! Coordinates y can't be null!")
    private double y; //Поле не может быть null
    public Coordinates (String x,
                        String y){
        try{
            this.x = Float.parseFloat(x);
            this.y = Double.parseDouble(y);
        }catch (IllegalArgumentException e) {
            System.out.println("There are incorrect types in the database! Check the database! This ticket was not cancelled! (Coordinates)");
        }catch (NullPointerException e){
            System.out.println("There are incorrect types in the database! Check the database! This ticket was not cancelled! (Coordinates)");
        }
    }
    @Override
    public String toString() {
        return "{" +
                "\n\t\tx=" + x +
                "\n\t\ty=" + y +
                "\n\t}";
    }
}
