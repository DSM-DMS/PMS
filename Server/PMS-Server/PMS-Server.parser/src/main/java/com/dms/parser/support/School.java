package com.dms.parser.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * School API
 * 전국 교육청 소속 교육기관의 학사일정, 메뉴를 간단히 불러올 수 있습니다.
 *
 * @author HyunJun Kim
 * @version 3.0
 */
public class School {
    private static final String MONTHLY_MENU_URL = "sts_sci_md00_001.do";
    private static final String SCHEDULE_URL = "sts_sci_sf01_001.do";

    private static String schoolType = "4";
    private static String schoolRegion = "stu.dje.go.kr";
    private static String schoolCode = "G100000170";

    /**
     * 월간 급식 메뉴를 불러옵니다.
     *
     * @param year  해당 년도를 yyyy 형식으로 입력. (ex. 2016)
     * @param month 해당 월을 m 형식으로 입력. (ex. 3, 12)
     * @return 각 일자별 급식메뉴 리스트
     */
    public static List<SchoolMenu> getMonthlyMenu(int year, int month) throws SchoolException {

        StringBuffer targetUrl = new StringBuffer("http://" + schoolRegion + "/" + MONTHLY_MENU_URL);
        targetUrl.append("?");
        targetUrl.append("schulCode=" + schoolCode + "&");
        targetUrl.append("schulCrseScCode=" + schoolType + "&");
        targetUrl.append("schulKndScCode=" + "0" + schoolType + "&");
        targetUrl.append("schYm=" + year + String.format("%02d", month) + "&");

        try {
            String content = getContentFromUrl(new URL(targetUrl.toString()), "<tbody>", "</tbody>");
            return SchoolMenuParser.parse(content);
        } catch (MalformedURLException e) {
            throw new SchoolException("교육청 접속 주소가 올바르지 않습니다.");
        }
    }

    /**
     * 월간 학사 일정을 불러옵니다.
     *
     * @param year  해당 년도를 yyyy 형식으로 입력. (ex. 2016)
     * @param month 해당 월을 m 형식으로 입력. (ex. 3, 12)
     * @return 각 일자별 학사일정 리스트
     */
    public static List<SchoolSchedule> getMonthlySchedule(int year, int month) throws SchoolException {

        StringBuffer targetUrl = new StringBuffer("http://" + schoolRegion + "/" + SCHEDULE_URL);
        targetUrl.append("?");
        targetUrl.append("schulCode=" + schoolCode + "&");
        targetUrl.append("schulCrseScCode=" + schoolType + "&");
        targetUrl.append("schulKndScCode=" + "0" + schoolType + "&");
        targetUrl.append("ay=" + year + "&");
        targetUrl.append("mm=" + String.format("%02d", month) + "&");

        try {
            String content = getContentFromUrl(new URL(targetUrl.toString()), "<tbody>", "</tbody>");
            return SchoolScheduleParser.parse(content);
        } catch (MalformedURLException e) {
            throw new SchoolException("교육청 접속 주소가 올바르지 않습니다.");
        }
    }

    private static String getContentFromUrl(URL url, String readAfter, String readBefore) throws SchoolException {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuffer buffer = new StringBuffer();
            String inputLine;

            boolean reading = false;

            while ((inputLine = reader.readLine()) != null) {
                if (reading) {
                    if (inputLine.contains(readBefore))
                        break;
                    buffer.append(inputLine);
                } else {
                    if (inputLine.contains(readAfter))
                        reading = true;
                }
            }
            reader.close();
            return buffer.toString();

        } catch (IOException e) {
            throw new SchoolException("교육청 서버에 접속하지 못하였습니다.");
        }
    }
}
