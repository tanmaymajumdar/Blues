package Utils.Interfaces;

import android.location.Location;

public interface BluesLocationListener {
    void onLocationCatched(Location location);
    void onPermissionDenied();
    void onProviderDisabled();
}
