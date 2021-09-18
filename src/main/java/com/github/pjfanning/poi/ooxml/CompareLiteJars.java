package com.github.pjfanning.poi.ooxml;

import java.io.IOException;
import java.util.Enumeration;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CompareLiteJars {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalStateException("need 2 parameters - the file paths for 2 poi-ooxml-lite jars");
        }
        try (ZipFile zipFile1 = new ZipFile(args[0]);
             ZipFile zipFile2 = new ZipFile(args[1])) {
            TreeSet<String> xsbsOnlyInFile1 = new TreeSet<>();
            int xsbsCount1 = 0;
            TreeSet<String> xsbsOnlyInFile2 = new TreeSet<>();
            int xsbsCount2 = 0;
            Enumeration<? extends ZipEntry> entries1 = zipFile1.entries();
            while(entries1.hasMoreElements()) {
                ZipEntry entry = entries1.nextElement();
                String entryName = entry.getName();
                if (entryName.endsWith(".xsb")) {
                    xsbsCount1++;
                    xsbsOnlyInFile1.add(entryName);
                }
            }
            Enumeration<? extends ZipEntry> entries2 = zipFile2.entries();
            while(entries2.hasMoreElements()) {
                ZipEntry entry = entries2.nextElement();
                String entryName = entry.getName();
                if (entryName.endsWith(".xsb")) {
                    xsbsCount2++;
                    if (!xsbsOnlyInFile1.remove(entryName)) {
                        xsbsOnlyInFile2.add(entryName);
                    }
                }
            }
            System.out.println("XSBs in file 1 - count=" + xsbsCount1);
            System.out.println("XSBs in file 2 - count=" + xsbsCount2);
            System.out.println();
            System.out.println("XSBs only in file 1:");
            for (String xsb : xsbsOnlyInFile1) {
                System.out.println(xsb);
            }
            System.out.println();
            System.out.println("XSBs only in file 2:");
            for (String xsb : xsbsOnlyInFile2) {
                System.out.println(xsb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
