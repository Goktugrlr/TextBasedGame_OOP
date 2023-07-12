public class Enemy {

    private double healthPoint;
    private int strength, vitality, intelligence;
    protected Item wieldedWeapon;
    protected Item wornClothing;

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

    public Enemy(int strength, int vitality, int intelligence){
        setStrength(strength);
        setVitality(vitality);
        setIntelligence(intelligence);
        setHealthPoint(Math.round(10 * (0.7*getVitality() + 0.2*getStrength() + 0.1*getIntelligence())));
    }

    private double calculateDamage(Character target){
        if (target.getWornClothing() != null) {  //if target has armor, then applying damage must be decreased
            return getWieldedWeapon().getValue() * getStrength() - target.getWornClothing().getValue();
        }
        else return getWieldedWeapon().getValue() * getStrength();
    }

    public void attack(Character target){
        double damage = calculateDamage(target);
        System.out.print(" does " + calculateDamage(target) + " damage on " + target.getName());

        if (target.getWornClothing() != null) {
            System.out.println("\n" + target.getWornClothing().getValue() + " is absorbed by " + target.getWornClothing().getName());
        }
        System.out.println("\n" + target.getName() + "'s HP was:" + target.getHealthPoint());
        target.setHealthPoint(target.getHealthPoint() - damage);

        if (target.getHealthPoint() <= 0){  //character is checked whether they are alive or not.
            target.setHealthPoint(0);
            System.out.println(target.getName() + " is died. Items were dropped.");
        } else{
            System.out.println("Now, " + target.getName() + "'s HP is:" + target.getHealthPoint() + "/" + target.getMaxHP());
        }
    }

}