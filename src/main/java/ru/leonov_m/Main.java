package ru.leonov_m;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
        options.addOption("rp", "regexp", true, "Regex for filename");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        File file = new File(cmd.getOptionValue("p"));
        Pattern pattern = Pattern.compile(cmd.getOptionValue("r"));
        Matcher matcher = pattern.matcher("");

        if (file.isFile()) {
            System.out.println("Чтение файла " + file.getName());
            lineByRegex(FileUtils.readLines(file, "UTF-8"), matcher);
        } else if (file.isDirectory()) {
            Collection<File> files = cmd.hasOption("rp") ?
                FileUtils.listFiles(file, new WildcardFileFilter(cmd.getOptionValue("rp")), TrueFileFilter.INSTANCE) :
                FileUtils.listFiles(file, new SuffixFileFilter("txt"), TrueFileFilter.INSTANCE);
            int progress = 0;
            int all = files.size();
            for (var t : files) {
                progress++;
                System.out.println("Чтение файла " + progress + " из " + all);
                lineByRegex(FileUtils.readLines(t, "UTF-8"), matcher);
            }
        }
    }

    private static void lineByRegex(List<String> lines, Matcher matcher) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
        writer.write(System.lineSeparator());
        int progress = 0;
        int all = lines.size();
        for (var line : lines) {
            progress++;
            System.out.println("Чтение строки " + progress + " из " + all);
            matcher.reset(line);
            if (matcher.find()) {
                writer.write(line + System.lineSeparator());
            }
        }
        System.out.println("Файл прочитан");
    }
}