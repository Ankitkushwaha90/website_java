import java.io.*;
import java.net.*;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        // Set the port number (8080)
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server is running on port " + port);

        while (true) {
            // Accept an incoming client connection
            Socket clientSocket = serverSocket.accept();
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String line;
            while ((line = input.readLine()) != null) {
                if (line.isEmpty()) break; // Empty line marks the end of the request
            }

            // Prepare the response header
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html");
            output.println("");  // Empty line between header and body

            // Read and serve the HTML file from the "web" directory
            File file = new File("./../web/index.html");
            if (file.exists() && file.isFile()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                String fileLine;
                while ((fileLine = fileReader.readLine()) != null) {
                    output.println(fileLine);
                }
                fileReader.close();
            } else {
                output.println("<html><body><h1>404 Not Found</h1></body></html>");
            }

            // Close the client connection
            input.close();
            output.close();
            clientSocket.close();
        }
    }
}
