import java.util.ArrayList;

public interface IActions {

    boolean attack(String target, ArrayList<Enemy> allEnemies, Character protagonist, ArrayList<Item> droppedItems);
    double calculateDamage();
    void pick(ArrayList<Item> droppedItems, String pickedItem);
    void wield(String wieldedWeapon);
    void wear(String wornClothing);
    void examine(ArrayList<Item> allItems, String examinedItem);
    void listInventory();
    boolean heal(ArrayList<Character> allCharacters, String healedProtagonist);
    void drop(ArrayList<Item> droppedItems, String droppedItem);

}