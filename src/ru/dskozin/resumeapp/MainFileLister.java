package ru.dskozin.resumeapp;

import java.io.File;

public class MainFileLister {

    private static StringBuilder sb = new StringBuilder("");

    public static void main(String[] args) {
        File startPoint = new File(".");
        fileRecursion(startPoint);
    }


    private static void fileRecursion(File file){
        if (file.isDirectory()){

            //отбрасываем скрытые файлы
            if (file.isHidden())
                return;

            System.out.println(sb.toString() + file.getName());
            sb.append("  ");

            File[] arr;
            if ((arr = file.listFiles()) == null)
                throw new RuntimeException();

            for (File f : arr){
                if (f != null)
                    fileRecursion(f);
            }

            sb.delete(0, 2);

        } else {
            //раскоментировать если хотим так же файлы
            //System.out.println(sb.toString() + file.getName());
        }
    }
}
