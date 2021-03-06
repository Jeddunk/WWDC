package ttk;

import java.awt.Font;
import java.awt.FontMetrics;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import etc.Constants;
import etc.TTKNamePair;
import main.Main;
import main.Main.DoTPair;
import mods.Mod;


public class TTKTarget implements Comparable{
  
  /**
   * ____________________________________________________________
   * CLASS VARIABLES
   * ____________________________________________________________
   */
  //Target Stats
  public int group = 0;
  public int baseLevel = 0;
  public int currentLevel = 0;
  public int baseArmor = 0;
  public int baseHealth = 0;
  public int baseShields = 0;
  public int maxArmor = 0;
  public int maxShields = 0;
  public int maxHealth = 0;
  public String surfaceType = "";
  public String armorType = "";
  public String shieldType = "";
  public String factionType = "";
  public String name = "";
  public double TTK = 0.0;
  public double minTTK = 0.0;
  public double maxTTK = 0.0;
  public Vector<Double> TTKVec = new Vector<Double>();
  
  //TTKSim Stats
  public double impactMult = 1.0;
  public double punctureMult = 1.0;
  public double slashMult = 1.0;
  public double fireMult = 1.0;
  public double iceMult = 1.0;
  public double electricMult = 1.0;
  public double toxinMult = 1.0;
  public double blastMult = 1.0;
  public double radiationMult = 1.0;
  public double gasMult = 1.0;
  public double corrosiveMult = 1.0;
  public double viralMult = 1.0;
  public double magneticMult = 1.0;
  public double armorImpactMult = 1.0;
  public double armorPunctureMult = 1.0;
  public double armorSlashMult = 1.0;
  public double armorFireMult = 1.0;
  public double armorIceMult = 1.0;
  public double armorElectricMult = 1.0;
  public double armorToxinMult = 1.0;
  public double armorBlastMult = 1.0;
  public double armorRadiationMult = 1.0;
  public double armorGasMult = 1.0;
  public double armorCorrosiveMult = 1.0;
  public double armorViralMult = 1.0;
  public double armorMagneticMult = 1.0;
  public double shieldImpactMult = 1.0;
  public double shieldPunctureMult = 1.0;
  public double shieldSlashMult = 1.0;
  public double shieldFireMult = 1.0;
  public double shieldIceMult = 1.0;
  public double shieldElectricMult = 1.0;
  public double shieldToxinMult = 1.0;
  public double shieldBlastMult = 1.0;
  public double shieldRadiationMult = 1.0;
  public double shieldGasMult = 1.0;
  public double shieldCorrosiveMult = 1.0;
  public double shieldViralMult = 1.0;
  public double shieldMagneticMult = 1.0;
  public double typeMult = 1.0;
  public double DoTBase = 0.0;
  public double localProjectileCount = 1.0;
  public double millisceondsPerShot = 0.0;
  public double millisecondMult = 1.0;
  public double reloadTimeMilliseconds = 0.0;
  public double baseImpactDamage = 0.0;
  public double basePunctureDamage = 0.0;
  public double baseSlashDamage = 0.0;
  public double baseFireDamage = 0.0;
  public double baseIceDamage = 0.0;
  public double baseElectricDamage = 0.0;
  public double baseToxinDamage = 0.0;
  public double baseBlastDamage = 0.0;
  public double baseCorrosiveDamage = 0.0;
  public double baseGasDamage = 0.0;
  public double baseMagneticDamage = 0.0;
  public double baseRadiationDamage = 0.0;
  public double baseViralDamage = 0.0;
  public double corrosiveProjectionMult = 0.0;
  public Vector<Integer> impactStacks = new Vector<Integer>();
  public Vector<Integer> punctureStacks = new Vector<Integer>();
  public Vector<DoTPair> slashStacks = new Vector<DoTPair>();
  public Vector<DoTPair> fireStacks = new Vector<DoTPair>();
  public Vector<Integer> iceStacks = new Vector<Integer>();
  public Vector<Integer> electricStacks = new Vector<Integer>();
  public Vector<DoTPair> toxinStacks = new Vector<DoTPair>();
  public Vector<Integer> blastStacks = new Vector<Integer>();
  public Vector<Integer> radiationStacks = new Vector<Integer>();
  public Vector<DoTPair> gasStacks = new Vector<DoTPair>();
  public Vector<Integer> corrosiveStacks = new Vector<Integer>();
  public Vector<Integer> viralStacks = new Vector<Integer>();
  public Vector<Integer> magneticStacks = new Vector<Integer>();
  public Random rng = new Random();
  
  /**
   * ____________________________________________________________
   * METHODS
   * ____________________________________________________________
   */
  
  /**
   * CTOR from a saved string
   * @param saveStr
   */
  public TTKTarget(String saveStr){
    String[] split = saveStr.split(",");
    name = split[0];
    baseLevel = Integer.parseInt(split[1]);
    currentLevel =  Integer.parseInt(split[2]);
    baseArmor =  Integer.parseInt(split[3]);
    baseHealth =  Integer.parseInt(split[4]);
    baseShields =  Integer.parseInt(split[5]);
    surfaceType = split[6];
    armorType = split[7];
    shieldType = split[8];
    factionType = split[9];
    try{
      group = Integer.parseInt(split[10]);
    }catch(Exception ex){
      //Legacy, default to group 0
    }
    
    maxArmor = (int)((Math.pow((currentLevel - baseLevel),1.75) * 0.005 * baseArmor) + baseArmor);
    maxShields = (int)((Math.pow((currentLevel - baseLevel),2.0) * 0.0075 * baseShields) + baseShields);
    maxHealth = (int)((Math.pow((currentLevel - baseLevel),2.0) * 0.015 * baseHealth) + baseHealth);
    
    System.out.println(name+","+currentLevel+","+maxArmor+","+maxHealth+","+maxShields);
  }
  
