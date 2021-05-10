import java.util.Scanner;

public class findIndex {
    static Scanner scanner = new Scanner(System.in);
    static int postPayTypeVal = 0;

    public static void main(String[] args) {
        for (;;){
            System.out.println("선후불 (Y/N)");
            String postPayType = scanner.next().toUpperCase();
            if (postPayType.equals("Y")) {
                postPayTypeVal = 4;
            }else if(postPayType.equals("end")){
                break;
            }
            else {
                postPayTypeVal = 8;
            }
            System.out.println("index");
            int index = scanner.nextInt();

            getPlFileAddress(index);
        }
    }
    private static void getPlFileAddress(int value) {
        int excludeCrcVal = (value / (510 * postPayTypeVal)) * (postPayTypeVal * 2);
        int result = (value + excludeCrcVal) / postPayTypeVal;
        String hex = Integer.toHexString(result);

        System.out.println("hex Address : " + "0x"+hex);
    }
}
