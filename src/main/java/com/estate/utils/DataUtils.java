package com.estate.utils;




import com.estate.enums.BuildingTypesEnum;
import com.estate.enums.DistrictsEnum;
import com.estate.enums.TransactionEnum;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataUtils {


    public static Map<String, String> getTransactions() {
        Map<String, String> transactions =  Stream.of(TransactionEnum.values())
                .collect(Collectors.toMap(k -> k.toString(), v -> v.getValue()));
        return transactions;
    }

    public static Map<String, String> getDistricts() {
        Map<String, String> districts = Stream.of(DistrictsEnum.values())
                .collect(Collectors.toMap(k -> k.toString(), v -> v.getDistrictValue()));
        return districts;
    }

    public static Map<String, String> getBuildingTypes() {
        Map<String, String> buildingTypes = Stream.of(BuildingTypesEnum.values())
                .collect(Collectors.toMap(k -> k.toString(), v -> v.getBuildingTypeValue()));
        return buildingTypes;
    }


}
