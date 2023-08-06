package zoeq.parroticator.standard.application.event;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The event for notify the message receiving from the receiver service to the sender service.
 */
@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class SpeakingEvent {
  byte[] fullMessage;
}
