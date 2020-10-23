/*
 * Copyright (c) 2020.  FanapSoft Software Inc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ir.moke.foodpicker.vo;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

import java.time.ZoneId;
import java.util.Date;

public class CalendarUtil {

    public static final ULocale PERSIAN_LOCALE = new ULocale("fa_IR@calendar=persian");
    public static final ULocale PERSIAN_EN_LOCALE = new ULocale("en@calendar=persian");
    public static final ZoneId IRAN_ZONE_ID = ZoneId.of("Asia/Tehran");


    public static void main(String[] args) {
       /* PersianDateConverter pdc = new PersianDateConverter();
        System.out.println(pdc.getPersianDate());
        System.out.println(pdc.getGregorianDate());
        System.out.println(pdc.getWeekDayStr());*/

        Calendar cal = Calendar.getInstance(PERSIAN_LOCALE);
        System.out.println(new SimpleDateFormat("dd/MM/yy - H:mm:dd",PERSIAN_EN_LOCALE).format(new Date()));
        System.out.println(DateFormat.getDateInstance(DateFormat.FULL,PERSIAN_LOCALE).format(new Date()));
        System.out.println(fromDateToPersianCalendar(new Date()));
    }

    public static Calendar fromDateToPersianCalendar(Date date) {
        Calendar persianCalendar = Calendar.getInstance(PERSIAN_LOCALE);
        persianCalendar.clear();
        persianCalendar.setTime(date);
        return persianCalendar;
    }
}
