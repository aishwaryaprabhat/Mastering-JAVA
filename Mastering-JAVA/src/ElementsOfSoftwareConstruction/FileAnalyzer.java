package ElementsOfSoftwareConstruction;

import java.io.*;
import java.util.ArrayList;

class Tester{
    public static void main(String[] args) throws IOException {

        ArrayList<FileAnalyzer> f = new ArrayList<>();
        FileAnalyzer f1 = new FileAnalyzer("C:\\Users\\Lenovo\\Google Drive\\Courses\\Term 5\\Elements of Software Construction\\Problem Sets\\Problem Set 2\\" +
                                            "You Give Love a Bad Name.txt");
        FileAnalyzer f2 = new FileAnalyzer("C:\\Users\\Lenovo\\Google Drive\\Courses\\Term 5\\Elements of Software Construction\\Problem Sets\\Problem Set 2\\" +
                                            "Get Lucky.txt");
        FileAnalyzer f3 = new FileAnalyzer("C:\\Users\\Lenovo\\Google Drive\\Courses\\Term 5\\Elements of Software Construction\\Problem Sets\\Problem Set 2\\TestFile1 - Copy (2).txt");
        f.add(f1);f.add(f2);f.add(f3);

        for(FileAnalyzer fa:f){
            System.out.println(fa.readnumberoflines());
        }

    }
}
public class FileAnalyzer{

    private File file;
    String aline;
    int count = 0;

    FileAnalyzer(String filename) throws IOException{

        file = new File(filename);

    }

    public int readnumberoflines() throws IOException {
        try {
            FileReader fr = new FileReader(this.file);
            BufferedReader br = new BufferedReader(fr);
            while((this.aline = br.readLine()) != null){
                this.count++;
            }
            br.close();



        } catch (IOException ex) {
            System.out.println("File not found. Please run again");
        }finally {
            return this.count;
        }

    }

    public void readLyrics() throws IOException {
        try {
            FileReader fr = new FileReader(this.file);
            BufferedReader br = new BufferedReader(fr);
            while ((this.aline = br.readLine()) != null) {
                System.out.println(this.aline);
            }
            br.close();
        }catch (IOException ex){
            System.out.println("File not found");
        }

    }

//    public void makeItThaiVersion() throws IOException{
//        System.out.println("***This program turns the word 'shot' to 'chep'***");
//        String lyric = "";
//        try {
//            FileReader fr = new FileReader(this.file);
//            BufferedReader br = new BufferedReader(fr);
//            while ((this.aline = br.readLine()) != null) {
//                String[] line1 = aline.split(" ");
//                for(String chep:line1){
//
//                    if(chep.equals("shot")|chep.equals("Shot")){
//                        int a =  Arrays.asList(line1).indexOf(chep);
//                        line1[a] = "CHEP";
//
//                    }else{continue;}
//
//                }
//                for(String chep:line1){
//                    lyric = lyric+" "+chep;
//                }lyric+="\n";
//
//            }
//
//            br.close();
//            System.out.println(lyric);
//
//        }catch (IOException ex){
//            System.out.println("File not found");
//        }
//
//    }




}