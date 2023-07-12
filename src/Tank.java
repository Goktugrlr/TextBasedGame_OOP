import java.util.ArrayList;

public class Tank extends Character {

    public Tank(String name, int strength, int vitality, int intelligence, ArrayList<Item> inventory){
        super(name, strength, vitality, intelligence, inventory);
    }

    //default constructor if needed
    public Tank(){
        super("Unknown", 0, 0, 0, new ArrayList<>());
    }

    @Override
    public void examine(ArrayList<Item> allItems, String examinedItem) {
        super.examine(allItems, examinedItem);
    }

    @Override
    public boolean heal(ArrayList<Character> allCharacters, String healedProtagonist) {
        System.out.println("WARNING! The Fighter does not have permission to perform this command");
        return false;
    }

    @Override
    public void listInventory() {
        System.out.println("---Tank's Inventory---");
        super.listInventory();
    }

    @Override
    public void displayCharacterInfo() {
        System.out.println("---Tank Info---");
        super.displayCharacterInfo();
    }

    @Override
    public void drop(ArrayList<Item> droppedItems, String droppedItem){
        super.drop(droppedItems, droppedItem);
    }

    @Override
    public double calculateDamage() {
        return super.calculateDamage() * getVitality();
    }

    @Override
    public boolean attack(String target, ArrayList<Enemy> allEnemies, Character protagonist, ArrayList<Item> droppedItems) {
        return super.attack(target, allEnemies, protagonist, droppedItems);
    }
}