  /**
   * Builds a string to save this TTK target by
   * @return save string
   */
  public String buildSaveString(){
    return name+","+baseLevel+","+currentLevel+","+baseArmor+","+baseHealth+","+baseShields+","+surfaceType+","+armorType+","+shieldType+","+factionType+","+group;
  }
  
  /**
   * Runs an advanced TTK calculation
   */
  public void runAdvancedTTK(){
    
    //Health Mults
    if(surfaceType.equals(Constants.ENEMY_SURFACE_CLONE_FLESH)){
      impactMult = 0.75;
      punctureMult = 1.0;
      slashMult = 1.25;
      fireMult = 1.25;
      iceMult = 1.0;
      electricMult = 1.0;
      toxinMult = 1.0;
      blastMult = 1.0;
      radiationMult = 1.0;
      gasMult = 0.5;
      corrosiveMult = 1.0;
      viralMult = 1.75;
      magneticMult = 1.0;
    }else if(surfaceType.equals(Constants.ENEMY_SURFACE_MECHANICAL)){
      impactMult = 1.25;
      punctureMult = 1.0;
      slashMult = 1.0;
      fireMult = 1.0;
      iceMult = 1.0;
      electricMult = 1.5;
      toxinMult = 0.75;
      blastMult = 1.75;
      radiationMult = 1.0;
      gasMult = 1.0;
      corrosiveMult = 1.0;
      viralMult = 0.75;
      magneticMult = 1.0;
    }else if(surfaceType.equals(Constants.ENEMY_SURFACE_CORPUS_FLESH)){
      impactMult = 0.75;
      punctureMult = 1.0;
      slashMult = 1.25;
      fireMult = 1.0;
      iceMult = 1.0;
      electricMult = 1.0;
      toxinMult = 1.5;
      blastMult = 1.0;
      radiationMult = 1.0;
      gasMult = 0.75;
      corrosiveMult = 1.0;
      viralMult = 1.5;
      magneticMult = 1.0;
    }else if(surfaceType.equals(Constants.ENEMY_SURFACE_INFESTED_FLESH)){
      impactMult = 1.0;
      punctureMult = 1.0;
      slashMult = 1.5;
      fireMult = 1.5;
      iceMult = 0.5;
      electricMult = 1.0;
      toxinMult = 1.0;
      blastMult = 1.0;
      radiationMult = 1.0;
      gasMult = 1.5;
      corrosiveMult = 1.0;
      viralMult = 1.0;
      magneticMult = 1.0;
    }else if(surfaceType.equals(Constants.ENEMY_SURFACE_FOSSILIZED)){
      impactMult = 1.0;
      punctureMult = 1.0;
      slashMult = 1.15;
      fireMult = 1.0;
      iceMult = 0.75;
      electricMult = 1.0;
      toxinMult = 0.5;
      blastMult = 1.5;
      radiationMult = 0.25;
      gasMult = 1.0;
      corrosiveMult = 1.75;
      viralMult = 1.0;
      magneticMult = 1.0;
    }else if(surfaceType.equals(Constants.ENEMY_SURFACE_SINEW)){
      impactMult = 1.0;
      punctureMult = 1.25;
      slashMult = 1.0;
      fireMult = 1.0;
      iceMult = 1.25;
      electricMult = 1.0;
      toxinMult = 1.0;
      blastMult = 0.5;
      radiationMult = 1.5;
      gasMult = 1.0;
      corrosiveMult = 1.0;
      viralMult = 1.0;
      magneticMult = 1.0;
    }else if(surfaceType.equals(Constants.ENEMY_SURFACE_ROBOTIC)){
      impactMult = 1.0;
      punctureMult = 1.25;
      slashMult = 0.75;
      fireMult = 1.0;
      iceMult = 1.0;
      electricMult = 1.5;
      toxinMult = 0.75;
      blastMult = 1.0;
      radiationMult = 1.25;
      gasMult = 1.0;
      corrosiveMult = 1.0;
      viralMult = 1.0;
      magneticMult = 1.0;
    }else if(surfaceType.equals(Constants.ENEMY_SURFACE_INFESTED)){
      impactMult = 1.0;
      punctureMult = 1.0;
      slashMult = 1.25;
      fireMult = 1.25;
      iceMult = 1.0;
      electricMult = 1.0;
      toxinMult = 1.0;
      blastMult = 1.0;
      radiationMult = 0.5;
      gasMult = 1.75;
      corrosiveMult = 1.0;
      viralMult = 0.5;
      magneticMult = 1.0;
    }
    
    //Armor Mults
    if(armorType.equals(Constants.ENEMY_SURFACE_FERRITE_ARMOR)){
      armorImpactMult = 1.0;
      armorPunctureMult = 1.5;
      armorSlashMult = 0.85;
      armorFireMult = 1.0;
      armorIceMult = 1.0;
      armorElectricMult = 1.0;
      armorToxinMult = 1.25;
      armorBlastMult = 0.75;
      armorRadiationMult = 1.0;
      armorGasMult = 1.0;
      armorCorrosiveMult = 1.75;
      armorViralMult = 1.0;
      armorMagneticMult = 1.0;
    }else if(armorType.equals(Constants.ENEMY_SURFACE_ALLOY_ARMOR)){
      armorImpactMult = 1.0;
      armorPunctureMult = 1.15;
      armorSlashMult = 0.5;
      armorFireMult = 1.0;
      armorIceMult = 1.25;
      armorElectricMult = 0.5;
      armorToxinMult = 1.0;
      armorBlastMult = 1.0;
      armorRadiationMult = 1.75;
      armorGasMult = 1.0;
      armorCorrosiveMult = 1.0;
      armorViralMult = 1.0;
      armorMagneticMult = 0.5;
    }
    
    //Shield Mults
    if(shieldType.equals(Constants.ENEMY_SURFACE_SHIELDS)){
      shieldImpactMult = 1.5;
      shieldPunctureMult = 0.85;
      shieldSlashMult = 1.0;
      shieldFireMult = 1.0;
      shieldIceMult = 1.0;
      shieldElectricMult = 1.0;
      shieldToxinMult = 1.0;
      shieldBlastMult = 1.0;
      shieldRadiationMult = 0.75;
      shieldGasMult = 1.0;
      shieldCorrosiveMult = 1.0;
      shieldViralMult = 1.0;
      shieldMagneticMult = 1.75;
    }else if(shieldType.equals(Constants.ENEMY_SURFACE_PROTO_SHIELD)){
      shieldImpactMult = 1.15;
      shieldPunctureMult = 0.5;
      shieldSlashMult = 1.0;
      shieldFireMult = 0.5;
      shieldIceMult = 1.0;
      shieldElectricMult = 1.0;
      shieldToxinMult = 1.25;
      shieldBlastMult = 1.0;
      shieldRadiationMult = 1.0;
      shieldGasMult = 1.0;
      shieldCorrosiveMult = 0.5;
      shieldViralMult = 1.0;
      shieldMagneticMult = 1.75;
    }
    
    if(factionType.equals(Constants.ENEMY_TYPE_INFESTED)){
      typeMult = Main.finalInfestedMult;
    }else if(factionType.equals(Constants.ENEMY_TYPE_GRINEER)){
      typeMult = Main.finalGrineerMult;
    }else if(factionType.equals(Constants.ENEMY_TYPE_CORPUS)){
      typeMult = Main.finalCorpusMult;
    }
    
    //Simulation Data
    millisceondsPerShot = 1000.0 / Main.finalFireRate;
    millisecondMult = 1.0;
    reloadTimeMilliseconds = Main.finalReloadTime * 1000.0;
    
    baseImpactDamage = Main.impact.finalBase;
    basePunctureDamage = Main.puncture.finalBase;
    baseSlashDamage = Main.slash.finalBase;
    baseFireDamage = Main.fire.finalBase;
    baseIceDamage = Main.ice.finalBase;
    baseElectricDamage = Main.electric.finalBase;
    baseToxinDamage = Main.toxin.finalBase;
    baseBlastDamage = Main.blast.finalBase;
    baseCorrosiveDamage = Main.corrosive.finalBase;
    baseGasDamage = Main.gas.finalBase;
    baseMagneticDamage = Main.magnetic.finalBase;
    baseRadiationDamage = Main.radiation.finalBase;
    baseViralDamage = Main.viral.finalBase;
    
    corrosiveProjectionMult = Main.getCorrosiveProjectionMult();
    DoTBase = (Main.raw.base * Main.finalDamageMult) * Main.finalDeadAimMult;
    
    Runnable advancedTTKRun = new Runnable(){
      public void run() {
        clearValues();
        for(int i = 0; i < Main.complexTTKIterations; i++){
          TTKVec.add(calculateRandomizedTimeToKill());
        }
        for(Double d : TTKVec){
          TTK += d;
        }
        TTK /= TTKVec.size();
        Collections.sort(TTKVec);
        minTTK = TTKVec.get(0);
        maxTTK = TTKVec.get(TTKVec.size()-1);
        Main.complexTTKCompletions += 1;
      }
    };
    Thread advancedTTKThread = new Thread(advancedTTKRun);
    advancedTTKThread.start();
  }
  
