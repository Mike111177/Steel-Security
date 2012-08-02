package net.othercraft.steelsecurity.listeners;

public class RuntimeData{
    
    private static final Runtime runtime = Runtime.getRuntime();
    private static final long maxmemory = runtime.maxMemory();
    private static final int proccessors = runtime.availableProcessors();
    private static long freememory = runtime.freeMemory();
    private static long totalmemory = runtime.totalMemory();

    public static long getMaxmemory() {
	return maxmemory;
    }

    public static long getFreememory() {
	return freememory;
    }

    public static int getProccessors() {
	return proccessors;
    }
}
