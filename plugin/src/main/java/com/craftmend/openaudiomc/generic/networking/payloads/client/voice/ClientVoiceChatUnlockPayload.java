package com.craftmend.openaudiomc.generic.networking.payloads.client.voice;

import com.craftmend.openaudiomc.generic.networking.abstracts.AbstractPacketPayload;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ClientVoiceChatUnlockPayload extends AbstractPacketPayload {

    private String streamKey;
    private String streamServer;
    private int radius;
    private boolean hasModeration;

}
