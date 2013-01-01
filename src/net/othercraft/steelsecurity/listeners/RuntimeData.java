package net.othercraft.steelsecurity.listeners;

public final class RuntimeData{
    
    private static final Runtime RUNTIME = Runtime.getRuntime();
    private static final long MAXMEMORY = RUNTIME.maxMemory();
    private static final int PROCCESSORS = RUNTIME.availableProcessors();
    private static long freememory = RUNTIME.freeMemory();
    private static long totalmemory = RUNTIME.totalMemory();

    public static long getMaxmemory() {
	return MAXMEMORY;
    }

    public static long getFreememory() {
	return freememory;
    }

    public static int getProccessors() {
	return PROCCESSORS;
    }
}
