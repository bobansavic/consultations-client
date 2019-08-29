package com.bs.consultationsclient.util;

import com.bs.consultationsclient.model.MessageDto;
import com.bs.consultationsclient.model.UserDto;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class Session {
  private UserDto activeUser;
  private Map<Long, List<MessageDto>> clientMessages;

  public Session() {
  }

  public Session(UserDto activeUser,
      Map<Long, List<MessageDto>> clientMessages) {
    this.activeUser = activeUser;
    this.clientMessages = clientMessages;
  }

  public UserDto getActiveUser() {
    return activeUser;
  }

  public void setActiveUser(UserDto activeUser) {
    this.activeUser = activeUser;
  }

  public Map<Long, List<MessageDto>> getClientMessages() {
    return clientMessages;
  }

  public void setClientMessages(
      Map<Long, List<MessageDto>> clientMessages) {
    this.clientMessages = clientMessages;
  }
}
