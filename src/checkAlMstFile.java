import java.io.*;

public class checkAlMstFile {
    private static byte[] alData = null;
    private static byte[] mstData = null;

    private static String filePrefix = "";

    public static void main(String[] args) {
        clear();
        try {
            readAlFile("AL12KSCC21030200_20210302180144");
            readMstFile(filePrefix);

            checkData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readAlFile(String fileName) throws IOException {
        ByteArrayOutputStream convertBody = new ByteArrayOutputStream();
        filePrefix = fileName.substring(0, 4);
        String plmaPath = "C:/Users/namsu choi/Desktop/plMwsSample/" + filePrefix;

        File alFile = new File(plmaPath + "/" + fileName);
        InputStream is = new FileInputStream(alFile);
        int readCnt;

        while ((readCnt = is.read(alData)) != -1) {
            convertBody.write(alData);
        }
        is.close();
        System.out.println("read alFile finish");
    }

    private static void clear() {
        filePrefix = "";
        alData = new byte[100000000];
        mstData = new byte[100000000];
    }

    private static void readMstFile(String filePrefix) throws IOException {
        String mstFilePrefix = "alias_mst_";
        String mstFileSeq = "0" + filePrefix.substring(2, 4);
        String mstFileName = mstFilePrefix + mstFileSeq;

        System.out.println(mstFileName);

        ByteArrayOutputStream convertBody = new ByteArrayOutputStream();
        String mstFilePath = "C:/Users/namsu choi/Desktop/alias_mst";    // "F:/share/file/toll/prefix"

        File mstFile = new File(mstFilePath + "/" + mstFileName);
        InputStream is = new FileInputStream(mstFile);
        int readCnt;

        while ((readCnt = is.read(mstData)) != -1) {
            convertBody.write(mstData);
        }
        is.close();
        System.out.println("read mstFile finish");
    }

    private static void checkData() {
        int mismatchCnt = 0;
        for (int i = 0; i < mstData.length; i++) {
            if (Byte.compare(alData[i], mstData[i]) != 0) {
                System.out.println(String.format("data mismatch [index : %d]", i));
                mismatchCnt++;
            }
        }
        System.out.println(String.format("check Finish [Total Mismatch Count : %d ]", mismatchCnt));
    }
}
