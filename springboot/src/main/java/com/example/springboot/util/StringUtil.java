package com.example.springboot.util;



import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.UUID;

public class StringUtil {
    /**
     * 获取编号
     * @return
     */
    public static String GenerateAUniqueNumber(){
        LocalDate today = LocalDate.now();
        String todayString = today.toString().replace("-","");
        String strCurrentTimeMillis = String.valueOf(System.currentTimeMillis());
        String subStrCurrentTimeMillisg = strCurrentTimeMillis.substring(strCurrentTimeMillis.length() - 3);
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        String uuidSubString = uuidString.replace("-", "").substring(26);

        return StringUtils.join(todayString, subStrCurrentTimeMillisg, uuidSubString);
    }
}
