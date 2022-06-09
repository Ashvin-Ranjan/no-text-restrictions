package vin.ash.nores;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import vin.ash.nores.sharedstates.SharedStates;

import java.util.HashSet;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class NoResMod implements ModInitializer {
	@Override
	public void onInitialize() {
		// Initialize commands
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(literal("allowedChars")
				.then(literal("toggle").then(
						literal("section")
								.executes(context -> {
									if (!SharedStates.charactersAllowed.contains((char) 167)) {
										SharedStates.charactersAllowed.add((char) 167);
										context.getSource().sendFeedback(Text.literal("Section is now enabled to type"));
									} else {
										SharedStates.charactersAllowed.remove((char) 167);
										context.getSource().sendFeedback(Text.literal("Section is now disabled to type"));
									}
									return 0;
								})
				).then(
						literal("newline").executes(context -> {
							if (!SharedStates.charactersAllowed.contains('\n')) {
								SharedStates.charactersAllowed.add('\n');
								context.getSource().sendFeedback(Text.literal("Newline is now enabled to type"));
							} else {
								SharedStates.charactersAllowed.remove('\n');
								context.getSource().sendFeedback(Text.literal("Newline is now disabled to type"));
							}
							return 0;
						})
				).then(
						literal("all").executes(context -> {
							SharedStates.allowAll = !SharedStates.allowAll;
							if (SharedStates.allowAll) {
								context.getSource().sendFeedback(Text.literal((char) 167 + "4 [DANGER]: YOU ARE ALLOWING ALL CHARACTERS TO BE TYPED, THIS MAY HAVE UNFORESEEN CONSEQUENCES " + (char) 167 + "r"));
							} else {

								context.getSource().sendFeedback(Text.literal("All characters are not able to be typed unless other perms say so"));
							}
							return 0;
						})
				)).then(
						literal("list")
								.executes(context -> {
									String out = "";
									out += "Section: " + SharedStates.charactersAllowed.contains((char) 167) + "\n";
									out += "Newline: " + SharedStates.charactersAllowed.contains('\n') + "\n";
									out += "All Allowed: " + SharedStates.allowAll;

									context.getSource().sendFeedback(Text.literal(out));
									return 0;
								})
						)
				.then(
						literal("clear")
								.executes(context -> {
									SharedStates.charactersAllowed = new HashSet<>();
									SharedStates.allowAll = false;
									context.getSource().sendFeedback(Text.literal("Reset to base permissions"));
									return 0;
								})
				)
			);
		});
	}
}
