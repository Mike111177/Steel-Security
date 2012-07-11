package net.othercraft.steelsecurity.utils;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

public class ChooseListPrompt extends MessagePrompt {
	
        private final String text;
	private final Prompt next;
 
	public ChooseListPrompt(String prefix, Set<String> text, Prompt next) {
		this(prefix, text.iterator(), next);
 
	}
 
	protected ChooseListPrompt(String prefix, Iterator<String> text, Prompt next) {
 
		StringBuilder b = new StringBuilder(prefix);
		for (int i = 0; i < 10; i++) {
			if (text.hasNext()) {
				if (i != 0) {
					b.append(", ");
				}
				b.append(text.next());
			}
 
		}
		this.text = b.toString();
		this.next = text.hasNext() ? new ChooseListPrompt(prefix, text, next) : next;
 
	}
 
	@Override
	protected Prompt getNextPrompt(ConversationContext paramConversationContext) {
		return this.next;
	}
 
	@Override
	public String getPromptText(ConversationContext paramConversationContext) {
		return this.text;
	}
}