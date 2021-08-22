package model;

import java.math.BigDecimal;
import java.util.Objects;

public class Ticket extends AbstractReadFile {

    private String movieName;
    private Type type;
    private BigDecimal price;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal colPrice() {
        //use movie and type
        Movie movie = Movie.getByName(movieName);
        assert movie != null;
        BigDecimal price = getPriceCol(movie.getPrice(), type.value);
        this.price = price;
        return price;
    }

    public enum Type {
        CHILD_MATINEE(0),
        CHILD_PRIMETIME(1),
        ADULT_MATINEE(2),
        ADULT_PRIMETIME(3),
        SENIOR_MATINEE(4),
        SENIOR_PRIMETIME(5);

        private Type(int i) {
            value = i;
        }

        int value;
    }

    @Override
    public AbstractReadFile readFromString(String s) {
        return null;
    }

    @Override
    public String toString() {
        return movieName + SEPARATOR_2 + type + SEPARATOR_2 + price;
    }

    public BigDecimal getPriceCol(BigDecimal price, int type) {
        switch (type) {
            case 0:
                return price.multiply(BigDecimal.valueOf(0.375));
            case 1:
                return price.multiply(BigDecimal.valueOf(0.75));
            case 2:
                return price.multiply(BigDecimal.valueOf(0.5));
            case 3:
                return price;
            case 4:
                return price.multiply(BigDecimal.valueOf(0.375));
            case 5:
                return price.multiply(BigDecimal.valueOf(0.75));
            default:
                return price.multiply(BigDecimal.valueOf(0.5));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(movieName, ticket.movieName) &&
                type == ticket.type &&
                Objects.equals(price, ticket.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieName, type, price);
    }
}