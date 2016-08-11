package recording.audio.prototype;

/**
 *
 * @author Jafeth
 */
public class Channel {

    private int channels;

    private String channelName;

    public Channel(int channels, String channelName) {
        this.channels = channels;
        this.channelName = channelName;
    }

    @Override
    public String toString() {
        return channelName;
    }

    public int getChannels() {
        return channels;
    }
}
