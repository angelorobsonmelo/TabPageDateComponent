package br.com.soluevo.tabpagedatelibrary.utils;

import br.com.soluevo.tabpagedatelibrary.domain.MonthResponse;

import java.util.*;

public class DateUtil {

    public static boolean isToday(long timeMilliseconds) {
        Calendar calendar = Calendar.getInstance();
        int todayYear = calendar.get(Calendar.YEAR);
        int todayMonth = calendar.get(Calendar.MONTH);
        int todayDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTimeInMillis(timeMilliseconds);
        return calendar.get(Calendar.YEAR) == todayYear &&
                calendar.get(Calendar.MONTH) == todayMonth &&
                calendar.get(Calendar.DAY_OF_MONTH) == todayDay;
    }

    public static int currentTimeInSeconds() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    public static List<MonthResponse> fillList(List<MonthResponse> response) {
        MonthResponse min = min(response);
        MonthResponse max = max(response);
        List<MonthResponse> allList = create(min, max);
        allList = result(allList, response);

        return allList;

    }

    private static List<MonthResponse> result(List<MonthResponse> allList, List<MonthResponse> response){
        for(MonthResponse e:allList) {
            MonthResponse monthSearch = getElement(response, e);
            if(monthSearch !=null) {
                e.setId(monthSearch.getId());
            }
        }

        return allList;
    }

    private static MonthResponse getElement(List<MonthResponse> list, MonthResponse element) {
        MonthResponse res = null;
        for(MonthResponse e:list) {
            if(e.getMonth() == element.getMonth() && e.getYear() == element.getYear()) {
                res = e;
            }
        }

        return res;
    }
    
    private static MonthResponse min(List<MonthResponse> persons) {
        return Collections.min(persons, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {

                Integer x1 = ((MonthResponse) o1).getYear();
                Integer x2 = ((MonthResponse) o2).getYear();
                int sComp = x1.compareTo(x2);

                if (sComp != 0) {
                    return sComp;
                }

                Integer x11 = ((MonthResponse) o1).getMonth();
                Integer x22 = ((MonthResponse) o2).getMonth();
                return x11.compareTo(x22);
            }});
    }

    private static MonthResponse max(List<MonthResponse> persons) {
        return Collections.max(persons, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {

                Integer x1 = ((MonthResponse) o1).getYear();
                Integer x2 = ((MonthResponse) o2).getYear();
                int sComp = x1.compareTo(x2);

                if (sComp != 0) {
                    return sComp;
                }

                Integer x11 = ((MonthResponse) o1).getMonth();
                Integer x22 = ((MonthResponse) o2).getMonth();
                return x11.compareTo(x22);
            }});
    }

    public static List<MonthResponse> create(MonthResponse min, MonthResponse max){
        List<MonthResponse> resp = new ArrayList<>();

        MonthResponse first = min;
        int ano = first.getYear();
        int mes = first.getMonth();
        while(true) {
            if(max.getYear() == ano && max.getMonth() == mes) {
                resp.add(new MonthResponse(mes, ano));
                break;
            }
            resp.add(new MonthResponse(mes, ano));
            if(mes == 12) {
                mes = 1;
                ano ++;
            }else {
                mes++;
            }
        }
        return resp;
    }
}
