package main.java.slav.model;

import javafx.beans.property.*;

import java.time.LocalDate;


/**
 * Created by kuharskiy on 12.01.2016.
 */
public class Tovar {

    private final IntegerProperty code;
    private final StringProperty name;
    private final DoubleProperty price;
    private final DoubleProperty weight;
    private final ObjectProperty<LocalDate> bestBefore;

    /**
     * Default constructor.
     */
    public Tovar() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param name
     * @param code
     */
    public Tovar(Integer code, String name) {
        this.name = new SimpleStringProperty(name);
        this.code = new SimpleIntegerProperty(code);

        // Some initial dummy data, just for convenient testing.
        this.price = new SimpleDoubleProperty(Math.round(Math.random() * 200) / 10.0d);
        this.weight = new SimpleDoubleProperty(Math.round(Math.random() * 10) / 10.0d);
        this.bestBefore = new SimpleObjectProperty<LocalDate>(LocalDate.of(2016, 1, 12));
    }

    public int getCode() {
        return code.get();
    }

    public void setCode(int code) {
        this.code.set(code);
    }

    public IntegerProperty codeProperty() {
        return code;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public double getWeight() {
        return weight.get();
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    public LocalDate getBestBefore() {
        return bestBefore.get();
    }

    public void setBestBefore(LocalDate bestBefore) {
        this.bestBefore.set(bestBefore);
    }

    public ObjectProperty<LocalDate> bestBeforeProperty() {
        return bestBefore;
    }
}
