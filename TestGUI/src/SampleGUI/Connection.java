package SampleGUI;

import java.lang.IllegalArgumentException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Connection{
    private final boolean logTraffic;
    private final Charset UTF8;
    private final Process process;
    private Socket socket;
    private final DataOutputStream sendStream;
    private final DataInputStream recvStream;
    private final ArrayList<Listener> listeners;
    private final Thread recvThread;

    public Connection(String command, int port, boolean logTraffic) throws IOException{
        UTF8 = Charset.forName("UTF-8");

        this.logTraffic = logTraffic;
//        System.out.println("cmd /c start /w cmd /c \"" + command + "\"");
        process = Runtime.getRuntime().exec("cmd /c start /w cmd /c \"" + command + "\"");

        final int TIMEOUT_SECONDS = 5;

        long start = System.currentTimeMillis();
        while (true){
            try{
                socket = new Socket("localhost", port);
            }catch (ConnectException e){
                if (System.currentTimeMillis() - start > TIMEOUT_SECONDS * 1000){
                    throw new ConnectException(
                        "Unable to connect to y-cruncher after " + TIMEOUT_SECONDS + " seconds."
                    );
                }
                continue;
            }
            break;
        }

        sendStream = new DataOutputStream(socket.getOutputStream());
        recvStream = new DataInputStream(socket.getInputStream());
        listeners = new ArrayList<Listener>();
        recvThread = new Thread(){
            public void run(){
                receiverThread();
            }
        };
        recvThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                try{
                    sendExitNow();
                }catch (IOException e){}
            }
        });
    }

    public static interface Listener{
        boolean onObject(JSONObject obj);
    }
    public void addListener(Listener listener){
        listeners.add(listener);
    }
    public void removeListener(Listener listener){
        listeners.remove(listener);
    }

    public static boolean getStringBoolean(JSONObject obj, String key){
        String value = obj.getString(key);
        switch (value){
        case "true": return true;
        case "false": return false;
        default: throw new JSONException("Invalid boolean string.");
        }
    }
    public static void putStringBoolean(JSONObject obj, String key, boolean value){
        obj.put(key, value ? "true" : "false");
    }

    public void sendExitNow() throws IOException{
        JSONObject object = new JSONObject();
        object.put("Action", "ExitNow");
        sendObject(object);
    }

    public void sendCustomComputeDefaults() throws IOException{
        //  Request default CustomCompute parameters.
        JSONObject object = new JSONObject();
        object.put("Action", "CustomCompute");
        object.put("Option", "Defaults");
        sendObject(object);
    }
    public void sendCustomComputeQuery(JSONObject data) throws IOException{
        //  Query CustomCompute parameters.
        JSONObject object = new JSONObject();
        object.put("Action", "CustomCompute");
        object.put("Option", "Query");
        object.put("CustomCompute", data);
        sendObject(object);
    }
    public void sendCustomComputeRun(JSONObject data) throws IOException{
        //  Run CustomCompute.
        JSONObject object = new JSONObject();
        object.put("Action", "CustomCompute");
        object.put("Option", "Run");
        object.put("CustomCompute", data);
        sendObject(object);
    }

    public void sendStressTestQuery(JSONObject data) throws IOException{
        //  Query StressTest parameters.
        JSONObject object = new JSONObject();
        object.put("Action", "StressTest");
        object.put("Option", "Query");
        object.put("StressTest", data);
        sendObject(object);
    }
    public void sendStressTestRun(JSONObject data) throws IOException{
        //  Run StressTest.
        JSONObject object = new JSONObject();
        object.put("Action", "StressTest");
        object.put("Option", "Run");
        object.put("StressTest", data);
        sendObject(object);
    }

    public void sendObject(JSONObject json) throws IOException{
        String str = json.toString(4);
        byte bytes[] = str.getBytes(UTF8);
        int size = bytes.length + 4;
        if (logTraffic){
            System.out.println("Sending " + size + " bytes:");
            System.out.println(str);
            System.out.println();
        }
        ByteBuffer buffer = ByteBuffer.allocate(size);
        buffer.putInt(Integer.reverseBytes(size));
        buffer.put(bytes);
        sendStream.write(buffer.array());
        sendStream.flush();
    }

    private void onObject(JSONObject json){
        for (int c = listeners.size(); c-- > 0;){
            if (listeners.get(c).onObject(json)){
                return;
            }
        }
    }


    private void receiverThread(){
        try{
            while (true){
                int size = Integer.reverseBytes(recvStream.readInt());
                if (size < 4){
                    throw new IllegalArgumentException("Server sent invalid packet.");
                }
                byte bytes[] = new byte[size - 4];
                recvStream.read(bytes);
                String str = new String(bytes, UTF8);
//                System.out.println(size);
//                System.out.println(str);
                if (logTraffic){
                    System.out.println("Received " + size + " bytes:");
                    System.out.println(str);
                    System.out.println();
                }
                onObject(new JSONObject(str));
            }
        }catch (Exception e){
            DialogBoxes.errorBox(e, true);
        }
    }
}

