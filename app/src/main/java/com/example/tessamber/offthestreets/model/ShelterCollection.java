package com.example.tessamber.offthestreets.model;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tess.amber on 3/13/18.
 */

public class ShelterCollection {

    public static final ShelterCollection INSTANCE = new ShelterCollection();

    private ArrayList<HomelessShelter> shelters;

    private ShelterCollection() {
        shelters = new ArrayList<>();
    }

    public void addShelter(HomelessShelter shelter) {
        shelters.add(shelter);
    }

    public ArrayList<HomelessShelter> getShelters() {
        return shelters;
    }

    public HomelessShelter findItemById(int id) {
        for (HomelessShelter h : shelters) {
            if (h.getId() == id) return h;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + id);
        return null;
    }

    private ArrayList<HomelessShelter> searchByName(String name) {
        ArrayList<HomelessShelter> displayList = new ArrayList<HomelessShelter>();
        for (int i = 0; i < shelters.size(); i++) {
            HomelessShelter shelt = shelters.get(i);
            // the replace all is so it can match families with newborns"
            if(shelt.getShelterName().equalsIgnoreCase(name)) {
                displayList.add(shelt);
            }
        }
        return displayList;
    }

    private boolean searchForGender(HomelessShelter s, String gender, String ageRange) {
        //if its empty return true
        if (gender.isEmpty()) {
            return true;
        }
        //if it says men or women.. check if it matches the homeless shelter restriction..
        if (gender.equalsIgnoreCase("women") || gender.equalsIgnoreCase("men")) {
            //differentiate between men and women...
            return (s.getGender().equalsIgnoreCase(gender));
        } else {
            return !(ageRange.toLowerCase().contains("children"));
        }
        //if it doesn't say men or women, don't bother, everything is true.. unless it specifies children
    }

    private boolean searchForAgeRange(HomelessShelter s, String ageRange) {
        if (ageRange.equalsIgnoreCase("Anyone")) {
            return true;
        } else if (ageRange.toLowerCase().contains("children") && s.getRestrictions().toLowerCase().contains("children")) {
            return true;
        } else if (ageRange.toLowerCase().contains("newborns") && s.getRestrictions().toLowerCase().contains("newborns")) {
            return true;
        } else {
            return (ageRange.toLowerCase().contains("young adults") && s.getRestrictions().toLowerCase().contains("young adults"));
        }
    }

    public ArrayList<HomelessShelter> searchShelterList(String gender, String ageRange, String name) {

        ArrayList<HomelessShelter> displayList = new ArrayList<HomelessShelter>();
        // if all the search boxes are empty
        if (TextUtils.isEmpty(gender) && TextUtils.isEmpty(ageRange) && TextUtils.isEmpty(name)) {
            Log.d("ifSearchBoxesAreEmpty", "display whole list of shelters");
            return shelters;
        }
        if (!TextUtils.isEmpty(name)) {
            Log.d("searchByName", "display shelter");
            return searchByName(name);
        }
        //run through the list, add if it equals this & this..
        boolean flag1;
        boolean flag2;
        for (HomelessShelter s : shelters) {

            flag1 = (gender.isEmpty() || searchForGender(s, gender, ageRange));
            flag2 = (ageRange.isEmpty() || searchForAgeRange(s, ageRange));

            if (flag1 && flag2) { displayList.add(s); }
        }
        return displayList;
    }
}