  /**
   * builds the simple TTK output
   * @return simple TTK
   */
  public String printSimpleData(){
    DecimalFormat f = new DecimalFormat("#.###");
    String returnStr = "";
    if(TTK > 3600.0){
      returnStr = "\nTime to Kill a lvl "+currentLevel+" " + name + " :: "+f.format(TTK/36000.0)+" hours";
    }else if(TTK > 60.0){
      returnStr = "\nTime to Kill a lvl "+currentLevel+" " + name + " :: "+f.format(TTK/60.0)+" minutes";
    }else{
      returnStr = "\nTime to Kill a lvl "+currentLevel+" " + name + " :: "+f.format(TTK)+" seconds";
    }
    return returnStr;
  }
  
  public TTKNamePair getTTKNamePair(){
    String nam = "";
    for(char c : name.toCharArray()){
      if(Character.isUpperCase(c)){
        try{
          int charIndex = name.indexOf(c);
          if(!Character.isUpperCase(name.charAt(charIndex+1))){
            nam += name.substring(charIndex,charIndex+2);
          }else{
            nam += c;
          }
        }catch(Exception ex){
          nam += c;
        }
      }
    }
    return new TTKNamePair(nam, TTK);
  }
  
  /**
   * builds the advanced TTK output
   * @return advanced TTK
   */
  public String printAdvancedData(){
    DecimalFormat f = new DecimalFormat("00.00");
    Font font = Main.output.getFont();
    FontMetrics metric = Main.output.getFontMetrics(font);
    String displayName = name+"["+currentLevel+"]";
    String returnStr = "\n"+displayName;
    String TTKStr = "";
    String minTTKStr = "";
    String maxTTKStr = "";
    if(TTK > 3600.0){
      TTKStr += f.format(TTK/36000.0)+" hrs";
    }else if(TTK > 60.0){
      TTKStr += f.format(TTK/60.0)+" min";
    }else{
      TTKStr += f.format(TTK)+" sec";
    }
    
    if(minTTK > 3600.0){
      minTTKStr += f.format(minTTK/36000.0)+" hrs";
    }else if(minTTK > 60.0){
      minTTKStr += f.format(minTTK/60.0)+" min";
    }else{
      minTTKStr += f.format(minTTK)+" sec";
    }
    
    if(maxTTK > 3600.0){
      maxTTKStr += f.format(maxTTK/36000.0)+" hrs";
    }else if(maxTTK > 60.0){
      maxTTKStr += f.format(maxTTK/60.0)+" min";
    }else{
      maxTTKStr += f.format(maxTTK)+" sec";
    }
    int spaceWidth = metric.stringWidth(".");
    int nameFieldWidth = metric.stringWidth(Main.longestTTKName);
    int ttkFieldWidth = metric.stringWidth("XXXXXXXXXXX");
    double nameDiff = (nameFieldWidth - metric.stringWidth(displayName))/spaceWidth;
    nameDiff = Math.ceil(nameDiff);
    nameDiff += 2;
    for(int i = 0; i < nameDiff; i++){
      returnStr += ".";
    }
    returnStr += "|";
    double ttkDiff = (ttkFieldWidth - metric.stringWidth(TTKStr))/spaceWidth;
    ttkDiff /= 2.0;
    ttkDiff = Math.ceil(ttkDiff);
    for(int i = 0; i < (ttkDiff); i++){
      returnStr += ".";
    }
    returnStr += TTKStr;
    for(int i = 0; i < (ttkDiff); i++){
      returnStr += ".";
    }
    returnStr += "|";
    double minTTkDiff = (ttkFieldWidth - metric.stringWidth(minTTKStr))/spaceWidth;
    minTTkDiff /= 2.0;
    minTTkDiff = Math.ceil(minTTkDiff);
    for(int i = 0; i < (minTTkDiff); i++){
      returnStr += ".";
    }
    returnStr += minTTKStr;
    for(int i = 0; i < (minTTkDiff); i++){
      returnStr += ".";
    }
    returnStr += "|";
    double maxTTKDiff = (ttkFieldWidth - metric.stringWidth(maxTTKStr))/spaceWidth;
    maxTTKDiff /= 2.0;
    maxTTKDiff = Math.ceil(maxTTKDiff);
    for(int i = 0; i < (maxTTKDiff); i++){
      returnStr += ".";
    }
    returnStr += maxTTKStr;
    return returnStr;
  }
  
