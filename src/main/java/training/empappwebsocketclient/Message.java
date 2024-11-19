package training.empappwebsocketclient;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(@JsonProperty("responseText") String text) {
}
