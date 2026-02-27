package com.narxoz.rpg.battle;

import java.util.List;
import java.util.Random;

public final class BattleEngine {
    private static BattleEngine instance;
    private Random random = new Random(1L);

    private BattleEngine() {
    }

    public static BattleEngine getInstance() {
        if (instance == null) {
            instance = new BattleEngine();
        }
        return instance;
    }

    public BattleEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public void reset() {
        
    }

    public EncounterResult runEncounter(List<Combatant> teamA, List<Combatant> teamB) {
        EncounterResult result = new EncounterResult();
        int rounds=0;
        while(hasLiving(teamA) && hasLiving(teamB)){
            rounds++;
            result.addLog("===ROUND "+ rounds+" ===");
            for(Combatant attacker : teamA){
                if(!attacker.isAlive()) continue;

                Combatant target=firstLiving(teamB);
                if(target==null) break;

                int damage=attacker.getAttackPower();
                target.takeDamage(damage);

                result.addLog(attacker.getName()+" hits "+target.getName()+" for "+damage);

                if(!target.isAlive()) {
                    result.addLog(target.getName()+" is defeated!");
                }
            }
            for (Combatant attacker : teamB){
                if (!attacker.isAlive()) continue;

                Combatant target=firstLiving(teamA);
                if(target==null) break;

                int damage =attacker.getAttackPower();
                target.takeDamage(damage);

                result.addLog(attacker.getName()+" hits "+target.getName()+" for " + damage);

                if(!target.isAlive()){
                    result.addLog(target.getName() +" is defeated!");
                }
            }
        }
        result.setRounds(rounds);
        if(hasLiving(teamA)){
            result.setWinner("Heroes");
        } 
        else{
            result.setWinner("Enemies");
        }

        return result;
    }
    private boolean hasLiving(List<Combatant> team) {
        for(Combatant c : team) {
            if(c.isAlive()) return true;
        }
        return false;
    }
    private Combatant firstLiving(List<Combatant> team){
        for(Combatant c : team){
            if (c.isAlive()) return c;
        }
        return null;
    }
}
