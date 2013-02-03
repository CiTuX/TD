/**
 * 2 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 * <p>
 * Licensed under the Beerware License : <br />
 * As long as you retain this notice you can do whatever you want with this stuff. If we meet some
 * day, and you think this stuff is worth it, you can buy me a beer in return
 */

package ch.citux.twitchdroid.data.requestmanager;

import android.content.Context;
import ch.citux.twitchdroid.data.service.TwitchService;
import com.foxykeep.datadroid.requestmanager.RequestManager;

public final class TwitchRequestManager extends RequestManager {

    // Singleton management
    private static TwitchRequestManager sInstance;

    public static TwitchRequestManager from(Context context) {
        if (sInstance == null) {
            sInstance = new TwitchRequestManager(context);
        }

        return sInstance;
    }

    // TODO change the TwitchService to your RequestService subclass
    private TwitchRequestManager(Context context) {
        super(context, TwitchService.class);
    }
}
