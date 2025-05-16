package com.az1.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The CommandUtils class provides utilities to execute system commands from a Java application.
 * This class uses ProcessBuilder to run command-line commands and returns the output as a string.
 */
public class CommandUtils {
    /**
     * Executes a system (command-line) command and returns the entire standard output (stdout).
     * If there are any errors, they will be merged into the stdout stream if supported.
     *
     * <p>Notes:</p>
     * <ul>
     *   <li>This method is blocking until the command completes.</li>
     *   <li>If elevated privileges (e.g., sudo) are required, include "sudo" in the command array.</li>
     * </ul>
     *
     * @param command Array of parameters representing the command to run. Example: "ls", "-l"
     * @return A string containing the full output of the command. Empty if there was no output or an error occurred.
     * @throws RuntimeException if an I/O error occurs or the process is unexpectedly interrupted.
     */
    public static String runCommand(String... command) {
        StringBuilder output = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true); // Gộp stderr vào stdout
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}

