package com.example.myapplication2.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "travels")
public class Travel implements Serializable {
    public String getTravelId() {
        return TravelId;
    }

    public void setTravelId(String travelId) {
        TravelId = travelId;
    }
   @NonNull
    @PrimaryKey
    private String TravelId;
    private String clientName;
    private String ClientPhone;
    private String ClientEmail;
   @TypeConverters(UserLocationConverter.class)
    private String UserLocation;

    public String getTarget() {
        return Target;
    }

    private String Target;
    @TypeConverters(RequestType.class)
    private RequestType requesType;
    @TypeConverters(DateConverter.class)
    private Date TravelDate;
    @TypeConverters(DateConverter.class)
    private Date ArrivalDate;
    @TypeConverters(CompanyConverter.class)
    private HashMap<String, Boolean> Company;
    private int NumOfTravelers;
    @TypeConverters(UserLocationConverter.class)
    private UserLocation us;
    public void setUs(com.example.myapplication2.Entities.UserLocation us) {
        this.us = us;
    }

    public com.example.myapplication2.Entities.UserLocation getUs() {
        return us;
    }


    public Travel() { }

    public Travel(String clientName, String clientPhone, String clientEmail, String city,
                  Date travelDate, Date arrivalDate, int numoftravelers, String target, LatLng Loc) {

        this.clientName = clientName;
        this.ClientPhone = clientPhone;
        this.ClientEmail = clientEmail;
        this.UserLocation =city;
        this.Target=target;
        this.TravelDate = travelDate;
        this.ArrivalDate = arrivalDate;
        this.NumOfTravelers=numoftravelers;
        Company=new HashMap<>();
        //Company.put("GNA",false);
        us=new UserLocation(Loc.latitude,Loc.longitude);

    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return ClientPhone;
    }

    public String getClientEmail() { return ClientEmail; }

    public String getUserLocation() {
        return UserLocation;
    }

    public RequestType getRequesType() {
        return requesType;
    }

    public Date getTravelDate() {
        return TravelDate;
    }

    public Date getArrivalDate() {
        return ArrivalDate;
    }

    public HashMap<String, Boolean> getCompany() {
        return Company;
    }

    public int getNumOfTravelers() {
        return NumOfTravelers;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhone(String clientPhone) {
        ClientPhone = clientPhone;
    }

    public void setClientEmail(String clientEmail) {
        ClientEmail = clientEmail;
    }

    public void setUserLocation(String userLocation) {
        UserLocation = userLocation;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public void setRequesType(RequestType requesType) { this.requesType=  requesType; }

    public void setTravelDate(Date travelDate) {
        TravelDate = travelDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        ArrivalDate = arrivalDate;
    }

    public void setCompany(HashMap<String, Boolean> company) {
        Company = company;
    }

    public void setNumOfTravelers(int numOfTravelers) {
        NumOfTravelers = numOfTravelers;
    }


    public enum RequestType {
        sent(0), accepted(1), run(2), close(3), paid(4);
        private final Integer code;
        RequestType(Integer value) {
            this.code = value;
        }
        public Integer getCode() {
            return code;
        }

        @TypeConverter
        public static RequestType getType(Integer numeral) {
            for (RequestType ds : values())
                if (ds.code.equals(numeral))
                    return ds;
            return null;
        }
        @TypeConverter
        public static Integer getTypeInt(RequestType requestType) {
            if (requestType != null)
                return requestType.code;
            return null;
        }
    }
    public static class DateConverter {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        @TypeConverter
        public Date fromTimestamp(String date) throws ParseException {
            return (date == null ? null : format.parse(date));
        }

        @TypeConverter
        public String dateToTimestamp(Date date) {
            return date == null ? null : format.format(date);
        }
    }

    public static class CompanyConverter {
        @TypeConverter
        public HashMap<String, Boolean> fromString(String value) {
            if (value == null || value.isEmpty())
                return null;
            String[] mapString = value.split(","); //split map into array of (string,boolean) strings
            HashMap<String, Boolean> hashMap = new HashMap<>();
            for (String s1 : mapString) //for all (string,boolean) in the map string
            {
                if (!s1.isEmpty()) {//is empty maybe will needed because the last char in the string is ","
                    String[] s2 = s1.split(":"); //split (string,boolean) to company string and boolean string.
                    Boolean aBoolean = Boolean.parseBoolean(s2[1]);
                    hashMap.put(/*company string:*/s2[0], aBoolean);
                }
            }
            return hashMap;
        }

        @TypeConverter
        public String asString(HashMap<String, Boolean> map) {
            if (map == null)
                return null;
            StringBuilder mapString = new StringBuilder();
            for (Map.Entry<String, Boolean> entry : map.entrySet())
                mapString.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            return mapString.toString();
        }
    }

    public static class UserLocationConverter {
        @TypeConverter
        public UserLocation fromString(String value) {
            if (value == null || value.equals(""))
                return null;
            double lat = Double.parseDouble(value.split(" ")[0]);
            double lang = Double.parseDouble(value.split(" ")[1]);
            return new UserLocation(lat, lang);
        }

        @TypeConverter
        public String asString(UserLocation warehouseUserLocation) {
            return warehouseUserLocation == null ? "" : warehouseUserLocation.getLat() + " " + warehouseUserLocation.getLon();
        }
    }



}
