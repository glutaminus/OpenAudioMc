package com.craftmend.openaudiomc.generic.events.events;

import com.craftmend.openaudiomc.api.events.BaseEvent;
import com.craftmend.openaudiomc.generic.oac.enums.CraftmendTag;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class AccountAddTagEvent extends BaseEvent {

    private CraftmendTag addedTag;

}
