public class Weapon extends Item{

    public Weapon(String name, int value, int weight){
        super(name, value, weight);
    }

    @Override
    public void displayItemInfo() {
        System.out.println("Weapon Name:" + getName());
        super.displayItemInfo();
    }
}