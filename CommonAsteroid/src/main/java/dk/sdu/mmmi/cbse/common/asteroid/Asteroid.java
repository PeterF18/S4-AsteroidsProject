package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {
    private Boolean isSplitRequired = false;

    public Boolean getIsSplitRequired() {
        return isSplitRequired;
    }

    public void setIsSplitRequired(Boolean splitRequired) {
        this.isSplitRequired = splitRequired;
    }
}