  /**
   * clears the TTK values
   */
  public void clearValues(){
    TTK = 0.0;
    minTTK = 0.0;
    maxTTK = 0.0;
    TTKVec = new Vector<Double>();
  }
  
  /**
   * Compares this to other targets
   */
  public int compareTo(Object o) {
    if(o instanceof Mod){
        return this.name.compareTo(((Mod)o).name);
    }else{
      return 0;
    }
  }
  
  /**
   * Calculates a random time to kill a target with the supplied stats
   */
  public double calculateRandomizedTimeToKill(){
    
    double targetAdjustedMaxShields = maxShields;
    double targetCurrentShields = maxShields;
    double targetAdjustedMaxHealth = maxHealth;
    double targetCurrentHealth = maxHealth;
    double targetAdjustedMaxArmor = maxArmor;
    int reloadTimeCounter = 0;
    int shotCounter = 2147483000;
    int iterations = 0;
    int timeToKill = 0;
    boolean reloading = false;
    impactStacks = new Vector<Integer>();
    punctureStacks = new Vector<Integer>();
    slashStacks = new Vector<DoTPair>();
    fireStacks = new Vector<DoTPair>();
    iceStacks = new Vector<Integer>();
    electricStacks = new Vector<Integer>();
    toxinStacks = new Vector<DoTPair>();
    blastStacks = new Vector<Integer>();
    radiationStacks = new Vector<Integer>();
    gasStacks = new Vector<DoTPair>();
    corrosiveStacks = new Vector<Integer>();
    viralStacks = new Vector<Integer>();
    magneticStacks = new Vector<Integer>();
    
    if(Main.weaponMode.equals(Constants.FULL_AUTO_RAMP_UP) || Main.weaponMode.equals(Constants.FULL_AUTO_BULLET_RAMP)){
      millisecondMult = 5.0;
    }
    
    //Run a 600 second simulation to calculate the time to kill
    for(timeToKill=0; timeToKill < 6000000; timeToKill++){
      //Add new stack
      if(!reloading){
        shotCounter++;
        //is it time to fire a new projectile?
        if(shotCounter >= (millisceondsPerShot*millisecondMult)){
          if(Main.weaponMode.equals(Constants.FULL_AUTO_RAMP_UP) || Main.weaponMode.equals(Constants.FULL_AUTO_BULLET_RAMP)){
            millisecondMult--;
            if(millisecondMult < 1.0){
              millisecondMult = 1.0;
            }
          }
          localProjectileCount = Main.finalProjectileCount;
          if(Main.weaponMode.equals(Constants.FULL_AUTO_BULLET_RAMP)){
            localProjectileCount /= millisecondMult;
          }
          
          //Adjust Max Stats
          //Shields
          targetAdjustedMaxShields = maxShields;
          if(magneticStacks.size() > 0){
            targetAdjustedMaxShields *= 0.25;
          }
          if(targetAdjustedMaxShields < targetCurrentShields){
            targetCurrentShields = targetAdjustedMaxShields;
          }
          //Health
          targetAdjustedMaxHealth = maxHealth;
          if(viralStacks.size() > 0){
            targetAdjustedMaxHealth *= 0.5;
          }
          if(targetAdjustedMaxHealth < targetCurrentHealth){
            targetCurrentHealth = targetAdjustedMaxHealth;
          }
          //Armor
          targetAdjustedMaxArmor = maxArmor;
          //Adjust armor based on corrosive projection stacks
          targetAdjustedMaxArmor *= corrosiveProjectionMult;
          if(corrosiveStacks.size() > 0){
            for(int i = 0; i < corrosiveStacks.size(); i++){
              targetAdjustedMaxArmor *= 0.75;
            }
          }
          
          for(int p = 0; p < localProjectileCount; p++){
            double localCritMult = 1.0;
            //Is this a crit?
            double crit = rng.nextDouble();
            if(crit <= Main.finalCritChance){
              localCritMult = Main.finalCritMult;
              //Is this a red crit?
              double redCritChance = Main.finalCritChance - 1.0;
              if(redCritChance > 0.0){
                double redCrit = rng.nextDouble();
                if(redCrit <= redCritChance){
                  localCritMult *= 2.0;
                }
              }
            }
            
            //Calculate Armor Reduction
            double armorReduction = 1.0 - ((targetAdjustedMaxArmor * (1.0/300.0)) / (1.0 + (targetAdjustedMaxArmor * (1.0/300.0))));
            double impactArmorReduciton = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorImpactMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorImpactMult - 1.0))) * (1.0/300.0))));
            double punctureArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorPunctureMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorPunctureMult - 1.0))) * (1.0/300.0))));
            double slashArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorSlashMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorSlashMult - 1.0))) * (1.0/300.0))));
            double fireArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorFireMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorFireMult - 1.0))) * (1.0/300.0))));
            double iceArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorIceMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorIceMult - 1.0))) * (1.0/300.0))));
            double electricArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorElectricMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorElectricMult - 1.0))) * (1.0/300.0))));
            double toxinArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorToxinMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorToxinMult - 1.0))) * (1.0/300.0))));
            double blastArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorBlastMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorBlastMult - 1.0))) * (1.0/300.0))));
            double corrosiveArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorCorrosiveMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorCorrosiveMult - 1.0))) * (1.0/300.0))));
            double gasArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorGasMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorGasMult - 1.0))) * (1.0/300.0))));
            double magneticArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorMagneticMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorMagneticMult - 1.0))) * (1.0/300.0))));
            double radiationArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorRadiationMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorRadiationMult - 1.0))) * (1.0/300.0))));
            double viralArmorReduction = 1.0 - (((targetAdjustedMaxArmor * (1.0 - (armorViralMult - 1.0))) * (1.0/300.0)) / (1.0 + ((targetAdjustedMaxArmor * (1.0 - (armorViralMult - 1.0))) * (1.0/300.0))));
            
            //Deal Damage
            if(targetCurrentShields > 0.0){
              targetCurrentShields -= (((baseImpactDamage * localCritMult) * typeMult) * shieldImpactMult) * impactArmorReduciton;
              targetCurrentShields -= (((basePunctureDamage * localCritMult) * typeMult) * shieldPunctureMult) * punctureArmorReduction;
              targetCurrentShields -= (((baseSlashDamage * localCritMult) * typeMult) * shieldSlashMult) * slashArmorReduction;
              targetCurrentShields -= (((baseFireDamage * localCritMult) * typeMult) * shieldFireMult) * fireArmorReduction;
              targetCurrentShields -= (((baseIceDamage * localCritMult) * typeMult) * shieldIceMult) * iceArmorReduction;
              targetCurrentShields -= (((baseElectricDamage * localCritMult) * typeMult) * shieldElectricMult) * electricArmorReduction;
              targetCurrentShields -= (((baseToxinDamage * localCritMult) * typeMult) * shieldToxinMult) * toxinArmorReduction;
              targetCurrentShields -= (((baseBlastDamage * localCritMult) * typeMult) * shieldBlastMult) * blastArmorReduction;
              targetCurrentShields -= (((baseCorrosiveDamage * localCritMult) * typeMult) * shieldCorrosiveMult) * corrosiveArmorReduction;
              targetCurrentShields -= (((baseGasDamage * localCritMult) * typeMult) * shieldGasMult) * gasArmorReduction;
              targetCurrentShields -= (((baseMagneticDamage * localCritMult) * typeMult) * shieldMagneticMult) * magneticArmorReduction;
              targetCurrentShields -= (((baseRadiationDamage * localCritMult) * typeMult) * shieldRadiationMult) * radiationArmorReduction;
              targetCurrentShields -= (((baseViralDamage * localCritMult) * typeMult) * shieldViralMult) * viralArmorReduction;
            }
            if(targetCurrentShields <= 0.0){
              double shieldDifference = 1.0;
              if(targetCurrentShields < 0.0){
                double unabsorbed = Math.abs(targetCurrentShields);
                double raw = ((Main.raw.finalBase * localCritMult) * typeMult) * armorReduction;
                shieldDifference = 1.0 - (unabsorbed / raw);
                targetCurrentShields = 0.0;
              }
              
              if(targetAdjustedMaxArmor > 0.0){
                targetCurrentHealth -= ((((baseImpactDamage * localCritMult) * typeMult) * armorImpactMult) * impactArmorReduciton) * shieldDifference;
                targetCurrentHealth -= ((((basePunctureDamage * localCritMult) * typeMult) * armorPunctureMult) * punctureArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseSlashDamage * localCritMult) * typeMult) * armorSlashMult) * slashArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseFireDamage * localCritMult) * typeMult) * armorFireMult) * fireArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseIceDamage * localCritMult) * typeMult) * armorIceMult) * iceArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseElectricDamage * localCritMult) * typeMult) * armorElectricMult) * electricArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseToxinDamage * localCritMult) * typeMult) * armorToxinMult) * toxinArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseBlastDamage * localCritMult) * typeMult) * armorBlastMult) * blastArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseCorrosiveDamage * localCritMult) * typeMult) * armorCorrosiveMult) * corrosiveArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseGasDamage * localCritMult) * typeMult) * armorGasMult) * gasArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseMagneticDamage * localCritMult) * typeMult) * armorMagneticMult) * magneticArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseRadiationDamage * localCritMult) * typeMult) * armorRadiationMult) * radiationArmorReduction) * shieldDifference;
                targetCurrentHealth -= ((((baseViralDamage * localCritMult) * typeMult) * armorViralMult) * viralArmorReduction) * shieldDifference;
              }else{
                targetCurrentHealth -= ((baseImpactDamage * localCritMult) * typeMult) * impactMult * shieldDifference;
                targetCurrentHealth -= ((basePunctureDamage * localCritMult) * typeMult) * punctureMult * shieldDifference;
                targetCurrentHealth -= ((baseSlashDamage * localCritMult) * typeMult) * slashMult * shieldDifference;
                targetCurrentHealth -= ((baseFireDamage * localCritMult) * typeMult) * fireMult * shieldDifference;
                targetCurrentHealth -= ((baseIceDamage * localCritMult) * typeMult) * iceMult * shieldDifference;
                targetCurrentHealth -= ((baseElectricDamage * localCritMult) * typeMult) * electricMult * shieldDifference;
                targetCurrentHealth -= ((baseToxinDamage * localCritMult) * typeMult) * toxinMult * shieldDifference;
                targetCurrentHealth -= ((baseBlastDamage * localCritMult) * typeMult) * blastMult * shieldDifference;
                targetCurrentHealth -= ((baseCorrosiveDamage * localCritMult) * typeMult) * corrosiveMult * shieldDifference;
                targetCurrentHealth -= ((baseGasDamage * localCritMult) * typeMult) * gasMult * shieldDifference;
                targetCurrentHealth -= ((baseMagneticDamage * localCritMult) * typeMult) * magneticMult * shieldDifference;
                targetCurrentHealth -= ((baseRadiationDamage * localCritMult) * typeMult) * radiationMult * shieldDifference;
                targetCurrentHealth -= ((baseViralDamage * localCritMult) * typeMult) * viralMult * shieldDifference;
              }
            }
            
            //Do Procs
            Vector<String> potentialProcs = new Vector<String>();
            double totalPhysical = Main.impact.base + Main.puncture.base + Main.slash.base;
            double localImpactProcMult = Main.impact.base / totalPhysical;
            double localPunctureProcMult = Main.puncture.base / totalPhysical;
            double localSlashProcMult = Main.slash.base / totalPhysical;
            double localElementalProcChance = Main.finalStatusChance;
            double localImpactProcChance = Main.finalStatusChance * localImpactProcMult;
            double localPunctureProcChance = Main.finalStatusChance * localPunctureProcMult;
            double localSlashProcChance = Main.finalStatusChance * localSlashProcMult;
            if(Main.impact.finalBase > 0.0){
              potentialProcs.add(Constants.IMPACT_WEAPON_DAMAGE);
            }
            if(Main.puncture.finalBase > 0.0){
              potentialProcs.add(Constants.PUNCTURE_WEAPON_DAMAGE);
            }
            if(Main.slash.finalBase > 0.0){
              potentialProcs.add(Constants.SLASH_WEAPON_DAMAGE);
            }
            if(Main.fire.finalBase > 0.0){
              potentialProcs.add(Constants.FIRE_WEAPON_DAMAGE);
            }
            if(Main.ice.finalBase > 0.0){
              potentialProcs.add(Constants.ICE_WEAPON_DAMAGE);
            }
            if(Main.electric.finalBase > 0.0){
              potentialProcs.add(Constants.ELECTRIC_WEAPON_DAMAGE);
            }
            if(Main.toxin.finalBase > 0.0){
              potentialProcs.add(Constants.TOXIN_WEAPON_DAMAGE);
            }
            if(Main.blast.finalBase > 0.0){
              potentialProcs.add(Constants.BLAST_WEAPON_DAMAGE);
            }
            if(Main.radiation.finalBase > 0.0){
              potentialProcs.add(Constants.RADIATION_WEAPON_DAMAGE);
            }
            if(Main.gas.finalBase > 0.0){
              potentialProcs.add(Constants.GAS_WEAPON_DAMAGE);
            }
            if(Main.magnetic.finalBase > 0.0){
              potentialProcs.add(Constants.MAGNETIC_WEAPON_DAMAGE);
            }
            if(Main.viral.finalBase > 0.0){
              potentialProcs.add(Constants.VIRAL_WEAPON_DAMAGE);
            }
            if(Main.corrosive.finalBase > 0.0){
              potentialProcs.add(Constants.CORROSIVE_WEAPON_DAMAGE);
            }
            Collections.shuffle(potentialProcs);
            for(String proc : potentialProcs){
              if(proc.equals(Constants.IMPACT_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localImpactProcChance){
                  impactStacks.add(1);
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.PUNCTURE_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localPunctureProcChance){
                  punctureStacks.add(6);
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.SLASH_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localSlashProcChance){
                  double localSlashMult = slashMult;
                  if(targetAdjustedMaxArmor > 0.0){
                    localSlashMult = armorSlashMult;
                  }
                  double bleedDamage = (((DoTBase * localCritMult) * typeMult) * localSlashMult) * 0.35;
                  int slashDuration = (int)(Math.round(5 * Main.finalStatusDuration));
                  slashStacks.add(new DoTPair(bleedDamage,slashDuration));
                  targetCurrentHealth -= bleedDamage;
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.FIRE_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localElementalProcChance){
                  double localFireMult = fireMult;
                  double localFireArmorReduciton = 1.0;
                  if(targetAdjustedMaxArmor > 0.0){
                    localFireMult = armorFireMult;
                    if(targetCurrentShields > 0.0){
                      localFireMult = shieldFireMult;
                    }
                    localFireArmorReduciton = fireArmorReduction;
                  }
                  double heatDamage = ((((DoTBase * localCritMult) * typeMult) * localFireMult) * localFireArmorReduciton) * 0.5;
                  int heatDuration = (int)(Math.round(5 * Main.finalStatusDuration));
                  fireStacks.add(new DoTPair(heatDamage,heatDuration));
                  targetCurrentHealth -= heatDamage;
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.ICE_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localElementalProcChance){
                  int iceDuration = (int)(Math.round(6 * Main.finalStatusDuration));
                  iceStacks.add(iceDuration);
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.ELECTRIC_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localElementalProcChance){
                  double localElectricMult = electricMult;
                  double localElectricArmorReduction = 1.0;
                  if(targetAdjustedMaxArmor > 0.0){
                    localElectricMult = armorElectricMult;
                    if(targetCurrentShields > 0.0){
                      localElectricMult = shieldElectricMult;
                    }
                    localElectricArmorReduction = electricArmorReduction;
                  }
                  Double electricProcDamage = ((((DoTBase * localCritMult) * typeMult) * localElectricMult) * localElectricArmorReduction);
                  targetCurrentHealth -= electricProcDamage;
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.TOXIN_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localElementalProcChance){
                  double localToxinMult = toxinMult;
                  double localToxinArmorReduction = 1.0;
                  if(targetAdjustedMaxArmor > 0.0){
                    localToxinMult = armorToxinMult;
                    localToxinArmorReduction = toxinArmorReduction;
                  }
                  double poisonDamage = ((((DoTBase * localCritMult) * typeMult) * localToxinMult) * localToxinArmorReduction) * 0.5;
                  int toxinDuration = (int)(Math.round(7 * Main.finalStatusDuration));
                  toxinStacks.add(new DoTPair(poisonDamage,toxinDuration));
                  if(poisonDamage < 10.0){
                    poisonDamage = 10.0;
                  }
                  targetCurrentHealth -= poisonDamage;
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.BLAST_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localElementalProcChance){
                  blastStacks.add(1);
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.RADIATION_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localElementalProcChance){
                  radiationStacks.add(12);
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.GAS_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localElementalProcChance){
                  double localGasMult = gasMult;
                  double localGasArmorReduction = 1.0;
                  if(targetAdjustedMaxArmor > 0.0){
                    localGasMult = armorGasMult;
                    localGasArmorReduction = gasArmorReduction;
                  }
                  double poisonDamage = ((((DoTBase * localCritMult) * typeMult) * localGasMult) * localGasArmorReduction) * 0.5;
                  int gasDuration = (int)(Math.round(7 * Main.finalStatusDuration));
                  gasStacks.add(new DoTPair(poisonDamage,gasDuration));
                  if(poisonDamage < 10.0){
                    poisonDamage = 10.0;
                  }
                  targetCurrentHealth -= poisonDamage * gasMult;
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.MAGNETIC_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localElementalProcChance){
                  magneticStacks.add(6);
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.VIRAL_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localElementalProcChance){
                  viralStacks.add(6);
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }else if(proc.equals(Constants.CORROSIVE_WEAPON_DAMAGE)){
                if(rng.nextDouble() <= localElementalProcChance){
                  corrosiveStacks.add(6);
                  //Decrement proc chances
                  localImpactProcChance *= 0.5;
                  localPunctureProcChance *= 0.5;
                  localSlashProcChance *= 0.5;
                  localElementalProcChance *= 0.5;
                }
              }
            }
          }
          
          //Check for Death
          if(targetCurrentHealth < 0.0){
            return timeToKill / 1000.0;
          }
          
          shotCounter = 0;
          //Have we unloaded the whole mag and need to reload?
          iterations++;
          if(iterations >= Main.finalMag){
            reloading = true;
            iterations = 0;
          }
        }
      }else{
        //Are we still reloading?
        reloadTimeCounter++;
        if(reloadTimeCounter >= reloadTimeMilliseconds){
          reloading = false;
          reloadTimeCounter = 0;
          if(Main.weaponMode.equals(Constants.FULL_AUTO_RAMP_UP) || Main.weaponMode.equals(Constants.FULL_AUTO_BULLET_RAMP)){
            millisecondMult = 5.0;
          }
        }
      }
      //Is this a whole second?
      if(timeToKill % 1000 == 0){
        //Deal DoT Damage
        if(slashStacks.size() > 0){
          for(int i = 0; i < slashStacks.size(); i++){
            double bleedDamage = slashStacks.get(i).damage;
            targetCurrentHealth -= bleedDamage;
          }
        }
        if(fireStacks.size() > 0){
          for(int i = 0; i < fireStacks.size(); i++){
            double heatDamage = fireStacks.get(i).damage;
            targetCurrentHealth -= heatDamage;
          }
        }
        if(toxinStacks.size() > 0){
          for(int i = 0; i < toxinStacks.size(); i++){
            double poisonDamage = toxinStacks.get(i).damage;
            if(poisonDamage < 10.0){
              poisonDamage = 10.0;
            }
            targetCurrentHealth -= poisonDamage;
          }
        }
        if(gasStacks.size() > 0){
          for(int i = 0; i < gasStacks.size(); i++){
            double poisonDamage = gasStacks.get(i).damage;
            if(poisonDamage < 10.0){
              poisonDamage = 10.0;
            }
            targetCurrentHealth -= poisonDamage;
          }
        }
        //Decrement stack timers
        for(int j=0;j<impactStacks.size();j++){
          int temp = impactStacks.get(j);
          temp--;
          impactStacks.set(j, temp);
        }
        for(int j=0;j<punctureStacks.size();j++){
          int temp = punctureStacks.get(j);
          temp--;
          punctureStacks.set(j, temp);
        }
        for(int j=0;j<slashStacks.size();j++){
          slashStacks.get(j).duration--;
        }
        for(int j=0;j<fireStacks.size();j++){
          fireStacks.get(j).duration--;
        }
        for(int j=0;j<iceStacks.size();j++){
          int temp = iceStacks.get(j);
          temp--;
          iceStacks.set(j, temp);
        }
        for(int j=0;j<electricStacks.size();j++){
          int temp = electricStacks.get(j);
          temp--;
          electricStacks.set(j, temp);
        }
        for(int j=0;j<toxinStacks.size();j++){
          toxinStacks.get(j).duration--;
        }
        for(int j=0;j<blastStacks.size();j++){
          int temp = blastStacks.get(j);
          temp--;
          blastStacks.set(j, temp);
        }
        for(int j=0;j<radiationStacks.size();j++){
          int temp = radiationStacks.get(j);
          temp--;
          radiationStacks.set(j, temp);
        }
        for(int j=0;j<gasStacks.size();j++){
          gasStacks.get(j).duration--;
        }
        for(int j=0;j<corrosiveStacks.size();j++){
          int temp = corrosiveStacks.get(j);
          temp--;
          corrosiveStacks.set(j, temp);
        }
        for(int j=0;j<viralStacks.size();j++){
          int temp = viralStacks.get(j);
          temp--;
          viralStacks.set(j, temp);
        }
        for(int j=0;j<magneticStacks.size();j++){
          int temp = magneticStacks.get(j);
          temp--;
          magneticStacks.set(j, temp);
        }
        //Remove stacks that have expired
        for(int k=0;k<impactStacks.size();k++){
          if(impactStacks.get(k) <= 0){
            impactStacks.remove(k);
          }
        }
        for(int k=0;k<punctureStacks.size();k++){
          if(punctureStacks.get(k) <= 0){
            punctureStacks.remove(k);
          }
        }
        for(int k=0;k<slashStacks.size();k++){
          if(slashStacks.get(k).duration <= 0){
            slashStacks.remove(k);
          }
        }
        for(int k=0;k<fireStacks.size();k++){
          if(fireStacks.get(k).duration <= 0){
            fireStacks.remove(k);
          }
        }
        for(int k=0;k<iceStacks.size();k++){
          if(iceStacks.get(k) <= 0){
            iceStacks.remove(k);
          }
        }
        for(int k=0;k<electricStacks.size();k++){
          if(electricStacks.get(k) <= 0){
            electricStacks.remove(k);
          }
        }
        for(int k=0;k<toxinStacks.size();k++){
          if(toxinStacks.get(k).duration <= 0){
            toxinStacks.remove(k);
          }
        }
        for(int k=0;k<blastStacks.size();k++){
          if(blastStacks.get(k) <= 0){
            blastStacks.remove(k);
          }
        }
        for(int k=0;k<radiationStacks.size();k++){
          if(radiationStacks.get(k) <= 0){
            radiationStacks.remove(k);
          }
        }
        for(int k=0;k<gasStacks.size();k++){
          if(gasStacks.get(k).duration <= 0){
            gasStacks.remove(k);
          }
        }
        for(int k=0;k<corrosiveStacks.size();k++){
          if(corrosiveStacks.get(k) <= 0){
            corrosiveStacks.remove(k);
          }
        }
        for(int k=0;k<viralStacks.size();k++){
          if(viralStacks.get(k) <= 0){
            viralStacks.remove(k);
          }
        }
        for(int k=0;k<magneticStacks.size();k++){
          if(magneticStacks.get(k) <= 0){
            magneticStacks.remove(k);
          }
        }
      }
    }
    
    return timeToKill / 1000.0;
  }
}
