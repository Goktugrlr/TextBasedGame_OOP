import java.util.ArrayList;

public abstract class Character implements IActions {

    public Character(String name,int strength, int vitality, int intelligence, ArrayList<Item> inventory) {
        setName(name);
        setStrength(strength);
        setVitality(vitality);
        setIntelligence(intelligence);
        setHealthPoint(getHealthPoint());
        setInventory(inventory);
        setMaxHP(getMaxHP());
        setHealthPoint(Math.round(10* (0.7*getVitality() + 0.2*getStrength() + 0.1*getIntelligence())));
    }

    //default constructor if needed
    public Character() {
        this.name = "Unknown";
        this.strength = 0;
        this.vitality = 0;
        this.intelligence = 0;
        this.healthPoint = 0.0;
        this.inventory = new ArrayList<>();
        this.maxHP = 0.0;
    }

    protected String name;
    protected int strength, vitality, intelligence;
    protected double healthPoint, maxHP;
    protected ArrayList<Item> inventory;
    protected int carryCapacity;
    protected Item wieldedWeapon, wornClothing;

    public int getCarryCapacity(){
        return 3 * getStrength();
    }
    public void setCarryCapacity(int carryCapacity) {
        this.carryCapacity = carryCapacity;
    }

    public Item getWieldedWeapon() {
        return wieldedWeapon;
    }
    public void setWieldedWeapon(Item wieldedWeapon) {
        this.wieldedWeapon = wieldedWeapon;
    }

    public Item getWornClothing() {
        return wornClothing;
    }
    public void setWornClothing(Item wornClothing) {
        this.wornClothing = wornClothing;
    }


