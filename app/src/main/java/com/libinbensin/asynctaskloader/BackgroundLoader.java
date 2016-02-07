package com.libinbensin.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Abstract Loader that provides an {@link AsyncTaskLoader} to do the work.
 * This forces an asynchronous load to ignore previously loaded data and load new one.
 *
 * @see {@link AsyncTaskLoader}
 *
 * @see {@link AsyncTaskLoader#forceLoad()}
 *
 * @author Libin
 */
public abstract class BackgroundLoader<D> extends AsyncTaskLoader<D>{

    /**
     * Default constructor
     *
     * @param context The {@link Context} to use
     */
    public BackgroundLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        // load new data
        forceLoad();
    }
}
