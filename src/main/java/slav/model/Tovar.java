package main.java.slav.model;

import javafx.beans.property.*;

import java.time.LocalDate;


/**
 * Created by kuharskiy on 12.01.2016.
 */
public class Tovar {

    private final LongProperty ID;
    private final StringProperty name;
    private final DoubleProperty weight;
    private final StringProperty units;
    private final StringProperty gost;
    private final DoubleProperty price;
    private final IntegerProperty category;
    private final ObjectProperty<LocalDate> bestBefore;

    /**
     * Default constructor.
     */
    public Tovar() {
        this.ID = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.weight = new SimpleDoubleProperty();
        this.units = new SimpleStringProperty();
        this.gost = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.category = new SimpleIntegerProperty();
        this.bestBefore = new SimpleObjectProperty<LocalDate>(LocalDate.now().plusDays(3)); //по умолчанию добавим к текущей дате 3 дня
    }

    /**
     * Constructor with some initial data.
     *  @param ID
     * @param name
     * @param weight
     * @param price
     */
    public Tovar(long ID, String name, Double weight, String units, String gost, Double price, Integer category) {
        this.ID = new SimpleLongProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.weight = new SimpleDoubleProperty(weight);
        this.bestBefore = new SimpleObjectProperty<LocalDate>(LocalDate.now().plusDays(3)); //по умолчанию добавим к текущей дате 3 дня
        this.units = new SimpleStringProperty(units);
        this.gost = new SimpleStringProperty(gost);
        this.price = new SimpleDoubleProperty(price);
        this.category = new SimpleIntegerProperty(category);
    }

    /**
     * Constructor only with ID
     *
     * @param ID
     */
    public Tovar(Integer ID) {
        this.ID = new SimpleLongProperty(ID);
        this.name = new SimpleStringProperty();
        this.weight = new SimpleDoubleProperty();
        this.bestBefore = new SimpleObjectProperty<LocalDate>(LocalDate.now().plusDays(3)); //по умолчанию добавим к текущей дате 3 дня
        this.units = new SimpleStringProperty();
        this.gost = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.category = new SimpleIntegerProperty();
    }

    public long getID() {
        return ID.get();
    }

    public void setID(long ID) {
        this.ID.set(ID);
    }

    public LongProperty IDProperty() {
        return ID;
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

    public String getGost() {
        return gost.get();
    }

    public void setGost(String gost) {
        this.gost.set(gost);
    }

    public StringProperty gostProperty() {
        return gost;
    }

    public String getUnits() {
        return units.get();
    }

    public void setUnits(String units) {
        this.units.set(units);
    }

    public StringProperty unitsProperty() {
        return units;
    }

    public int getCategory() {
        return category.get();
    }

    public void setCategory(int category) {
        this.category.set(category);
    }

    public IntegerProperty categoryProperty() {
        return category;
    }

    @Override
    public String toString() {
        return "Tovar{" +
                "ID=" + ID +
                ", name=" + name +
                ", weight=" + weight +
                ", units=" + units +
                ", gost=" + gost +
                ", price=" + price +
                ", category=" + category +
                ", bestBefore=" + bestBefore +
                '}';
    }
}
