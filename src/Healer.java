import java.util.ArrayList;

public class Healer extends Character {

    public Healer(String name, int strength, int vitality, int intelligence, ArrayList<Item> inventory){
        super(name, strength, vitality, intelligence, inventory);
    }

    //default constructor if needed
    public Healer(){
        super("Unknown", 0, 0, 0, new ArrayList<>());
    }

    @Override
    public void examine(ArrayList<Item> allItems, String examinedItem) {
        super.examine(allItems, examinedItem);
    }

    @Override
    public boolean heal(ArrayList<Character> allCharacters, String healedProtagonist) {
        return super.heal(allCharacters, healedProtagonist);
    }

    @Override
    public void listInventory() {
        System.out.println("---Healer's Inventory---");
        super.listInventory();
    }

    @Override
    public void displayCharacterInfo() {
        System.out.println("---Healer Info---");
        super.displayCharacterInfo();
    }

    @Override
    public void drop(ArrayList<Item> droppedItems, String droppedItem){
        super.drop(droppedItems, droppedItem);
    }

    @Override
    public double calculateDamage() {
        return super.calculateDamage() * getIntelligence();
    }

    @Override
    public boolean attack(String target, ArrayList<Enemy> allEnemies, Character protagonist, ArrayList<Item> droppedItems) {
        return super.attack(target, allEnemies, protagonist, droppedItems);
    }
}