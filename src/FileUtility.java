package extensions;

import java.io.File;

public class FileUtility
{
    private final String[] exts;
    private final String[] dirs_name;
    
    private String ext;
    private File dir;
    private int total_files;
    private File[] files;
    private File file;
    private String filename;
    private String dir_name;

    public FileUtility(String dir_name, String ext)
    {
        this.dirs_name = new String[] { dir_name };
        this.exts = new String[] { ext };
    }

    public FileUtility(String[] dirs_name, String[] exts)
    {
        this.dirs_name = dirs_name;
        this.exts = exts;
    }

    public void process_dir2file()
    {
        String filename_noExt = "";
        String filename_toLowerCase = "";
        String filename_toLowerCase_noExt = "";

        for(int dirs_name_i = 0; dirs_name_i < dirs_name.length; dirs_name_i++)
        {
            dir_name = dirs_name[dirs_name_i];
            set_dir(dir_name);
            files = dir.listFiles();
            total_files = files.length;

            if (is_dir_validated())
            {
                process_pre_perDir(dirs_name_i, dir_name);

                for (int file_i = 0; file_i < total_files; file_i++)
                {
                    if (is_file_validated(file_i, exts[dirs_name_i]))
                    {
                        filename_noExt = filename.replaceAll(exts[dirs_name_i], "");
                        filename_toLowerCase = filename.toLowerCase();
                        filename_toLowerCase_noExt = filename_toLowerCase.replaceAll(exts[dirs_name_i], "");

                        if (filename_toLowerCase.endsWith(exts[dirs_name_i]))
                            process_files_perDir(filename, filename_noExt, filename_toLowerCase_noExt, file, dirs_name_i);
                    }
                }

                process_post_perDir(dirs_name_i, dir_name);
            }
        }
    }

    protected void process_pre_perDir(int dirs_name_i, String dir_name){}
    protected void process_files_perDir(String filename, String filename_noExt, String filename_toLowerCase_noExt, File file, int dirs_name_i){}
    protected void process_post_perDir(int dirs_name_i, String dir_name){}
    protected void show_list_req_dataInput(String dir_name){}

    // <editor-fold defaultstate="collapsed" desc="Setter">

    public void set_dir(String dir_name)
    {
        String sysPath = System.getProperty("user.dir");
        dir = new File(sysPath + "\\src");
        if(dir.exists())
            init_dir(dir.getAbsolutePath() + "\\" + dir_name);   //For Netbeans env
        else
            init_dir(sysPath + "\\" + dir_name); //For built project env
    }

    private void init_dir(String path)
    {
        dir = new File(path);
        if (!dir.exists())
        {
            boolean isMkDir = dir.mkdir();
            if (isMkDir)
                System.out.println("NEW FOLDER CREATED: " + dir_name);
        }
    }

    public void set_curr_file(int i)
    {
        set_curr_file(files[i]);
    }

    public void set_curr_file(File curr_file)
    {
        this.file = curr_file;
        this.filename = curr_file.getName();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean">

    public boolean is_dir_validated()
    {
        if (!dir.exists())
        {
            System.out.println("Please create folder name \"" + dir_name + "\" and put \"" + ext + "\" files in there");
            return false;
        }
        else if(!dir.isDirectory())
        {
            System.out.println(dir_name + " is not directory");
            return false;
        }
        else if (total_files == 0)
        {
            System.out.println("The folder \"" + dir_name + "\" is empty, please put the file...");
            show_list_req_dataInput(dir_name);
            return false;
        }

        return true;
    }

    public boolean is_file_validated(int i, String ext)
    {
        set_curr_file(files[i]);
        return is_file_validated(ext);
    }
    
    public boolean is_file_validated(String ext)
    {
        if(file.isFile())
        {
            if(filename.toLowerCase().endsWith(ext))
                return true;
            System.out.println("REJECTED the file with unsupported extension, \"" + ext + "\" is required : " + filename);
        }
        else
        {
            System.out.println("REJECTED the non-file : " + filename);
        }

        return false;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter">

    public String get_ext()                 { return ext; }
    public File get_dir()                   { return dir; }
    public String get_dirName()             { return dir_name; }
    public int get_total_files()            { return total_files; }
    public File[] get_list_files()          { return files; }    
    public File get_file_i(int i)           { return files[i]; }
    public File get_curr_file()             { return file; }
    public String get_curr_fileName()       { return filename; }
    public String get_curr_fileName_noExt() { return filename.substring(0, filename.lastIndexOf(".")); }

    // </editor-fold>
}