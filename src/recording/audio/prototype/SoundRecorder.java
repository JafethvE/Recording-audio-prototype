package recording.audio.prototype;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.DataLine.Info;

/**
 * A recorder of sounds.
 *
 * @author Jafeth
 */
public class SoundRecorder {

    private final File wavFile;//Path of the wav file
    private final AudioFileFormat.Type fileType;//Format of audio file
    private TargetDataLine line;//The line from which audio data is captured
    private Thread recorderThread;//The seperate thread for the recording of the audio file.
    private boolean recording;//Tells wether the system is recording or not.
    private final RecordingEventCatcher eventCatcher;//An event catcher for Recording Events.
    private AudioFormat format;//An audio format.
    private Info info;//DataLine info for the capturing of the microphone.
    private final FileSystem fileSystem;

    /**
     * A default constructor for the SoundRecorder class.
     *
     * @param gui The GUI of the running program.
     */
    public SoundRecorder(RecorderGUI gui) {
        //Creates a file system to check the OS.
        fileSystem = new FileSystem();

        //Creates an event catcher to notify the user of errors.
        eventCatcher = new RecordingEventCatcher(gui);

        //Checks if the OS is supported. This does not neccaserily mean the program will not work.
        if (fileSystem.getOs() == Os.Different) {
            //Notifies the user the OS is not officially supported.
            eventCatcher.osNotSupportedEvent();
        }

        //Creates the directory if needed.
        new File(fileSystem.getFilePath()).mkdirs();

        //The file we'll be recording to.
        wavFile = new File(fileSystem.getFilePath() + "RecordAudio.wav");

        //Sets the filetype of the recording to .wav.
        fileType = AudioFileFormat.Type.WAVE;

        //We're not recording yet,
        recording = false;

    }

    /**
     * Sets an audio format according to the given parameters.
     *
     * @param sampleRate The sample rate the user has chosen.
     * @param sampleSize The sample size the user has chosen.
     * @param channels The amount of channels the user has chosen.
     * @param signed Indicates whether the data is signed or unsigned
     * @param bigEndian Indicates whether the audio data is stored in big-endian
     * or little-endian order.
     */
    private void setAudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian) {
        format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    /**
     * Starts the recording of the microphone.
     *
     * @param sampleRate The sample rate the user has chosen.
     * @param sampleSize The sample size the user has chosen.
     * @param channels The amount of channels the user has chosen.
     */
    public void startRecording(float sampleRate, int sampleSize, int channels) {
        //Checks if the program is not recording already.
        if (!recording) {
            //Defines the new recording thread.
            recorderThread = new Thread(() -> {
                SoundRecorder.this.start();
            });

            //Sets the audio format the user has defined.
            setAudioFormat(sampleRate, sampleSize, channels, true, true);

            //Starts the recording thread.
            recorderThread.start();
        } else {
            //Notifies the user the recording has already started.
            eventCatcher.recordingAlreadyStartedEvent();
        }
    }

    /**
     * Captures the sound and records it into a WAV file
     */
    private void start() {
        //Gets the data line info, such as supported formats.
        info = new DataLine.Info(TargetDataLine.class, format);
        try {
            //Sets the input line.
            line = (TargetDataLine) AudioSystem.getLine(info);

            //Checks if the input line is set correctly.
            if (line != null) {
                //Checks if system supports the data line
                if (AudioSystem.isLineSupported(info)) {
                    try {
                        //Start capturing
                        line.open(format);
                        line.start();

                        AudioInputStream ais = new AudioInputStream(line);

                        //This is set before the actual recording start because it will not fire otherwise.
                        //If an error happens anyway, the recording is set back to false and the recordingNotStartedEvent is fired
                        //This means the user will not even see the notification of the recording being started.
                        recording = true;
                        eventCatcher.recordingStartedEvent();

                        //Start recording
                        AudioSystem.write(ais, fileType, wavFile);
                    } catch (LineUnavailableException | IOException ex) {
                        //Recording is set back to false.
                        recording = false;

                        //Notifies the user the recording has not started.
                        eventCatcher.recordingNotStartedEvent();
                    }
                } else {
                    //Recording is set back to false.
                    recording = false;

                    //Notifies the user the audio format they selected is not supported.
                    eventCatcher.formatNotSupportedEvent();
                }
            } else {
                //Recording is set back to false.
                recording = false;

                //Notifies the user the mircophone was not found.
                eventCatcher.lineNotFoundEvent();
            }
        } catch (Exception ex) {
            //Recording is set back to false.
            recording = false;

            //Notifies the user the audio format they selected is not supported.
            eventCatcher.formatNotSupportedEvent();
        }
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    public void finish() {
        //Checks if the program is indeed recording.
        if (recording) {
            //Stops the line monitoring.
            line.stop();

            //Closes the line.
            line.close();

            //Stops the recording thread.
            recorderThread.interrupt();

            //Recording is set back to false.
            recording = false;

            //The user is notified of the recording having stopped.
            eventCatcher.recordingStoppedEvent(fileSystem);
        } else {
            //The user is notified no recording is going on for them to stop.
            eventCatcher.recordingAlreadyStoppedEvent();
        }
    }
}
