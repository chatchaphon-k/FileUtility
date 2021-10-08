import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtility
{
    private String ext;
    private File dir;
    private int total_files;
    private File[] list_files;
    private File file;
    private String fileName;
    private String dirName;
    private boolean is_file_found;

    public FileUtility(String dirName, String ext)
    {
        this.ext = ext;
        this.dirName = dirName;
        set_dir(dirName);
        list_files = dir.listFiles();
        total_files = list_files.length;
        for(int i = 0; i < list_files.length; i++)
        {
            if(list_files[i].isFile())
            {
                is_file_found = true;
                break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Setter">

    public void set_dir(String dirName)
    {
        String sysPath = System.getProperty("user.dir");
        dir = new File(sysPath + "\\src");
        if(dir.exists())
            init_dir(dir.getAbsolutePath() + "\\" + dirName);   //For Netbeans env
        else
            init_dir(sysPath + "\\" + dirName); //For built project env
    }

    private void init_dir(String path)
    {
        dir = new File(path);
        if (!dir.exists())
        {
            boolean isMkDir = dir.mkdir();
            if (isMkDir)
                System.out.println("NEW FOLDER CREATED: " + dirName);
        }
    }

    public void set_curr_file(int i)
    {
        set_curr_file(list_files[i]);
    }

    public void set_curr_file(File currFile)
    {
        this.file = currFile;
        this.fileName = currFile.getName();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean">

    public boolean is_dir_validated()
    {
        if (!dir.exists())
        {
            System.err.println("Please create folder name \"" + dirName + "\" and put \"" + ext + "\" files in there");
            return false;
        }
        else if(!dir.isDirectory())
        {
            System.err.println(dirName + " is not directory");
            return false;
        }
        else if (total_files == 0 || !is_file_found)
        {
            System.err.println("Please put your \"" + ext + "\" file into folder \"" + dirName + "\"");
            return false;
        }

        return true;
    }
    
    public boolean is_file_validated(int i)
    {
        set_curr_file(list_files[i]);
        return is_file_validated();
    }
    
    public boolean is_file_validated()
    {
        if(file.isFile())
        {
            if(fileName.toLowerCase().endsWith(ext))
                return true;
            System.err.println("REJECTED: " + fileName);
            System.err.println("\tFile extension must be a \"" + ext + "\"");
        }

        return false;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter">

    public String get_ext()
    {
        return ext;
    }

    public File get_dir()
    {
        return dir;
    }

    public String get_dirName()
    {
        return dirName;
    }

    public int get_total_files()
    {
        return total_files;
    }

    public File[] get_list_files()
    {
        return list_files;
    }
    
    public File get_file_i(int i)
    {
        return list_files[i];
    }

    public File get_curr_file()
    {
        return file;
    }

    public String get_curr_fileName()
    {
        return fileName;
    }

    public String get_curr_fileName_noExt()
    {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter - date">

    public String get_rptDate(String filename)
    {
        Pattern date_pattern = Pattern.compile("([2-9]\\d\\d\\d)([0-1]\\d)([0-3]\\d)");
        return get_rptDate_viaFileName(filename, date_pattern);
    }

    public String get_rptDate_hyphenFormat(String filename)
    {
        String rptDate = get_rptDate(filename);
        return rptDate.substring(0, 4) + "-" + rptDate.substring(4, 6) + "-" + rptDate.substring(6, 8);
    }

    public String get_rptDate_asBeginningOfMonth(String rptDate)
    {
        int endIndex = 6;
        if(rptDate.contains("-") || rptDate.contains("/"))
            endIndex = 8;
        return rptDate.substring(0, endIndex) + "01";
    }
    
    public String get_rptDate_noDay(String filename)
    {
        Pattern dateNoDay_pattern = Pattern.compile("([2-9]\\d\\d\\d)([0-1]\\d)");
        return get_rptDate_viaFileName(filename, dateNoDay_pattern);
    }

    public String get_rptDate_yearOnly(String filename)
    {
        Pattern dateNoDay_pattern = Pattern.compile("([2-9]\\d\\d\\d)");
        return get_rptDate_viaFileName(filename, dateNoDay_pattern);
    }

    public int get_month_string2int(String date)
    {
        return Integer.parseInt(date.substring(4, 6));
    }

    public int get_day_string2int(String date)
    {
        return Integer.parseInt(date.substring(6, date.length()));
    }

    public String get_rptDate_viaFileName(String fileName, Pattern pattern)
    {
        Matcher matcher = pattern.matcher(fileName);
        if(matcher.find())
            return matcher.group();

        System.out.println("No report date found on " + fileName);
        return "";
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean - date">
    
    public boolean is_month_validated(int month)
    {
        return (month > 0 && month <= 12) ? true : false;
    }

    public boolean is_day_validated(int day)
    {
        return (day > 0 && day <= 31) ? true : false;
    }

    // </editor-fold>
}