package vin.ash.nores;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.text.LiteralText;
import vin.ash.nores.sharedstates.SharedStates;

import java.util.ArrayList;
import java.util.HashSet;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class NoResMod implements ModInitializer {
	@Override
	public void onInitialize() {
		// Initialize commands

		ClientCommandManager.DISPATCHER.register(literal("allowedChars")
				.then(literal("toggle").then(
						literal("section")
								.executes(context -> {
									if (!SharedStates.charactersAllowed.contains((char) 167)) {
										SharedStates.charactersAllowed.add((char) 167);
										context.getSource().sendFeedback(new LiteralText("Section is now enabled to type"));
									} else {
										SharedStates.charactersAllowed.remove((char) 167);
										context.getSource().sendFeedback(new LiteralText("Section is now disabled to type"));
									}
									return 0;
								})
				).then(
						literal("newline").executes(context -> {
							if (!SharedStates.charactersAllowed.contains('\n')) {
								SharedStates.charactersAllowed.add('\n');
								context.getSource().sendFeedback(new LiteralText("Newline is now enabled to type"));
							} else {
								SharedStates.charactersAllowed.remove('\n');
								context.getSource().sendFeedback(new LiteralText("Newline is now disabled to type"));
							}
							return 0;
						})
				).then(
						literal("all").executes(context -> {
							SharedStates.allowAll = !SharedStates.allowAll;
							if (SharedStates.allowAll) {
								context.getSource().sendFeedback(new LiteralText((char) 167 + "4 [DANGER]: YOU ARE ALLOWING ALL CHARACTERS TO BE TYPED, THIS MAY HAVE UNFORESEEN CONSEQUENCES " + (char) 167 + "r"));
							} else {

								context.getSource().sendFeedback(new LiteralText("All characters are not able to be typed unless other perms say so"));
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

									context.getSource().sendFeedback(new LiteralText(out));
									return 0;
								})
						)
				.then(
						literal("clear")
								.executes(context -> {
									SharedStates.charactersAllowed = new HashSet<>();
									SharedStates.allowAll = false;
									context.getSource().sendFeedback(new LiteralText("Reset to base permissions"));
									return 0;
								})
				));
	}
}
