package util;

import java.time.Duration;
import java.time.LocalDateTime;

public class CompareDate {

	public static String compareDate(String d1, String d2) {

		try {

			String[] d1_1 = d1.split(" ")[0].split("-");
			String[] d1_2 = d1.split(" ")[1].split(":");

			LocalDateTime ldt_d1 = LocalDateTime.of(Integer.parseInt(d1_1[0]), Integer.parseInt(d1_1[1]), Integer.parseInt(d1_1[2]), Integer.parseInt(d1_2[0]), Integer.parseInt(d1_2[1]), Integer.parseInt(d1_2[2]));

			String[] d2_1 = d2.split(" ")[0].split("-");
			String[] d2_2 = d2.split(" ")[1].split(":");

			LocalDateTime ldt_d2 = LocalDateTime.of(Integer.parseInt(d2_1[0]), Integer.parseInt(d2_1[1]), Integer.parseInt(d2_1[2]), Integer.parseInt(d2_2[0]), Integer.parseInt(d2_2[1]), Integer.parseInt(d2_2[2]));

			long dif = Duration.between(ldt_d1, ldt_d2).toMinutes();

			// 得られた分数によって表示を変更
			if(dif < 60) {
				return dif + "分前";
			}
			else if((dif /= 60) < 24) {
				return dif + "時間前";
			}
			else if((dif /= 24) < 7) {
				return dif + "日前";
			}
			else {
				return d1;
			}

		}
		catch(Exception e) {
			return "エラー";
		}

	}

}
