package ru.leonov_m;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        Options options = new Options();
        options.addRequiredOption("p", "path", true, "Path to file/folder");
        options.addRequiredOption("r", "regex", true, "Regex for search");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        File file = new File(cmd.getOptionValue("p"));
        Pattern pattern = Pattern.compile(cmd.getOptionValue("r"));
        Matcher matcher = pattern.matcher("");

        if (file.isFile()) {
            lineByRegex(FileUtils.readLines(file, "UTF-8"), matcher);
        } else if (file.isDirectory()) {
            Collection<File> files = FileUtils.listFiles(file, new SuffixFileFilter("txt"), TrueFileFilter.INSTANCE);
            for (var t : files) {
                lineByRegex(FileUtils.readLines(t, "UTF-8"), matcher);
            }
        }
    }

    private static void lineByRegex(List<String> lines, Matcher matcher) {
        for (var line : lines) {
            matcher.reset(line);
            if (matcher.find()) {
                System.out.println(line);
            }
        }
    }
}