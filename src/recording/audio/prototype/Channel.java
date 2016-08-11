package recording.audio.prototype;

/**
 * A wrapper class for the amount of channels the program records.
 * 
 * @author Jafeth
 */
public class Channel {

    private int channels;//The amount of channels recorded on.

    private String channelName;//The name that is shown to the user.

    /**
     * Constructor for the Channel class
     * @param channels The amount of channels recorded on.
     * @param channelName The name that is shown to the user.
     */
    public Channel(int channels, String channelName) {
        this.channels = channels;
        this.channelName = channelName;
    }

    /**
     * Gives back the name of channel.
     * @return The name of the channel.
     */
    @Override
    public String toString() {
        return channelName;
    }

    /**
     * Gives back the amount of channels recorded on.
     * @return The amount of channels.
     */
    public int getChannels() {
        return channels;
    }
}
