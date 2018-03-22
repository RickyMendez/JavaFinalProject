package financialApp;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class AccountFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        String name = f.getName();

        int pointIndex = name.lastIndexOf(".");

        if (pointIndex == name.length()-1 || pointIndex == -1){
            return false;
        }

        String ext = name.substring(pointIndex+1 , name.length());

        if (ext.equals("csv")){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "Comma Separated[*.csv]";
    }
}
