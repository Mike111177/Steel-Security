package net.othercraft.steelsecurity.listeners;
 
import net.othercraft.steelsecurity.antispam.*;
 
import org.bukkit.event.player.PlayerChatEvent;
 
public class PlayerChatListener {
private CensoredWordProccess listener;
public PlayerChatListener(CensoredWordProccess listener) {
this.listener = listener;
}
 
public void onChat(PlayerChatEvent event) {
listener.onSpeak(event);
 
}
 
}