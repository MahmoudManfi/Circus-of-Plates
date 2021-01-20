package eg.edu.alexu.csd.oop.game.GUI.Frames;

import eg.edu.alexu.csd.oop.game.Constants.Game;
import eg.edu.alexu.csd.oop.game.GUI.Panels.LoadingJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Loading {

    private static final File file = new File(Game.databaseDirectory+Game.separator+"Data.txt");

    public Loading(){

        long[] arr = null;
        try{
            arr= readLargestNumber();
        } catch (Exception e){
            e.getStackTrace();
        }

        JFrame frame = new JFrame(Game.TITLE);
        frame.setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);
        frame.add(new LoadingJPanel(frame,arr[0],arr[1]));
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo((Component)null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int num = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?",Game.TITLE,JOptionPane.YES_NO_OPTION);
                if (num == 0) {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });



    }

    // the first number is the score and the second number is the time.
    private static long[] readLargestNumber() throws IOException {

        File dataBase = new File(Game.databaseDirectory);
        dataBase.mkdirs();
        if (file.createNewFile()){
           make();
        }else if (file.canWrite()){
            file.delete();
            make();
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        long[] arr = new long[2];
        try {
            arr[0] = Long.parseLong(bufferedReader.readLine());
            arr[1] = Long.parseLong(bufferedReader.readLine());
        } catch (Exception e){
            file.delete();
            make();
        }

        bufferedReader.close();


        return arr;
    }

    public static boolean writeLargest(long score, long time) throws IOException {

        boolean flag = false;
        long[] arr = null;
        try{
            arr = readLargestNumber();
        } catch (Exception e){
            e.getStackTrace();
        }


        if (score > arr[0] || (score == arr[0] && time < arr[1])){
            file.setWritable(true);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(Long.toString(score));
            bufferedWriter.newLine();
            bufferedWriter.write(Long.toString(time));
            flag = true;
            bufferedWriter.close();
            file.setReadOnly();
        }

        return flag;
    }

    private static void make() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(Long.toString(0));
        bufferedWriter.newLine();
        bufferedWriter.write(Long.toString(0));
        bufferedWriter.close();
        file.setReadOnly();
    }

}
