package com.az1.app.service;

import org.springframework.stereotype.Service;
import oshi.hardware.NetworkIF;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class InternetService extends ServerService {
    /**
     * Retrieves detailed information about all available network interfaces.
     *
     * @return A string containing information about the network interfaces.
     */
    public String getNetworkInterfacesInfo() {
        StringBuilder result = new StringBuilder();

        for (NetworkIF net : hardware.getNetworkIFs()) {
            result.append("Device Name: ").append(net.getName()).append("\n");
            result.append("Display Name: ").append(net.getDisplayName()).append("\n");
            result.append("Speed: ").append(net.getSpeed()).append(" bits/s\n");
            result.append("MTU: ").append(net.getMTU()).append("\n");
            result.append("MAC Address: ").append(net.getMacaddr()).append("\n");

            // IP addresses
            result.append("IPv4 Addresses: ").append(String.join(", ", net.getIPv4addr())).append("\n");
            result.append("IPv6 Addresses: ").append(String.join(", ", net.getIPv6addr())).append("\n");

            // Traffic statistics
            result.append("Packets Received: ").append(net.getPacketsRecv()).append("\n");
            result.append("Packets Sent: ").append(net.getPacketsSent()).append("\n");
            result.append("Data Received: ").append(net.getBytesRecv()).append(" bytes\n");
            result.append("Data Sent: ").append(net.getBytesSent()).append(" bytes\n");

            result.append("------------------------------\n");
        }

        return result.toString();
    }

    /**
     * Checks whether the device has an active internet connection.
     *
     * <p>First, this method checks if any network interface is currently in the UP state.
     * If at least one interface is active, it returns {@code true}.</p>
     *
     * <p>If no interface is active, the method attempts to ping 8.8.8.8 (a public DNS server by Google)
     * to verify if the device can access the internet through a physical connection.
     * The ping command is selected based on the operating system:</p>
     * - On Windows: uses the command {@code ping -n 1 8.8.8.8}
     * - On Linux/macOS: uses the command {@code ping -c 1 8.8.8.8}
     *
     * <p>Note: This function relies on the OSHI library to retrieve network interface information.</p>
     *
     * @return {@code true} if the device is connected to the internet; otherwise, {@code false}.
     */
    public boolean isInternetConnected() {
        List<NetworkIF> networkIFs = hardware.getNetworkIFs();

        // Check if any network interface is UP
        for (NetworkIF net : networkIFs) {
            if (net.getIfOperStatus() == NetworkIF.IfOperStatus.UP) {
                return true;
            }
        }

        // If no interface is UP, try pinging 8.8.8.8
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String command;

            if (os.contains("win")) {
                // Windows uses "ping -n"
                command = "ping -n 1 8.8.8.8";
            } else {
                // Linux/macOS uses "ping -c"
                command = "ping -c 1 8.8.8.8";
            }

            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();

            return exitCode == 0;

        } catch (Exception e) {
            // Catch any error during ping execution
            return false;
        }
    }

    /**
     * Checks whether the device can connect to a specific IP address.
     *
     * <p>This method performs a ping to the specified IP address to determine if the current device
     * can communicate with that IP. The ping command is adjusted according to the operating system:</p>
     * <ul>
     *   <li>On Windows: uses the command {@code ping -n 1 [ipAddress]}</li>
     *   <li>On Linux/macOS: uses the command {@code ping -c 1 [ipAddress]}</li>
     * </ul>
     *
     * <p>The method returns {@code true} if the ping succeeds (exit code = 0), and {@code false} otherwise.
     * If any exception occurs during command execution, it will be printed to the console and the method returns {@code false}.</p>
     *
     * @param ipAddress The IP address to test connectivity to (e.g., "8.8.8.8", "192.168.1.1")
     * @return {@code true} if the connection to the IP address is successful, otherwise {@code false}
     */
    public boolean isInternetConnectedToIP(String ipAddress) {
        try {
            String command;
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Windows: use "ping -n 1"
                command = "ping -n 1 " + ipAddress;
            } else {
                // Linux / macOS: use "ping -c 1"
                command = "ping -c 1 " + ipAddress;
            }

            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();

            // If exit code is 0 => ping succeeded
            return exitCode == 0;

        } catch (Exception e) {
            // Error occurred while executing ping command
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks whether a specific port on a server is open (listening).
     *
     * @param host The IP address or domain name of the server to check.
     * @param port The port number to check (value between 0 and 65535).
     * @return true if the port is open, false otherwise.
     */
    public static boolean isPortOpen(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scans a range of ports on a server and returns a list of open ports.
     *
     * @param host      The IP address or domain name of the server to scan.
     * @param startPort Starting port in the range (from 0 to 65535).
     * @param endPort   Ending port in the range (from startPort to 65535).
     * @return A list of port numbers that are open on the server.
     * @throws IllegalArgumentException if startPort or endPort is invalid
     *                                  (less than 0, greater than 65535, or startPort > endPort).
     */
    public static List<Integer> scanPorts(String host, int startPort, int endPort) {
        List<Integer> openPorts = new ArrayList<>();

        for (int port = startPort; port <= endPort; port++) {
            if (isPortOpen(host, port)) {
                openPorts.add(port);
            }
        }

        return openPorts;
    }
}

