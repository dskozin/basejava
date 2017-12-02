package ru.dskozin.resumeapp;

import java.io.File;

public class MainFileLister {

    public static void main(String[] args) {
        File startPoint = new File(".");
        fileRecursion(startPoint);
    }


    private static void fileRecursion(File file){
        if (file.isDirectory()){

            //отбрасываем скрытые файлы
            if (file.isHidden())
                return;

            File[] arr;
            if ((arr = file.listFiles()) == null)
                throw new RuntimeException();

            for (File f : arr){
                if (f != null)
                    fileRecursion(f);
            }

        } else { //имена директорий не выводим, только файлов.
            System.out.println(file.getName());
        }
    }
}
