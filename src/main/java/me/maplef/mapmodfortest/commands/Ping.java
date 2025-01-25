package me.maplef.mapmodfortest.commands;


public class Ping {
    private static final String cmdHeader = "ping";
    
    public static CommandResponse func(Integer argc, String[] argv) {
        CommandResponse rsp = new CommandResponse();
        
        rsp.text = "pong!";

        return rsp;
    }
}
