package com.company;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    private final Map<String, String> execTimeDict;
    private int arrLength;
    private int resultCount;

    public Main() {
        this.execTimeDict = new HashMap<>();
        this.resultCount = 0;
    }

    public static class SortClass {

        void quickSort(int[] array, int low, int high) {
            if (array.length == 0)
                return;//завершить выполнение, если длина массива равна 0

            if (low >= high)
                return;//завершить выполнение если уже нечего делить

            // выбрать опорный элемент
            int middle = low + (high - low) / 2;
            int opora = array[middle];

            // разделить на подмассивы, который больше и меньше опорного элемента
            int i = low, j = high;
            while (i <= j) {
                while (array[i] < opora) {
                    i++;
                }

                while (array[j] > opora) {
                    j--;
                }

                if (i <= j) {//меняем местами
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    i++;
                    j--;
                }
            }

            // вызов рекурсии для сортировки левой и правой части
            if (low < j)
                quickSort(array, low, j);

            if (high > i)
                quickSort(array, i, high);
        }

        void bubbleSort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (arr[j] > arr[j + 1]) {
                        // swap arr[j+1] and arr[j]
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
        }

        void cocktailSort(int[] a) {
            boolean swapped = true;
            int start = 0;
            int end = a.length;

            while (swapped) {
                swapped = false;
                for (int i = start; i < end - 1; ++i) {
                    if (a[i] > a[i + 1]) {
                        int temp = a[i];
                        a[i] = a[i + 1];
                        a[i + 1] = temp;
                        swapped = true;
                    }
                }
                if (!swapped)
                    break;
                swapped = false;
                end = end - 1;
                for (int i = end - 1; i >= start; i--) {
                    if (a[i] > a[i + 1]) {
                        int temp = a[i];
                        a[i] = a[i + 1];
                        a[i + 1] = temp;
                        swapped = true;
                    }
                }
                start = start + 1;
            }
        }
    }

    public static class MyThread extends Thread {

        private String typeSort;
        private int[] arr;


        public MyThread(String typeSort, int[] arr) {
            if (this.arr == null) {
                this.typeSort = typeSort;
                this.arr = arr;
            }
        }

        @Override
        public void run() {
            SortClass sortClass = new SortClass();
            if (this.typeSort.equals("bubbleSort")) {
                sortClass.bubbleSort(this.arr);
            }
            if (this.typeSort.equals("cocktailSort")) {
                sortClass.cocktailSort(this.arr);
            }
            if (this.typeSort.equals("quickSort")) {
                sortClass.quickSort(this.arr, 0, this.arr.length - 1);
            }
        }
    }

    public static class GenArrayThread extends Thread{
        private int i;
        private int[] arr;
        private int arrayLen;

        public GenArrayThread(){
            if (arrayLen == 0) {
                Random rd = new Random();
                this.arrayLen = rd.nextInt(1, 100);
            }
        }

        @Override
        public void run() {
            Random rd = new Random();
            this.arr = new int[this.arrayLen];
            for (int j = this.i; i < arr.length; j++) {
                arr[j] = rd.nextInt(1, 100);
            }

        }
        public int[] getArr(){
            return this.arr;
        }
    }

    public void startThreadsWithCritical(String typeSort, int[] arr, int countThreads) {
        Thread[] listThreads = new Thread[countThreads];
        long start = System.currentTimeMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String startTime = LocalTime.now().format(formatter);

        for (int i = 0; i < countThreads; i++) {
            listThreads[i] = new MyThread(typeSort, arr);
            listThreads[i].start();
        }
        for (int i = 0; i < countThreads; i++) {
            try {
                listThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        float execTime = (end - start) / 1000F;
        this.execTimeDict.put(countThreads + "_execTime_" + this.resultCount, String.valueOf(execTime));
        this.execTimeDict.put(countThreads + "_startTime_" + this.resultCount, startTime);
    }

    public void startThreads(String typeSort, int[] arr, int countThreads) {
        Thread[] listThreads = new Thread[countThreads];
        long start = System.currentTimeMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String startTime = LocalTime.now().format(formatter);
            for (int i = 0; i < countThreads; i++) {
                listThreads[i] = new MyThread(typeSort, arr);
                listThreads[i].start();
            }
            for (int i = 0; i < countThreads; i++) {
                try {
                    listThreads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        long end = System.currentTimeMillis();
        float execTime = (end - start) / 1000F;
        this.execTimeDict.put(countThreads + "_execTime_" + this.resultCount, String.valueOf(execTime));
        this.execTimeDict.put(countThreads + "_startTime_" + this.resultCount, startTime);
    }

    public Map<String, String> getExecTime() {
        return this.execTimeDict;
    }

    public void sortAndCalculateTime(String typeSort, int[] arr) {
        SortClass sortClass = new SortClass();
        long start = System.currentTimeMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String startTime = LocalTime.now().format(formatter);
        if (typeSort.equals("bubbleSort")) {
            sortClass.bubbleSort(arr);
        }
        if (typeSort.equals("cocktailSort")) {
            sortClass.cocktailSort(arr);
        }
        if (typeSort.equals("quickSort")) {
            sortClass.quickSort(arr, 0, arr.length - 1);
        }
        long end = System.currentTimeMillis();
        float execTime = (end - start) / 1000F;
        this.execTimeDict.put("1_execTime_" + this.resultCount, String.valueOf(execTime));
        this.execTimeDict.put("1_startTime_" + this.resultCount, startTime);
        this.execTimeDict.put("arrLen_" + this.resultCount, String.valueOf(this.arrLength));
    }

    int[] createArray() {
        Random rd = new Random();
        int arrayLen = rd.nextInt(1, 100000);
        int[] arr = new int[arrayLen];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextInt(1, 100);
        }
        this.arrLength = arr.length;
        return arr;
    }

    public void startWork(String typeSort, int[] arrInit) {
        int[] arrOne = Arrays.copyOf(arrInit, arrInit.length);
        int[] arrTwo = Arrays.copyOf(arrInit, arrInit.length);
        int[] arrFour = Arrays.copyOf(arrInit, arrInit.length);
        int[] arrEight = Arrays.copyOf(arrInit, arrInit.length);
        this.sortAndCalculateTime(typeSort, arrOne);
        this.startThreads(typeSort, arrTwo, 2);
        this.startThreads(typeSort, arrFour, 4);
        this.startThreads(typeSort, arrEight, 8);
    }

    public void printResults(String typeSort) {
        Formatter tableOutput = new Formatter();
        Map<String, String> execTimeDict = this.getExecTime();
        System.out.println(typeSort);
        tableOutput.format("%15s %33s %32s %18s %17s %18s %17s %18s %17s\n",
                "Размер объектов",
                "Последовательный алгоритм(начало)", "Последовательный алгоритм(время)",
                "2 процесса(начало)", "2 процесса(время)",
                "4 процесса(начало)", "4 процесса(время)",
                "8 процесса(начало)", "8 процесса(время)");
        for (int i = 0; i < this.resultCount; i++) {
            tableOutput.format("%15s %33s %32s %18s %17s %18s %17s %18s %17s\n",
                    execTimeDict.get("arrLen_" + i),
                    execTimeDict.get("1_startTime_" + i), execTimeDict.get("1_execTime_" + i),
                    execTimeDict.get("2_startTime_" + i), execTimeDict.get("2_execTime_" + i),
                    execTimeDict.get("4_startTime_" + i), execTimeDict.get("4_execTime_" + i),
                    execTimeDict.get("8_startTime_" + i), execTimeDict.get("8_execTime_" + i));
        }
        System.out.println(tableOutput);
    }

    public void setResultCount(int n) {
        this.resultCount = n;
    }

    public static void main(String[] args) {
        Main ob = new Main();
        String[] typesOfSort = {"cocktailSort", "bubbleSort", "quickSort"};
        for (String s : typesOfSort) {
            for (int j = 1; j < 3; j++) {
                int[] arrInit = ob.createArray();
                ob.startWork(s, arrInit);
                ob.setResultCount(j);
            }
            ob.printResults(s);
            ob.setResultCount(0);
        }
    }
}
