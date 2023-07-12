import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        SecureRandom randomize = new SecureRandom();

        //Creating instances
        ArrayList<Item> fighterInventory = new ArrayList<>();
        ArrayList<Item> healerInventory = new ArrayList<>();
        ArrayList<Item> tankInventory = new ArrayList<>();
        ArrayList<Item> droppedItems = new ArrayList<>();

        Item shortSword = new Weapon("ShortSword", 3, 4);
        Item longSword = new Weapon("Longsword", 4, 5);
        Item excalibur = new Weapon("Excalibur", 11, 6);

        Item smallShield = new Weapon("SmallShield", 4, 3);
        Item hugeShield = new Weapon("HugeShield", 5, 4);

        Item woodWand = new Weapon("WoodWand", 6, 3);
        Item boneWand = new Weapon("BoneWand", 8, 5);


        Item lightArmor = new Clothing("LightArmor", 6, 3);
        Item heavyArmor = new Clothing("HeavyArmor", 8, 5);
        Item hardArmor = new Clothing("HardArmor", 10, 7);

        Item[] items = {shortSword, longSword, excalibur, smallShield, hugeShield, woodWand, boneWand, lightArmor, heavyArmor, hardArmor};
        ArrayList<Item> allItems = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            allItems.add(i, items[i]);
        }

        //Creating character instances
        Character fighter = new Fighter("Fighter", randomize.nextInt(4) + 6, randomize.nextInt(4) + 3, randomize.nextInt(4) + 1, fighterInventory);
        Character healer = new Healer("Healer", randomize.nextInt(4) + 3, randomize.nextInt(4) + 1, randomize.nextInt(4) + 6, healerInventory);
        Character tank = new Tank("Tank", randomize.nextInt(4) + 1, randomize.nextInt(4) + 6, randomize.nextInt(4) + 3, tankInventory);

        fighterInventory.add(shortSword);
        fighter.setWieldedWeapon(shortSword);
        healerInventory.add(woodWand);
        healer.setWieldedWeapon(woodWand);
        tankInventory.add(smallShield);
        tank.setWieldedWeapon(smallShield);

        ArrayList<Character> allCharacters = new ArrayList<>();
        allCharacters.add(fighter);
        allCharacters.add(healer);
        allCharacters.add(tank);

        ArrayList<Enemy> allEnemies = new ArrayList<>();

        String[] commands = {"Attack", "Pick", "Wield", "Wear", "Examine", "List", "Heal", "Drop"};
        String firstWord, secondWord, thirdWord ;

        int level = 0;

        //Game is starting
        while(true) {
            try {
                displayMenu();
                int choice = input.nextInt();
                input.nextLine();
                switch (choice) {
                    case 1 -> {
                        System.out.println("Welcome to the LEGEND ARENA !");
                        System.out.println("Your characters created as:\n");
                        fighter.displayCharacterInfo();
                        healer.displayCharacterInfo();
                        tank.displayCharacterInfo();

                        while (true) {
                            //To check if all the characters are alive or not
                            if (!fighter.isAlive() && !healer.isAlive() && !tank.isAlive()) {
                                System.out.println("All characters have died!");
                                System.out.println("You lost at level:" + level);
                                int score;
                                if (level == 0) {
                                    score = 1;
                                } else {
                                    score = (int) (Math.pow(2, level - 1)) - 1;
                                }
                                System.out.println("Your score is:" + score);
                                break;
                            }

                            int userOrder = 0; //It is arranging the turn between user and enemy side

                            if (IsAllEnemyDead(allEnemies)) {
                                allEnemies.clear();
                                level++;
                                double enemyCount = Math.pow(2, level - 1);
                                int intEnemyCount = (int) enemyCount;
                                System.out.println("Level " + (level) + ". There exists " + intEnemyCount + " enemy.");

                                //Creating new enemies
                                for (int i = 0; i < Math.pow(2, level - 1); i++) {
                                    allEnemies.add(new Enemy(randomize.nextInt(3) + 3, randomize.nextInt(3) + 5, randomize.nextInt(3) + 4));
                                    allEnemies.get(i).setWieldedWeapon(randomItemGenerator(allItems, "weapon"));
                                    allEnemies.get(i).setWornClothing(randomItemGenerator(allItems, "clothing"));
                                }
                            }


                            while (userOrder == 0) {
                                String command = input.nextLine();
                                String[] arr = command.split(" ");

                                if (arr.length != 3 && !command.equalsIgnoreCase("help")) {
                                    System.out.println("You entered fewer or more than 3 words!");
                                } else if (command.equalsIgnoreCase("help")) {
                                    help();
                                } else {
                                    firstWord = arr[0];
                                    secondWord = arr[1];
                                    thirdWord = arr[2];

                                    checkCharacters(allCharacters, firstWord);
                                    checkCommands(commands, secondWord);

                                    //Fighter's part
                                    if (firstWord.equalsIgnoreCase("Fighter")) {
                                        if (fighter.isAlive()) {
                                            if (secondWord.equalsIgnoreCase("List")) {
                                                if (thirdWord.equalsIgnoreCase("Inventory")) {
                                                    fighter.listInventory();
                                                } else if (thirdWord.equalsIgnoreCase("Arena")) {
                                                    displayDroppedItems(droppedItems);
                                                } else {
                                                    System.out.println(thirdWord + " is not valid list type!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Examine")) {
                                                fighter.examine(allItems, thirdWord);
                                            }
                                            if (secondWord.equalsIgnoreCase("Heal")) {
                                                fighter.heal(allCharacters, thirdWord);
                                            }
                                            if (secondWord.equalsIgnoreCase("Pick")) {
                                                if (checkItems(allItems, thirdWord)) {
                                                    fighter.pick(droppedItems, thirdWord);
                                                } else {
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Wield")) {
                                                if (checkItems(allItems, thirdWord)){
                                                    fighter.wield(thirdWord);
                                                } else{
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Wear")) {
                                                if (checkItems(allItems, thirdWord)){
                                                    fighter.wear(thirdWord);
                                                } else{
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Attack")) {
                                                thirdWord = thirdWord.substring(0, 1).toUpperCase() + thirdWord.substring(1).toLowerCase();
                                                boolean attackSuccess = fighter.attack(thirdWord, allEnemies, fighter, droppedItems);
                                                if (attackSuccess) {
                                                    userOrder++;  //It is increased for passing the turn to enemy side
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Drop")) {
                                                if (checkItems(allItems, thirdWord)){
                                                    fighter.drop(droppedItems, thirdWord);
                                                } else{
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                        } else {
                                            System.out.println(fighter.getName() + " is already died!");
                                        }
                                    }

                                    //Healer's part
                                    if (firstWord.equalsIgnoreCase("Healer")) {
                                        if (healer.isAlive()) {
                                            if (secondWord.equalsIgnoreCase("List")) {
                                                if (thirdWord.equalsIgnoreCase("Inventory")) {
                                                    healer.listInventory();
                                                } else if (thirdWord.equalsIgnoreCase("Arena")) {
                                                    displayDroppedItems(droppedItems);
                                                } else {
                                                    System.out.println(thirdWord + " is not valid list type!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Examine")) {
                                                healer.examine(allItems, thirdWord);
                                            }
                                            if (secondWord.equalsIgnoreCase("Heal")) {
                                                boolean healSuccess = healer.heal(allCharacters, thirdWord);
                                                if (healSuccess) {
                                                    userOrder++;  //It is increased for passing the turn to enemy side
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Pick")) {
                                                if (checkItems(allItems, thirdWord)) {
                                                    healer.pick(droppedItems, thirdWord);
                                                } else {
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Wield")) {
                                                if (checkItems(allItems, thirdWord)){
                                                    healer.wield(thirdWord);
                                                } else{
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Wear")) {
                                                if (checkItems(allItems, thirdWord)){
                                                    healer.wear(thirdWord);
                                                } else{
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Attack")) {
                                                boolean attackSuccess = healer.attack(thirdWord, allEnemies, healer, droppedItems);
                                                if (attackSuccess) {
                                                    userOrder++;  //It is increased for passing the turn to enemy side
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Drop")) {
                                                if (checkItems(allItems, thirdWord)){
                                                    healer.drop(droppedItems, thirdWord);
                                                } else{
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                        } else {
                                            System.out.println(healer.getName() + " is already died!");
                                        }
                                    }

                                    //Tank's part
                                    if (firstWord.equalsIgnoreCase("Tank")) {
                                        if (tank.isAlive()) {
                                            if (secondWord.equalsIgnoreCase("List")) {
                                                if (thirdWord.equalsIgnoreCase("Inventory")) {
                                                    tank.listInventory();
                                                } else if (thirdWord.equalsIgnoreCase("Arena")) {
                                                    displayDroppedItems(droppedItems);
                                                } else {
                                                    System.out.println(thirdWord + " is not valid list type!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Examine")) {
                                                tank.examine(allItems, thirdWord);
                                            }
                                            if (secondWord.equalsIgnoreCase("Heal")) {
                                                tank.heal(allCharacters, thirdWord);
                                            }
                                            if (secondWord.equalsIgnoreCase("Pick")) {
                                                if (checkItems(allItems, thirdWord)) {
                                                    tank.pick(droppedItems, thirdWord);
                                                } else {
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Wield")) {
                                                if (checkItems(allItems, thirdWord)) {
                                                    tank.wield(thirdWord);
                                                } else {
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Wear")) {
                                                if (checkItems(allItems, thirdWord)) {
                                                    tank.wear(thirdWord);
                                                } else {
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                            if (secondWord.equalsIgnoreCase("Attack")) {
                                                boolean attackSuccess = tank.attack(thirdWord, allEnemies, tank, droppedItems);
                                                if (attackSuccess) {
                                                    userOrder++;  //It is increased for passing the turn to enemy side
                                                }
                                            }

                                            if (secondWord.equalsIgnoreCase("Drop")) {
                                                if (checkItems(allItems, thirdWord)){
                                                    tank.drop(droppedItems, thirdWord);
                                                } else{
                                                    System.out.println(thirdWord + " does not exist item name!");
                                                }
                                            }
                                        } else {
                                            System.out.println(tank.getName() + " is already died!");
                                        }
                                    }

                                }
                            }  //User turn's has ended

                            for (int j = 0; j < allEnemies.size(); j++) { //Enemy turn
                                if (allEnemies.get(j).getHealthPoint() != 0) {
                                    System.out.print("\nEnemy" + (j+1));
                                    allEnemies.get(j).attack(randomTargetGenerator(allCharacters));
                                    break;
                                }
                            }

                        }
                        System.exit(0);  //Game has finished
                    }
                    case 2 -> displayDetails();

                    default -> System.out.println("You are required to enter only 1 and 2. Try Again!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                input.nextLine();
            }
        }
    }

    public static void displayMenu() {
        System.out.println("*** ENTER 1 TO START THE GAME ***");
        System.out.println("*** ENTER 2 TO VIEW DETAILS ABOUT THE GAME ***");
    }

    public static void displayDetails() {
        System.out.println("*** LEGEND ARENA is a turn-based and text-based game ***");
        System.out.println("*** CHARACTERS ***");
        System.out.println("You have 3 characters: Fighter, Tank, Healer");
        System.out.println("When the game starts, check your characters' information.");
        System.out.println("Their attack damage is calculated by multiplying their weapon value with their different abilities.");
        System.out.println("Fighter's important ability is strength.");
        System.out.println("Tank's important ability is vitality.");
        System.out.println("Healer's important ability is intelligence.");

        System.out.println("\n*** WEAPONS AND ARMORS ***");
        System.out.println("Each weapon has its attack value.");
        System.out.println("Each armor has its protection value.");
        System.out.println("Each character has a carrying capacity. You cannot exceed it.");
        System.out.println("Carrying capacity is calculated by multiplying the character's strength with 3.");

        System.out.println("\n*** GAMEPLAY ***");
        System.out.println("You have to enter 3 words according to the following rules:");
        System.out.println("1. Character Name 2. Command Name 3. Target Name");
        System.out.println("e.g., Tank Wield LongSword");
        System.out.println("Make sure to wield and wear your items. Otherwise, your damage or protection will be useless.");
        System.out.println("Check your inventory to see wielded and worn items.");
        System.out.println("For all names, type HELP in the gameplay.");

        System.out.println("\n*** LEVEL AND SCORE ***");
        System.out.println("For each level, there are (2^level) enemies. The arena is filled with unlimited enemies, so try to reach the highest level possible.");
        System.out.println("When you have killed all the enemies in the current level, you will automatically progress to the next level.");
        System.out.println("Your score is calculated as the sum of the total killed enemies up to your current level.");

        Scanner input = new Scanner(System.in);
        String back;
        do {
            System.out.println("Type BACK to return to the menu");
            back = input.nextLine();
        } while (!back.equalsIgnoreCase("BACK"));
    }

    public static void help() {
        System.out.println("--- CHARACTERS ---");
        System.out.println("Fighter");
        System.out.println("Healer");
        System.out.println("Tank");

        System.out.println("\n--- ITEMS ---");
        System.out.println("ShortSword - Weapon - Recommended for Fighter");
        System.out.println("LongSword - Weapon - Recommended for Fighter");
        System.out.println("Excalibur - Weapon - Recommended for Fighter");
        System.out.println("WoodWand - Weapon - Recommended for Healer");
        System.out.println("BoneWand - Weapon - Recommended for Healer");
        System.out.println("SmallShield - Weapon - Recommended for Tank");
        System.out.println("HugeShield - Weapon - Recommended for Tank");
        System.out.println("LightArmor - Clothing");
        System.out.println("HeavyArmor - Clothing");
        System.out.println("HardArmor - Clothing");

        System.out.println("\n--- COMMANDS + TARGETS ---");
        System.out.println("Help - This command is used by typing only 1 word. It opens the help menu.");
        System.out.println("Attack + Enemy1 - Select your target based on Enemy[code].");
        System.out.println("Pick + ItemName - Add that item to your inventory.");
        System.out.println("Examine + ItemName - Get details about an item.");
        System.out.println("Wear + ClothingName - Wear that clothing.");
        System.out.println("Wield + WeaponName - Wield that weapon.");
        System.out.println("List + Inventory - Lists your character's inventory.");
        System.out.println("List + Arena - Lists the dropped items in the arena.");
        System.out.println("Heal + CharacterName - Heal a character's health.");
        System.out.println("Drop + ItemName - Drop an item to reduce your carrying capacity.");
    }

    public static void checkCharacters(ArrayList<Character> allCharacters, String firstWord) {
        boolean isValid = false;
        for (Character character : allCharacters) {
            if (character.getName().equalsIgnoreCase(firstWord)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            System.out.println(firstWord + " is not a valid character name!");
        }
    }

    public static void checkCommands(String[] commands, String secondWord) {
        boolean isValid = false;
        for (String command : commands) {
            if (command.equalsIgnoreCase(secondWord)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            System.out.println(secondWord + " is not a valid command!");
        }
    }

    public static boolean checkItems(ArrayList<Item> allItems, String thirdWord) {
        for (Item item : allItems) {
            if (item.getName().equalsIgnoreCase(thirdWord)) {
                return true;
            }
        }
        return false;
    }

    public static Character randomTargetGenerator(ArrayList<Character> allCharacters){
        SecureRandom randomizeChars = new SecureRandom();
        while(true){
            int randomNumber = randomizeChars.nextInt(3);
            if (allCharacters.get(randomNumber).getHealthPoint() != 0){
                return allCharacters.get(randomNumber);
            }
        }
    }

    public static Item randomItemGenerator(ArrayList<Item> allItems, String itemType){
        SecureRandom randomize = new SecureRandom();
        Item drawnItem;
        while(true){
            int randomlyItemOrder = randomize.nextInt(allItems.size());
            drawnItem = allItems.get(randomlyItemOrder);
            if (drawnItem instanceof Weapon && itemType.equalsIgnoreCase("weapon")){
                return drawnItem;
            }
            if (drawnItem instanceof Clothing && itemType.equalsIgnoreCase("clothing")){
                return drawnItem;
            }
        }
    }

    public static void displayDroppedItems(ArrayList<Item> droppedItems) {
        if (!droppedItems.isEmpty()) {
            System.out.println("Here is the list of dropped items:");
            for (int i = 0; i < droppedItems.size(); i++) {
                System.out.println("---- " + (i + 1) + " ----");
                System.out.println("Name: " + droppedItems.get(i).getName());
                System.out.println("Value: " + droppedItems.get(i).getValue());
                System.out.println("Weight: " + droppedItems.get(i).getWeight());
            }
        } else {
            System.out.println("There are no dropped items in the arena!");
        }
    }

    public static boolean IsAllEnemyDead(ArrayList<Enemy> allEnemies) {
        for (Enemy enemy : allEnemies) {
            if (enemy.getHealthPoint() != 0) {
                return false;
            }
        }
        return true;
    }

}