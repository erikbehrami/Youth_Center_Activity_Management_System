package utils.modeManager;

import javafx.scene.Scene;
import utils.Navigator;

public class ModeChange {
    private static ModeChange modeChange;
   private static String mode;
    private ModeChange(){
    mode = Navigator.LIGHT_MODE;
    }
    public static ModeChange getInstance(){
        if(ModeChange.modeChange == null){
           ModeChange.modeChange = new ModeChange();
        }
        return ModeChange.modeChange;
    }

    public static String getMode() {
        return mode;
    }

    public static void setMode(String mode) {
        ModeChange.mode = mode;
    }

    public void changeMode(Scene scene){
      if(ModeChange.mode.equals(Navigator.DARK_MODE)){
          scene.getStylesheets().remove(getClass().getResource(Navigator.LIGHT_MODE).toExternalForm());
          scene.getStylesheets().add(getClass().getResource(Navigator.DARK_MODE).toExternalForm());
      }else{
          scene.getStylesheets().remove(getClass().getResource(Navigator.DARK_MODE).toExternalForm());
          scene.getStylesheets().add(getClass().getResource(Navigator.LIGHT_MODE).toExternalForm());
      }
    }



}
