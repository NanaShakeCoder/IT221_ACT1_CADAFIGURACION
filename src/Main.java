import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static ArrayList<Manga> mangaList = new ArrayList<>();
    static String[] rows = new String[8];
    static String[] header = new String[8];
    static String filePath = "src\\DataSet.csv";
    static String line;
    static BufferedReader br;
    static {
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static int counter = 0;

    public static void main(String[] args) throws IOException {
        readHeader();
        populateList();
        displayMenu();

    }
    //DISPLAY MAIN MENU
    public static void displayMenu() {
        Scanner scn = new Scanner(System.in);
        //MAIN MENU
        System.out.println("""
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⣴⡂⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢼⣷⣿⣿⡰⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⡞⣿⣿⣿⣿⣇⣣⣔⣖⢀⠀⣀⣤⣖⣂⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⡀⠀⠀⠠⣶⣆⢧⣿⣿⣞⣿⣿⣿⣿⣷⣷⣾⣿⣿⣿⢿⢇⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⣤⣤⡀⠀  ⠀⢀⣤⣄⠀⠀⠀⣠⡤⠀⣠⣤⣤⣤⣤⡄⢀⣤⠀⠀⠀⠀⠀⢠⣤⣤⣤⣤⡤⠀⣠⣤⣤⣤⣤⡄ ⢀⣤⡀⠀⠀⢀⣠⡄⠀⣤⣤⣤⣤⣤⡄
                ⠀⡴⣽⣗⡵⣠⢃⣿⣿⣿⣿⣟⡿⣽⣞⡽⣯⣟⣽⣻⣿⣿⣿⣿⠮⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⣿⡇⢀⣴⠟⢻⣿⠀⣠⡾⠋⠀⣰⣿⣭⣭⣭⡭⠁⣼⠏⠀⠀⠀⠀⢠⡿⠉⠉⠉⠉⠁⣰⡟⠉⠉⣹⡟⢀⣾⣿⡇⠀⣠⣾⡿⠁⣼⣯⣭⣭⣭⡍⠀
                ⠀⢇⢿⣿⣿⣿⣿⡿⣿⢿⣧⣯⣽⣿⠿⠻⠿⠿⣮⣿⢾⡽⣿⣿⣳⣾⣿⣿⠕⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣷⠟⠁⠀⢸⣿⡾⠋⠀⠀⢰⣿⣉⣉⣉⡉⠁⣼⣏⣀⣀⣀⢠⣿⣁⣀⣀⣀⠀⣰⣿⣀⣀⣰⡟⢀⣾⠋⣿⣧⠾⢹⡿⠁⣼⣯⣉⣉⣉⡉⠀⠀
                ⠀⠈⢞⣽⣿⡾⣽⣻⣟⡟⣾⡿⠋⠀⠀⠄⡀⢀⠈⠙⢿⣽⣯⢿⣿⣿⣿⣯⢧⡇⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠁⠀⠀⠀⠈⠉⠀⠀⠀⠀⠉⠉⠉⠉⠉⠁⠈⠉⠉⠉⠉⠉⠀⠉⠉⠉⠉⠉⠁⠀⠉⠉⠉⠉⠉⠁⠈⠁⠀⠉⠉⠀⠉⠁⠀⠉⠉⠉⠉⠉⠀⠀⠀
                ⠀⠀⣨⢸⣿⣿⢿⣿⢾⣽⣿⠃⠀⠂⠁⠄⡀⢲⠇⢀⠂⠹⢿⣿⣟⡾⣿⣿⣷⣶⣶⣶⣅⣦⣶⣴⣶⣶⣒⣒⣒⣒⣒⡦⠤⠶⠖⠶⠖⠢⠴⠴⣒⣒⣦⣄⠤⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⡕⣿⣿⣯⢿⣯⢟⣾⣟⠀⠄⠡⢈⠠⠐⣼⠈⢀⠐⡤⠈⠻⣯⣟⣯⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣾⣖⣅⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠾⢿⣿⠿⠿⠇⢠⡿⠿⠿⢿⣿⠃
                ⠀⣠⢜⣸⣿⣟⣯⣿⡾⣿⣿⡄⠈⠄⠂⡀⢂⡏⡄⠂⢠⡇⠐⡀⠈⠻⣯⣿⢯⣿⣿⣿⣿⠿⠿⠟⠻⠛⠻⠻⠿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡞⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠇⠀⠀⢠⡿⠁⠀ ⢀⣾⠃⠀
                ⢸⢺⣿⣿⣿⢿⣾⣻⡽⣶⣻⣷⡌⢀⠂⠄⢸⡅⠄⠂⢼⠣⠐⢀⠁⠂⠙⢻⣿⡾⠋⠁⠀⠀⢀⠠⠀⠐⠀⠄⠀⡀⠀⡀⠉⠛⠿⣿⠛⠛⠻⣿⣿⣿⣿⣿⣿⣿⣿⣷⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠼⠏⠀⠀⠀⠿⠿⠿⠿⠿⠃⠀⠀
                ⠀⠑⢜⠛⡿⢿⣾⢿⣽⣷⢿⡽⣿⣤⠈⠀⣾⠀⠐⠈⣾⠀⡁⠂⠈⠄⡁⣶⠏⠀⢀⠀⠂⠁⡀⠠⠀⡁⢂⠀⠡⢀⠐⠀⠂⠁⡀⠈⠙⢦⣴⣿⣿⣿⣿⣿⣿⣿⢮⠌⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠈⠲⠥⣿⣿⣷⣿⣻⡽⣟⡿⣷⣌⠃⠈⠄⢡⡟⠂⡀⠡⢈⠀⣼⠇⠀⠀⠂⠀⡐⠀⠄⡐⢀⢀⡀⠄⢂⠀⠄⢂⠈⠀⡐⠀⢠⣾⣿⣿⣿⣿⣿⣿⣿⣞⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣤⣤⣤⣤⣤⣤⡄⠀⣤⠀⠀⠀ ⣤⠄⢠⣤⣤⣤⣤⣤⠀
                ⠀⠀⠀⠀⠀⠑⢪⠕⣟⣿⣷⣿⡿⣽⣻⣿⣷⣈⠀⢲⠇⡀⠄⢁⠠⢰⡏⡀⠐⠈⢀⠐⠀⢁⠠⠐⠀⡀⢀⠐⠀⡈⢀⠠⠀⠐⠀⢠⣿⣿⣿⣿⣿⣿⣿⣿⢮⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⣹⡟⠉⠉⠀⣼⣯⣤⣤⣼⠏⢠⣿⣭⣭⣭⣭⠁⠀
                ⠀⠀⠀⠀⠀⠠⡱⣿⣿⣿⣷⣽⣿⡽⣿⣮⢿⣿⣦⡄⠠⡀⠌⠀⠄⣿⢁⠀⠄⠈⠀⠠⠈⠀⢀⠂⡀⢀⠂⠀⠐⠀⠂⠐⠀⡀⠀⣿⣿⣿⣿⣿⣿⡿⠛⢿⡦⣑⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⡟⠀⠀⠀⣼⠏⠉⠉⣽⡟⢀⣿⣯⣩⣍⣭⠁⠀⠀
                ⠀⠀⠀⠀⠀⠀⠈⠊⠭⢰⣶⡺⢿⣿⣷⣻⣿⡾⣽⣿⣦⡐⢀⠡⢰⡏⠀⠠⠀⠂⠁⡠⢀⣡⡠⠤⠄⣀⡀⠂⠀⠂⠈⠐⠠⠀⠐⣿⣿⣿⣿⣿⡟⠁⠀⠀⠻⣾⢬⢆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠟⠀⠀⠀⠈⠉⠀⠀⠀⠉⠀⠈⠉⠉⠉⠉⠁⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⢨⢲⣾⣿⣿⣷⣿⣷⣯⣟⣿⣦⡄⢸⡇⠄⠁⢀⠠⠀⣰⣿⣿⣿⣷⣦⡀⠙⢢⡌⠀⠄⠁⠠⠀⠠⠙⠻⠿⠿⠋⠀⠀⠠⢀⠀⠘⣿⣪⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣇⣿⡇⡞⠸⣿⣾⣽⣾⡿⣽⢿⣾⡃⠀⡈⠀⠀⢸⣿⣿⣿⣿⣿⣿⡀⠀⠀⢳⠀⠂⠈⢀⠈⢀⠐⠀⠠⠀⢀⠐⠈⠀⡀⠈⠀ ⠈⢷⡯⡣⠀⠀⠀⠀⠀⠀⠀⠻⣿⣿⠀⠀⢀⣴⣿⠃⣰⡿⠿⠿⣿⡟ ⢀⣾⣿⡀⠀⣼⠏⢀⣾⠿⠿⠿⠿⠃⢠⡿⠿⠿⢿⡿⠁
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⢻⣼⠃⡃⣤⣿⣿⣿⣷⣿⣯⣿⣿⠄⠂⢀⠀⠄⢺⣿⣿⣿⣿⣿⣿⣿⡇⠀⢸⢀⠈⠀⠄⠐⠠⠀⠌⠐⠠⠀⠂⠄⠡⢀⠠⠁⠄⠈⢷⡽⢥⠀⠀⠀ ⠀⣰⡟⣿⢀⣴⢿⣿⠃⣰⡿⠿⠿⢿⡿⢀⣾⠋⢻⣇⣼⠟⢀⣾⠃⠾⢿⣿⠃⢠⡿⠿⠿⢿⣿⠁⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢘⢼⣿⠀⣇⢹⣿⣿⣿⡿⢹⣿⣟⣿⡇⠀⠂⠀⠄⠘⣿⣿⣿⣿⣿⣿⡿⠀⠀⡼⠀⠠⠐⠀⠂⠄⠂⠄⠂⠄⡐⠠⢀⠂⠄⡀⠄⠠⠀⠈⢿⡩⣣⠀⠀⠀⣰⡟⠀⠿⠟⠁⠾⠃⠰⠟⠀⠀⠰⠿⠁⠾⠏⠀⠘⠿⠟⠀⠾⠿⠶⠶⠾⠏⠠⠿⠁⠀⠀⠿⠃⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣸⣿⡀⡘⢄⠙⠛⣛⢋⡆⠀⠉⢹⠀⢀⠀⠂⠈⣆⠘⠻⣛⠛⠛⠋⢀⡠⠊⢠⠴⠀⢀⠐⠈⡀⠁⡈⢀⠂⠀⡁⢀⠈⢀⠀⡐⠀⡀⠂⠈⣷⣣⢣⠐⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡏⣿⡇⠉⠀⠉⠉⠁⢸⣇⠀⠀⣼⣇⡀⠀⠀⣰⡟⠀⠠⠀⠌⠉⠁⢀⠀⠀⢀⠛⠆⠀⠐⠠⠀⠡⠐⠀⠌⠐⠀⠂⠈⠀⠂⠠⠁⠀⠐⠀  ⠸⣯⢋⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⠀⣤⣤⡤⠀⠀⢀⣤⠀⣠⡄⠀⠀⣠⡄⢀⣤⣤⣤⣤⣤⠄
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣹⣻⡄⠀⠀⡀⠠⠀⡉⠛⣿⣟⣻⣟⢿⡟⠋⠀⢀⠀⠂⠀⠂⠄⠠⠐⠈⠀⠄⠠⠐⠈⠄⢂⠡⠠⠈⠄⢂⠡⢀⠁⢂⠡⠐⠠⠈⠠⠀⠀⢹⡟⡰⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀  ⢨⣿⣥⣤⣤⣿⠃⣰⡟⠀⠀⢠⡿⠁⣼⣯⣭⣭⣽⠏⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠡⠭⣿⡄⠀⠀⠄⠠⠀⠄⠸⣯⢿⣼⣫⠇⠀⠀⠄⠀⠄⡁⠠⠀⠄⠠⢀⠁⠠⠀⠄⡈⢀⠠⢀⠀⡁⠠⠀⠄⡀⢈⠀⠠⢀⠁⡀⠁⡀⠁ ⠈⣷⣇⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡿⠉⠉⢉⣿⠃⣰⣿⣤⣤⣤⡿⠁⣼⣯⣭⣭⣽⠏⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠑⡽⣿⣦⠀⡀⢀⠈⠀⡀⠘⠛⠻⠋⠀⠀⠁⠀⠌⠐⠀⢁⠈⠀⡁⠂⠈⡀⠁⠂⠐⠀⠂⠀⠂⢀⠁⠈⡀⠐⠀⡈⠐⠀⠂⢀⠁⢀⠈⠀  ⣻⡿⢰⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠿⠃⠀⠀⠈⠁⠀⠉⠉⠉⠉⠉⠁⠈⠉⠉⠉⠉⠉⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠊⠻⣷⡄⠀⢀⠐⠠⠈⠄⠀⠄⠀⠁⠂⢈⠐⠈⠐⠠⠈⡐⠀⢂⠡⠀⠡⠈⠄⠡⠈⠄⢁⠂⢈⠐⠠⠈⠐⢀⠂⠡⠈⠄⠂⠀⡐⠀ ⢹⣏⡼⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠑⢝⢿⣦⡀⠀⡀⠄⠀⢂⠀⠁⠄⠂⢀⠠⠈⠀⠄⠁⠠⠐⡀⠄⡐⠠⠐⠠⢀⠂⠄⢂⠠⠀⠄⢂⠐⡀⢂⠠⠐⠠⢀⠂⢀⠀ ⠠⢸⣧⠧⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠱⣿⣿⡆⠀⠀⠀⢂⣌⡀⠄⠈⠀⢠⡆⠁⠠⠈⠀⠄⠀⠄⢀⠁⡈⢀⠠⠀⠈⢀⠀⠂⠈⡀⠀⠄⡀⠠⠈⢀⠀⡈⠀⠠⠀   ⢸⣇⡇⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢣⢿⣷⡀⠀⠁⠀⠛⠁⠀⡀⠈⣌⠁⠀⠁⠀⡀⡼⠀⠂⠀⠠⠀⠀⢀⠈⠀⠀⡐⠀⠐⠀⠁⠂⠄⠁⠈⠄⠂⠐⠀⡐⠀    ⣽⡿⢻⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣾⣿⡅⠢⠀⠀⢎⢟⣷⡈⠀⠀⠀⢯⡀⢀⢠⡏⠀⠀⠈⠀⣼⠁⠀⡀⠐⠀⠀⣁⣠⠶⠛⠛⠉⠀⠂⠌⡐⠠⠂⠌⠐⡀⠂⡐⠀⠠⠀  ⣿⣿⡛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡸⣝⡿⠉⢿⡿⡱⣶⣿⣾⠿⣷⡀⠁⠀⠚⡇⠀⣾⠇⠀⠈⢠⡿⢵⡶⢶⣄⠀⢤⣾⠟⠁⠀⡀⠀⠂⠀⠄⡀⠄⠠⢀⠀⠂⠀⠄⢀⠈⠀⣰⣟⣇⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⢧⢿⡇⠀⠈⣿⣽⡿⠋⠀⠀⠈⢳⣆⣈⣼⠇⠀⢸⣇⢀⣼⡿⠁⣾⠙⠀⢿⣾⡟⠁⠀⠀⠐⠀⡀⢁⠈⠐⠀⠈⢀⠀⠀⢠⡼⠃⠀⠈⢠⣿⣿⡜⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣸⣿⢀⠀⠐⠘⣇⠀⠀⠄⠂⠁⠂⡈⢉⠁⠠⠐⠀⡉⠉⠉⠀⠀⣿⢈⠀⠀⢹⡀⠀⢀⠈⠐⠠⠐⠂⠈⠀⡁⠀⠂⠀⣠⠞⠁⠀⠀⣠⣿⡷⡵⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⢫⡟⠀⠈⠀⠌⠉⠀⠠⠀⡀⠂⠄⠐⡀⠀⠄⠐⠀⠠⠁⠐⠈⠀⣿⠀⠈⠀⠄⠳⠀⡀⠀⢂⠠⠀⠄⠁⡀⠀⡀⣤⠞⠁⠈⠀⠀⣴⡿⡿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣼⣇⠀⠐⡀⢈⠀⡁⢀⠂⠀⠁⠀⠂⠀⠐⠈⢀⠈⠀⠄⡀⠂⠈⣿⠀⠀⠁⡀⠂⠄⠀⢂⠀⢀⣈⣀⡥⠴⠒⠋⠁⠀⡀⠂⣰⡾⠟⠻⢿⣖⠅⣄⣀⡤⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⡘⠿⣦⣤⣤⣤⣤⣤⣤⣌⣤⣡⣌⣤⣡⣌⣤⣤⣌⣤⣤⣤⣤⣼⣿⠀⠀⠁⠀⠂⠐⠀⠂⠈⠠⠐⠀⠐⠀⢀⠀⣂⣠⣥⣾⣯⣤⣤⣤⣤⣾⡿⣷⣷⣿⣯⣧⣪⢔⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠂⠽⠽⠿⠿⠿⠿⠿⠊⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠘⠿⠿⠿⡹⠟⣻⣛⣟⣛⣛⣛⣛⡛⣻⣟⢛⠛⠟⣛⣿⣿⣫⠾⠶⠶⠾⠿⠿⠷⠽⡭⢽⣭⣽⢷⣿⣿⣿⣧⣣⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                """);
        System.out.println("===================================================================================================================================================================");
        System.out.print("Would you like to see the list of available mangas: (Y/N)");
        String chc = scn.nextLine();
        boolean stop = false;
        while (!stop) {
            if (chc.equals("Y") || chc.equals("y")) {
                displayList();
                System.out.println("===================================================================================================================================================================");
                System.out.println("""
                        \nWhat would you like to do next?
                        A. Search for a specific manga
                        B. Sort Manga
                        C. Suggest a manga
                        D. Show Top 10s
                        E. Exit Program
                        Please Select an Option:
                        """);
                String chc2 = scn.nextLine();
                boolean stop2 = false;
                while (!stop2) {
                    switch (chc2) {
                        case "a":
                        case "A":
                            boolean stop3 = false;
                            System.out.println("===================================================================================================================================================================");
                            searchManga();
                            System.out.print("Would you like to search for another manga? (Y/N)");
                            String chc3 = scn.nextLine();
                            while (!stop3){
                                if (chc3.equals("Y") || chc3.equals("y")) {
                                    break;
                                }
                                else if (chc3.equals("N") ||chc3.equals("n")) {
                                    System.out.println("Okay, going back to the main menu!");
                                    stop2 = true;
                                    stop3 = true;
                                } else {
                                    System.out.println("That is not a valid option, please select again: Y/N");
                                    chc3 = scn.nextLine();
                                }
                            }
                            break;
                        case "b":
                            ;
                        case "B":
                            ;
                        case "c":
                            ;
                        case "C":
                            suggestManga();
                            stop = true;
                            break;
                        case "d":
                            ;
                        case "D":
                            showTop10s();
                            stop = true;
                            break;
                        case "e":
                            ;
                        case "E":
                            System.out.println("Okay, see you again!");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("===================================================================================================================================================================");
                            System.out.print("That is not a valid option, please select again: Y/N");
                            chc2 = scn.nextLine();
                    }
                }
            } else if (chc.equals("N") || chc.equals("n")) {
                System.out.println("Okay, see you again!");
                System.exit(0);
            } else {
                System.out.println("That is not a valid option, please select again: Y/N");
                chc = scn.nextLine();
            }
        }
    }

    public static void readHeader() throws IOException {
        line = br.readLine();
        header = line.split("@");
    }
    public static void displayHeader(){
        System.out.println("===================================================================================================================================================================");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s", header[0], header[1], header[2], header[3], header[4], header[5], header[6], header[7]);
        System.out.println();
    }

    public static void populateList() throws IOException {
        while ((line = br.readLine()) != null) {
            rows = line.split("@");
            mangaList.add(new Manga(rows[0].replace("\"", ""),
                    Double.parseDouble(rows[1]),
                    Integer.parseInt(rows[2]),
                    Integer.parseInt(rows[3]),
                    Integer.parseInt(rows[4]),
                    Integer.parseInt(rows[5]),
                    rows[6],
                    rows[7]));

        }
    }
    public static void displayList() {
        displayHeader();
        for (Manga manga : mangaList) {
            System.out.println(manga);
        }
    }
    public static void searchManga() {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Input manga title here: ");
        String key = scnr.nextLine().toLowerCase();
        ArrayList<Integer> mangasFound = new ArrayList<>();
        for (int i = 0; i < mangaList.size(); i++) {
            if (mangaList.get(i).getTitle().toLowerCase().contains(key)) {
                mangasFound.add(i);
                counter++;
            }
        }

        displayHeader();
        for (Integer integer : mangasFound) {
            System.out.println(mangaList.get(integer).toString());
        }
        System.out.println();
        System.out.println("Found " + counter + " manga");
        counter = 0;
    }

    public static void suggestManga() {
        Random rand = new Random();
        System.out.println(" Here are 5 manga suggestions for you:");
        displayHeader();
        HashSet<Integer> indices = new HashSet<>();

        while (indices.size() < 5 && indices.size() < mangaList.size()) {
            indices.add(rand.nextInt(mangaList.size()));
        }

        for (int index : indices) {
            System.out.println(mangaList.get(index));
        }
    }

    public static void showTop10s() {
        List<Manga> topByScore = new ArrayList<>(mangaList);
        topByScore.sort((m1, m2) -> Double.compare(m2.getScore(), m1.getScore()));

        List<Manga> topByVote = new ArrayList<>(mangaList);
        topByVote.sort((m1, m2) -> Integer.compare(m2.getVotes(), m1.getVotes()));

        List<Manga> topByPopularity = new ArrayList<>(mangaList);
        topByPopularity.sort((m1, m2) -> Integer.compare(m2.getPopularity(), m1.getPopularity()));

        List<Manga> topByMembers = new ArrayList<>(mangaList);
        topByMembers.sort((m1, m2) -> Integer.compare(m2.getMembers(), m1.getMembers()));

        List<Manga> topByFavorite = new ArrayList<>(mangaList);
        topByFavorite.sort((m1, m2) -> Integer.compare(m2.getFavorites(), m1.getFavorites()));

        System.out.println(" Top 10 Mangas by Score:");
        displayHeader();
        for (int i = 0; i < 10 && i < topByScore.size(); i++) {
            System.out.println(topByScore.get(i));
        }

        System.out.println("\n Top 10 Mangas by Vote:");
        displayHeader();
        for (int i = 0; i < 10 && i < topByVote.size(); i++) {
            System.out.println(topByVote.get(i));
        }

        System.out.println("\n Top 10 Mangas by Popularity:");
        displayHeader();
        for (int i = 0; i < 10 && i < topByPopularity.size(); i++) {
            System.out.println(topByPopularity.get(i));
        }

        System.out.println("\n Top 10 Mangas by Members:");
        displayHeader();
        for (int i = 0; i < 10 && i < topByMembers.size(); i++) {
            System.out.println(topByMembers.get(i));
        }

        System.out.println("\n Top 10 Mangas by Favorite:");
        displayHeader();
        for (int i = 0; i < 10 && i < topByFavorite.size(); i++) {
            System.out.println(topByFavorite.get(i));
        }
    }
    public static void sortManga() {
        Scanner scn = new Scanner(System.in);
        boolean validOption = false;
        while (!validOption) {
            System.out.println("""
                    How would you like to sort the manga list?
                    A. Alphabetically
                    B. By Score (Highest to Lowest)
                    C. By Popularity (Highest to Lowest)
                    D. By Amount of Members (Highest to Lowest)
                    E. By Number of Votes (Highest to Lowest)
                    F. By Number of Favorites (Highest to Lowest)
                    Please Select an Option: """);
            String sortOption = scn.nextLine();

            switch (sortOption) {
                case "a":
                case "A":
                    sortMangaAlphabetically();
                    validOption = true;
                    break;
                case "b":
                case "B":
                    sortMangaByScore();
                    validOption = true;
                    break;
                case "c":
                case "C":
                    sortMangaByPopularity();
                    validOption = true;
                    break;
                case "d":
                case "D":
                    sortMangaByMembers();
                    validOption = true;
                    break;
                case "e":
                case "E":
                    sortMangaByVotes();
                    validOption = true;
                    break;
                case "f":
                case "F":
                    sortMangaByFavorites();
                    validOption = true;
                    break;
                case "g":
                case "G":
                    displayMenu();
                    return;
                default:
                    System.out.println("Invalid option. Returning to main menu.");
            }
        }
        Main.displayList();
    }

    public static void sortMangaAlphabetically() {
        mangaList.sort(Comparator.comparing(m -> m.getTitle().toLowerCase()));
    }

    public static void sortMangaByScore() {
        mangaList.sort((m1, m2) -> Double.compare(m2.getScore(), m1.getScore()));
    }

    public static void sortMangaByPopularity() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getPopularity(), m1.getPopularity()));
    }

    public static void sortMangaByMembers() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getMembers(), m1.getMembers()));
    }

    public static void sortMangaByVotes() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getVotes(), m1.getVotes()));
    }

    public static void sortMangaByFavorites() {
        mangaList.sort((m1, m2) -> Integer.compare(m2.getFavorites(), m1.getFavorites()));
    }

}

