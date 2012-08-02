package net.othercraft.steelsecurity.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class Tools {
    /**
     * 
     * @param pname
     *            The string to use
     * @return A list of players whos name start with pname and are online.
     */
    public static Set<Player> safePlayer(String pname) {
	Set<Player> list = new HashSet<Player>();
	for (Player scan : Bukkit.getServer().getOnlinePlayers()) {
	    if (scan.getName().toLowerCase().startsWith(pname.toLowerCase())) {
		list.add(scan);
	    }
	}
	return list;
    }

    /**
     * Test if a String is safe to parse to an Integer.
     * 
     * @param toparse
     *            the string to test
     * @return weather or not the string is safe to parse into and integer
     */

    public static Boolean isSafeNumber(String toparse) {
	Boolean result;
	try {
	    @SuppressWarnings("unused")
	    int i = Integer.parseInt(toparse);
	    result = true;
	} catch (NumberFormatException e) {
	    result = false;
	}
	return result;
    }

    /**
     * Get a page of a list of strings
     * 
     * @param l
     *            A list containing all Strings,
     * @param pagenr
     *            The page number
     * @param pagel
     *            the page length
     * @return List<String> containing all Strings on the page
     */
    public static List<String> getPage(List<String> l, int pagenr, int pagel) {
	List<String> page = new ArrayList<String>();

	int listart = (pagenr - 1) * pagel;
	int liend = listart + pagel;

	for (int i = listart; i < liend; i++) {
	    if (i < l.size()) {
		page.add(l.get(i));
	    } else {
		break;
	    }
	}

	return page;
    }

    /**
     * Gets the amount of pages in a list
     * 
     * @param l
     *            A list containing all Strings,
     * @param pagel
     *            the page length
     * @return List<String> containing all Strings on the page
     */
    public static Integer getPages(List<String> l, int pagel) {
	if (l.size() > 0) {
	    int counter = 1;
	    boolean done = false;
	    while (!done) {
		if (getPage(l, counter, pagel).size() == 0) {
		    done = true;
		    return counter - 1;
		} else {
		    counter++;
		}
	    }
	    return counter;
	} else {
	    return 0;
	}
    }
    /**
     * Retreives an object from a file.
     * @param file the location of the object
     * @return the object within the file
     */
    public static Object grabObject(File file){
	Object object = null;
	try {
	    FileInputStream fin = new FileInputStream(file);
	    ObjectInputStream in = new ObjectInputStream(fin);
	    object = in.readObject();
	    in.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	return object;
    }
    public static void saveObject(File file, Object object){
	try {
		FileOutputStream fout = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(object);
		out.close();
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
    }
}
