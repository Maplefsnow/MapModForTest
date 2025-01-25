package me.maplef.mapmodfortest;

import java.util.HashMap;
import java.util.HashSet;

import kotlin.jvm.functions.Function2;
import me.maplef.mapmodfortest.commands.CommandResponse;

public class CommandManager {
    private static final HashSet<String> commandHeaders = new HashSet<>();
    private static final HashMap<String, Function2<Integer, String[], CommandResponse>> commands = new HashMap<>();

    public static void registerCommand(String header, Function2<Integer, String[], CommandResponse> func) {
        commandHeaders.add(header);
        commands.put(header, func);
    }

    public static CommandResponse execute(String cmdHeader, int argc, String[] argv) {
        CommandResponse rsp = new CommandResponse();

        if (!commandHeaders.contains(cmdHeader)) {
            rsp.code = 4;
            rsp.text = "no such command!";
            return rsp;
        }

        try {
            Function2<Integer,String[],CommandResponse> func = commands.get(cmdHeader);;
            rsp = func.invoke(argc, argv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rsp;
    }
}
