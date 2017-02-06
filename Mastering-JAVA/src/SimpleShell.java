

import java.io.*;
import java.util.regex.Pattern;

public class SimpleShell {
    static String pwd = System.getProperty("user.dir");
    static String[] history  = new String[100];
    static int historycount = 0;
    static String[] command;
    public static void main(String[] args) throws java.io.IOException {


        String commandLine;
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // read what the user entered

            System.out.print("jsh>");


            commandLine = console.readLine();

            // TODO: adding a history feature

            // if the user entered a return, just loop again
            if (commandLine.equals("")) {
                continue;
            }

            if (commandLine.equals("quit")){System.exit(0);}
            else {
                historycount++;
                history[historycount]= commandLine;
                command = commandLine.split(" ");


                try {
                    ProcessBuilder pb = new ProcessBuilder(command);
                    if (commandLine.equals("pwd")) {
                        System.out.println(System.getProperty("user.dir"));

                    }
                    if (command[0].equals("cd")) {

                        try {
                            String path = System.getProperty("user.dir");

                            String pattern = Pattern.quote(System.getProperty("file.separator"));
                            String[] splitpath = path.split(pattern);
//                        ArrayList patharray = new ArrayList();
//                        for (int i=0;i<splitpath.length;i++){patharray.add(splitpath[i]);}
                            if (command[1].equals("..")) {

                                String sb = "";
                                for (int i = 0; i < splitpath.length - 1; i++) {
                                    sb = sb + splitpath[i];
//                                    System.out.println(sb);
                                }
                                pwd = sb;
                            }
                            else if (command[1].equals(".")) {
                                continue;
                            }
                            else if (command[1].equals("...")) {
                                String sb = "";
                                for (int i = 0; i < splitpath.length - 2; i++) {
                                    sb = sb + splitpath[i];
                                }
                                pwd = sb;
                            } else {
                                File dir = new File(command[1]);
                                if (dir.isDirectory() == true) {
                                    pwd = command[1];
                                } else {
                                    System.out.println("Not a command");
                                }

                            }


                            System.setProperty("user.dir", pwd);
                        } catch (Exception ex) {
                            System.out.println("Not a directory");
                        }

                    }
                    if(command[0].equals("history")){
                        String hist = "";
                        for(int i=0;i<history.length-2;i++){
                            if(history[i]!=null){
                            hist = hist + history[i] +"\n";
                            }
                        }
                        System.out.println(hist);
                    }
                    if(command[0].equals("!!")){
                        String[] var = history[historycount-1].split(" ");

                        if(var[0].equals("cd")){
                            cd(history[historycount]);
                        }
                        if(var[0].equals("pwd")){
                            System.out.println(System.getProperty("user.dir"));
                        }

                    }


                    else {
                        continue;
                    }
                    pb.start();
                } catch (IOException ex) {
                    continue;
                    }
                }
            }


            // TODO: creating the external process and executing the command in that process
            // TODO: modifying the shell to allow changing directories


    }
    static void cd(String commandLine){
        historycount++;
        history[historycount]= commandLine;
        command = commandLine.split(" ");



                try {
                    String path = System.getProperty("user.dir");

                    String pattern = Pattern.quote(System.getProperty("file.separator"));
                    String[] splitpath = path.split(pattern);
//                        ArrayList patharray = new ArrayList();
//                        for (int i=0;i<splitpath.length;i++){patharray.add(splitpath[i]);}
                    if (command[1].equals("..")) {
                        String sb = "";
                        for (int i = 0; i < splitpath.length - 1; i++) {
                            sb = sb + splitpath[i];
                        }
                        pwd = sb;
                    }
                    if (command[1].equals(".")) {
                        pwd = System.getProperty("user.dir");
                    }
                    if (command[1].equals("...")) {
                        String sb = "";
                        for (int i = 0; i < splitpath.length - 2; i++) {
                            sb = sb + splitpath[i];
                        }
                        pwd = sb;
                    } else {
                        File dir = new File(command[1]);
                        if (dir.isDirectory() == true) {
                            pwd = command[1];
                        } else {
                            System.out.println("Not a command");
                        }

                    }


                    System.setProperty("user.dir", pwd);
                } catch (Exception ex) {
                    System.out.println("Not a directory");
                }

    }


}