import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class FindProcesses 
{

    private final String path;
    private ArrayList<String> processes;
    private ArrayList<String> processesToKill;

    private HashSet<String> checkIDName;



    public FindProcesses(String path, String[] checkIDNameI)
    {
        this.checkIDName = new HashSet<>();
        for (String s : checkIDNameI)
            checkIDName.add(s);
        this.path = path;
    }
    
    public void openFile() 
    {

        ArrayList<String> lines = new ArrayList<>();
        try 
        {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-32");
            BufferedReader textReader = new BufferedReader(isr);
            String result;
        
            while ((result = textReader.readLine()) != null)
                lines.add(result);

            fis.close();
            isr.close();            
            textReader.close();
        
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(FindProcesses.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FindProcesses.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.processes = lines;
    }

    public void sortProcesses()
    {

        processesToKill = new ArrayList<>();

        for (String s : processes)
        {
            String[] split = s.split(" ");

            System.out.println("set: " + checkIDName + "\n" + split[1]);

            if (checkIDName.contains(split[1]))
                processesToKill.add(split[0]);

        } // for
    } // sortProcesses

    public void writeFile() 
    {

        try 
        {
            System.out.println("ids: " + processesToKill);

            PrintWriter pw = new PrintWriter("processes-to-kill");

            for (String i : processesToKill)
                pw.println(i);

            pw.close();
        } 
        catch (IOException ex) {
            Logger.getLogger(FindProcesses.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException 
    {
        System.out.println("Working Directory = " +
        System.getProperty("user.dir"));
        String path = System.getProperty("user.dir") + "\\process-list";
        File currentDir = new File(path);
         
        if (currentDir.exists())
        {

            String[] checkIDName = {"TillClient", 
                                    "OutputClient", 
                                    "WaiterClient", 
                                    "MyServer"};

            FindProcesses fp = new FindProcesses(path, checkIDName);
            fp.openFile();
            fp.sortProcesses();
            fp.writeFile();
         }
         else
             System.err.println("Could not find file 'process-list'");

    } // main
    
}
