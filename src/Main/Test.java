package Main;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Test {

    private static final String defaultNumbers = " hai ba bốn năm sáu bảy tám chín";
    private static final String[] chuHangDonVi = ("một" + defaultNumbers).split(" ");
    private static final String[] chuHangChuc = ("lẻ mười" + defaultNumbers).split(" ");
    private static final String[] chuHangTram = ("không một" + defaultNumbers).split(" ");
    private static final String[] dvBlock = {"đồng", "nghìn", "triệu", "tỷ", "nghìn tỷ", "triệu tỷ", "tỷ tỷ"};

    private static String convertBlockTwo(String number) {
        String s0 = number.substring(0, 1);
        String s1 = number.substring(1, 2);
        Integer n0 = Integer.parseInt(s0);
        Integer n1 = Integer.parseInt(s1);

        String dv = n1 == 0 ? "" : chuHangDonVi[n1 - 1];
        String chuc = chuHangChuc[n0];
        String append = "";

        if (n0 > 0 && n1 == 5) {
            dv = "lăm";
        }

        if (n0 > 1) {
            append = " mươi";
            if (n1 == 1) {
                dv = "mốt";
            }
        }

        return (chuc + append + " " + dv).trim();
    }

	private static String convertBlockThree(String number) {
        switch (number.length())
        {
            case 0:
                return "";
            case 1:
                return chuHangDonVi[Integer.parseInt(number) - 1];
            case 2:
                return convertBlockTwo(number);
            case 3:
                String chuc_dv = "";
                if (!number.substring(1, 3).equals("00"))
                {
                    chuc_dv = convertBlockTwo(number.substring(1, 3));
                }
                String tram = chuHangTram[Integer.parseInt(number.substring(0, 1))] + " trăm";
                return (tram + " " + chuc_dv).trim();
        }
        return "";
    }

    private static String toVietnamese(long number)
    {
    	if (number == 0) {
    		return "không đồng";
    	}
        int i = 0;
        String str = String.valueOf(Math.abs(number));
        ArrayList<String> arr = new ArrayList<String>();
        int index = str.length();
        ArrayList<String> result = new ArrayList<String>();
        StringBuilder rsString = new StringBuilder();

        if (index == 0 || str == "NaN")
        {
            return "";
        }

        while (index > 0)
        {
        	int startIndex = Math.max(index - 3, 0);
            arr.add(str.substring(startIndex, index));
            index -= 3;
        }

        for (i = arr.size() - 1; i >= 0; i--)
        {
            if (arr.get(i) != "" && !arr.get(i).equals("000"))
            {
                result.add(convertBlockThree(arr.get(i)));

                if (dvBlock[i] != null)
                {
                    result.add(dvBlock[i]);
                }
            }
        }

        for (int j = 0; j < result.size(); j++) {
        	rsString.append(result.get(j).toString());
            if (j < result.size() - 1) {
            	rsString.append(" ");
            }
        }
        
        String res = rsString.toString(); 
        if (!res.endsWith("đồng")) {
        	res += " đồng";
        }
        
        if (number < 0) {
        	res = "âm " + res;
        }
        return res;
    }
	
	public static void main(String[] args) {
		double number = 7500000000000d;

		Locale locale = new Locale("vi", "VI");
		DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
		String formattedNumber = decimalFormat.format(number);
		
		System.out.println(formattedNumber);
		System.out.println(toVietnamese(75000));
	}
}
