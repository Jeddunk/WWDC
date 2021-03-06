package mods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import weapons.WeaponInitializer;

import etc.Constants;


public class ModInitializer {
  
  /**
   * ____________________________________________________________
   * GLOBAL VARIABLES
   * ____________________________________________________________
   */
  
  /** Singleton instance */
  private static ModInitializer instance;
  
  /** Mods */
  public Vector<Mod> mods = new Vector<Mod>();
  
  /** Mods file */
  protected File modsDB;
  
  
  /**
   * ____________________________________________________________
   * METHODS
   * ____________________________________________________________
   */
  
  /**
   * CTOR
   */
  private ModInitializer(){
    initialize();
  }
  
  /**
   * Gets the singleton Instance
   * @return instance
   */
  public static ModInitializer getInstance(){
    if(instance == null){
      instance = new ModInitializer();
    }
    return instance;
  }
  
  /**
   * Initializes the mod data
   */
  public void initialize(){
    modsDB = new File("mods.db");
    try {
      if(modsDB.exists()){
        mods.clear();
        BufferedReader reader = new BufferedReader(new FileReader(modsDB));
        String line = reader.readLine();
        while(line != null){
          Mod mod = new Mod(line);
          mods.add(mod);
          line = reader.readLine();
        }
        reader.close();
      }else{
        mods.clear();
        modsDB.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(modsDB));
        for(String modStr : Constants.baseModDB){
          writer.write(modStr+"\n");
          Mod mod = new Mod(modStr);
          mods.add(mod);
        }
        writer.close();
        
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Saves the mod data
   */
  public void saveModDB(){
    try {
      if(modsDB.exists()){
        modsDB.delete();
      }
      modsDB.createNewFile();
      BufferedWriter writer = new BufferedWriter(new FileWriter(modsDB));
      for(int i = 0; i < mods.size(); i++){
        writer.write(mods.get(i).writeOut()+"\n");
      }
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
