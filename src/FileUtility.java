import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtility
{
    private String ext;
    private File dir;
    private int totalFiles;
    private File[] filesList;
    private File file;
    private String fileName;
    private String dirName;
    private boolean isFoundFile;

    // <editor-fold defaultstate="collapsed" desc="GETTER">

    public String getExt()
    {
        return ext;
    }

    public File getDir()
    {
        return dir;
    }

    public String getDirName()
    {
        return dirName;
    }

    public int getTotalFiles()
    {
        return totalFiles;
    }

    public File[] getFilesList()
    {
        return filesList;
    }
    
    public File getFile_i(int i)
    {
        return filesList[i];
    }

    public File getCurrFile()
    {
        return file;
    }

    public String getCurrFileName()
    {
        return fileName;
    }
    
    // </editor-fold>

    public FileUtility(String dirName, String ext)
    {
        this.ext = ext;
        this.dirName = dirName;
        setDir(dirName);
        filesList = dir.listFiles();
        totalFiles = filesList.length;
        for(int i = 0; i < filesList.length; i++)
        {
            if(filesList[i].isFile())
            {
                isFoundFile = true;
                break;
            }
        }
    }

    public void setDir(String dirName)
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

    public boolean isDirValidated()
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
        else if (totalFiles == 0 || !isFoundFile)
        {
            System.err.println("Please put your \"" + ext + "\" file into folder \"" + dirName + "\"");
            return false;
        }

        return true;
    }

    public void setCurrFile(int i)
    {
        setCurrFile(filesList[i]);
    }

    public void setCurrFile(File currFile)
    {
        this.file = currFile;
        this.fileName = currFile.getName();
    }

    public boolean isFileValidated(int i)
    {
        setCurrFile(filesList[i]);
        return isFileValidated();
    }

    public boolean isFileValidated()
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

    public String getReportDate(String filename)
    {
        Pattern date_pattern = Pattern.compile("([2-9]\\d\\d\\d)([0-1]\\d)([0-3]\\d)");
        return getReportDateViaFileName(filename, date_pattern);
    }
    
    public String getReportDate_NoDay(String filename)
    {
        Pattern dateNoDay_pattern = Pattern.compile("([2-9]\\d\\d\\d)([0-1]\\d)");
        return getReportDateViaFileName(filename, dateNoDay_pattern);
    }

    public String getReportDate_OnlyYear(String filename)
    {
        Pattern dateNoDay_pattern = Pattern.compile("([2-9]\\d\\d\\d)");
        return getReportDateViaFileName(filename, dateNoDay_pattern);
    }

    public int getMonth_String2Int(String date)
    {
        return Integer.parseInt(date.substring(4, 6));
    }

    public int getDay_String2Int(String date)
    {
        return Integer.parseInt(date.substring(6, date.length()));
    }

    public boolean isMonthValidated(int month)
    {
        return (month > 0 && month <= 12) ? true : false;
    }

    public boolean isDayValidated(int day)
    {
        return (day > 0 && day <= 31) ? true : false;
    }

    public String getReportDateViaFileName(String fileName, Pattern pattern)
    {
        Matcher matcher = pattern.matcher(fileName);
        if(matcher.find())
            return matcher.group();

        System.out.println("No report date found on " + fileName);
        return "";
    }
}
