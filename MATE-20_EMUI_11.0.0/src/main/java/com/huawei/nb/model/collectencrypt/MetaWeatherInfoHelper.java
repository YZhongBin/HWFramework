package com.huawei.nb.model.collectencrypt;

import android.database.Cursor;
import com.huawei.odmf.database.Statement;
import com.huawei.odmf.model.AEntityHelper;

public class MetaWeatherInfoHelper extends AEntityHelper<MetaWeatherInfo> {
    private static final MetaWeatherInfoHelper INSTANCE = new MetaWeatherInfoHelper();

    @Override // com.huawei.odmf.model.AEntityHelper
    public int getNumberOfRelationships() {
        return 0;
    }

    public Object getRelationshipObject(String str, MetaWeatherInfo metaWeatherInfo) {
        return null;
    }

    private MetaWeatherInfoHelper() {
    }

    public static MetaWeatherInfoHelper getInstance() {
        return INSTANCE;
    }

    public void bindValue(Statement statement, MetaWeatherInfo metaWeatherInfo) {
        Integer id = metaWeatherInfo.getId();
        if (id != null) {
            statement.bindLong(1, (long) id.intValue());
        } else {
            statement.bindNull(1);
        }
        String city_code = metaWeatherInfo.getCity_code();
        if (city_code != null) {
            statement.bindString(2, city_code);
        } else {
            statement.bindNull(2);
        }
        String time_zone = metaWeatherInfo.getTime_zone();
        if (time_zone != null) {
            statement.bindString(3, time_zone);
        } else {
            statement.bindNull(3);
        }
        Long update_time = metaWeatherInfo.getUpdate_time();
        if (update_time != null) {
            statement.bindLong(4, update_time.longValue());
        } else {
            statement.bindNull(4);
        }
        Float temperature_high = metaWeatherInfo.getTemperature_high();
        if (temperature_high != null) {
            statement.bindString(5, Float.toString(temperature_high.floatValue()));
        } else {
            statement.bindNull(5);
        }
        Float temperature_low = metaWeatherInfo.getTemperature_low();
        if (temperature_low != null) {
            statement.bindString(6, Float.toString(temperature_low.floatValue()));
        } else {
            statement.bindNull(6);
        }
        String weather_text = metaWeatherInfo.getWeather_text();
        if (weather_text != null) {
            statement.bindString(7, weather_text);
        } else {
            statement.bindNull(7);
        }
        Integer wind_speed = metaWeatherInfo.getWind_speed();
        if (wind_speed != null) {
            statement.bindLong(8, (long) wind_speed.intValue());
        } else {
            statement.bindNull(8);
        }
        String wind_direction = metaWeatherInfo.getWind_direction();
        if (wind_direction != null) {
            statement.bindString(9, wind_direction);
        } else {
            statement.bindNull(9);
        }
        Integer p_num = metaWeatherInfo.getP_num();
        if (p_num != null) {
            statement.bindLong(10, (long) p_num.intValue());
        } else {
            statement.bindNull(10);
        }
        String p_status_cn = metaWeatherInfo.getP_status_cn();
        if (p_status_cn != null) {
            statement.bindString(11, p_status_cn);
        } else {
            statement.bindNull(11);
        }
        String p_status_en = metaWeatherInfo.getP_status_en();
        if (p_status_en != null) {
            statement.bindString(12, p_status_en);
        } else {
            statement.bindNull(12);
        }
        Float pm10 = metaWeatherInfo.getPm10();
        if (pm10 != null) {
            statement.bindString(13, Float.toString(pm10.floatValue()));
        } else {
            statement.bindNull(13);
        }
        Float pm2_5 = metaWeatherInfo.getPm2_5();
        if (pm2_5 != null) {
            statement.bindString(14, Float.toString(pm2_5.floatValue()));
        } else {
            statement.bindNull(14);
        }
        Float no2 = metaWeatherInfo.getNo2();
        if (no2 != null) {
            statement.bindString(15, Float.toString(no2.floatValue()));
        } else {
            statement.bindNull(15);
        }
        Float so2 = metaWeatherInfo.getSo2();
        if (so2 != null) {
            statement.bindString(16, Float.toString(so2.floatValue()));
        } else {
            statement.bindNull(16);
        }
        Float o3 = metaWeatherInfo.getO3();
        if (o3 != null) {
            statement.bindString(17, Float.toString(o3.floatValue()));
        } else {
            statement.bindNull(17);
        }
        Float co = metaWeatherInfo.getCo();
        if (co != null) {
            statement.bindString(18, Float.toString(co.floatValue()));
        } else {
            statement.bindNull(18);
        }
        String humidity = metaWeatherInfo.getHumidity();
        if (humidity != null) {
            statement.bindString(19, humidity);
        } else {
            statement.bindNull(19);
        }
        Float air_pressure = metaWeatherInfo.getAir_pressure();
        if (air_pressure != null) {
            statement.bindString(20, Float.toString(air_pressure.floatValue()));
        } else {
            statement.bindNull(20);
        }
        String alarm_type = metaWeatherInfo.getAlarm_type();
        if (alarm_type != null) {
            statement.bindString(21, alarm_type);
        } else {
            statement.bindNull(21);
        }
        String alarm_level = metaWeatherInfo.getAlarm_level();
        if (alarm_level != null) {
            statement.bindString(22, alarm_level);
        } else {
            statement.bindNull(22);
        }
        Integer mReservedInt = metaWeatherInfo.getMReservedInt();
        if (mReservedInt != null) {
            statement.bindLong(23, (long) mReservedInt.intValue());
        } else {
            statement.bindNull(23);
        }
        String mReservedText = metaWeatherInfo.getMReservedText();
        if (mReservedText != null) {
            statement.bindString(24, mReservedText);
        } else {
            statement.bindNull(24);
        }
    }

    @Override // com.huawei.odmf.model.AEntityHelper
    public MetaWeatherInfo readObject(Cursor cursor, int i) {
        return new MetaWeatherInfo(cursor);
    }

    public void setPrimaryKeyValue(MetaWeatherInfo metaWeatherInfo, long j) {
        metaWeatherInfo.setId(Integer.valueOf((int) j));
    }
}
