package com.tinker;

import com.tinker.wctool.core.WCService;
import com.tinker.wctool.models.Stats;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static com.tinker.wctool.constants.Constants.WORDS;
import static com.tinker.wctool.constants.Constants.CHARS;
import static com.tinker.wctool.constants.Constants.LINES;
import static com.tinker.wctool.constants.Constants.BYTES;
import static com.tinker.wctool.constants.Constants.ACCEPTED_OPERATIONS;

/**
 * Entry point to WCTool
 */
public class WCToolMain {
    private WCToolMain() {
    }

    public static void main(String[] args) {
        List<String> cliArgs = Arrays.asList(args);
        verifyOperations(cliArgs);

        try {
            BufferedReader reader = cliArgs.size() > 1 ?
                    new BufferedReader(new FileReader(cliArgs.get(1))) :
                    new BufferedReader(new InputStreamReader(System.in));
            WCService wcService = new WCService(reader);
            Stats result = wcService.computeStats();

            show(result, cliArgs);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Provided path for the file is invalid: " + fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Encountered I/O error: " + ioe.getMessage());
        }
    }

    private static void verifyOperations(List<String> args) {
        if (args.isEmpty() || !ACCEPTED_OPERATIONS.contains(args.get(0))) {
            throw new IllegalArgumentException("Provided arguments are not valid. Please verify!");
        }
    }

    private static void show(Stats result, List<String> args) {
        String operation = args.get(0);
        switch (operation) {
            case BYTES:
            case CHARS:
                System.out.println(result.getCharCount());
                break;
            case LINES:
                System.out.println(result.getLineCount());
                break;
            case WORDS:
                System.out.println(result.getWordCount());
                break;
            default:
                System.out.println(result.toString());
                break;
        }
    }

}
