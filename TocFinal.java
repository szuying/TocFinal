import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.net.*;
import org.json.*;

public class TocFinal{

	public static void main(String[] args) throws Exception {

		String addr[] = new String[1000];
		float weight[] = new float[1000];
		int price_house[] = new int[1000];

		/*************** 實價登錄資訊 ******************************************************/
		URL url = new URL(
				"https://raw.githubusercontent.com/szuying/TocFinal/master/info/House.json");
		URLConnection connect = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connect.getInputStream(), "UTF-8"));
		String inputLine;

		String address = null;
		int price = 0;
		int total = 0;
		int count = 0;

		while ((inputLine = in.readLine()) != null) { // System.out.println(inputLine);

			if (count != 0 && inputLine.charAt(0) == '{') {
				JSONTokener toke = new JSONTokener(inputLine);
				JSONObject houseOBJ;
				try {
					houseOBJ = (JSONObject) toke.nextValue();
					address = houseOBJ.getString("土地區段位置或建物區門牌");
					price = houseOBJ.getInt("單價每平方公尺");
					String patt_address = "臺北市.*路.*段|臺北市.*路|臺北市.*街.*段|臺北市.*街";
					Pattern pattern_address = Pattern.compile(patt_address);
					Matcher match = pattern_address.matcher(address);
					while (match.find()) {
						if (total == 0) {
							addr[total] = match.group();
							weight[total] = 0;
							price_house[total] = price;
							total++;
						}
						for (int i = 0; i <= total; i++) {
							String mat1 = addr[i];
							String mat2 = match.group();
							if (mat2.equals(mat1)) {
								if (price_house[i] > price)
									price_house[i] = price;
								break;
							} else if (i == total) {
								addr[total] = match.group();
								weight[total] = 0;
								price_house[total] = price;
								total++;
								break;
							}
						}
						// System.out.println(addr[total]+" "+price_house[total]);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			count++;
		}
		in.close();
		 System.out.println(total);

		/*************** 捷運站資訊 ******************************************************/
		URL url2 = new URL(
				"https://raw.githubusercontent.com/szuying/TocFinal/master/info/MRT.json");
		URLConnection connect2 = url2.openConnection();
		BufferedReader in2 = new BufferedReader(new InputStreamReader(
				connect2.getInputStream(), "UTF-8"));
		String inputLine2;
		String station = null;
		String address_sta = null;
		int count2 = 0;
		int total2 = 0;
		while ((inputLine2 = in2.readLine()) != null) { // System.out.println(inputLine);
			if (count2 != 0 && inputLine2.charAt(0) == '{') {
				JSONTokener toke = new JSONTokener(inputLine2);
				JSONObject stationOBJ;
				try {
					stationOBJ = (JSONObject) toke.nextValue();
					station = stationOBJ.getString("name");
					address_sta = stationOBJ.getString("address");
					String patt_station = "臺北市.*路.*段|臺北市.*路|臺北市.*街.*段|臺北市.*街";
					Pattern pattern_station = Pattern.compile(patt_station);
					Matcher match_sta = pattern_station.matcher(address_sta);
					while (match_sta.find()) {
						// System.out.println(station + "站    " +
						// match_sta.group() );
						total2++;
						for (int i = 0; i < total; i++) {
							String now_station = match_sta.group() + ".*";
							Pattern pattern_now = Pattern.compile(now_station);
							Matcher match_now = pattern_now.matcher(addr[i]);
							while (match_now.find()) {
								// System.out.println(match_sta.group()+"  "+addr[i]);
								weight[i] += 5;
							}
						}
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			count2++;
		}
		in2.close();
		// System.out.println(total2);

		/*************** 超商資訊 ******************************************************/
		URL url3 = new URL(
				"https://raw.githubusercontent.com/szuying/TocFinal/master/info/Market.json");
		URLConnection connect3 = url3.openConnection();
		BufferedReader in3 = new BufferedReader(new InputStreamReader(
				connect3.getInputStream(), "UTF-8"));
		String inputLine3;
		String market = null;
		String address_mar = null;
		int count3 = 0;
		int total3 = 0;
		while ((inputLine3 = in3.readLine()) != null) { // System.out.println(inputLine);
			if (count3 != 0 && inputLine3.charAt(0) == '{') {
				JSONTokener toke = new JSONTokener(inputLine3);
				JSONObject marketOBJ;
				try {
					marketOBJ = (JSONObject) toke.nextValue();
					market = marketOBJ.getString("name");
					address_mar = marketOBJ.getString("address");
					String patt_market = "臺北市.*路.*段|臺北市.*路|臺北市.*街.*段|臺北市.*街";
					Pattern pattern_market = Pattern.compile(patt_market);
					Matcher match_mar = pattern_market.matcher(address_mar);
					while (match_mar.find()) {
						// System.out.println(market + "    " +
						// match_mar.group() );
						total3++;
						for (int i = 0; i < total; i++) {
							String mar_station = match_mar.group() + ".*";
							Pattern pattern_mar = Pattern.compile(mar_station);
							Matcher match_now = pattern_mar.matcher(addr[i]);
							while (match_now.find()) {
								// System.out.println(match_mar.group()+"  "+addr[i]);
								weight[i] += 4;
							}
						}
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			count3++;
		}
		in3.close();

		/*************** 圖書館資訊 ******************************************************/
		URL url4 = new URL(
				"https://raw.githubusercontent.com/szuying/TocFinal/master/info/Library.json");
		URLConnection connect4 = url4.openConnection();
		BufferedReader in4 = new BufferedReader(new InputStreamReader(
				connect4.getInputStream(), "UTF-8"));
		String inputLine4;
		String library = null;
		String address_lib = null;
		int total4 = 0;
		while ((inputLine4 = in4.readLine()) != null) { // System.out.println(inputLine);
			if (inputLine4.charAt(0) == '{') {
				JSONTokener toke = new JSONTokener(inputLine4);
				JSONObject libOBJ;
				try {
					libOBJ = (JSONObject) toke.nextValue();
					library = libOBJ.getString("name");
					address_lib = libOBJ.getString("address");
					String patt_lib = ".*路.*段|.*路|.*街.*段|.*街";
					Pattern pattern_lib = Pattern.compile(patt_lib);
					Matcher match_lib = pattern_lib.matcher(address_lib);
					while (match_lib.find()) {
						// System.out.println(gas + "    " + match_gas.group()
						// );
						total4++;
						for (int i = 0; i < total; i++) {
							String library_station = ".*" + match_lib.group()
									+ ".*";
							Pattern pattern_library = Pattern
									.compile(library_station);
							Matcher match_now = pattern_library
									.matcher(addr[i]);
							while (match_now.find()) {
								// System.out.println(match_gas.group()+"  "+addr[i]);
								weight[i] += 2;
							}
						}
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}
		in4.close();

		/*************** 學校資訊 ******************************************************/
		URL url5 = new URL(
				"https://raw.githubusercontent.com/szuying/TocFinal/master/info/School.json");
		URLConnection connect5 = url5.openConnection();
		BufferedReader in5 = new BufferedReader(new InputStreamReader(
				connect5.getInputStream(), "UTF-8"));
		String inputLine5;
		String school = null;
		String address_sch = null;
		while ((inputLine5 = in5.readLine()) != null) { // System.out.println(inputLine);
			if (inputLine5.charAt(0) == '{') {
				JSONTokener toke = new JSONTokener(inputLine5);
				JSONObject schoolOBJ;
				try {
					schoolOBJ = (JSONObject) toke.nextValue();
					school = schoolOBJ.getString("school_name");
					address_sch = schoolOBJ.getString("address");
					String patt_school = "臺北市.*路.*段|臺北市.*路|臺北市.*街.*段|臺北市.*街";
					Pattern pattern_market = Pattern.compile(patt_school);
					Matcher match_sch = pattern_market.matcher(address_sch);
					while (match_sch.find()) {
						// System.out.println(market + "    " +
						// match_mar.group() );
						for (int i = 0; i < total; i++) {
							String sch_station = match_sch.group() + ".*";
							Pattern pattern_school = Pattern
									.compile(sch_station);
							Matcher match_now = pattern_school.matcher(addr[i]);
							while (match_now.find()) {
								// System.out.println(match_sch.group()+"  "+addr[i]);
								weight[i] += 2;
							}
						}
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}
		in5.close();

		/*************** 加油站資訊 ******************************************************/
		URL url6 = new URL(
				"https://raw.githubusercontent.com/szuying/TocFinal/master/info/GasStation.json");
		URLConnection connect6 = url6.openConnection();
		BufferedReader in6 = new BufferedReader(new InputStreamReader(
				connect6.getInputStream(), "UTF-8"));
		String inputLine6;
		String address_gas = null;
		while ((inputLine6 = in6.readLine()) != null) { // System.out.println(inputLine);
			if (inputLine6.charAt(0) == '{') {
				JSONTokener toke = new JSONTokener(inputLine6);
				JSONObject gasOBJ;
				try {
					gasOBJ = (JSONObject) toke.nextValue();
					address_gas = gasOBJ.getString("address");
					String patt_gas = "臺北市.*路.*段|臺北市.*路|臺北市.*街.*段|臺北市.*街";
					Pattern pattern_gas = Pattern.compile(patt_gas);
					Matcher match_gas = pattern_gas.matcher(address_gas);
					while (match_gas.find()) {
						// System.out.println(match_gas.group() );
						for (int i = 0; i < total; i++) {
							String gas_station = match_gas.group() + ".*";
							Pattern pattern_gaso = Pattern.compile(gas_station);
							Matcher match_now = pattern_gaso.matcher(addr[i]);
							while (match_now.find()) {
								// System.out.println(match_gas.group()+"  "+addr[i]);
								weight[i] += 3;
							}
						}
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}
		in6.close();

		/*************** 商圈資訊 ******************************************************/
		URL url7 = new URL(
				"https://raw.githubusercontent.com/szuying/TocFinal/master/info/BusinessDistrict.json");
		URLConnection connect7 = url7.openConnection();
		BufferedReader in7 = new BufferedReader(new InputStreamReader(
				connect7.getInputStream(), "UTF-8"));
		String inputLine7;
		String block = null;
		while ((inputLine7 = in7.readLine()) != null) { // System.out.println(inputLine);
			if (inputLine7.charAt(0) == '{') {
				JSONTokener toke = new JSONTokener(inputLine7);
				JSONObject busOBJ;
				try {
					busOBJ = (JSONObject) toke.nextValue();
					block = busOBJ.getString("district");
					// address_bus = busOBJ.getString("address");
					String patt_bus = ".*區";
					Pattern pattern_bus = Pattern.compile(patt_bus);
					Matcher match_bus = pattern_bus.matcher(block);
					while (match_bus.find()) {
						// System.out.println(match_bus.group() );
						for (int i = 0; i < total; i++) {
							String bus_station = ".*" + match_bus.group()
									+ ".*";
							Pattern pattern_business = Pattern
									.compile(bus_station);
							Matcher match_now = pattern_business
									.matcher(addr[i]);
							while (match_now.find()) {
								// System.out.println(match_bus.group()+"  "+addr[i]);
								weight[i] += 1;
							}
						}
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}
		in7.close();

		/*************** 醫院資訊 ******************************************************/
		URL url8 = new URL(
				"https://raw.githubusercontent.com/szuying/TocFinal/master/info/Hospital.json");
		URLConnection connect8 = url8.openConnection();
		BufferedReader in8 = new BufferedReader(new InputStreamReader(
				connect8.getInputStream(), "UTF-8"));
		String inputLine8;
		String address_hospital = null;
		while ((inputLine8 = in8.readLine()) != null) { // System.out.println(inputLine);
			if (inputLine8.charAt(0) == '{') {
				JSONTokener toke = new JSONTokener(inputLine8);
				JSONObject hospitalOBJ;
				try {
					hospitalOBJ = (JSONObject) toke.nextValue();
					address_hospital = hospitalOBJ.getString("address");
					// address_bus = busOBJ.getString("address");
					String patt_hospital = "臺北市.*路.*段|臺北市.*路|臺北市.*街.*段|臺北市.*街";
					Pattern pattern_bus = Pattern.compile(patt_hospital);
					Matcher match_hospital = pattern_bus
							.matcher(address_hospital);
					while (match_hospital.find()) {
						// System.out.println(match_bus.group() );
						for (int i = 0; i < total; i++) {
							String hospital_station = ".*"
									+ match_hospital.group() + ".*";
							Pattern pattern_hos = Pattern
									.compile(hospital_station);
							Matcher match_now = pattern_hos.matcher(addr[i]);
							while (match_now.find()) {
								// System.out.println(match_hospital.group()+"  "+addr[i]);
								weight[i] += 2;
							}
						}
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		}
		in8.close();

		/*************** 統整資訊 ******************************************************/
		int j = 0;
		int k = 0;
		class choose_house implements Comparable{
			int price;
			String address;
			public int compareTo(Object anotherData) throws ClassCastException {
				if (!(anotherData instanceof choose_house))
					throw new ClassCastException("A Data object expected.");
				int anotherDataprice = ((choose_house) anotherData).price;
				return anotherDataprice - this.price;
			}
		}
		choose_house choose[] = new choose_house[20];
		for(int i=0;i<20;i++)
		{
			choose[i]=new choose_house();
		}
		for (int i = 0; i < total; i++)
			if (weight[i] >= 15) {
				j++;
				if (price_house[i] != 0){
					choose[k].address=addr[i];
					choose[k].price=price_house[i];
					k++;
				}
			}
		Arrays.sort(choose);
		System.out.println("1. "+choose[k-1].address+"   "+choose[k-1].price);
		System.out.println("2. "+choose[k-2].address+"   "+choose[k-2].price);
		System.out.println("3. "+choose[k-3].address+"   "+choose[k-3].price);
	}
}
