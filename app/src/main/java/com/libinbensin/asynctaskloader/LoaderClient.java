package com.libinbensin.asynctaskloader;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Loader client that provides the caller to do work on
 * background thread and receive the result on UI Thread.
 *
 * @see {@link BackgroundLoader}
 *
 * @author  Libin
 */
public class LoaderClient<D> {

    /**
     * The activity context requires to run loader using {@link LoaderManager}
     */
    private Activity mActivity;

    /**
     * Default empty constructor as private to avoid null {@link Activity}
     */
    private LoaderClient(){
        //NOP
    }

    /**
     * Constructor with accepts the {@link Activity} context
     *
     * @param activity The {@link Activity} to use {@link LoaderManager}
     */
    public LoaderClient(@NonNull Activity activity) {
        mActivity = activity;
    }

    /**
     * Start loader to load data on Worker thread.
     * This forces an asynchronous load to ignore previously loaded data and load new one.
     *
     * @param callback The {@link LoaderClientCallback} to use
     */
    public void load(@NonNull LoaderClientCallback<D> callback){
        load(0 , callback);
    }

    /**
     * Start loader to load data on Worker thread.
     *
     * @param id A unique identifier for this loader
     * @param callback The {@link LoaderClientCallback}
     */
    public void load(int id , @NonNull final LoaderClientCallback<D> callback){
        mActivity.getLoaderManager().restartLoader(id, null, new LoaderManager.LoaderCallbacks<D>() {
            @Override
            public Loader<D> onCreateLoader(int id, Bundle args) {
                return new BackgroundLoader<D>(mActivity) {
                    @Override
                    public D loadInBackground() {
                        return callback.onLoadInBackground();
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<D> loader, D data) {
                callback.onResult(data);
            }

            @Override
            public void onLoaderReset(Loader<D> loader) {
                //NOP
                // clear data here
            }
        });
    }

    /**
     * Callback Interface for the application to load data.
     *
     * @param <D>
     */
    public interface LoaderClientCallback<D> {
        /**
         * Called on a worker thread to perform the actual load and to return
         * the result of the load operation.
         *
         * @return The result of the load operation.
         */
        D onLoadInBackground();

        /**
         * Called to deliver the result on UI Thread
         * @param result The result of the load operation
         */
        void onResult(D result);
    }

}
