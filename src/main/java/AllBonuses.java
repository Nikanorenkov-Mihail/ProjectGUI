public class AllBonuses {
    Bonus[] bonusesExist = new Bonus[200];// можем менять количество бонусов

    public void addRandoBonuses(int gridHight, int gridWight) {
        Bonus newBonus = new Bonus(1, 1);
        for (int i = 0; i < bonusesExist.length; i++) {
            bonusesExist[i] = newBonus.newRandomBonus(gridHight, gridWight);
        }
    }
}
