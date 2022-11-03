package ru.leonov_m;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.IOException;
import java.util.*;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        Options options = new Options();
        options.addRequiredOption("p", "path", true, "Path to file/folder");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        File file = new File(cmd.getOptionValue("p"));
        List<String> lines = new ArrayList<>();

        if (file.isFile()) {
            lines = FileUtils.readLines(file, "UTF-8");
        } else if (file.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(file, new SuffixFileFilter("txt"), TrueFileFilter.INSTANCE);
            for (var t  : files) {
                lines = FileUtils.readLines(file, "UTF-8");
            }
        }
    }
}