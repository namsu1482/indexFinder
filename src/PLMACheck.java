import java.io.*;

public class PLMACheck {
    private static int fileSize = 100000000;
    private static byte[] curData = null;
    private static byte[] preData = null;

    public static void main(String[] args) throws IOException {
        String curDate = args[0];
        String preDate = args[1];

        for (int i = 0; i < 13; i++) {
            // 오늘 날짜 파일
            String filePrefix = String.format("AL%02d", i);
            System.out.println(filePrefix + "-----------");
            curData = new byte[fileSize];
            readFile(curDate, filePrefix, curData);
            // 어제 날짜 파일
            preData = new byte[fileSize];
            readFile(preDate, filePrefix, preData);

            checkData(curData, preData);
        }
//        // 오늘 날짜 파일
//        curData = new byte[fileSize];
//        readFile("20210422", "AL08", curData);
//        // 어제 날짜 파일
//        preData = new byte[fileSize];
//        readFile("20210421", "AL08", preData);
//
//        checkData(curData, preData);
    }

    private static void readFile(String date, String filePrefix, byte[] fileData) throws IOException {
        byte[] data = fileData;
        String filePath = "D:/plcom/" + date;
        File plmaDir = new File(filePath);
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File file, String fileName) {
                return fileName.startsWith(filePrefix);

            }
        };

        File[] files = plmaDir.listFiles(filenameFilter);
        File mwsFile = null;
        for (File f : files) {
            System.out.println(String.format("File : %s", f));
            mwsFile = f;
        }
        InputStream is = new FileInputStream(mwsFile);
        ByteArrayOutputStream convertBody = new ByteArrayOutputStream();
        int readCnt;

        while ((readCnt = is.read(data)) != -1) {
            convertBody.write(data);
        }
        is.close();
        convertBody.close();
//        System.out.println("read mwsFile finish");
    }

    private static void init(byte[] fileData) {
        byte[] data = new byte[fileSize];

    }

    private static void checkData(byte[] data1, byte[] data2) {
        int changeCnt = 0;
        int enabledCnt = 0;
        int disabledCnt = 0;
        for (int i = 0; i < data2.length; i++) {
            if (Byte.compare(data1[i], data2[i]) != 0) {
                String curData = String.format("%02x", data1[i]);
                String preData = String.format("%02x", data2[i]);
                String curDataState = unHex(curData);
                String preDataState = unHex(preData);

                if (preDataState.equals("0") && curDataState.equals("1")) {
                    enabledCnt++;
                } else {
                    disabledCnt++;
                }
                changeCnt++;
            }
        }
        System.out.println(String.format("check Finish [change Count : %d ]", changeCnt));
        System.out.println(String.format("[enabled Count : %d ]", enabledCnt));
        System.out.println(String.format("[disabled Count : %d ]", disabledCnt));
    }

    public static String unHex(String arg) {
        String str = "";
        for (int i = 0; i < arg.length(); i += 2) {
            String s = arg.substring(i, (i + 2));
            int decimal = Integer.parseInt(s, 16);
            str = str + (char) decimal;
        }
        return str;
    }

}