    public ArrayList<Item> getInventory() {
        return inventory;
    }
    public void setInventory(ArrayList<Item> items) {
        this.inventory = items;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getVitality() {
        return vitality;
    }
    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public int getIntelligence() {
        return intelligence;
    }
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public double getHealthPoint() {
        return healthPoint;
    }
    public void setHealthPoint(double healthPoint) {
        this.healthPoint = healthPoint;
    }

    public double getMaxHP(){
        return Math.round(10* (0.7*getVitality() + 0.2*getStrength() + 0.1*getIntelligence()));
    }
    public void setMaxHP(double maxHP){
        this.maxHP = getHealthPoint();
    }

    @Override
    public void examine(ArrayList<Item> allItems, String examinedItem) {
        boolean isValid = false;
        for (Item item : allItems){
            if (item.getName().equalsIgnoreCase(examinedItem)){
                System.out.println(getName() + " has examined " + item.getName());
                item.displayItemInfo();
                isValid = true;
                break;
            }
        }
        if (!isValid){
            System.out.println("The item " + examinedItem + " does not valid!");
        }
    }

    @Override
    public void wield(String wieldedWeapon) {
        boolean inInventory = false;
        for (int i=0; i<getInventory().size(); i++){
            if (getInventory().get(i).getName().equalsIgnoreCase(wieldedWeapon)){
                inInventory = true;
                if (getInventory().get(i) instanceof Weapon){
                    System.out.println(getInventory().get(i).getName() + " wielded by " + getName());
                    setWieldedWeapon(getInventory().get(i));
                } else{
                    System.out.println(getInventory().get(i).getName() + " is clothing. You can use wield command for only weapons!");
                }
                break;
            }
        }
        if (!inInventory){
            System.out.println(wieldedWeapon + " not found in inventory!");
        }
    }

    @Override
    public void wear(String wornClothing) {
        boolean inInventory = false;
        for (int i=0; i<getInventory().size(); i++){
            if (getInventory().get(i).getName().equalsIgnoreCase(wornClothing)){
                inInventory = true;
                if (getInventory().get(i) instanceof Clothing){
                    System.out.println(getInventory().get(i).getName() + " worn by " + getName());
                    setWornClothing(getInventory().get(i));
                } else{
                    System.out.println(getInventory().get(i).getName() + " is weapon. You can use wear command for only clothing!");
                }
                break;
            }
        }
        if (!inInventory){
            System.out.println(wornClothing + " not found in inventory!");
        }
    }

    @Override
    public void pick(ArrayList<Item> droppedItems, String pickedItem) {
        boolean isDropped = false;
        for (int i=0; i<droppedItems.size(); i++){
            if (droppedItems.get(i).getName().equalsIgnoreCase(pickedItem)){
                isDropped = true;
                if (calculateCurrCapacity() + droppedItems.get(i).getWeight() <= getCarryCapacity()){
                    getInventory().add(droppedItems.get(i));
                    droppedItems.remove(i);
                    System.out.println(getName() + " picked " + pickedItem + " successfully.");
                    break;
                } else{
                    System.out.println(getName() + " can not picked " + droppedItems.get(i).getName() + " because of it exceeds carry limit.You may drop some items.");
                }
            }
        }
        if (!isDropped){
            System.out.println(pickedItem + " may not dropped yet or already taken by other protagonists!");
        }
    }

    @Override
    public void drop(ArrayList<Item> droppedItems, String droppedItem){
        boolean inInventory = false;
        for (int i=0; i< getInventory().size(); i++){
            if (getInventory().get(i).getName().equalsIgnoreCase(droppedItem)){
                inInventory = true;
                if (getWieldedWeapon() != null && droppedItem.equalsIgnoreCase(getWieldedWeapon().getName())){
                    setWieldedWeapon(null);
                } else if (getWornClothing() != null && droppedItem.equalsIgnoreCase(getWornClothing().getName())){
                    setWornClothing(null);
                }
                droppedItems.add(getInventory().get(i));
                setCarryCapacity(getCarryCapacity()-getInventory().get(i).getWeight());
                getInventory().remove(i);
                System.out.println(droppedItem + " was dropped successfully!");
                break;
            }
        }
        if (!inInventory){
            System.out.println(droppedItem + " can not found in the inventory!");
        }
    }

    @Override
    public boolean heal(ArrayList<Character> allCharacters, String healedProtagonist) {
        for (Character character : allCharacters) {
            if (character.getName().equalsIgnoreCase(healedProtagonist)) {
                if (character.getHealthPoint() != 0) {
                    double requiredHP = character.getMaxHP() - character.getHealthPoint();
                    double healAmount;
                    if (getWieldedWeapon() != null) {
                        healAmount = getWieldedWeapon().getValue() * getIntelligence();
                    } else {
                        healAmount = 0;
                    }

                    if (requiredHP < healAmount && requiredHP != 0) {
                        character.setHealthPoint(character.getMaxHP());
                        System.out.println("Healer is healing " + healedProtagonist + " as " + requiredHP + " HP.");
                        System.out.println("Now " + healedProtagonist + " has reached maximum HP: " + character.getHealthPoint());
                    } else if (requiredHP == 0) {
                        System.out.println(character.getName() + "'s HP is already max. You cannot heal!");
                        return false;
                    } else {
                        character.setHealthPoint(character.getHealthPoint() + healAmount);
                        System.out.println("Healer is healing " + healedProtagonist + " as " + healAmount + " HP.");
                        System.out.println("Current " + healedProtagonist + "'s HP is: " + character.getHealthPoint() + "/" + character.getMaxHP());
                    }
                    return true;
                } else {
                    System.out.println(character.getName() + " has died! You cannot heal anymore.");
                    return false;
                }
            }
        }
        System.out.println(healedProtagonist + " is not valid to heal!");
        return false;
    }

    @Override
    public void listInventory() {
        for (int i=0; i<getInventory().size(); i++){
            String currItem = getInventory().get(i).getName();
            if (getWieldedWeapon() != null && currItem.equalsIgnoreCase(getWieldedWeapon().getName())){
                System.out.println(getWieldedWeapon().getName() + " (Wielded)");
            }
            if (getWornClothing() != null && currItem.equalsIgnoreCase(getWornClothing().getName())){
                System.out.println(getWornClothing().getName() + " (Worn)");
            }
            getInventory().get(i).displayItemInfo();
        }
        System.out.println("### WEIGHT: (" + calculateCurrCapacity() + "/" + getCarryCapacity() + ") ###");
    }

    private int calculateCurrCapacity(){
        int sumWeight = 0;
        for (int i=0; i<getInventory().size(); i++){
            sumWeight += getInventory().get(i).getWeight();
        }
        return sumWeight;
    }

    @Override
    public double calculateDamage() {
        if (getWieldedWeapon() != null) {
            return getWieldedWeapon().getValue();
        } else {
            return 0;
        }
    }

    public void displayCharacterInfo(){
        System.out.println("Strength:" + getStrength());
        System.out.println("Vitality:" + getVitality());
        System.out.println("Intelligence:" + getIntelligence());
        System.out.println("HP:" + getHealthPoint());
        for (int i=0; i<getInventory().size(); i++){
            getInventory().get(i).displayItemInfo();
        }
        System.out.println();
    }

    public boolean attack(String target, ArrayList<Enemy> allEnemies, Character protagonist, ArrayList<Item> droppedItems){
        if (protagonist.getName().equalsIgnoreCase(target)){  //to prevent the player from causing damage to themselves:
            System.out.println("You can not attack yourself!");
        }
        else if (target.equalsIgnoreCase("Fighter") || target.equalsIgnoreCase("Tank") || target.equalsIgnoreCase("Healer")){
            System.out.println("Nobody wants to hurt teammates, right?"); // To prevent the player from causing damage to teammates:
        }
        else if (target.startsWith("Enemy")){
            String[] enemyCode = target.split("Enemy");
            int intEnemyCode = Integer.parseInt(enemyCode[1]) - 1;
            
            if (intEnemyCode < allEnemies.size() && -1 < intEnemyCode){
                if (allEnemies.get(intEnemyCode).getHealthPoint() == 0) {
                    System.out.println("The target " + target + " is already died!");
                } else {
                    System.out.println(target + "'s HP was:" + allEnemies.get(intEnemyCode).getHealthPoint());
                    double damage;
                    if (getWieldedWeapon() != null){
                        damage = calculateDamage() - allEnemies.get(intEnemyCode).getWornClothing().getValue();
                        System.out.println(protagonist.getName() + " does " + damage + " damage!");
                        System.out.println(allEnemies.get(intEnemyCode).getWornClothing().getValue() + " is absorbed by " + allEnemies.get(intEnemyCode).getWornClothing().getName());
                    } else{
                        damage = calculateDamage();
                        System.out.println(protagonist.getName() + " does " + damage + " damage because of " + protagonist.getName() + " has not any weapon!");
                    }

                    allEnemies.get(intEnemyCode).setHealthPoint(allEnemies.get(intEnemyCode).getHealthPoint() - damage);

                    if (allEnemies.get(intEnemyCode).getHealthPoint() <= 0) {  //if the enemy has been killed by player
                        System.out.println(target + " is died!" + target + "'s items are dropped.");
                        droppedItems.add(allEnemies.get(intEnemyCode).getWieldedWeapon());
                        droppedItems.add(allEnemies.get(intEnemyCode).getWornClothing());
                        allEnemies.get(intEnemyCode).setHealthPoint(0);
                    } else {
                        System.out.println(target + "'s current HP is:" + allEnemies.get(intEnemyCode).getHealthPoint()); //if the enemy has not yet been killed by player
                    }
                    return true;
                }
            } else{
                System.out.println(target + " does not exist in this level!");  //meaningful enemy but not available in that level
            }
        } else{
            System.out.println("The target " + target + " does not exist!");  //if user enters meaningless enemy
        }
        return false;
    }

    public boolean isAlive() {
        return getHealthPoint() != 0;
    }

}