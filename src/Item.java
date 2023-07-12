public class Item {

    protected String name;
    protected int weight, value;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public Item(String name, int value, int weight){
        setName(name);
        setWeight(value);
        setValue(weight);
    }

    //default constructor
    public Item(){
        this.name = "Unknown";
        this.value = 0;
        this.weight = 0;
    }

    public void displayItemInfo(){
        System.out.println(getName() + " value:" + getValue());
        System.out.println(getName() + " weight:" + getWeight());
        System.out.println();
    }
}