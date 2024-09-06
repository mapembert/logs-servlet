package com.serco.logs;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/logs-servlet")
public class LogsServlet extends HttpServlet {

    private static final String BASE_PATH = "/srv/logs-servlet";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String folder = request.getParameter("folder");
        String log = request.getParameter("log");

        if (log != null) {
            // If a specific log file is requested
            File logFile = new File(BASE_PATH, folder + File.separator + log);
            viewFileContent(logFile, response);
        } else if (folder != null) {
            // List contents of the specified folder
            File directory = new File(BASE_PATH, folder);
            listDirectoryContents(directory, response);
        } else {
            // List contents of the base path
            File directory = new File(BASE_PATH);
            listDirectoryContents(directory, response);
        }
    }

    private void listDirectoryContents(File directory, HttpServletResponse response) throws IOException {
        if (directory.exists() && directory.isDirectory()) {
            JSONArray jsonArray = listDirectoryContents(directory);
            response.setContentType("application/json");
            response.getWriter().println(jsonArray.toString(4));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Directory not found");
        }
    }

    private JSONArray listDirectoryContents(File directory) {
        JSONArray jsonArray = new JSONArray();
        File[] files = directory.listFiles();
        if (files != null) {
            Arrays.sort(files); // Optionally sort files
            for (File file : files) {
                JSONObject fileObject = new JSONObject();
                fileObject.put("name", file.getName());
                fileObject.put("type", file.isDirectory() ? "directory" : "file");
                if (file.isDirectory()) {
                    fileObject.put("contents", listDirectoryContents(file));
                } else {
                    fileObject.put("size", file.length()); // File size in bytes
                    fileObject.put("lastModified", file.lastModified()); // Last modified time in milliseconds
                }
                jsonArray.put(fileObject);
            }
        }
        return jsonArray;
    }

private void viewFileContent(File file, HttpServletResponse response) throws IOException {
    if (file.exists() && file.isFile()) {
        response.setContentType("text/plain");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
             PrintWriter writer = response.getWriter()) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(line);
            }
        }
    } else {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
    }
}

}
