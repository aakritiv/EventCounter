import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * Contains main method to instantiate the Counter class.
 * Reads the input from the console and calls the respective method from the class.
 */
public class bbst {

	public static void main(String[] args) throws IOException {
		Counter c = new Counter(args[0]);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		while((line = br.readLine())!=null){
			String[] cmd = line.split(" ");
			if(cmd[0].equalsIgnoreCase("quit"))
				break;
			int a = Integer.parseInt(cmd[1]);
			if(cmd[0].equalsIgnoreCase("increase"))
				c.increase(a,Integer.parseInt(cmd[2]));
			else if(cmd[0].equalsIgnoreCase("reduce"))
				c.reduce(a,Integer.parseInt(cmd[2]));
			else if(cmd[0].equalsIgnoreCase("inrange"))
				c.inRange(a,Integer.parseInt(cmd[2]));
			else if(cmd[0].equalsIgnoreCase("count"))
				c.count(a);
			else if(cmd[0].equalsIgnoreCase("next"))
				c.next(a);
			else if(cmd[0].equalsIgnoreCase("previous"))
				c.previous(a);
		}
		br.close();
	}
}

