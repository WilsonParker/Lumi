package com.graction.developer.lumi.Model.Address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Graction06 on 2018-01-30.
 */

public class PostcodifyModel {
    private String version, error, mes, time, lang, sort, type, nums, cashe;
    private int count;
    private ArrayList<ItemModel> results;

    public static Map<String, String> getParameter(String q){
        Map<String, String> params = new HashMap<>();
        params.put("q",q);
        params.put("v","3.0.0-com.graction.lumi");
        params.put("ref","graction.co.kr");
        params.put("language","ko");
        return params;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getCashe() {
        return cashe;
    }

    public void setCashe(String cashe) {
        this.cashe = cashe;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<ItemModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<ItemModel> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "PostcodifyModel{" +
                "version='" + version + '\'' +
                ", error='" + error + '\'' +
                ", mes='" + mes + '\'' +
                ", time='" + time + '\'' +
                ", lang='" + lang + '\'' +
                ", sort='" + sort + '\'' +
                ", type='" + type + '\'' +
                ", nums='" + nums + '\'' +
                ", cashe='" + cashe + '\'' +
                ", count=" + count +
                ", results=" + results +
                '}';
    }

    public class ItemModel implements Serializable{
        private String postcode5, postcode6, ko_common, ko_doro, ko_jibeon, en_common, en_doro, en_jibeon, building_id, building_name, building_nums, other_addresses, road_id, internal_id;

        public String getPostcode5() {
            return postcode5;
        }

        public void setPostcode5(String postcode5) {
            this.postcode5 = postcode5;
        }

        public String getPostcode6() {
            return postcode6;
        }

        public void setPostcode6(String postcode6) {
            this.postcode6 = postcode6;
        }

        public String getKo_common() {
            return ko_common;
        }

        public void setKo_common(String ko_common) {
            this.ko_common = ko_common;
        }

        public String getKo_doro() {
            return ko_doro;
        }

        public void setKo_doro(String ko_doro) {
            this.ko_doro = ko_doro;
        }

        public String getKo_jibeon() {
            return ko_jibeon;
        }

        public void setKo_jibeon(String ko_jibeon) {
            this.ko_jibeon = ko_jibeon;
        }

        public String getEn_common() {
            return en_common;
        }

        public void setEn_common(String en_common) {
            this.en_common = en_common;
        }

        public String getEn_doro() {
            return en_doro;
        }

        public void setEn_doro(String en_doro) {
            this.en_doro = en_doro;
        }

        public String getEn_jibeon() {
            return en_jibeon;
        }

        public void setEn_jibeon(String en_jibeon) {
            this.en_jibeon = en_jibeon;
        }

        public String getBuilding_id() {
            return building_id;
        }

        public void setBuilding_id(String building_id) {
            this.building_id = building_id;
        }

        public String getBuilding_name() {
            return building_name;
        }

        public void setBuilding_name(String building_name) {
            this.building_name = building_name;
        }

        public String getBuilding_nums() {
            return building_nums;
        }

        public void setBuilding_nums(String building_nums) {
            this.building_nums = building_nums;
        }

        public String getOther_addresses() {
            return other_addresses;
        }

        public void setOther_addresses(String other_addresses) {
            this.other_addresses = other_addresses;
        }

        public String getRoad_id() {
            return road_id;
        }

        public void setRoad_id(String road_id) {
            this.road_id = road_id;
        }

        public String getInternal_id() {
            return internal_id;
        }

        public void setInternal_id(String internal_id) {
            this.internal_id = internal_id;
        }

        @Override
        public String toString() {
            return "ItemModel{" +
                    "postcode5='" + postcode5 + '\'' +
                    ", postcode6='" + postcode6 + '\'' +
                    ", ko_common='" + ko_common + '\'' +
                    ", ko_doro='" + ko_doro + '\'' +
                    ", ko_jibeon='" + ko_jibeon + '\'' +
                    ", en_common='" + en_common + '\'' +
                    ", en_doro='" + en_doro + '\'' +
                    ", en_jibeon='" + en_jibeon + '\'' +
                    ", building_id='" + building_id + '\'' +
                    ", building_name='" + building_name + '\'' +
                    ", building_nums='" + building_nums + '\'' +
                    ", other_addresses='" + other_addresses + '\'' +
                    ", road_id='" + road_id + '\'' +
                    ", internal_id='" + internal_id + '\'' +
                    '}';
        }

        public String getAddress(){
            return (ko_common+" "+ko_jibeon+" "+building_name+" ("+ko_doro+")");
        }
    }
}
