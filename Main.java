import java.util.*;
import javax.swing.table.*;
import java.io.*;
class Main implements Serializable
{
	HashMap<String, Long> hmap;
	public static void main(String args[]) throws Exception
	{
		Main m=new Main();
		m.hmap=new HashMap<String,Long>();
		m.hmap.put("Kishore", Long.parseLong("9999999999"));
		File f=new File("sample.txt");
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(m);
		oos.flush();
	}
}
