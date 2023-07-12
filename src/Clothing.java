public class Clothing extends Item{

    public Clothing(String name, int value, int weight){
        super(name, value, weight);
    }

    @Override
    public void displayItemInfo() {
        System.out.println("Clothing Name:" + getName());
        super.displayItemInfo();
    }
}