package recording.audio.prototype;

/**
 *
 * @author Jafeth
 */
public class RecordingEventCatcher
{
    private final RecorderGUI gui;//The GUI object of the running program
    
    /**
     * Constructor method for the RecordingEventCatcher class
     * @param gui The GUI object of the running program.
     */
    public RecordingEventCatcher(RecorderGUI gui)
    {
        this.gui = gui;
    }
    
    /**
     * Is fired when the set audio format can not be recorded on the found microphone.
     */
    public void formatNotSupportedEvent()
    {
        gui.formatNotSupportedActionPerformed();
    }
    
    /**
     * Is fired when an attempt to find a microphone is unsuccessful.
     */
    public void lineNotFoundEvent()
    {
        //Tells the GUI to notify the user that a suitable microphone could not be found.
        gui.lineNotFoundActionPerformed();
    }
    
    /**
     * Is fired when a recording start attempt is successful.
     */
    public void recordingStartedEvent()
    {
        //Tells the GUI to notify the user that the recording start attempt was successful.
        gui.recordingStartedActionPerformed();
    }
    
    /**
     * Is fired when a recording start attempt is unsuccessful.
     */
    public void recordingNotStartedEvent()
    {
        //Tells the GUI to notify the user that the recording start attempt was unsuccessful.
        gui.recordingNotStartedActionPerformed();
    }
    
    /**
     * Is fired when a recording stop attempt is successful.
     */
    public void recordingStoppedEvent(FileSystem fileSystem)
    {
        //Tells the GUI to notify the user that the recording stop attempt was successful.
        gui.recordingStoppedActionPerformed(fileSystem);
    }
    
    /**
     * Is fired when a recording stop attempt is unsuccessful.
     */
    public void recordingNotStoppedEvent()
    {
        //Tells the GUI to notify the user that the recording stop attempt was unsuccessful.
        gui.recordingNotStoppedActionPerformed();
    }
    
    /**
     * Is fired when the OS is not supported.
     */
    public void osNotSupportedEvent()
    {
        //Tells the GUI to notify the user that the OS is not supported and recording might not work.
        gui.osNotSupportedActionperformed();
    }
}
