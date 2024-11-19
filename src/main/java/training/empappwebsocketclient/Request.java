package training.empappwebsocketclient;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Request(@JsonProperty("requestText") String text) {
}
