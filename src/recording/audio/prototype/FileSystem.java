package recording.audio.prototype;

/**
 * A wrapper class that makes the system platform-independent. Runs Windows,
 * Linux and MacOS right now.
 *
 * @author Jafeth
 */
public class FileSystem {

    private final Os os;//Determines which OS the user uses.
    private final String fileSeperator;//The file seperator for the current OS.

    /**
     * A constructor for the FileSystem class.
     */
    public FileSystem() {
        //Gets the OS name.
        String oSName = System.getProperty("os.name");

        //Fills the os Enum variable.
        if (oSName.contains("Windows")) {
            os = Os.Windows;
        } else if (oSName.contains("Linux")) {
            os = Os.Linux;
        } else if (oSName.contains("Mac OS")) {
            os = Os.MacOS;
        } else {
            os = Os.Different;
        }

        fileSeperator = System.getProperty("file.separator");
    }

    /**
     * Gives back the operating system the user has (Windows, Linux, MaxOS or
     * Different)
     *
     * @return The operating system the user has.
     */
    public Os getOs() {
        return os;
    }

    /**
     * Gives back the file seperator for the current OS.
     *
     * @return the file seperator.
     */
    public String getFileSeperator() {
        return fileSeperator;
    }

    /**
     * Gives back a OS-specific filepath where the recorded line is stored.
     *
     * @return The filepath. TODO: Create way for the user to give their own
     * chosen filepath for the recording.
     */
    public String getFilePath() {
        String filePath;

        switch (os) {
            case Windows:
                filePath = "C:" + fileSeperator + "Test" + fileSeperator;
                break;
            case Linux:
                filePath = fileSeperator + "Documents" + fileSeperator + "Test" + fileSeperator;
                break;
            case MacOS:
                filePath = fileSeperator + "Documents" + fileSeperator + "Test" + fileSeperator;
                break;
            default:
                filePath = "";
                break;
        }

        return filePath;
    }
}
