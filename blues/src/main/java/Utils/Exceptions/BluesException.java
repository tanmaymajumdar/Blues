package Utils.Exceptions;

import android.util.Log;

import static Utils.Exceptions.ExceptionEnum.NULL_MANAGER;

public class BluesException extends Exception {

    private final String TAG = "BluesException";

    private BluesException(){}

    public BluesException(ExceptionEnum exceptionEnum) {
        switch (exceptionEnum){
            case NULL_MANAGER:
                Log.e(TAG , "Unable to find any location manager");
                break;

            case GPS_DIABLED:
                Log.e(TAG , "Gps is disabled");
                break;

            case NETWORK_DISABLED:
                Log.e(TAG , "There is no network available");
                break;

            case PASSIVE_DISABLED:
                Log.e(TAG , "There is no passive driver provided");
        }
    }

}
