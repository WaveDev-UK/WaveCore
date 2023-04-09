package dev.wave.wavecore.configuration;

import java.util.Map;

public interface Serializable {

    default Map<String, Object> serialize(){
        return Serialization.serialize(this);
    }

}
