package net.leonemc.lithium.bounties;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class Bounty {

    private String target;
    private String addedBy;
    private int balance;

}
