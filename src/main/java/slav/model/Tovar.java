package main.java.slav.model;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by kuharskiy on 12.01.2016.
 */
public class Tovar {

    private LongProperty ID;
    private StringProperty name;
    //private  DoubleProperty weight;
    private BigDecimal weight;
    private StringProperty units;
    private StringProperty gost;
    //private  DoubleProperty price;
    //private BigDecimal price;
    private SimpleObjectProperty price;
    private IntegerProperty category;
    private ObjectProperty<LocalDate> bestBefore;

    /**
     * Default constructor.
     */
    public Tovar() {
        this.ID = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        //this.weight = new SimpleDoubleProperty();
        this.weight = new BigDecimal("0");
        this.units = new SimpleStringProperty();
        this.gost = new SimpleStringProperty();
        //this.price = new SimpleDoubleProperty();
        //this.price = new BigDecimal("0");
        this.price = new SimpleObjectProperty<BigDecimal>(new BigDecimal("0"));
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
    public Tovar(long ID, String name, BigDecimal weight, String units, String gost, SimpleObjectProperty<BigDecimal> price, Integer category) {
        this.ID = new SimpleLongProperty(ID);
        this.name = new SimpleStringProperty(name);
        //this.weight = new SimpleDoubleProperty(weight);
        this.weight = weight;
        this.bestBefore = new SimpleObjectProperty<LocalDate>(LocalDate.now().plusDays(3)); //по умолчанию добавим к текущей дате 3 дня
        this.units = new SimpleStringProperty(units);
        this.gost = new SimpleStringProperty(gost);
        //this.price = new SimpleDoubleProperty(price);
        this.price = price;
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
        //this.weight = new SimpleDoubleProperty();
        this.weight = new BigDecimal("0");
        this.bestBefore = new SimpleObjectProperty<LocalDate>(LocalDate.now().plusDays(3)); //по умолчанию добавим к текущей дате 3 дня
        this.units = new SimpleStringProperty();
        this.gost = new SimpleStringProperty();
        //this.price = new SimpleDoubleProperty();
        //this.price = new BigDecimal("0");
        this.price = new SimpleObjectProperty<BigDecimal>(new BigDecimal("0"));
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

    public BigDecimal getPrice() {
        return (BigDecimal) price.get();
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal weightProperty() {
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
