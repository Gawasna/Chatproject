package SampleCode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPv4Extractor {
    public static void main(String[] args) {
        try {
            // Thực thi lệnh ipconfig trong hệ thống
            Process process = Runtime.getRuntime().exec("ipconfig");

            // Đọc kết quả từ tiến trình
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Tìm dòng chứa địa chỉ IPv4
                if (line.contains("IPv4 Address")) {
                    // Sử dụng biểu thức chính quy để tìm IPv4 trong dòng
                    String ipv4Pattern = "(\\d{1,3}\\.){3}\\d{1,3}";
                    Pattern pattern = Pattern.compile(ipv4Pattern);
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String ipv4 = matcher.group();
                        System.out.println(ipv4);
                    }
                }
            }

            // Đóng luồng đọc
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
