package com.craftmend.openaudiomc.generic.commands.subcommands;

import com.craftmend.openaudiomc.OpenAudioMc;
import com.craftmend.openaudiomc.generic.commands.interfaces.SubCommand;
import com.craftmend.openaudiomc.generic.commands.objects.Argument;
import com.craftmend.openaudiomc.generic.networking.interfaces.NetworkingService;
import com.craftmend.openaudiomc.generic.platform.OaColor;
import com.craftmend.openaudiomc.generic.platform.Platform;
import com.craftmend.openaudiomc.generic.rest.RestRequest;
import com.craftmend.openaudiomc.generic.rest.routes.Endpoint;
import com.craftmend.openaudiomc.generic.rest.types.ClaimCodeResponse;
import com.craftmend.openaudiomc.generic.user.User;

public class LinkSubCommand extends SubCommand {

    public LinkSubCommand() {
        super("link", "login", "account", "claim");
        registerArguments(
                new Argument("", "Generates a link for you to claim your account")
        );
    }

    @Override
    public void onExecute(User sender, String[] args) {
        if (OpenAudioMc.getInstance().getInvoker().isNodeServer()) {
            message(sender, Platform.makeColor("RED") + "WARNING! This OpenAudioMc can't accept links, because it's running in node mode.");
            message(sender, Platform.makeColor("Yellow") + "If you run a proxy (Bunguard, Velocity, Waterfall, etc), then:");
            message(sender, Platform.makeColor("RED") + " - Install the plugin on your proxy, if you have one.");
            message(sender, Platform.makeColor("YELLOW") + "Or, if you don't run one or don't know what this means:");
            message(sender, Platform.makeColor("RED") + " - Enable " + Platform.makeColor("WHITE") + "force-offline-mode" + Platform.makeColor("RED") + " in the config.yml if your host doesn't support proxies.");
            sender.sendClickableCommandMessage(
                    Platform.makeColor("RED") + " - Or click here to do it automatically, but you need to restart your server after doing this.",
                    "Automatically enable force-offline-mode",
                    "oa config setkv SETTINGS_FORCE_OFFLINE_MODE true"
            );
            return;
        }

        // init connection so we receive the event
        OpenAudioMc.getService(NetworkingService.class).connectIfDown();

        message(sender, OaColor.GRAY + "Generating link...");

        RestRequest<ClaimCodeResponse> request = new RestRequest<>(ClaimCodeResponse.class, Endpoint.CLAIM_CODE);

        request.runAsync()
                .thenAccept(state -> {
                    if (state.hasError()) {
                        message(sender, OaColor.RED + state.getError().getMessage());
                        return;
                    }

                    ClaimCodeResponse response = state.getResponse();
                    String url = response.getClaimUrl();

                    message(sender, "Successfully generated a link for you to claim your account!");
                    sender.sendClickableUrlMessage(
                            OaColor.GOLD + " >> Click here to claim your account << ",
                            "Click here to claim your account",
                            url
                    );
                    sender.sendMessage(OaColor.GRAY + " >> or visit " + url);
                });

    }

}
